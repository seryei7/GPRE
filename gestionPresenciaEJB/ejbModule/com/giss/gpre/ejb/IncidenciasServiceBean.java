package com.giss.gpre.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.giss.gpre.entidades.Incidencias;
import com.giss.gpre.entidades.Incidencias_Home;

@Stateless
public class IncidenciasServiceBean implements IncidenciasService{
	
	@PersistenceContext(unitName="gestionPresencia")
	private EntityManager em;

	@Override
	public List<Incidencias> allIncidencias() {
		List<Incidencias> listaIncidencias = new ArrayList<>();
		Incidencias_Home inci_home = new Incidencias_Home(em);
		listaIncidencias = inci_home.allIncidencias();		
		
		return listaIncidencias;
	}
	
}
