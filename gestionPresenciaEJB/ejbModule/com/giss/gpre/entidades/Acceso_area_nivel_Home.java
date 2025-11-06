package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class Acceso_area_nivel_Home {

	private EntityManager em;

	public Acceso_area_nivel_Home(EntityManager em) {
		this.em = em;
	}

	public List<Acceso_area_nivel> findByIdDni(String iddni) {
		return em.createNamedQuery("findByIdDni", Acceso_area_nivel.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.setParameter(1, iddni).getResultList();
	}
	
	public List<Acceso_area_nivel> findByEntProCen(BigDecimal entidad, BigDecimal provincia, BigDecimal centro) {
		return em.createNamedQuery("findByEntProCen", Acceso_area_nivel.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH").setParameter(1, entidad).setParameter(2, provincia)
				.setParameter(3, centro).getResultList();
	}

}