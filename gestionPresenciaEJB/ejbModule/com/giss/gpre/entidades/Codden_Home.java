package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class Codden_Home {
    private EntityManager em;

    public Codden_Home(EntityManager em) {
        this.em = em;
    }

    public void save(Codden codden) {
        Codden_Key key = new Codden_Key(codden.getNtcodden(), codden.getIdcodden());
        if (em.find(Codden.class, key) == null) {
            em.persist(codden);
        } else {
            em.merge(codden);
        }
        em.flush();
    }

    public void delete(BigDecimal ntcodden, BigDecimal idcodden) {
        Codden_Key key = new Codden_Key(ntcodden, idcodden);
        Codden entity = em.find(Codden.class, key);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }

    public Codden findByKey(BigDecimal ntcodden, BigDecimal idcodden) {
        Codden_Key key = new Codden_Key(ntcodden, idcodden);
        return em.find(Codden.class, key);
    }
}
