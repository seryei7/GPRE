package com.giss.gpre.servlet;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import com.giss.gpre.constants.JNDIConstants;
import com.giss.gpre.constants.SessionAttributes;
import com.giss.gpre.constants.URLConstants;
import com.giss.gpre.constants.ValidationConstants;
import com.giss.gpre.datos.DatosArea;
import com.giss.gpre.ejb.AANService;
import com.giss.gpre.entidades.Persona;
import com.giss.gpre.util.GpreException;
import com.giss.gpre.util.LimpiarParametro;
import com.giss.gpre.util.VariablesGlobales;

/**
 * Servlet para la elección de áreas de trabajo.
 * Muestra al usuario las áreas de trabajo disponibles según sus permisos.
 */
@WebServlet(name = "inicio", urlPatterns = { "/eleccionArea" })
public class EleccionAreasTrabajoServlet extends BaseServlet {

	private static final long serialVersionUID = 2217548888270629877L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		initializeESAPI(req, resp);
		HttpSession session = ESAPI.currentRequest().getSession(false);

		String usuario = (String) session.getAttribute(SessionAttributes.DNI);
		
		try {
			Context ctx = new InitialContext();
			AANService aanService = (AANService) ctx.lookup(JNDIConstants.AAN_SERVICE);

			// Validar usuario
			int codigoValidacion = (int) aanService.ValidarUsuario(usuario);
			validarCodigoUsuario(codigoValidacion);
			
			Persona persona = aanService.personaByDNI(usuario);
			if (persona == null) {
				throw new GpreException("No se pudo obtener información del usuario");
			}
			
			// Obtener áreas de trabajo del usuario
			List<DatosArea> listaAccesoNivel = aanService.areasDeTrabajoNivel(usuario);
			if (listaAccesoNivel == null || listaAccesoNivel.isEmpty()) {
				throw new GpreException("Persona sin áreas de trabajo. Consultar con su administrador.");
			}
			
			// Establecer atributos de request
			req.setAttribute("lista", listaAccesoNivel);
			req.setAttribute("persona", persona);
			req.setAttribute("nodos", false);

			// Actualizar versión en sesión
			if (VariablesGlobales.getVersion() != null) {
				session.setAttribute(SessionAttributes.VERSION, VariablesGlobales.getVersion());
			}
			
			String csrfToken = LimpiarParametro.getOwaspCsrfTokenGet(req, URLConstants.INDEX_JSP);
			doForward(req, resp, "/index.jsp?" + csrfToken);
		} catch (Exception e) {
			handleError(req, resp, e, "Error al cargar áreas de trabajo");
		}
	}

	/**
	 * Valida el código de retorno de la validación de usuario.
	 * 
	 * @param codigoValidacion Código retornado por el servicio
	 * @throws GpreException Si el código indica un error
	 */
	private void validarCodigoUsuario(int codigoValidacion) throws GpreException {
		if (codigoValidacion == ValidationConstants.ERROR_USUARIO_NO_ALTA) {
			throw new GpreException("El usuario no está dado de alta en el sistema");
		} else if (codigoValidacion == ValidationConstants.ERROR_USUARIO_NO_AUTORIZADO) {
			throw new GpreException("El usuario no ha sido autorizado en la aplicación");
		}
	}
}
