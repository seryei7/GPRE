package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class GpreListados_Home {
    private EntityManager em;

    public GpreListados_Home(EntityManager em) {
        this.em = em;
    }

    public List<GpreListados> findAll() {
        return em.createNamedQuery("GpreListados.findAll", GpreListados.class).getResultList();
    }

    public GpreListados findById(BigDecimal id) {
    	return em.createNamedQuery("GpreListados.findById", GpreListados.class).setParameter(1, id).getSingleResult();
    }

    public List<GpreListados> findByUsuario(String usuario) {
        return em.createNamedQuery("GpreListados.findByUsuario", GpreListados.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, usuario).getResultList();
    }

    public void save(GpreListados gpreListados) {
        if (em.find(GpreListados.class, gpreListados.getIdListado()) == null) {
            em.persist(gpreListados);
        } else {
            em.merge(gpreListados);
        }
        em.flush();
    }

    public void delete(BigDecimal idListado) {
        GpreListados entity = em.find(GpreListados.class, idListado);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }
}
