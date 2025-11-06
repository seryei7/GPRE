package com.giss.gpre.entidades;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Bean de implementacion de la tabla de la base de datos UNIDADES
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "UNIDADES")
@IdClass(Unidades_Key.class)
@NamedQueries({
    @NamedQuery(name = "Unidades.findAll", query = "SELECT u FROM Unidades u"),
    @NamedQuery(name = "Unidades.findByEntiProvCenCentro", query = "SELECT u FROM Unidades u WHERE u.entiGesEp = ?1 AND u.provEp = ?2 AND u.cenGesEp = ?3 AND u.cdCentro = ?4"),
    @NamedQuery(name = "Unidades.findByIdUnidad", query = "SELECT u FROM Unidades u WHERE u.idUnidad = ?1"),
    @NamedQuery(name = "Unidades.findBySituacion", query = "SELECT u FROM Unidades u WHERE u.situacion = ?1")
})
@NamedStoredProcedureQuery(
	    name = "callGetVistaUnidad",
	    procedureName = "packunidades.GetVistaUnidad",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "Rc1", type = Void.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pEntiGesEp", type = BigDecimal.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pProvEp", type = BigDecimal.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "PCenGesEp", type = BigDecimal.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pCdCentro", type = String.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pCdUsuario", type = String.class)
	    }
	)
public class Unidades implements Serializable {

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
    @Column(name = "CDCENTRO", length = 8, nullable = false)
    private String cdCentro;

    @Id
    @Column(name = "IDUNIDAD", length = 8, nullable = false)
    private String idUnidad;

    @Column(name = "DSUNIDAD", length = 50, nullable = false)
    private String dsUnidad;

    @Column(name = "CDUNIDADPADRE", length = 8, nullable = false)
    private String cdUnidadPadre;

    @Column(name = "CDUNIDADORD", length = 250)
    private String cdUnidadOrd;

    @Column(name = "SITUACION", length = 1, nullable = false)
    private String situacion;

    @Column(name = "FECHA_SITUACION", length = 8, nullable = false)
    private String fechaSituacion;

    public Unidades() {
    }

    public Unidades(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String cdCentro,
            String idUnidad, String dsUnidad, String cdUnidadPadre, String cdUnidadOrd, 
            String situacion, String fechaSituacion) {
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.cdCentro = cdCentro;
        this.idUnidad = idUnidad;
        this.dsUnidad = dsUnidad;
        this.cdUnidadPadre = cdUnidadPadre;
        this.cdUnidadOrd = cdUnidadOrd;
        this.situacion = situacion;
        this.fechaSituacion = fechaSituacion;
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

    public String getCdCentro() {
        return cdCentro;
    }

    public void setCdCentro(String cdCentro) {
        this.cdCentro = cdCentro;
    }

    public String getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(String idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getDsUnidad() {
        return dsUnidad;
    }

    public void setDsUnidad(String dsUnidad) {
        this.dsUnidad = dsUnidad;
    }

    public String getCdUnidadPadre() {
        return cdUnidadPadre;
    }

    public void setCdUnidadPadre(String cdUnidadPadre) {
        this.cdUnidadPadre = cdUnidadPadre;
    }

    public String getCdUnidadOrd() {
        return cdUnidadOrd;
    }

    public void setCdUnidadOrd(String cdUnidadOrd) {
        this.cdUnidadOrd = cdUnidadOrd;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getFechaSituacion() {
        return fechaSituacion;
    }

    public void setFechaSituacion(String fechaSituacion) {
        this.fechaSituacion = fechaSituacion;
    }
}
