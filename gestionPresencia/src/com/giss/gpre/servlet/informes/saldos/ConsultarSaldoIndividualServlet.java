package com.giss.gpre.servlet.informes.saldos;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giss.gpre.datos.DatosPersonaBasico;
import com.giss.gpre.datos.DatosPersonaNivelAcceso;
import com.giss.gpre.ejb.PersonaBasicoService;
import com.giss.gpre.ejb.PersonasNivelService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet(name = "consultarSaldoIndividual", urlPatterns = { "/ConsultarSaldoInd" })
public class ConsultarSaldoIndividualServlet extends HttpServlet {

	private static final long serialVersionUID = -4650028833558982602L;
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ESAPI.httpUtilities().setCurrentHTTP(req, resp);
		Context ctx;
		boolean finalizar = false;
		String salidaJson = null;

		HttpSession session = ESAPI.currentRequest().getSession(false);
		if (session == null || session.getAttribute("tDni") == null) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resp.setContentType("application/json");
			salidaJson = "{\"error\":\"Sesión expirada\"}";
			finalizar = true;
		}

		// Leer JSON del body
		if (!finalizar) {
			StringBuilder sb = new StringBuilder();
			try (BufferedReader reader = req.getReader()) {
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}

			String requestBody = sb.toString();
			String dni;
			try {
				JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
				dni = String.format("%10s", jsonObject.get("dni").getAsString()).replace(' ', '0');

				ctx = new InitialContext();
				PersonaBasicoService personaBasicoService = (PersonaBasicoService) ctx
						.lookup("java:global/GPRE/gestionPresenciaEJB/PersonaBasicoServiceBean");
				PersonasNivelService personasService = (PersonasNivelService) ctx
						.lookup("java:global/GPRE/gestionPresenciaEJB/PersonasNivelServiceBean");

				BigDecimal entidad = new BigDecimal((String) session.getAttribute("entGesEp"));
				BigDecimal provincia = new BigDecimal((String) session.getAttribute("provincia"));
				BigDecimal centro = new BigDecimal((String) session.getAttribute("cenGesEp"));
				String dniSesion = (String) session.getAttribute("tDni");

				List<DatosPersonaNivelAcceso> listaPersonas = personasService.GetNivelVistaPersona(dniSesion, entidad,
						provincia, centro);
				boolean existe = listaPersonas.stream().anyMatch(p -> p.getIddni().equalsIgnoreCase(dni))
						|| dni.equalsIgnoreCase(dniSesion);

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");

				if (existe) {
					List<DatosPersonaBasico> datos = personaBasicoService.obtenerDatosBasicos(dni);
					if (!datos.isEmpty()) {
						DatosPersonaBasico p = datos.get(0);
						salidaJson = String.format(
								"{" + "\"nombre\":\"%s\"," + "\"empresa\":\"%s\"," + "\"contrato\":\"%s\","
										+ "\"categoria\":\"%s\"," + "\"proyecto\":\"%s\"," + "\"centro\":\"%s\","
										+ "\"unidad\":\"%s\"," + "\"entiGesEp\":\"%s\"," + "\"provEp\":\"%s\","
										+ "\"cenGesEp\":\"%s\"," + "\"idTipoPersonal\":\"%s\"," + "\"idCentro\":\"%s\","
										+ "\"idUnidad\":\"%s\"," + "\"idCifNif\":\"%s\"," + "\"existe\":\"%s\"}",
								safeJson(p.getApellido1() + " " + p.getApellido2() + ", " + p.getNombre()),
								safeJson(p.getNombreEmpresa()), safeJson(p.getDsContrato()), safeJson(p.getDsCodDen()),
								safeJson(p.getDsProyecto()), safeJson(p.getDsCentro()), safeJson(p.getDsUnidad()),
								p.getEntiGesEp(), p.getProvEp(), p.getCenGesEp(), p.getIdTipoPersonal(),
								safeJson(p.getIdCentro()), safeJson(p.getIdUnidad()), safeJson(p.getIdFiscal()),
								existe);
					} else {
						salidaJson = String.format("{\"existe\":\"%s\"}", existe);
					}
				} else {
					salidaJson = String.format("{\"existe\":\"%s\"}", existe);
				}

			} catch (Exception e) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resp.setContentType("application/json");
				salidaJson = "{\"error\":\"JSON inválido o campo 'dni' ausente\"}";
				LOGGER.debug("Error al procesar la solicitud {" + e + "}");
			}
		}

		// Salida centralizada
		if (salidaJson != null) {
			resp.getWriter().write(salidaJson);
		}
	}

	private String safeJson(String value) {
		String resultado = null;
		if (value == null) {
			resultado = "";
		} else {
			resultado = value.replace("\"", "\\\"").replace("\n", "").replace("\r", "");
		}
		return resultado;
	}
	
}
