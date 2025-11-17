package com.giss.gpre.entidades;

import java.util.List;

import javax.persistence.EntityManager;

public class Empresas_Home {
    private EntityManager em;

    public Empresas_Home(EntityManager em) {
        this.em = em;
    }

    public List<Empresas> findAll() {
        return em.createNamedQuery("Empresas.findAll", Empresas.class).getResultList();
    }

    public Empresas findByIdCifNif(String idCifNif) {
        List<Empresas> results = em.createNamedQuery("Empresas.findByIdCifNif", Empresas.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, idCifNif)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<Empresas> findByNombre(String nombre) {
        return em.createNamedQuery("Empresas.findByNombre", Empresas.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, "%" + nombre + "%")
                .getResultList();
    }

    public List<Empresas> findActivas() {
        return em.createNamedQuery("Empresas.findActivas", Empresas.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .getResultList();
    }

    public void save(Empresas empresas) {
        Empresas_Key key = new Empresas_Key(empresas.getIdFiscal());
        if (em.find(Empresas.class, key) == null) {
            em.persist(empresas);
        } else {
            em.merge(empresas);
        }
        em.flush();
    }

    public void delete(String idFiscal) {
        Empresas_Key key = new Empresas_Key(idFiscal);
        Empresas entity = em.find(Empresas.class, key);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }
}
