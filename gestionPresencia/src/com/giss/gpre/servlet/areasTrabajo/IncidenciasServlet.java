package com.giss.gpre.servlet.areasTrabajo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

import com.giss.gpre.ejb.IncidenciasService;
import com.giss.gpre.entidades.Incidencias;
import com.giss.gpre.util.GenerarInformes;
import com.giss.gpre.util.LimpiarParametro;
import com.google.gson.Gson;

@WebServlet(name = "incidencia", urlPatterns = { "/mntIncidencias" })
public class IncidenciasServlet extends HttpServlet {

	private static final long serialVersionUID = 2217548888270629877L;
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");
	private static final String INFORME = "GIDEGPREGIINC";

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
		String mensaje;
		HttpSession session = ESAPI.currentRequest().getSession(false);

		try {

			Context ctx = new InitialContext();
			IncidenciasService incidenciasService = (IncidenciasService) ctx.lookup("java:global/GPRE/gestionPresenciaEJB/IncidenciasServiceBean");
			
			// DNI del usuario de la sesión
			String dniSesion = (String) session.getAttribute("tDni");
			
			List<Incidencias> listIncidencias = incidenciasService.allIncidencias();
			
			String incidenciasJson = new Gson().toJson(listIncidencias);
			
			req.setAttribute("listaIncidencias", incidenciasJson);
			
            req.setAttribute("pdf", GenerarInformes.generarUrlFichero(listIncidencias, dniSesion, INFORME, "pdf"));
			
			String csrftokenC = LimpiarParametro.getOwaspCsrfTokenGet(req, "/gestionPresencia/jsp/Incidencias.jsp");
			doForward(req, resp, "/jsp/Incidencias.jsp?" + csrftokenC);

		} catch (NamingException e) {
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
