package com.giss.gpre.servlet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
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

import com.giss.gpre.datos.DatosArea;
import com.giss.gpre.ejb.AANService;
import com.giss.gpre.entidades.Persona;
import com.giss.gpre.util.GpreException;
import com.giss.gpre.util.LimpiarParametro;
import com.giss.gpre.util.VariablesGlobales;

@WebServlet(name = "inicio", urlPatterns = { "/eleccionArea" })
public class EleccionAreasTrabajoServlet extends HttpServlet {

	private static final long serialVersionUID = 2217548888270629877L;
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");
	private static final ConcurrentHashMap<String, HttpSession> activeSessions = new ConcurrentHashMap<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		ESAPI.httpUtilities().setCurrentHTTP(req, resp);
		HttpSession session = ESAPI.currentRequest().getSession(false);

		boolean nodos = false;
		String mensaje;
		String usuario = (String) session.getAttribute("tDni");
		
		try {
			List<DatosArea> listaAccesoNivel;
			Persona persona = new Persona();
			Context ctx = new InitialContext();
			AANService aanService = (AANService) ctx.lookup("java:global/GPRE/gestionPresenciaEJB/AANServiceBean");

			int userValidacion = (int) aanService.ValidarUsuario(usuario);
			if (userValidacion == 22001) {
				throw new GpreException("El usuario no esta dado de alta en Acceso");
			} else if (userValidacion == 22003) {
				throw new GpreException("El usuario no ha sido autorizado en la Aplicación");
			} else if (userValidacion == 0) {
				persona = aanService.personaByDNI(usuario);
			}
			
			//Lista de Areas de trabajo del usuario
			listaAccesoNivel = aanService.areasDeTrabajoNivel(usuario);
			if (listaAccesoNivel == null || listaAccesoNivel.isEmpty()) {
				throw new GpreException("Persona sin Areas de trabajo. Consultar con su administrador.");
			}
			
			req.setAttribute("lista", listaAccesoNivel);
			req.setAttribute("persona", persona);
			req.setAttribute("nodos", nodos);

			if(VariablesGlobales.getVersion() != null) {
				session.setAttribute("version", VariablesGlobales.getVersion());
			}
			
			String csrftokenC = LimpiarParametro.getOwaspCsrfTokenGet(req, "/gestionPresencia/index.jsp");
			doForward(req, resp, "/index.jsp?" + csrftokenC);
//			GpreException | NamingException | 
		} catch (Throwable e) {
			mensaje = "Error al procesar la solicitud {" + e + "}";
			LOGGER.debug(mensaje);
			req.setAttribute("javax.servlet.jsp.jspException", e);
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
