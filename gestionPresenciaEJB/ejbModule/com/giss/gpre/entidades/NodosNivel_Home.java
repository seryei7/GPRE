package com.giss.gpre.entidades;

import java.util.List;

import javax.persistence.EntityManager;

public class NodosNivel_Home {

	private EntityManager em;

	public NodosNivel_Home(EntityManager em) {
		this.em = em;
	}

	public List<NodosNivel> allNodosNivel() {
		return em.createNamedQuery("areasTrabajoNivel", NodosNivel.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH").getResultList();
	}

}