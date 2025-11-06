package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class Nodos_Home {

	private EntityManager em;

	public Nodos_Home(EntityManager em) {
		this.em = em;
	}

	public List<Nodos> nodosByNivel(BigDecimal nivel) {
		return em.createNamedQuery("nodosNivelPorNivel", Nodos.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.setParameter(1, nivel).getResultList();
	}

}