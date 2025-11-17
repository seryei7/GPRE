package com.giss.gpre.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

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

import com.giss.gpre.util.LimpiarParametro;
import com.giss.gpre.util.VariablesGlobales;

@WebServlet(name = "login", urlPatterns = { "/acceso" })
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 2217548888270629877L;
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

	@Override
	public void init() throws ServletException {
		
		Enumeration<URL> en;
		try {
			en = Thread.currentThread().getContextClassLoader().getResources("META-INF/MANIFEST.MF");
			while (en.hasMoreElements()) {
				URL u = en.nextElement();
				try (InputStream is = u.openStream()) {
					Manifest mf = new Manifest(is);
					Attributes a = mf.getMainAttributes();
					VariablesGlobales.setVersion(a.getValue("Implementation-Version"));
				}
			}
		} catch (IOException e) {                       
			String mensaje = "Error al procesar la solicitud {" + e + "}";
			LOGGER.debug(mensaje);
		}

		LOGGER.info("inicio GPRE - " + VariablesGlobales.getVersion());

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		// Initialize ESAPI request and response
		ESAPI.httpUtilities().setCurrentHTTP(req, resp);
		
		// Creación de la session
		HttpSession session = ESAPI.currentRequest().getSession(true);
		
		String mensaje;
		String usuario = req.getParameter("tDni");
		
		ESAPI.currentRequest().changeSessionId();
		
		try {			
			if (usuario != null && usuario.matches("[a-zA-Z0-9]+")) {
				session.setAttribute("tDni", usuario);
			}
			
			if(VariablesGlobales.getVersion() != null) {
				session.setAttribute("version", VariablesGlobales.getVersion());
			}
			
			String csrftokenC = LimpiarParametro.getOwaspCsrfTokenGet(req, "/gestionPresencia/eleccionArea");
			doForward(req, resp, "/eleccionArea?" + csrftokenC);
		} catch (Exception e) {
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
