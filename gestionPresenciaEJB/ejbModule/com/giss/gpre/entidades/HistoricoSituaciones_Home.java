package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class HistoricoSituaciones_Home {
    private EntityManager em;

    public HistoricoSituaciones_Home(EntityManager em) {
        this.em = em;
    }

    public void save(HistoricoSituaciones historicoSituaciones) {
        HistoricoSituaciones_Key key = new HistoricoSituaciones_Key(
                historicoSituaciones.getEntiGesEp(), 
                historicoSituaciones.getProvEp(),
                historicoSituaciones.getCenGesEp(), 
                historicoSituaciones.getCddni());
        if (em.find(HistoricoSituaciones.class, key) == null) {
            em.persist(historicoSituaciones);
        } else {
            em.merge(historicoSituaciones);
        }
        em.flush();
    }

    public void delete(BigDecimal enti, BigDecimal prov, BigDecimal cen, String cddni) {
        HistoricoSituaciones_Key key = new HistoricoSituaciones_Key(enti, prov, cen, cddni);
        HistoricoSituaciones entity = em.find(HistoricoSituaciones.class, key);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }

    public HistoricoSituaciones findByKey(BigDecimal enti, BigDecimal prov, BigDecimal cen, String cddni) {
        HistoricoSituaciones_Key key = new HistoricoSituaciones_Key(enti, prov, cen, cddni);
        return em.find(HistoricoSituaciones.class, key);
    }
}
