package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class Unidades_Home {
    private EntityManager em;

    public Unidades_Home(EntityManager em) {
        this.em = em;
    }

    public List<Unidades> findAll() {
        return em.createNamedQuery("Unidades.findAll", Unidades.class).getResultList();
    }

    public List<Unidades> findByEntiProvCenCentro(BigDecimal enti, BigDecimal prov, 
            BigDecimal cen, String cdCentro) {
        return em.createNamedQuery("Unidades.findByEntiProvCenCentro", Unidades.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, enti)
                .setParameter(2, prov)
                .setParameter(3, cen)
                .setParameter(4, cdCentro)
                .getResultList();
    }

    public List<Unidades> findByIdUnidad(String idUnidad) {
        return em.createNamedQuery("Unidades.findByIdUnidad", Unidades.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, idUnidad)
                .getResultList();
    }

    public List<Unidades> findBySituacion(String situacion) {
        return em.createNamedQuery("Unidades.findBySituacion", Unidades.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, situacion)
                .getResultList();
    }

    public void save(Unidades unidades) {
        Unidades_Key key = new Unidades_Key(unidades.getEntiGesEp(), unidades.getProvEp(), 
                unidades.getCenGesEp(), unidades.getCdCentro(), unidades.getIdUnidad());
        if (em.find(Unidades.class, key) == null) {
            em.persist(unidades);
        } else {
            em.merge(unidades);
        }
        em.flush();
    }

    public void delete(BigDecimal enti, BigDecimal prov, BigDecimal cen, 
            String cdCentro, String idUnidad) {
        Unidades_Key key = new Unidades_Key(enti, prov, cen, cdCentro, idUnidad);
        Unidades entity = em.find(Unidades.class, key);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }
}
