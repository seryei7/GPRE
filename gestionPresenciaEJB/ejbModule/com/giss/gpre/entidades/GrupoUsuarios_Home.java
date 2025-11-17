package com.giss.gpre.entidades;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

public class GrupoUsuarios_Home {

	private EntityManager em;

	public GrupoUsuarios_Home(EntityManager em) {
		this.em = em;
	}

	public Persona findByPersonalNivelAcceso(String userEntrante, BigDecimal entidad, BigDecimal provincia, BigDecimal centro) {
		return em.createNamedQuery("findByPersonalNivelAcceso", Persona.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.setParameter(1, userEntrante).setParameter(2, entidad).setParameter(3, provincia).setParameter(4, centro).getSingleResult();
	}

}