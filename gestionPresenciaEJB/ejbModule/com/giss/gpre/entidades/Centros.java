package com.giss.gpre.entidades;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Bean de implementacion de la tabla de la base de datos CENTROS
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "CENTROS")
@IdClass(Centros_Key.class)
@NamedQueries({
    @NamedQuery(name = "Centros.findAll", query = "SELECT c FROM Centros c"),
    @NamedQuery(name = "Centros.findByEntiProvCen", query = "SELECT c FROM Centros c WHERE c.entiGesEp = ?1 AND c.provEp = ?2 AND c.cenGesEp = ?3"),
    @NamedQuery(name = "Centros.findByIdCentro", query = "SELECT c FROM Centros c WHERE c.idCentro = ?1")
})
@NamedStoredProcedureQuery(
	    name = "callGetVistaCentro",
	    procedureName = "packcentros.GetVistaCentro",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "Rc1", type = Void.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pCdUsuario", type = String.class)
	    }
	)
public class Centros implements Serializable {

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
    @Column(name = "IDCENTRO", length = 8, nullable = false)
    private String idCentro;

    @Column(name = "DSCENTRO", length = 50, nullable = false)
    private String dsCentro;

    @Column(name = "CLASEVIA", length = 2, nullable = false)
    private String claseVia;

    @Column(name = "CALLE", length = 20, nullable = false)
    private String calle;

    @Column(name = "NUMERO", length = 5, nullable = false)
    private String numero;

    @Column(name = "CODIGOPOSTAL", length = 5, nullable = false)
    private String codigoPostal;

    @Column(name = "LOCALIDAD", length = 60, nullable = false)
    private String localidad;

    @Column(name = "NTINCIDENCIA", precision = 6, scale = 0, nullable = false)
    private BigDecimal ntIncidencia;

    public Centros() {
    }

    public Centros(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String idCentro, 
            String dsCentro, String claseVia, String calle, String numero, String codigoPostal, 
            String localidad, BigDecimal ntIncidencia) {
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.idCentro = idCentro;
        this.dsCentro = dsCentro;
        this.claseVia = claseVia;
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.ntIncidencia = ntIncidencia;
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

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }

    public String getDsCentro() {
        return dsCentro;
    }

    public void setDsCentro(String dsCentro) {
        this.dsCentro = dsCentro;
    }

    public String getClaseVia() {
        return claseVia;
    }

    public void setClaseVia(String claseVia) {
        this.claseVia = claseVia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public BigDecimal getNtIncidencia() {
        return ntIncidencia;
    }

    public void setNtIncidencia(BigDecimal ntIncidencia) {
        this.ntIncidencia = ntIncidencia;
    }
}
