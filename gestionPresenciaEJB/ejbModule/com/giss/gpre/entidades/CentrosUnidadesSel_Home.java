package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class CentrosUnidadesSel_Home {
	private EntityManager em;

	public CentrosUnidadesSel_Home(EntityManager em) {
		this.em = em;
	}
	
	public List<CentrosUnidadesSel> findAll() {
		return em.createNamedQuery("CentrosUnidadesSel.findAll", CentrosUnidadesSel.class).getResultList();
	}

	public List<CentrosUnidadesSel> findByUsuario(String usuario) {
		return em.createNamedQuery("CentrosUnidadesSel.findByUsuario", CentrosUnidadesSel.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.setParameter(1, usuario).getResultList();
	}

	public void save(CentrosUnidadesSel centrosUnidadesSel) {
		CentrosUnidadesSel_Key key = new CentrosUnidadesSel_Key(centrosUnidadesSel.getIdUsuario(), 
				centrosUnidadesSel.getEntiGesEp(), centrosUnidadesSel.getProvEp(), centrosUnidadesSel.getCenGesEp(),
				centrosUnidadesSel.getCdCentro(), centrosUnidadesSel.getCdUnidad());
		if (em.find(CentrosUnidadesSel.class, key) == null) {
			em.persist(centrosUnidadesSel);
		} else {
			em.merge(centrosUnidadesSel);
		}
		em.flush();
	}

	public void delete(String idUsuario, BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String cdCentro, String cdUnidad) {
		CentrosUnidadesSel_Key key = new CentrosUnidadesSel_Key(idUsuario, entiGesEp, provEp, cenGesEp, cdCentro, cdUnidad);
		CentrosUnidadesSel entity = em.find(CentrosUnidadesSel.class, key);
		if (entity != null) {
			em.remove(entity);
		}
		em.flush();
	}
}
