package com.giss.gpre.servlet.informes.saldos;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

import com.giss.gpre.datos.DatosCentro;
import com.giss.gpre.datos.DatosEmpresas;
import com.giss.gpre.datos.DatosTipoPersonal;
import com.giss.gpre.datos.DatosUnidades;
import com.giss.gpre.datos.UnidadNode;
import com.giss.gpre.ejb.CentrosService;
import com.giss.gpre.ejb.EmpresasService;
import com.giss.gpre.ejb.TipoPersonalService;
import com.giss.gpre.ejb.UnidadesService;
import com.giss.gpre.util.LimpiarParametro;

@WebServlet(name = "saldoSemanal", urlPatterns = { "/InfSaldo" })
public class SaldoSemanalServlet extends HttpServlet {

	private static final long serialVersionUID = 929777525313542421L;
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ESAPI.httpUtilities().setCurrentHTTP(req, resp);
		HttpSession session = ESAPI.currentRequest().getSession(false);
		String dniSesion = (String) session.getAttribute("tDni");
		String mensaje;

		try {
			Context ctx = new InitialContext();
			CentrosService centrosService = (CentrosService) ctx.lookup("java:global/GPRE/gestionPresenciaEJB/CentrosServiceBean");
			UnidadesService unidadesService = (UnidadesService) ctx.lookup("java:global/GPRE/gestionPresenciaEJB/UnidadesServiceBean");
			EmpresasService empresasService = (EmpresasService) ctx.lookup("java:global/GPRE/gestionPresenciaEJB/EmpresasServiceBean");
			TipoPersonalService tipoPersonalService = (TipoPersonalService) ctx.lookup("java:global/GPRE/gestionPresenciaEJB/TipoPersonalServiceBean");

			Map<DatosCentro, List<UnidadNode>> mapaCentros = new LinkedHashMap<>();

			// Obtener lista de centros desde el procedimiento almacenado
			List<DatosCentro> centros = centrosService.obtenerCentros(dniSesion);

			for (Iterator<DatosCentro> iterator = centros.iterator(); iterator.hasNext();) {
				DatosCentro datosCentro = (DatosCentro) iterator.next();
				if (datosCentro != null) {
					// Obtener lista de unidades por cada centro
					List<DatosUnidades> datosUnidades = unidadesService.obtenerUnidades(datosCentro.getEnti_ges_ep(),
							datosCentro.getProv_ep(), datosCentro.getCen_ges_ep(), datosCentro.getIdcentro(),
							dniSesion);

					// Construir árbol jerárquico de unidades
					List<UnidadNode> arbolUnidades = construirArbolUnidades(datosUnidades);
					mapaCentros.put(datosCentro, arbolUnidades);
				}
			}
			
			// Obtener lista de empresas desde el procedimiento almacenado
			List<DatosEmpresas> empresas = empresasService.obtenerEmpresas(dniSesion);
			
			// Obtener lista de empresas desde el procedimiento almacenado
			List<DatosTipoPersonal> tipoPersonal = tipoPersonalService.obtenerTipoPersonal();
			
			// Pasar la lista al JSP
			req.setAttribute("mapaCentros", mapaCentros);
			req.setAttribute("empresas", empresas);
			req.setAttribute("tipoPersonal", tipoPersonal);

			// Calcular semana actual por defecto
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate hoy = LocalDate.now();
			LocalDate lunes = hoy.with(DayOfWeek.MONDAY);
			LocalDate domingo = hoy.with(DayOfWeek.SUNDAY);

			req.setAttribute("fechaDesde", lunes.format(formatter));
			req.setAttribute("fechaHasta", domingo.format(formatter));
			session.setAttribute("fechaDesde", lunes.format(formatter));
			session.setAttribute("fechaHasta", domingo.format(formatter));

			String csrftokenC = LimpiarParametro.getOwaspCsrfTokenGet(req, "/gestionPresencia/jsp/SaldoSemanalServlet.jsp");
			doForward(req, resp, "/jsp/SaldoSemanal.jsp?" + csrftokenC);

		} catch (NamingException e) {
			mensaje = "Error al procesar la solicitud {" + e + "}";
			LOGGER.debug(mensaje);
			req.setAttribute("javax.servlet.jsp.jspException", e);
			doForward(req, resp, "/jsp/error.jsp");
		}
	}

	/**
	 * Construye un árbol jerárquico de unidades basándose en cdunidadord
	 */
	private List<UnidadNode> construirArbolUnidades(List<DatosUnidades> unidades) {
		// Crear mapa de unidades por ID para búsqueda rápida
		Map<String, UnidadNode> mapaUnidades = new HashMap<>();

		// Crear nodos para todas las unidades
		for (DatosUnidades unidad : unidades) {
			UnidadNode node = new UnidadNode(unidad);
			mapaUnidades.put(unidad.getIdunidad(), node);
		}

		// Lista de nodos raíz (unidades sin padre o cuyo padre no existe)
		List<UnidadNode> raices = new ArrayList<>();

		// Construir la jerarquía
		for (DatosUnidades unidad : unidades) {
			UnidadNode node = mapaUnidades.get(unidad.getIdunidad());
			String cdunidadpadre = unidad.getCdunidadpadre();

			// Si tiene padre y el padre existe en el mapa, añadirlo como hijo
			if (cdunidadpadre != null && !cdunidadpadre.trim().isEmpty() && !cdunidadpadre.equals("-1")
					&& mapaUnidades.containsKey(cdunidadpadre)) {
				UnidadNode nodoPadre = mapaUnidades.get(cdunidadpadre);
				nodoPadre.addHijo(node);
			} else {
				// Es una unidad raíz
				raices.add(node);
			}
		}

		// Ordenar las unidades por cdunidadord en cada nivel
		ordenarPorCdunidadord(raices);

		return raices;
	}

	/**
	 * Ordena recursivamente los nodos por cdunidadord
	 */
	private void ordenarPorCdunidadord(List<UnidadNode> nodos) {
		if (nodos == null || nodos.isEmpty()) {
			return;
		}

		// Ordenar el nivel actual
		nodos.sort((n1, n2) -> {
			String ord1 = n1.getDatos().getCdunidadord();
			String ord2 = n2.getDatos().getCdunidadord();

			if (ord1 == null)
				ord1 = "";
			if (ord2 == null)
				ord2 = "";

			return ord1.compareTo(ord2);
		});

		// Ordenar recursivamente los hijos
		for (UnidadNode nodo : nodos) {
			if (nodo.getHijos() != null && !nodo.getHijos().isEmpty()) {
				ordenarPorCdunidadord(nodo.getHijos());
			}
		}
	}

	private final void doForward(HttpServletRequest request, HttpServletResponse response, String nextPage) {
		try {
			LimpiarParametro.safeSendForward(request, response, nextPage);
		} catch (IOException | ServletException | AccessControlException e) {
			LOGGER.debug("Error al procesar la solicitud {" + e + "}");
		}
	}
}
