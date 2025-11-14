package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class Contratos_Home {
    private EntityManager em;

    public Contratos_Home(EntityManager em) {
        this.em = em;
    }

    public void save(Contratos contratos) {
        Contratos_Key key = new Contratos_Key(
                contratos.getEntiGesEp(), 
                contratos.getProvEp(),
                contratos.getCenGesEp(), 
                contratos.getIdcontrato());
        if (em.find(Contratos.class, key) == null) {
            em.persist(contratos);
        } else {
            em.merge(contratos);
        }
        em.flush();
    }

    public void delete(BigDecimal enti, BigDecimal prov, BigDecimal cen, BigDecimal idcontrato) {
        Contratos_Key key = new Contratos_Key(enti, prov, cen, idcontrato);
        Contratos entity = em.find(Contratos.class, key);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }

    public Contratos findByKey(BigDecimal enti, BigDecimal prov, BigDecimal cen, BigDecimal idcontrato) {
        Contratos_Key key = new Contratos_Key(enti, prov, cen, idcontrato);
        return em.find(Contratos.class, key);
    }
}
