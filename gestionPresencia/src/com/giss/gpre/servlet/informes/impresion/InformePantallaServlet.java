package com.giss.gpre.servlet.informes.impresion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giss.gpre.ejb.InformeSaldoMesService;
import com.giss.gpre.ejb.ListadoService;
import com.giss.gpre.entidades.GpreListados;
import com.giss.gpre.entidades.InformeSaldoMes;
import com.giss.gpre.util.GenerarInformes;
import com.giss.gpre.util.TipoInforme;
import com.giss.gpre.util.Utils;
import com.giss.gpre.util.VariablesGlobales;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet(name = "informePantalla", urlPatterns = { "/InformePantalla" })
public class InformePantallaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ESAPI.httpUtilities().setCurrentHTTP(req, resp);
		HttpSession session = req.getSession(true);
		String dniSesion = (String) session.getAttribute("tDni");
		String salidaJson = null;

		boolean procesar = true;
		String idListado = null;
		String tipo = null;
		GpreListados informe = null;
		Context ctx = null;
		ListadoService listadoService = null;

		// Leer y parsear JSON
		try (BufferedReader reader = req.getReader()) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null)
				sb.append(line);
			JsonObject jsonObject = JsonParser.parseString(sb.toString()).getAsJsonObject();
			idListado = jsonObject.get("idListado").getAsString();
			tipo = jsonObject.get("tipoImpresion").getAsString();
		} catch (Exception e) {
			enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "JSON inválido o campo 'idListado' ausente");
			procesar = false;
		}

		// Validaciones iniciales
		if (procesar && (idListado == null || tipo == null || idListado.trim().isEmpty() || tipo.trim().isEmpty())) {
			enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID de listado y tipo requerido");
			procesar = false;
		}

		// Cargar servicio e informe
		if (procesar) {
			try {
				ctx = new InitialContext();
				listadoService = (ListadoService) ctx.lookup("java:global/GPRE/gestionPresenciaEJB/ListadoServiceBean");
				informe = listadoService.findById(new BigDecimal(idListado));

				if (informe == null) {
					enviarError(resp, HttpServletResponse.SC_FORBIDDEN,
							"No tiene permisos para visualizar este informe");
					procesar = false;
				} else if (informe.gettFinal() == null) {
					enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "El informe no está disponible");
					procesar = false;
				}
			} catch (Exception e) {
				enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar el informe");
				procesar = false;
			}
		}

		// Procesar según tipo
		if (procesar) {
			try {
				if (tipo.equalsIgnoreCase("excel")) {
					procesarDescarga(resp, obtenerInputStreamFichero(informe, ctx, dniSesion, VariablesGlobales.excel),
							TipoInforme.NOMBRE_INFORME_SALDO_MES + ".xls",
							"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				} else if (tipo.equalsIgnoreCase("pdf")) {
					procesarDescarga(resp, obtenerInputStreamFichero(informe, ctx, dniSesion, VariablesGlobales.pdf),
							TipoInforme.NOMBRE_INFORME_SALDO_MES + ".pdf", "application/pdf");
				} else {
					String urlPdf = obtenerUrlFichero(informe, ctx, dniSesion, VariablesGlobales.pdf);
					if (urlPdf == null || urlPdf.trim().isEmpty()) {
						enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener el PDF");
					} else {
						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						salidaJson = String.format("{\"success\": true, \"url\": \"%s\"}", Utils.escapeJson(urlPdf));
						resp.getWriter().write(salidaJson);
						
					}
				}
				marcarComoImpreso(informe, listadoService);
			} catch (Exception e) {
				enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la solicitud");
			}
		}
	}

	// Métodos auxiliares
	private void enviarError(HttpServletResponse resp, int status, String mensaje) throws IOException {
		resp.sendError(status, mensaje);
	}

	private void procesarDescarga(HttpServletResponse resp, InputStream stream, String nombreArchivo,
			String contentType) throws IOException {
		if (stream == null) {
			enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener el archivo");
			return;
		}
		String nombreSeguro = nombreArchivo.replaceAll("[^a-zA-Z0-9._-]", "_");
		resp.setContentType(contentType);
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + nombreSeguro);
		try (InputStream is = stream; OutputStream os = resp.getOutputStream()) {
			byte[] buffer = new byte[8192];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1)
				os.write(buffer, 0, bytesRead);
			os.flush();
		}
	}

	/**
	 * Marca el informe como impreso actualizando el campo T_IMPRESION
	 * 
	 * @param informe
	 *            El informe a marcar como impreso
	 * @param listadoService
	 *            Servicio para actualizar el informe
	 */
	private void marcarComoImpreso(GpreListados informe, ListadoService listadoService) {
		try {
			informe.setImpreso("S");
			listadoService.saveListado(informe);
			LOGGER.info("Informe {} marcado como impreso", informe.getIdListado());
		} catch (Exception e) {
			LOGGER.error("Error al marcar informe como impreso: {}", e.getCause(), e);
			// No lanzamos excepción porque el informe ya se descargó/visualizó
			// correctamente
		}
	}

	/**
	 * Obtiene la URL del fichero del informe
	 * 
	 * @param informe
	 *            El informe del que obtener el fichero
	 * @return URL del fichero
	 */
	private String obtenerUrlFichero(GpreListados informe, Context ctx, String dni, String tipo) {
		String url = null;
		try {
			if (informe.getListado().equalsIgnoreCase("SALDOMENFUN")) {
				InformeSaldoMesService informeMesService = (InformeSaldoMesService) ctx
						.lookup("java:global/GPRE/gestionPresenciaEJB/InformeSaldoMesServiceBean");
				List<InformeSaldoMes> listadoSaldoMes = informeMesService.informeByUsuario(dni);
				String nombreInforme = tipo != null && tipo.equals("pdf") ? TipoInforme.INFORME_SALDO_MES_PDF
						: TipoInforme.INFORME_SALDO_MES_EXCEL;
				url = GenerarInformes.generarUrlFichero(listadoSaldoMes, dni, nombreInforme, tipo);
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener URL del fichero: {}", e.getCause(), e);
		}

		return url;
	}

	/**
	 * Obtiene el InputStream del fichero del informe
	 * 
	 * @param informe
	 *            El informe del que obtener el fichero
	 * @return InputStream del fichero
	 */
	private InputStream obtenerInputStreamFichero(GpreListados informe, Context ctx, String dni, String tipo) {
		InputStream is = null;
		try {
			if (informe.getListado().equalsIgnoreCase("SALDOMENFUN")) {
				InformeSaldoMesService informeMesService = (InformeSaldoMesService) ctx
						.lookup("java:global/GPRE/gestionPresenciaEJB/InformeSaldoMesServiceBean");
				List<InformeSaldoMes> listadoSaldoMes = informeMesService.informeByUsuario(dni);
				String nombreInforme = tipo != null && tipo.equals("pdf") ? TipoInforme.INFORME_SALDO_MES_PDF
						: TipoInforme.INFORME_SALDO_MES_EXCEL;
				is = GenerarInformes.generarInputStreamFichero(listadoSaldoMes, dni, nombreInforme, tipo);
			}
		} catch (Exception e) {
			LOGGER.error("Error al obtener InputStream del fichero: {}", e.getCause(), e);
		}

		return is;
	}
}
