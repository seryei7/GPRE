package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Bean de implementacion de la tabla de la base de datos CONTRATOS
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "CONTRATOS")
@IdClass(Contratos_Key.class)
public class Contratos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ENTI_GES_EP", precision = 2, scale = 0, nullable = false)
    private BigDecimal entiGesEp;

    @Id
    @Column(name = "PROV_EP", precision = 2, scale = 0, nullable = false)
    private BigDecimal provEp;

    @Id
    @Column(name = "CEN_GES_EP", precision = 2, scale = 0, nullable = false)
    private BigDecimal cenGesEp;

    @Id
    @Column(name = "IDCONTRATO", precision = 10, scale = 0, nullable = false)
    private BigDecimal idcontrato;

    @Column(name = "DSCONTRATO", length = 100)
    private String dscontrato;

    @Column(name = "DSCONTRATORED", length = 50)
    private String dscontratored;

    public Contratos() {
    }

    public Contratos(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, BigDecimal idcontrato,
            String dscontrato, String dscontratored) {
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.idcontrato = idcontrato;
        this.dscontrato = dscontrato;
        this.dscontratored = dscontratored;
    }

    // Getters y Setters

    public BigDecimal getEntiGesEp() {
        return entiGesEp;
    }

    public void setEntiGesEp(BigDecimal entiGesEp) {
        this.entiGesEp = entiGesEp;
    }

    public BigDecimal getProvEp() {
        return provEp;
    }

    public void setProvEp(BigDecimal provEp) {
        this.provEp = provEp;
    }

    public BigDecimal getCenGesEp() {
        return cenGesEp;
    }

    public void setCenGesEp(BigDecimal cenGesEp) {
        this.cenGesEp = cenGesEp;
    }

    public BigDecimal getIdcontrato() {
        return idcontrato;
    }

    public void setIdcontrato(BigDecimal idcontrato) {
        this.idcontrato = idcontrato;
    }

    public String getDscontrato() {
        return dscontrato;
    }

    public void setDscontrato(String dscontrato) {
        this.dscontrato = dscontrato;
    }

    public String getDscontratored() {
        return dscontratored;
    }

    public void setDscontratored(String dscontratored) {
        this.dscontratored = dscontratored;
    }
}
