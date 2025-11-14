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
 * Bean de implementacion de la tabla de la base de datos CODDEN
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "CODDEN")
@IdClass(Codden_Key.class)
public class Codden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "NTCODDEN", precision = 10, scale = 0, nullable = false)
    private BigDecimal ntcodden;

    @Id
    @Column(name = "IDCODDEN", precision = 10, scale = 0, nullable = false)
    private BigDecimal idcodden;

    @Column(name = "DSCODDEN", length = 100)
    private String dscodden;

    public Codden() {
    }

    public Codden(BigDecimal ntcodden, BigDecimal idcodden, String dscodden) {
        this.ntcodden = ntcodden;
        this.idcodden = idcodden;
        this.dscodden = dscodden;
    }

    // Getters y Setters

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
