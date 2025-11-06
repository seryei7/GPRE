package com.giss.gpre.servlet.areasTrabajo;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import com.giss.gpre.constants.JNDIConstants;
import com.giss.gpre.constants.SessionAttributes;
import com.giss.gpre.constants.URLConstants;
import com.giss.gpre.ejb.IncidenciasService;
import com.giss.gpre.entidades.Incidencias;
import com.giss.gpre.servlet.BaseServlet;
import com.giss.gpre.util.GenerarInformes;
import com.giss.gpre.util.LimpiarParametro;
import com.google.gson.Gson;

/**
 * Servlet para gestión de incidencias.
 * Permite visualizar y gestionar las incidencias del sistema.
 */
@WebServlet(name = "incidencia", urlPatterns = { "/mntIncidencias" })
public class IncidenciasServlet extends BaseServlet {

	private static final long serialVersionUID = 2217548888270629877L;
	private static final String CODIGO_INFORME = "GIDEGPREGIINC";
	private static final String FORMATO_PDF = "pdf";
	private static final Gson GSON = new Gson();

	@Override
	public void init() throws ServletException {
		ServletContext contexto = getServletContext();
		if (contexto.getAttribute(SessionAttributes.ACTIVE_SESSIONS) == null) {
			contexto.setAttribute(SessionAttributes.ACTIVE_SESSIONS, 
				new ConcurrentHashMap<String, HttpSession>());
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		initializeESAPI(req, resp);
		HttpSession session = ESAPI.currentRequest().getSession(false);

		try {
			Context ctx = new InitialContext();
			IncidenciasService incidenciasService = (IncidenciasService) ctx.lookup(JNDIConstants.INCIDENCIAS_SERVICE);
			
			// Obtener DNI del usuario desde la sesión
			String dniUsuario = (String) session.getAttribute(SessionAttributes.DNI);
			
			// Obtener todas las incidencias
			List<Incidencias> listIncidencias = incidenciasService.allIncidencias();
			
			// Serializar a JSON de forma segura
			String incidenciasJson = GSON.toJson(listIncidencias);
			req.setAttribute("listaIncidencias", incidenciasJson);
			
			// Generar URL del informe PDF
			String urlPdf = GenerarInformes.generarUrlFichero(listIncidencias, dniUsuario, 
				CODIGO_INFORME, FORMATO_PDF);
            req.setAttribute(SessionAttributes.PDF, urlPdf);
			
			String csrfToken = LimpiarParametro.getOwaspCsrfTokenGet(req, URLConstants.INCIDENCIAS_JSP);
			doForward(req, resp, "/jsp/Incidencias.jsp?" + csrfToken);

		} catch (Exception e) {
			handleError(req, resp, e, "Error al cargar incidencias");
		}
	}
}
