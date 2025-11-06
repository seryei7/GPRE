package com.giss.gpre.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import com.giss.gpre.constants.SessionAttributes;
import com.giss.gpre.constants.URLConstants;
import com.giss.gpre.constants.ValidationConstants;
import com.giss.gpre.util.LimpiarParametro;
import com.giss.gpre.util.VariablesGlobales;

/**
 * Servlet de inicio de sesión.
 * Gestiona el acceso inicial del usuario a la aplicación.
 */
@WebServlet(name = "login", urlPatterns = { "/acceso" })
public class LoginServlet extends BaseServlet {

	private static final long serialVersionUID = 2217548888270629877L;

	@Override
	public void init() throws ServletException {
		try {
			Enumeration<URL> manifestUrls = Thread.currentThread()
				.getContextClassLoader()
				.getResources("META-INF/MANIFEST.MF");
			
			while (manifestUrls.hasMoreElements()) {
				URL manifestUrl = manifestUrls.nextElement();
				try (InputStream is = manifestUrl.openStream()) {
					Manifest manifest = new Manifest(is);
					Attributes attributes = manifest.getMainAttributes();
					String version = attributes.getValue("Implementation-Version");
					if (version != null) {
						VariablesGlobales.setVersion(version);
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("Error al cargar versión desde MANIFEST.MF: {}", e.getMessage(), e);
		}

		LOGGER.info("Inicio GPRE - versión: {}", VariablesGlobales.getVersion());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		initializeESAPI(req, resp);
		
		// Creación de la sesión con regeneración del ID por seguridad
		HttpSession session = ESAPI.currentRequest().getSession(true);
		ESAPI.currentRequest().changeSessionId();
		
		String usuario = req.getParameter(SessionAttributes.DNI);
		
		try {
			// Validar y establecer usuario en sesión
			if (usuario != null && usuario.matches(ValidationConstants.ALPHANUMERIC_PATTERN)) {
				session.setAttribute(SessionAttributes.DNI, usuario);
			}
			
			// Establecer versión en sesión
			if (VariablesGlobales.getVersion() != null) {
				session.setAttribute(SessionAttributes.VERSION, VariablesGlobales.getVersion());
			}
			
			String csrfToken = LimpiarParametro.getOwaspCsrfTokenGet(req, URLConstants.ELECCION_AREA_SERVLET);
			doForward(req, resp, "/eleccionArea?" + csrfToken);
		} catch (Exception e) {
			handleError(req, resp, e, "Error en el proceso de login");
		}
	}
}
