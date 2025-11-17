package com.giss.gpre.entidades;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

public class Areas_trabajo_Home {

	private EntityManager em;

	/**
	 * @param em
	 */
	public Areas_trabajo_Home(EntityManager em) {
		this.em = em;
	}

	public Areas_trabajo findById(BigDecimal enti_ges_ep, BigDecimal prov_ep, BigDecimal cen_ges_ep) {
		return em.createNamedQuery("findById", Areas_trabajo.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH").setParameter(1, enti_ges_ep)
				.setParameter(2, prov_ep).setParameter(3, cen_ges_ep).getSingleResult();
	}

}
