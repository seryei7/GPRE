package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giss.gpre.datos.DatosArea;
import com.giss.gpre.entidades.Acceso_area_nivel;
import com.giss.gpre.entidades.Areas_trabajo;
import com.giss.gpre.entidades.Areas_trabajo_Home;
import com.giss.gpre.entidades.Nodos;
import com.giss.gpre.entidades.Nodos_Home;
import com.giss.gpre.entidades.Persona;
import com.giss.gpre.entidades.Persona_Home;

/**
 * Bean de sesión para gestión de áreas de trabajo y niveles de acceso.
 * Proporciona servicios relacionados con autenticación y autorización de usuarios.
 */
@Stateless
public class AANServiceBean implements AANService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AANServiceBean.class);
	
	@PersistenceContext(unitName = "gestionPresencia")
	private EntityManager em;

	@Override
	public boolean testConnection() {
		try {
			Query query = em.createNativeQuery("SELECT 1 FROM DUAL");
			query.getSingleResult();
			return true;
		} catch (Exception e) {
			LOGGER.error("Error al probar la conexión a base de datos", e);
			return false;
		}
	}

	@Override
	public Persona personaByDNI(String dni) {
		if (dni == null || dni.isEmpty()) {
			LOGGER.warn("Se intentó buscar persona con DNI nulo o vacío");
			return null;
		}
		
		try {
			Persona_Home personaHome = new Persona_Home(em);
			return personaHome.personaByDNI(dni);
		} catch (Exception e) {
			LOGGER.error("Error al buscar persona por DNI: {}", dni, e);
			return null;
		}
	}
	
	@Override
	public List<DatosArea> areasDeTrabajoNivel(String dni) {
		if (dni == null || dni.isEmpty()) {
			LOGGER.warn("Se intentó obtener áreas con DNI nulo o vacío");
			return Collections.emptyList();
		}
		
		try {
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetAreasTrabajo")
				.setParameter("pIdDNI", dni);
			
			@SuppressWarnings("unchecked")
			List<Object[]> resultados = query.getResultList();
			
			List<DatosArea> listaAreas = new ArrayList<>();
			for (Object[] row : resultados) {
				DatosArea area = new DatosArea();
				area.setEnti_ges_ep((BigDecimal) row[0]);
				area.setProv_ep((BigDecimal) row[1]);
				area.setCen_ges_ep((BigDecimal) row[2]);
				area.setDenominacion((String) row[3]);
				area.setCdnivel((BigDecimal) row[4]);
				listaAreas.add(area);
			}
			
			return listaAreas;
		} catch (Exception e) {
			LOGGER.error("Error al obtener áreas de trabajo para DNI: {}", dni, e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<Nodos> nodosTrabajo(BigDecimal nivel) {
		if (nivel == null) {
			LOGGER.warn("Se intentó obtener nodos con nivel nulo");
			return Collections.emptyList();
		}
		
		try {
			Nodos_Home nodosHome = new Nodos_Home(em);
			return nodosHome.nodosByNivel(nivel);
		} catch (Exception e) {
			LOGGER.error("Error al obtener nodos de trabajo para nivel: {}", nivel, e);
			return Collections.emptyList();
		}
	}

	@Override
	public Areas_trabajo areasTrabajo(Acceso_area_nivel acceso) {
		if (acceso == null) {
			LOGGER.warn("Se intentó obtener área de trabajo con acceso nulo");
			return null;
		}
		
		try {
			Areas_trabajo_Home areaHome = new Areas_trabajo_Home(em);
			return areaHome.findById(acceso.getEnti_ges_ep(), 
				acceso.getProv_ep(), acceso.getCen_ges_ep());
		} catch (Exception e) {
			LOGGER.error("Error al obtener área de trabajo", e);
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Acceso_area_nivel> ObtenerAreasDeTrabajoNivel(String dni) {
		if (dni == null || dni.isEmpty()) {
			LOGGER.warn("Se intentó obtener áreas con DNI nulo o vacío");
			return Collections.emptyList();
		}
		
		try {
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callObtenerAreasTrabajo")
				.setParameter("p_iddni", dni);
			return query.getResultList();
		} catch (Exception e) {
			LOGGER.error("Error al obtener áreas de trabajo nivel para DNI: {}", dni, e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public Object ValidarUsuario(String dni) {
		if (dni == null || dni.isEmpty()) {
			LOGGER.warn("Se intentó validar usuario con DNI nulo o vacío");
			return -1;
		}
		
		try {
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetPralAccesoNew")
				.setParameter("pCdDNI", dni);
			return query.getFirstResult();
		} catch (Exception e) {
			LOGGER.error("Error al validar usuario con DNI: {}", dni, e);
			return -1;
		}
	}
	
	@Override
	public Object IncluirAreaSeleccionada(String dni, BigDecimal entidad, 
			BigDecimal provincia, BigDecimal centro) {
		if (dni == null || entidad == null || provincia == null || centro == null) {
			LOGGER.warn("Se intentó incluir área seleccionada con parámetros nulos");
			return -1;
		}
		
		try {
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetAreasTrabajoSel")
				.setParameter("pIdDNI", dni)
				.setParameter("pIdEntiGesEp", entidad)
				.setParameter("pIdProvEp", provincia)
				.setParameter("pIdCenGesEp", centro);
			query.execute();
			return query.getFirstResult();
		} catch (Exception e) {
			LOGGER.error("Error al incluir área seleccionada para DNI: {}", dni, e);
			return -1;
		}
	}
}
