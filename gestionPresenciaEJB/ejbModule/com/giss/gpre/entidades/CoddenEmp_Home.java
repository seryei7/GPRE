package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class CoddenEmp_Home {
    private EntityManager em;

    public CoddenEmp_Home(EntityManager em) {
        this.em = em;
    }

    public void save(CoddenEmp coddenEmp) {
        CoddenEmp_Key key = new CoddenEmp_Key(
                coddenEmp.getEntiGesEp(), 
                coddenEmp.getProvEp(),
                coddenEmp.getCenGesEp(), 
                coddenEmp.getNtcodden(), 
                coddenEmp.getIdcodden());
        if (em.find(CoddenEmp.class, key) == null) {
            em.persist(coddenEmp);
        } else {
            em.merge(coddenEmp);
        }
        em.flush();
    }

    public void delete(BigDecimal enti, BigDecimal prov, BigDecimal cen, 
            BigDecimal ntcodden, BigDecimal idcodden) {
        CoddenEmp_Key key = new CoddenEmp_Key(enti, prov, cen, ntcodden, idcodden);
        CoddenEmp entity = em.find(CoddenEmp.class, key);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }

    public CoddenEmp findByKey(BigDecimal enti, BigDecimal prov, BigDecimal cen, 
            BigDecimal ntcodden, BigDecimal idcodden) {
        CoddenEmp_Key key = new CoddenEmp_Key(enti, prov, cen, ntcodden, idcodden);
        return em.find(CoddenEmp.class, key);
    }
}
