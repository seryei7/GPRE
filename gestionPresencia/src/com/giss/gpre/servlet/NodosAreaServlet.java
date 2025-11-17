package com.giss.gpre.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.giss.gpre.ejb.AANService;
import com.giss.gpre.entidades.Nodos;
import com.giss.gpre.entidades.Persona;
import com.giss.gpre.util.GpreException;
import com.giss.gpre.util.LimpiarParametro;

@WebServlet(name = "nodos", urlPatterns = { "/NodosTrabajos" })
public class NodosAreaServlet extends HttpServlet {

	private static final long serialVersionUID = 2217548888270629877L;
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

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
//		ESAPI.currentRequest().getSession(false);

		boolean nodos = true;
		String mensaje;
		HttpSession session = ESAPI.currentRequest().getSession(false);

		try {
			Context ctx = new InitialContext();
			AANService aanService = (AANService) ctx.lookup("java:global/GPRE/gestionPresenciaEJB/AANServiceBean");
			
			String cenGesEp = req.getParameter("cenGesEp");
			String provincia = req.getParameter("provincia");
			String entGesEp = req.getParameter("entGesEp");
			String usuario = req.getParameter("tDni");

			aanService.IncluirAreaSeleccionada(usuario, new BigDecimal(entGesEp), new BigDecimal(provincia), new BigDecimal(cenGesEp));
			Persona persona = aanService.personaByDNI(usuario);

			String nivel = req.getParameter("cdNivel");
			BigDecimal bdNivel = new BigDecimal(nivel);
			List<Nodos> listaNodos = aanService.nodosTrabajo(bdNivel);

			List<Nodos> nRaiz = new ArrayList<>();
			List<Nodos> n1 = new ArrayList<>();
			List<Nodos> n2 = new ArrayList<>();

			for (Nodos nodo : listaNodos) {
				if (nodo.getCdNodoPadre() != null && nodo.getCdNodoPadre().intValue() == 1) {
					nRaiz.add(nodo);
				}
			}

			for (Nodos nodo : listaNodos) {
				for (Nodos padre : nRaiz) {
					if (nodo.getCdNodoPadre() != null && nodo.getCdNodoPadre().equals(padre.getIdNodo())) {
						n1.add(nodo);
					}
				}
			}

			for (Nodos nodo : listaNodos) {
				for (Nodos hijo : n1) {
					if (nodo.getCdNodoPadre() != null && nodo.getCdNodoPadre().equals(hijo.getIdNodo())) {
						n2.add(nodo);
					}
				}
			}

			if (persona == null) {
				throw new GpreException("Persona no autorizada. Consultar con su administrador.");
			}

			req.setAttribute("persona", persona);
			req.setAttribute("nodos", nodos);
			req.setAttribute("nRaiz", nRaiz);
			req.setAttribute("n1", n1);
			req.setAttribute("n2", n2);
			if(usuario != null && usuario.matches("[a-zA-Z0-9]+") && nivel != null && nivel.matches("[a-zA-Z0-9]+") 
					&& cenGesEp != null && cenGesEp.matches("[a-zA-Z0-9]+") 
					&& provincia != null && provincia.matches("[a-zA-Z0-9]+")
					&& entGesEp != null && entGesEp.matches("[a-zA-Z0-9]+")) {
				session.setAttribute("tDni", usuario);
				session.setAttribute("cdNivel", nivel);
				session.setAttribute("cenGesEp", cenGesEp);
				session.setAttribute("provincia", provincia);
				session.setAttribute("entGesEp", entGesEp);
			}

			String csrftokenC = LimpiarParametro.getOwaspCsrfTokenGet(req, "/gestionPresencia/index.jsp");
			doForward(req, resp, "/index.jsp?" + csrftokenC);

		} catch (GpreException | NamingException e) {
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
