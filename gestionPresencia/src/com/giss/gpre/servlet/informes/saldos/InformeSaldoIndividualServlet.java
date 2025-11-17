package com.giss.gpre.servlet.informes.saldos;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

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
import com.giss.gpre.ejb.ListadoService;
import com.giss.gpre.ejb.PersonaBasicoService;
import com.giss.gpre.entidades.CentrosUnidadesSel;
import com.giss.gpre.entidades.EmpresasSel;
import com.giss.gpre.entidades.TiposPersonalSel;
import com.giss.gpre.util.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet(name = "informeSaldoInd", urlPatterns = { "/InformeSaldoInd" })
public class InformeSaldoIndividualServlet extends HttpServlet {

	private static final long serialVersionUID = -1151752861830318354L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ESAPI.httpUtilities().setCurrentHTTP(req, resp);

		boolean finalizar = false;
		String salidaJson = null;
		int statusCode = HttpServletResponse.SC_OK;

		HttpSession session = ESAPI.currentRequest().getSession(false);
		if (session == null || session.getAttribute("tDni") == null) {
			statusCode = HttpServletResponse.SC_UNAUTHORIZED;
			salidaJson = "{\"error\":\"Sesión expirada\"}";
			finalizar = true;
		}

		String requestBody = null;
		JsonObject json = null;
		String dniSesion = null;

		if (!finalizar) {
			// Leer JSON del body
			StringBuilder sb = new StringBuilder();
			try (BufferedReader reader = req.getReader()) {
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}
			requestBody = sb.toString();
			dniSesion = (String) session.getAttribute("tDni");

			try {
				json = JsonParser.parseString(requestBody).getAsJsonObject();
			} catch (Exception e) {
				statusCode = HttpServletResponse.SC_BAD_REQUEST;
				salidaJson = "{\"error\":\"JSON inválido\"}";
				finalizar = true;
			}
		}

		if (!finalizar) {
			// Extraer parámetros del JSON
			String dni = json.has("dni") ? json.get("dni").getAsString() : null;
			dni = String.format("%10s", dni).replace(' ', '0');
			String fechaDesde = Utils
					.convertirFecha(json.has("fechaDesde") ? json.get("fechaDesde").getAsString() : null);
			String fechaHasta = Utils
					.convertirFecha(json.has("fechaHasta") ? json.get("fechaHasta").getAsString() : null);
			String idCentro = json.has("idCentro") ? json.get("idCentro").getAsString() : null;
			String idUnidad = json.has("idUnidad") ? json.get("idUnidad").getAsString() : null;
			String idCifNif = json.has("idCifNif") ? json.get("idCifNif").getAsString() : null;
			BigDecimal entiGesEp = json.has("entiGesEp") ? json.get("entiGesEp").getAsBigDecimal() : null;
			BigDecimal provEp = json.has("provEp") ? json.get("provEp").getAsBigDecimal() : null;
			BigDecimal cenGesEp = json.has("cenGesEp") ? json.get("cenGesEp").getAsBigDecimal() : null;
			BigDecimal idTipoPersonal = json.has("idTipoPersonal") ? json.get("idTipoPersonal").getAsBigDecimal()
					: null;

			try {
				Context ctx = new InitialContext();
				PersonaBasicoService personaBasicoService = (PersonaBasicoService) ctx
						.lookup("java:global/GPRE/gestionPresenciaEJB/PersonaBasicoServiceBean");
				ListadoService listadoService = (ListadoService) ctx
						.lookup("java:global/GPRE/gestionPresenciaEJB/ListadoServiceBean");

				// Elimina e inserta datos en BD
				personaBasicoService.deleteCentrosByUsuario(dni);
				personaBasicoService.deleteEmpresasByUsuario(dni);
				personaBasicoService.deleteTiposByUsuario(dni);

				String fecha = Utils.obtenerFechaActual();
				String hora = Utils.obtenerHoraActual();
				personaBasicoService.saveTiposPersonalSel(
						new TiposPersonalSel(dni, entiGesEp, provEp, cenGesEp, idTipoPersonal, fecha, hora));
				personaBasicoService.saveCentrosUnidadesSel(
						new CentrosUnidadesSel(dni, entiGesEp, provEp, cenGesEp, idCentro, idUnidad, fecha, hora));
				personaBasicoService
						.saveEmpresasSel(new EmpresasSel(dni, entiGesEp, provEp, cenGesEp, idCifNif, fecha, hora));

				// Llamada al procedimiento PL/SQL
				DatosGeneraListado resultado = listadoService.generarListado("SALDOMENFUN", fechaDesde, fechaHasta,
						dniSesion, "sDiario", dni, null, null, null, null, null, null, null, null, null, null);

				JsonObject jsonResp = new JsonObject();
				jsonResp.addProperty("numRegistros",
						resultado.getNumRegistros() != null ? resultado.getNumRegistros().toString() : "0");
				jsonResp.addProperty("codigoError",
						resultado.getCodigoError() != null ? Utils.escapeJson(resultado.getCodigoError()) : null);
				jsonResp.addProperty("descripcionError", resultado.getDescripcionError() != null
						? Utils.escapeJson(resultado.getDescripcionError()) : null);

				salidaJson = jsonResp.toString();

			} catch (NamingException e) {
				statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
				salidaJson = "{\"error\":\"Error al acceder al servicio\"}";
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

}
