package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class TipoPersonal_Home {
    private EntityManager em;

    public TipoPersonal_Home(EntityManager em) {
        this.em = em;
    }

    public List<TipoPersonal> findAll() {
        return em.createNamedQuery("TipoPersonal.findAll", TipoPersonal.class).getResultList();
    }

    public TipoPersonal findByIdTipoPersonal(BigDecimal idTipoPersonal) {
        List<TipoPersonal> results = em.createNamedQuery("TipoPersonal.findByIdTipoPersonal", TipoPersonal.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, idTipoPersonal)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<TipoPersonal> findByPersonalExterno(String personalExterno) {
        return em.createNamedQuery("TipoPersonal.findByPersonalExterno", TipoPersonal.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, personalExterno)
                .getResultList();
    }

    public void save(TipoPersonal tipoPersonal) {
        TipoPersonal_Key key = new TipoPersonal_Key(tipoPersonal.getIdTipoPersonal());
        if (em.find(TipoPersonal.class, key) == null) {
            em.persist(tipoPersonal);
        } else {
            em.merge(tipoPersonal);
        }
        em.flush();
    }

    public void delete(BigDecimal idTipoPersonal) {
        TipoPersonal_Key key = new TipoPersonal_Key(idTipoPersonal);
        TipoPersonal entity = em.find(TipoPersonal.class, key);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }
}
