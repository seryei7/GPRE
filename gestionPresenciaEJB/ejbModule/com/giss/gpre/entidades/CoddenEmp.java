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
 * Bean de implementacion de la tabla de la base de datos CODDEN_EMP
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "CODDEN_EMP")
@IdClass(CoddenEmp_Key.class)
public class CoddenEmp implements Serializable {

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
    @Column(name = "NTCODDEN", precision = 10, scale = 0, nullable = false)
    private BigDecimal ntcodden;

    @Id
    @Column(name = "IDCODDEN", precision = 10, scale = 0, nullable = false)
    private BigDecimal idcodden;

    @Column(name = "DSCODDEN", length = 100)
    private String dscodden;

    public CoddenEmp() {
    }

    public CoddenEmp(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp,
            BigDecimal ntcodden, BigDecimal idcodden, String dscodden) {
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.ntcodden = ntcodden;
        this.idcodden = idcodden;
        this.dscodden = dscodden;
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

    public BigDecimal getNtcodden() {
        return ntcodden;
    }

    public void setNtcodden(BigDecimal ntcodden) {
        this.ntcodden = ntcodden;
    }

    public BigDecimal getIdcodden() {
        return idcodden;
    }

    public void setIdcodden(BigDecimal idcodden) {
        this.idcodden = idcodden;
    }

    public String getDscodden() {
        return dscodden;
    }

    public void setDscodden(String dscodden) {
        this.dscodden = dscodden;
    }
}
