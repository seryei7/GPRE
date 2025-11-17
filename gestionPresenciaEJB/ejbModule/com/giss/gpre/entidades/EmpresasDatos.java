package com.giss.gpre.entidades;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Bean de implementacion de la tabla de la base de datos EMPRESASDATOS
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "EMPRESASDATOS")
@IdClass(EmpresasDatos_Key.class)
@NamedQueries({
    @NamedQuery(name = "EmpresasDatos.findAll", query = "SELECT e FROM EmpresasDatos e"),
    @NamedQuery(name = "EmpresasDatos.findByEntiProvCen", query = "SELECT e FROM EmpresasDatos e WHERE e.entiGesEp = ?1 AND e.provEp = ?2 AND e.cenGesEp = ?3"),
    @NamedQuery(name = "EmpresasDatos.findByIdCifNif", query = "SELECT e FROM EmpresasDatos e WHERE e.idFiscal = ?1"),
    @NamedQuery(name = "EmpresasDatos.findByNombre", query = "SELECT e FROM EmpresasDatos e WHERE e.nombre LIKE ?1")
})
public class EmpresasDatos implements Serializable {

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
    @Column(name = "IDCIFNIF", length = 12, nullable = false)
    private String idFiscal;

    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

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

    @Column(name = "TELEFONO", length = 12, nullable = false)
    private String telefono;

    @Column(name = "FAX", length = 12, nullable = false)
    private String fax;

    @Column(name = "PERCONTACTO", length = 50, nullable = false)
    private String perContacto;

    public EmpresasDatos() {
    }

    public EmpresasDatos(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String idFiscal,
            String nombre, String claseVia, String calle, String numero, String codigoPostal, String localidad,
            String telefono, String fax, String perContacto) {
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.idFiscal = idFiscal;
        this.nombre = nombre;
        this.claseVia = claseVia;
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.telefono = telefono;
        this.fax = fax;
        this.perContacto = perContacto;
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

    public String getIdFiscal() {
        return idFiscal;
    }

    public void setIdCifNif(String idFiscal) {
        this.idFiscal = idFiscal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPerContacto() {
        return perContacto;
    }

    public void setPerContacto(String perContacto) {
        this.perContacto = perContacto;
    }
}
