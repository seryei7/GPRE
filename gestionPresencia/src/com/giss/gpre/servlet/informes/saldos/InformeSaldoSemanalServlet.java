package com.giss.gpre.servlet.informes.saldos;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import com.giss.gpre.datos.DatosGeneraListado;
import com.giss.gpre.datos.SeleccionSaldoSemanal;
import com.giss.gpre.ejb.ListadoService;
import com.giss.gpre.ejb.PersonaBasicoService;
import com.giss.gpre.entidades.CentrosUnidadesSel;
import com.giss.gpre.entidades.EmpresasSel;
import com.giss.gpre.entidades.TiposPersonalSel;
import com.giss.gpre.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet(name = "informeSaldoSemanal", urlPatterns = { "/InformeSaldoSemanal" })
public class InformeSaldoSemanalServlet extends HttpServlet {

	private static final long serialVersionUID = -1151752861830318354L;
	
	// Gson configurado para escapar HTML y caracteres especiales
	private static final Gson GSON_SAFE = new GsonBuilder()
			.disableHtmlEscaping() // Desactivamos escape HTML porque lo haremos manualmente
			.serializeNulls()
			.create();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ESAPI.httpUtilities().setCurrentHTTP(req, resp);

		boolean finalizar = false;
		String salidaJson = null;
		int statusCode = HttpServletResponse.SC_OK;

		HttpSession session = ESAPI.currentRequest().getSession(false);
		if (session == null || session.getAttribute("tDni") == null) {
			statusCode = HttpServletResponse.SC_UNAUTHORIZED;
			salidaJson = crearJsonError("Sesión expirada");
			finalizar = true;
		}

		StringBuilder sb = new StringBuilder();
		if (!finalizar) {
			try (BufferedReader reader = req.getReader()) {
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}

			String dniSesion = (String) session.getAttribute("tDni");

			try {
				Context ctx = new InitialContext();
				PersonaBasicoService personaBasicoService = (PersonaBasicoService) ctx
						.lookup("java:global/GPRE/gestionPresenciaEJB/PersonaBasicoServiceBean");
				ListadoService listadoService = (ListadoService) ctx
						.lookup("java:global/GPRE/gestionPresenciaEJB/ListadoServiceBean");

				String json = sb.toString();
				SeleccionSaldoSemanal seleccionSaldoSemanal = Utils.fromJsonSafe(json, SeleccionSaldoSemanal.class);

				if (seleccionSaldoSemanal == null) {
					statusCode = HttpServletResponse.SC_BAD_REQUEST;
					salidaJson = crearJsonError("Datos inválidos");
					finalizar = true;
				} else {
					BigDecimal codigoEntidad = Utils.toBigDecimal(seleccionSaldoSemanal.getCodigoEntidad());
					BigDecimal codigoProvincia = Utils.toBigDecimal(seleccionSaldoSemanal.getCodigoProvincia());
					BigDecimal codigoCgpe = Utils.toBigDecimal(seleccionSaldoSemanal.getCodigoCgpe());

					// Elimina e inserta datos en BD
					personaBasicoService.deleteCentrosByUsuario(dniSesion);
					personaBasicoService.deleteEmpresasByUsuario(dniSesion);
					personaBasicoService.deleteTiposByUsuario(dniSesion);

					String fecha = Utils.obtenerFechaActual();
					String hora = Utils.obtenerHoraActual();
					String fechaDesde = Utils.convertirFecha(seleccionSaldoSemanal.getFechaDesde());
					String fechaHasta = Utils.convertirFecha(seleccionSaldoSemanal.getFechaHasta());

					if (seleccionSaldoSemanal.getUnidades() != null && !seleccionSaldoSemanal.getUnidades().isEmpty()) {
						for (String unidad : seleccionSaldoSemanal.getUnidades()) {
							String[] partes = unidad.split("\\s+");
							personaBasicoService.saveCentrosUnidadesSel(new CentrosUnidadesSel(dniSesion, codigoEntidad,
									codigoProvincia, codigoCgpe, partes[0], partes[1], fecha, hora));
						}
					}

					if (seleccionSaldoSemanal.getTipoPersonal() != null
							&& !seleccionSaldoSemanal.getTipoPersonal().isEmpty()) {
						for (String tipo : seleccionSaldoSemanal.getTipoPersonal()) {
							personaBasicoService.saveTiposPersonalSel(new TiposPersonalSel(dniSesion, codigoEntidad,
									codigoProvincia, codigoCgpe, Utils.toBigDecimal(tipo), fecha, hora));
						}
					}

					if (seleccionSaldoSemanal.getEmpresas() != null && !seleccionSaldoSemanal.getEmpresas().isEmpty()) {
						for (String empresa : seleccionSaldoSemanal.getEmpresas()) {
							personaBasicoService.saveEmpresasSel(new EmpresasSel(dniSesion, codigoEntidad,
									codigoProvincia, codigoCgpe, empresa, fecha, hora));
						}
					}

					DatosGeneraListado resultado = listadoService.generarListado("SALDOSEMANAL", fechaDesde, fechaHasta,
							dniSesion, null, null, null, null, null, null, null, null, null, null, null, null);

					Map<String, String> respuesta = new HashMap<>();
					respuesta.put("numRegistros", resultado.getNumRegistros() != null
							? sanitizeJsonValue(resultado.getNumRegistros().toString()) : "0");
					respuesta.put("codigoError", sanitizeJsonValue(resultado.getCodigoError()));
					respuesta.put("descripcionError", sanitizeJsonValue(resultado.getDescripcionError()));

					salidaJson = GSON_SAFE.toJson(respuesta);
				}
			} catch (IllegalArgumentException e) {
				statusCode = HttpServletResponse.SC_BAD_REQUEST;
				salidaJson = crearJsonError("Datos inválidos: " + sanitizeJsonValue(e.getCause().toString()));
			} catch (NamingException e) {
				statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
				salidaJson = crearJsonError("Error al acceder al servicio");
			}
		}

		// Salida centralizada
		resp.setStatus(statusCode);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		if (salidaJson != null) {
			resp.getWriter().write(salidaJson);
		}
	}

	private String crearJsonError(String mensaje) {
		return "{\"error\":\"" + sanitizeJsonValue(mensaje) + "\"}";
	}
	
	/**
	 * CORRECCIÓN KIUWAN: Método seguro para sanitizar valores JSON
	 * Previene JSON Injection eliminando caracteres peligrosos
	 */
	private String sanitizeJsonValue(String value) {
		String resultado = null;
		if (value != null) {
			// Eliminar caracteres de control y peligrosos para JSON
			String sanitized = value
					.replaceAll("[\\x00-\\x1F\\x7F]", "") // Caracteres de control
					.replace("\\", "\\\\")  // Escapar backslash
					.replace("\"", "\\\"")  // Escapar comillas
					.replace("\n", "\\n")   // Escapar saltos de línea
					.replace("\r", "\\r")   // Escapar retorno de carro
					.replace("\t", "\\t");  // Escapar tabulaciones
			
			// Limitar longitud para prevenir DoS
			if (sanitized.length() > 1000) {
				sanitized = sanitized.substring(0, 1000);
			}
			resultado = sanitized;
		}		
		
		return resultado;
	}
	
}
