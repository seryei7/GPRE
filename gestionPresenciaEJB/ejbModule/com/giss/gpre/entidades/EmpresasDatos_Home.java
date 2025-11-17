package com.giss.gpre.entidades;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class EmpresasDatos_Home {
    private EntityManager em;

    public EmpresasDatos_Home(EntityManager em) {
        this.em = em;
    }

    public List<EmpresasDatos> findAll() {
        return em.createNamedQuery("EmpresasDatos.findAll", EmpresasDatos.class).getResultList();
    }

    public List<EmpresasDatos> findByEntiProvCen(BigDecimal enti, BigDecimal prov, BigDecimal cen) {
        return em.createNamedQuery("EmpresasDatos.findByEntiProvCen", EmpresasDatos.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, enti)
                .setParameter(2, prov)
                .setParameter(3, cen)
                .getResultList();
    }

    public List<EmpresasDatos> findByIdCifNif(String idCifNif) {
        return em.createNamedQuery("EmpresasDatos.findByIdCifNif", EmpresasDatos.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, idCifNif)
                .getResultList();
    }

    public List<EmpresasDatos> findByNombre(String nombre) {
        return em.createNamedQuery("EmpresasDatos.findByNombre", EmpresasDatos.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .setParameter(1, "%" + nombre + "%")
                .getResultList();
    }

    public void save(EmpresasDatos empresasDatos) {
        EmpresasDatos_Key key = new EmpresasDatos_Key(empresasDatos.getEntiGesEp(), 
                empresasDatos.getProvEp(), empresasDatos.getCenGesEp(), empresasDatos.getIdFiscal());
        if (em.find(EmpresasDatos.class, key) == null) {
            em.persist(empresasDatos);
        } else {
            em.merge(empresasDatos);
        }
        em.flush();
    }

    public void delete(BigDecimal enti, BigDecimal prov, BigDecimal cen, String idFiscal) {
        EmpresasDatos_Key key = new EmpresasDatos_Key(enti, prov, cen, idFiscal);
        EmpresasDatos entity = em.find(EmpresasDatos.class, key);
        if (entity != null) {
            em.remove(entity);
        }
        em.flush();
    }
}
