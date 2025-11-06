package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class Persona_Home {

	private EntityManager em;

	public Persona_Home(EntityManager em) {
		this.em = em;
	}

	public Persona personaByDNI(String iddni) {
		return em.createNamedQuery("personaByDNI", Persona.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.setParameter(1, iddni).getSingleResult();
	}
	
	public List<Persona> allUsuarios() {
		return em.createNamedQuery("allUsuarios", Persona.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.getResultList();
	}
	
	public List<Persona> personasForGestor(BigDecimal entidad, BigDecimal provincia, BigDecimal centro) {
		return em.createNamedQuery("personasForGestor", Persona.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH").setParameter(1, entidad).setParameter(2, provincia)
				.setParameter(3, centro).getResultList();
	}
	
	public void remove () {
		 em.remove("");
	}
	
	public void create () {
		em.persist("");
		em.flush();
	}
}