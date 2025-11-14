package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class AreasTrabajoSel_Home {
    private EntityManager em;

    public AreasTrabajoSel_Home(EntityManager em) {
        this.em = em;
    }

    public void save(AreasTrabajoSel areasTrabajoSel) {
        AreasTrabajoSel_Key key = new AreasTrabajoSel_Key(
                areasTrabajoSel.getEntiGesEp(), 
                areasTrabajoSel.getProvEp(),
                areasTrabajoSel.getCenGesEp(), 
                areasTrabajoSel.getIdusuario());
        if (em.find(AreasTrabajoSel.class, key) == null) {
            em.persist(areasTrabajoSel);
        } else {
            em.merge(areasTrabajoSel);
        }
        em.flush();
    }

    public void delete(BigDecimal enti, BigDecimal prov, BigDecimal cen, String idusuario) {
        AreasTrabajoSel_Key key = new AreasTrabajoSel_Key(enti, prov, cen, idusuario);
        AreasTrabajoSel entity = em.find(AreasTrabajoSel.class, key);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }

    public AreasTrabajoSel findByKey(BigDecimal enti, BigDecimal prov, BigDecimal cen, String idusuario) {
        AreasTrabajoSel_Key key = new AreasTrabajoSel_Key(enti, prov, cen, idusuario);
        return em.find(AreasTrabajoSel.class, key);
    }
}
