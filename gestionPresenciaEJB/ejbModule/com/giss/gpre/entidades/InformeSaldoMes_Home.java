package com.giss.gpre.entidades;

import java.util.List;

import javax.persistence.EntityManager;

public class InformeSaldoMes_Home {

    private EntityManager em;

    public InformeSaldoMes_Home(EntityManager em) {
        this.em = em;
    }

    public List<InformeSaldoMes> allInformeSaldoMes() {
        return em.createNamedQuery("allInformeSaldoMes", InformeSaldoMes.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .getResultList();
    }
    
    public List<InformeSaldoMes> informeByUsuario(String cdusuario) {
        return em.createNamedQuery("informeByUsuario", InformeSaldoMes.class)
                .setParameter("cdusuario", cdusuario)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .getResultList();
    }
    
    public List<InformeSaldoMes> informeByFecha(String fcfichada) {
        return em.createNamedQuery("informeByFecha", InformeSaldoMes.class)
                .setParameter("fcfichada", fcfichada)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .getResultList();
    }
}