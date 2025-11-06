package com.giss.gpre.servlet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
import com.giss.gpre.constants.ValidationConstants;
import com.giss.gpre.ejb.AANService;
import com.giss.gpre.entidades.Nodos;
import com.giss.gpre.entidades.Persona;
import com.giss.gpre.util.GpreException;
import com.giss.gpre.util.LimpiarParametro;

/**
 * Servlet para gestionar nodos de áreas de trabajo.
 * Organiza los nodos en una estructura jerárquica de 3 niveles.
 */
@WebServlet(name = "nodos", urlPatterns = { "/NodosTrabajos" })
public class NodosAreaServlet extends BaseServlet {

	private static final long serialVersionUID = 2217548888270629877L;
	private static final BigDecimal NODO_RAIZ_ID = BigDecimal.ONE;

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
			// Obtener y validar parámetros
			String usuario = req.getParameter(SessionAttributes.DNI);
			String cenGesEp = req.getParameter(SessionAttributes.CENTRO);
			String provincia = req.getParameter(SessionAttributes.PROVINCIA);
			String entGesEp = req.getParameter(SessionAttributes.ENTIDAD);
			String nivel = req.getParameter(SessionAttributes.NIVEL);
			
			validarParametrosEntrada(usuario, cenGesEp, provincia, entGesEp, nivel);

			Context ctx = new InitialContext();
			AANService aanService = (AANService) ctx.lookup(JNDIConstants.AAN_SERVICE);
			
			// Incluir área seleccionada
			aanService.IncluirAreaSeleccionada(usuario, 
				new BigDecimal(entGesEp), 
				new BigDecimal(provincia), 
				new BigDecimal(cenGesEp));
			
			Persona persona = aanService.personaByDNI(usuario);
			if (persona == null) {
				throw new GpreException("Persona no autorizada. Consultar con su administrador.");
			}

			// Obtener y organizar nodos en jerarquía
			List<Nodos> listaNodos = aanService.nodosTrabajo(new BigDecimal(nivel));
			OrganizacionNodos organizacion = organizarNodosJerarquia(listaNodos);

			// Establecer atributos de request
			req.setAttribute("persona", persona);
			req.setAttribute("nodos", true);
			req.setAttribute("nRaiz", organizacion.nRaiz);
			req.setAttribute("n1", organizacion.n1);
			req.setAttribute("n2", organizacion.n2);
			
			// Actualizar sesión con datos validados
			actualizarSesion(session, usuario, nivel, cenGesEp, provincia, entGesEp);

			String csrfToken = LimpiarParametro.getOwaspCsrfTokenGet(req, URLConstants.INDEX_JSP);
			doForward(req, resp, "/index.jsp?" + csrfToken);

		} catch (Exception e) {
			handleError(req, resp, e, "Error al cargar nodos de trabajo");
		}
	}

	/**
	 * Valida que todos los parámetros sean correctos.
	 */
	private void validarParametrosEntrada(String usuario, String cenGesEp, 
			String provincia, String entGesEp, String nivel) throws GpreException {
		if (!esParametroValido(usuario) || !esParametroValido(nivel) 
				|| !esParametroValido(cenGesEp) || !esParametroValido(provincia)
				|| !esParametroValido(entGesEp)) {
			throw new GpreException("Parámetros de entrada inválidos");
		}
	}

	/**
	 * Valida un parámetro individual.
	 */
	private boolean esParametroValido(String parametro) {
		return parametro != null && parametro.matches(ValidationConstants.ALPHANUMERIC_PATTERN);
	}

	/**
	 * Organiza los nodos en una estructura jerárquica de 3 niveles.
	 */
	private OrganizacionNodos organizarNodosJerarquia(List<Nodos> listaNodos) {
		OrganizacionNodos org = new OrganizacionNodos();
		
		// Nodos raíz (padre = 1)
		org.nRaiz = listaNodos.stream()
			.filter(nodo -> nodo.getCdNodoPadre() != null 
				&& nodo.getCdNodoPadre().compareTo(NODO_RAIZ_ID) == 0)
			.collect(Collectors.toList());

		// Nodos nivel 1 (hijos de raíz)
		org.n1 = listaNodos.stream()
			.filter(nodo -> esHijoDe(nodo, org.nRaiz))
			.collect(Collectors.toList());

		// Nodos nivel 2 (hijos de nivel 1)
		org.n2 = listaNodos.stream()
			.filter(nodo -> esHijoDe(nodo, org.n1))
			.collect(Collectors.toList());

		return org;
	}

	/**
	 * Verifica si un nodo es hijo de alguno de los nodos padres.
	 */
	private boolean esHijoDe(Nodos nodo, List<Nodos> padres) {
		if (nodo.getCdNodoPadre() == null) {
			return false;
		}
		return padres.stream()
			.anyMatch(padre -> nodo.getCdNodoPadre().equals(padre.getIdNodo()));
	}

	/**
	 * Actualiza la sesión con los datos del usuario.
	 */
	private void actualizarSesion(HttpSession session, String usuario, String nivel, 
			String cenGesEp, String provincia, String entGesEp) {
		session.setAttribute(SessionAttributes.DNI, usuario);
		session.setAttribute(SessionAttributes.NIVEL, nivel);
		session.setAttribute(SessionAttributes.CENTRO, cenGesEp);
		session.setAttribute(SessionAttributes.PROVINCIA, provincia);
		session.setAttribute(SessionAttributes.ENTIDAD, entGesEp);
	}

	/**
	 * Clase interna para organizar nodos por niveles.
	 */
	private static class OrganizacionNodos {
		List<Nodos> nRaiz = new ArrayList<>();
		List<Nodos> n1 = new ArrayList<>();
		List<Nodos> n2 = new ArrayList<>();
	}
}
