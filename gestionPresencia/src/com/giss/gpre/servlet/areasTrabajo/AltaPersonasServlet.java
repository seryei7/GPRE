package com.giss.gpre.servlet.areasTrabajo;

import java.math.BigDecimal;
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
import com.giss.gpre.ejb.UsuariosService;
import com.giss.gpre.entidades.Persona;
import com.giss.gpre.servlet.BaseServlet;
import com.giss.gpre.util.LimpiarParametro;
import com.google.gson.Gson;

/**
 * Servlet para gestión de personas/usuarios.
 * Permite dar de alta y gestionar personal del área de trabajo.
 */
@WebServlet(name = "personas", urlPatterns = { "/mntPersonal" })
public class AltaPersonasServlet extends BaseServlet {

	private static final long serialVersionUID = 2217548888270629877L;
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
			// Obtener datos de área de trabajo desde la sesión
			String entidad = (String) session.getAttribute(SessionAttributes.ENTIDAD);
			String provincia = (String) session.getAttribute(SessionAttributes.PROVINCIA);
			String centro = (String) session.getAttribute(SessionAttributes.CENTRO);
			
			BigDecimal entidadBD = new BigDecimal(entidad);
			BigDecimal provinciaBD = new BigDecimal(provincia);
			BigDecimal centroBD = new BigDecimal(centro);
			
			// Obtener servicio de usuarios
			Context ctx = new InitialContext();
			UsuariosService usuariosService = (UsuariosService) ctx.lookup(JNDIConstants.USUARIOS_SERVICE);
			
			// Obtener lista de usuarios del área
			List<Persona> listUsuarios = usuariosService.personaForGestor(entidadBD, provinciaBD, centroBD);
			
			// Serializar a JSON de forma segura
			String usuariosJson = GSON.toJson(listUsuarios);
			req.setAttribute("listUsuarios", usuariosJson);
			
			String csrfToken = LimpiarParametro.getOwaspCsrfTokenGet(req, URLConstants.USUARIOS_JSP);
			doForward(req, resp, "/jsp/Usuarios.jsp?" + csrfToken);

		} catch (Exception e) {
			handleError(req, resp, e, "Error al cargar lista de usuarios");
		}
	}
}
