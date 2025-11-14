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
 * Bean de implementacion de la tabla de la base de datos HISTORICOSITUACIONES
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "HISTORICOSITUACIONES")
@IdClass(HistoricoSituaciones_Key.class)
public class HistoricoSituaciones implements Serializable {

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
    @Column(name = "CDDNI", length = 10, nullable = false)
    private String cddni;

    @Column(name = "FCINICIO", length = 8)
    private String fcinicio;

    @Column(name = "FCFIN", length = 8)
    private String fcfin;

    @Column(name = "CDCENTRO", length = 8)
    private String cdcentro;

    @Column(name = "CDUNIDAD", length = 8)
    private String cdunidad;

    @Column(name = "CDEMPRESA", length = 12)
    private String cdempresa;

    @Column(name = "CDCONTRATO", precision = 10, scale = 0)
    private BigDecimal cdcontrato;

    @Column(name = "NTCODDEN", precision = 10, scale = 0)
    private BigDecimal ntcodden;

    @Column(name = "CDCODDEN", precision = 10, scale = 0)
    private BigDecimal cdcodden;

    public HistoricoSituaciones() {
    }

    public HistoricoSituaciones(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String cddni,
            String fcinicio, String fcfin, String cdcentro, String cdunidad, String cdempresa,
            BigDecimal cdcontrato, BigDecimal ntcodden, BigDecimal cdcodden) {
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.cddni = cddni;
        this.fcinicio = fcinicio;
        this.fcfin = fcfin;
        this.cdcentro = cdcentro;
        this.cdunidad = cdunidad;
        this.cdempresa = cdempresa;
        this.cdcontrato = cdcontrato;
        this.ntcodden = ntcodden;
        this.cdcodden = cdcodden;
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

    public String getCddni() {
        return cddni;
    }

    public void setCddni(String cddni) {
        this.cddni = cddni;
    }

    public String getFcinicio() {
        return fcinicio;
    }

    public void setFcinicio(String fcinicio) {
        this.fcinicio = fcinicio;
    }

    public String getFcfin() {
        return fcfin;
    }

    public void setFcfin(String fcfin) {
        this.fcfin = fcfin;
    }

    public String getCdcentro() {
        return cdcentro;
    }

    public void setCdcentro(String cdcentro) {
        this.cdcentro = cdcentro;
    }

    public String getCdunidad() {
        return cdunidad;
    }

    public void setCdunidad(String cdunidad) {
        this.cdunidad = cdunidad;
    }

    public String getCdempresa() {
        return cdempresa;
    }

    public void setCdempresa(String cdempresa) {
        this.cdempresa = cdempresa;
    }

    public BigDecimal getCdcontrato() {
        return cdcontrato;
    }

    public void setCdcontrato(BigDecimal cdcontrato) {
        this.cdcontrato = cdcontrato;
    }

    public BigDecimal getNtcodden() {
        return ntcodden;
    }

    public void setNtcodden(BigDecimal ntcodden) {
        this.ntcodden = ntcodden;
    }

    public BigDecimal getCdcodden() {
        return cdcodden;
    }

    public void setCdcodden(BigDecimal cdcodden) {
        this.cdcodden = cdcodden;
    }
}
