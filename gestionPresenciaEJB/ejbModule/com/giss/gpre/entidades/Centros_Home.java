package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class Centros_Home {
    private EntityManager em;

    public Centros_Home(EntityManager em) {
        this.em = em;
    }

    public List<Centros> findAll() {
        return em.createNamedQuery("Centros.findAll", Centros.class).getResultList();
    }

    public List<Centros> findByEntiProvCen(BigDecimal enti, BigDecimal prov, BigDecimal cen) {
        return em.createNamedQuery("Centros.findByEntiProvCen", Centros.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, enti)
                .setParameter(2, prov)
                .setParameter(3, cen)
                .getResultList();
    }

    public List<Centros> findByIdCentro(String idCentro) {
        return em.createNamedQuery("Centros.findByIdCentro", Centros.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, idCentro)
                .getResultList();
    }

    public void save(Centros centros) {
        Centros_Key key = new Centros_Key(centros.getEntiGesEp(), centros.getProvEp(), 
                centros.getCenGesEp(), centros.getIdCentro());
        if (em.find(Centros.class, key) == null) {
            em.persist(centros);
        } else {
            em.merge(centros);
        }
        em.flush();
    }

    public void delete(BigDecimal enti, BigDecimal prov, BigDecimal cen, String idCentro) {
        Centros_Key key = new Centros_Key(enti, prov, cen, idCentro);
        Centros entity = em.find(Centros.class, key);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }
}
