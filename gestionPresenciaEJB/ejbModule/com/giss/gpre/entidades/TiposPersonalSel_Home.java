package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class TiposPersonalSel_Home {
	private EntityManager em;

	public TiposPersonalSel_Home(EntityManager em) {
		this.em = em;
	}

	public List<TiposPersonalSel> findAll() {
		return em.createNamedQuery("TipoPersonasSel.findAll", TiposPersonalSel.class).getResultList();
	}

	public List<TiposPersonalSel> findByUsuario(String usuario) {
		return em.createNamedQuery("TipoPersonasSel.findByUsuario", TiposPersonalSel.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.setParameter(1, usuario).getResultList();
	}

	public void save(TiposPersonalSel tiposPersonalSel) {
		TiposPersonalSel_Key key = new TiposPersonalSel_Key(tiposPersonalSel.getIdUsuario(),
				tiposPersonalSel.getEntiGesEp(), tiposPersonalSel.getProvEp(), tiposPersonalSel.getCenGesEp(),
				tiposPersonalSel.getCdTipoPersonal());
		if (em.find(TiposPersonalSel.class, key) == null) {
			em.persist(tiposPersonalSel);
		} else {
			em.merge(tiposPersonalSel);
		}
		em.flush();
	}

	public void delete(String usuario, BigDecimal enti, BigDecimal prov, BigDecimal cen, BigDecimal cdTipoPersonal) {
		TiposPersonalSel_Key key = new TiposPersonalSel_Key(usuario, enti, prov, cen, cdTipoPersonal);
		TiposPersonalSel entity = em.find(TiposPersonalSel.class, key);
		if (entity != null) {
			em.remove(entity);
		}
		em.flush();
	}

}
