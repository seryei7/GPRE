package com.giss.gpre.servlet.seguridad;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.AccessControlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giss.gpre.datos.DatosPersonaNivelAcceso;
import com.giss.gpre.ejb.PersonasNivelService;
import com.giss.gpre.util.LimpiarParametro;
import com.google.gson.Gson;

@WebServlet(name = "personasNivel", urlPatterns = { "/mntPersonasNivel" })
public class PersonasNivelAccesoServlet extends HttpServlet {

	private static final long serialVersionUID = 2217548888270629877L;
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

	// @EJB
	// private transient AANService aanService;

	@Override
	public void init() throws ServletException {
		ServletContext contexto = getServletContext();
		if (contexto.getAttribute("activeSessions") == null) {
			contexto.setAttribute("activeSessions", new ConcurrentHashMap<String, HttpSession>());
		}

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		// Initialize ESAPI request and response
		ESAPI.httpUtilities().setCurrentHTTP(req, resp);

		boolean nodos = true;
		String mensaje;
		HttpSession session = ESAPI.currentRequest().getSession(false);
		String entidad = (String) session.getAttribute("entGesEp");
		String provincia = (String) session.getAttribute("provincia");
		String centro = (String) session.getAttribute("cenGesEp");
		BigDecimal entidad2 = new BigDecimal(entidad);
		BigDecimal provincia2 = new BigDecimal(provincia);
		BigDecimal centro2 = new BigDecimal(centro);
		String idDni = (String) session.getAttribute("tDni");
		
		try {

			
			Context ctx = new InitialContext();
			PersonasNivelService personasService = (PersonasNivelService) ctx.lookup("java:global/GPRE/gestionPresenciaEJB/PersonasNivelServiceBean");
			
			List<DatosPersonaNivelAcceso> listaPersonas = personasService.GetNivelVistaPersona(idDni, entidad2, provincia2, centro2);
			
			String usuariosJson = new Gson().toJson(listaPersonas);
			
			req.setAttribute("listaPersonas", usuariosJson);
			
			String csrftokenC = LimpiarParametro.getOwaspCsrfTokenGet(req, "/gestionPresencia/jsp/PersonasNivelAcceso.jsp");
			doForward(req, resp, "/jsp/PersonasNivelAcceso.jsp?" + csrftokenC);

		} catch (Exception e) {
			mensaje = "Error al procesar la solicitud {" + e + "}";
			LOGGER.debug(mensaje);
			req.setAttribute("javax.servlet.jsp.jspException", e);
			// forward the control to your jsp error page
			doForward(req, resp, "/jsp/error.jsp");
		}
	}

	/**
	 * Forward the request
	 *
	 * @param request
	 *            - HTTP request
	 * @param response
	 *            - HTTP response
	 * @param nextPage
	 *            - the next page to forward too
	 */
	private final void doForward(HttpServletRequest request, HttpServletResponse response, String nextPage) {
		try {
			LimpiarParametro.safeSendForward(request, response, nextPage);
		} catch (IOException | ServletException | AccessControlException e) {
			LOGGER.debug("Error al procesar la solicitud {" + e + "}");
		}
	}
}
