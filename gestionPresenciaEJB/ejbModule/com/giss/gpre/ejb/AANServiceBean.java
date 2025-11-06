package com.giss.gpre.ejb;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import com.giss.gpre.datos.DatosArea;
import com.giss.gpre.entidades.Acceso_area_nivel;
import com.giss.gpre.entidades.Areas_trabajo;
import com.giss.gpre.entidades.Areas_trabajo_Home;
import com.giss.gpre.entidades.Nodos;
import com.giss.gpre.entidades.Nodos_Home;
import com.giss.gpre.entidades.Persona;
import com.giss.gpre.entidades.Persona_Home;

@Stateless
public class AANServiceBean implements AANService{
	
	@PersistenceContext(unitName="gestionPresencia")
	private EntityManager em;
	
	public Vector<Acceso_area_nivel> obtenerAcceso(String idDni) {
		Vector<Acceso_area_nivel> accesoAuth = new Vector<>();
		
		return accesoAuth;
	}

	@Override
	public boolean testConnection() {
		boolean resultado = true;
		try {
			Query query = em.createNativeQuery("SELECT 1 FROM DUAL");
			query.getSingleResult();
		} catch (Exception e) {
			resultado = false;
		}
		return resultado;
	}

	@Override
	public Persona personaByDNI(String iddni) {
		Persona personaLogueada = new Persona();
		Persona_Home perHome = new Persona_Home(em);
		
		try { 
			if (iddni != null && !iddni.isEmpty()) {
				personaLogueada = perHome.personaByDNI(iddni);
			}
		} catch (Exception e) {
			personaLogueada = null;
		}
		
		return personaLogueada;
	}
	
	@Override
	public List<DatosArea> areasDeTrabajoNivel(String dni) {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetAreasTrabajo").setParameter("pIdDNI", dni);
		List<DatosArea> listaAreas = new ArrayList<>();
		List<Object[]> listaAcceso = new ArrayList<>();
		listaAcceso = query.getResultList();
		for (Object[] row : listaAcceso) {
			DatosArea da = new DatosArea();
			da.setCdnivel((BigDecimal) row[4]);
			da.setDenominacion((String) row[3]);
			da.setEnti_ges_ep((BigDecimal) row[0]);
			da.setProv_ep((BigDecimal) row[1]);
			da.setCen_ges_ep((BigDecimal) row[2]);
			listaAreas.add(da);
		}
		
		return listaAreas;
	}

	@Override
	public List<Nodos> nodosTrabajo(BigDecimal nivel) {
		List<Nodos> listNodosTrabajo = new ArrayList<>();
		Nodos_Home nodosHome = new Nodos_Home(em);
		listNodosTrabajo = nodosHome.nodosByNivel(nivel);
		
		return listNodosTrabajo;
	}

	@Override
	public Areas_trabajo areasTrabajo(Acceso_area_nivel acc) {
		Areas_trabajo_Home at = new Areas_trabajo_Home(em);
		return at.findById(acc.getEnti_ges_ep(), acc.getProv_ep(), acc.getCen_ges_ep());
	}
	
	@Override
	public List<Acceso_area_nivel> ObtenerAreasDeTrabajoNivel(String dni) throws IOException {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callObtenerAreasTrabajo").setParameter("p_iddni", dni);
        return query.getResultList();
	}
	
	@Override
	public Object ValidarUsuario(String dni) throws IOException {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetPralAccesoNew").setParameter("pCdDNI", dni);
        int resultado = query.getFirstResult();
        return resultado;
	}
	
	@Override
	public Object IncluirAreaSeleccionada(String dni, BigDecimal entidad, BigDecimal provincia, BigDecimal centro) {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetAreasTrabajoSel").setParameter("pIdDNI", dni).setParameter("pIdEntiGesEp", entidad).setParameter("pIdProvEp", provincia).setParameter("pIdCenGesEp", centro);
        query.execute();
        int resultado = query.getFirstResult();
        return resultado;
	}



}
