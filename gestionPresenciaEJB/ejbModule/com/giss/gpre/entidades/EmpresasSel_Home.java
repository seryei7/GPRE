package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class EmpresasSel_Home {
	private EntityManager em;

	public EmpresasSel_Home(EntityManager em) {
		super();
		this.em = em;
	}

	public List<EmpresasSel> findAll() {
		return em.createNamedQuery("EmpresasSel.findAll", EmpresasSel.class).getResultList();
	}

	public List<EmpresasSel> findByUsuario(String usuario) {
		return em.createNamedQuery("EmpresasSel.findByUsuario", EmpresasSel.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.setParameter(1, usuario).getResultList();
	}

	public void save(EmpresasSel empresasSel) {
		EmpresasSel_Key key = new EmpresasSel_Key(empresasSel.getIdUsuario(), empresasSel.getEntiGesEp(),
				empresasSel.getProvEp(), empresasSel.getCenGesEp(), empresasSel.getCdFiscal());
		if (em.find(EmpresasSel.class, key) == null) {
			em.persist(empresasSel);
		} else {
			em.merge(empresasSel);
		}
		em.flush();
	}

	public void delete(String usuario, BigDecimal enti, BigDecimal prov, BigDecimal cen, String cdFiscal) {
		EmpresasSel_Key key = new EmpresasSel_Key(usuario, enti, prov, cen, cdFiscal);
		EmpresasSel entity = em.find(EmpresasSel.class, key);
		if (entity != null) {
			em.remove(entity);
		}
		em.flush();
	}
}
