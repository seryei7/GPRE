package com.giss.gpre.entidades;

import java.util.List;

import javax.persistence.EntityManager;

public class Incidencias_Home {

	private EntityManager em;

	public Incidencias_Home(EntityManager em) {
		this.em = em;
	}

	public List<Incidencias> allIncidencias() {
		return em.createNamedQuery("allIncidencias", Incidencias.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH").getResultList();
	}

}