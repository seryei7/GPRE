package com.giss.gpre.entidades;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

/**
 * Bean de implementacion de la tabla de la base de datos EMPRESAS
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "EMPRESAS")
@IdClass(Empresas_Key.class)
@NamedQueries({
    @NamedQuery(name = "Empresas.findAll", query = "SELECT e FROM Empresas e"),
    @NamedQuery(name = "Empresas.findByIdCifNif", query = "SELECT e FROM Empresas e WHERE e.idFiscal = ?1"),
    @NamedQuery(name = "Empresas.findByNombre", query = "SELECT e FROM Empresas e WHERE e.nombre LIKE ?1"),
    @NamedQuery(name = "Empresas.findActivas", query = "SELECT e FROM Empresas e WHERE e.fcBaja = '00000000'")
})
@NamedStoredProcedureQuery(
	    name = "callGetVistaEmpresa",
	    procedureName = "packempresas.GetVistaEmpresa",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "Rc1", type = Void.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pCdUsuario", type = String.class)
	    }
	)
public class Empresas implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Column(name = "FCALTA", length = 8, nullable = false)
    private String fcAlta;

    @Column(name = "FCBAJA", length = 8, nullable = false)
    private String fcBaja;

    public Empresas() {
    }

    public Empresas(String idFiscal, String nombre, String claseVia, String calle, String numero,
            String codigoPostal, String localidad, String telefono, String fax, String perContacto,
            String fcAlta, String fcBaja) {
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
        this.fcAlta = fcAlta;
        this.fcBaja = fcBaja;
    }

    // Getters y Setters

    public String getIdFiscal() {
        return idFiscal;
    }

    public void setIdFiscal(String idFiscal) {
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

    public String getFcAlta() {
        return fcAlta;
    }

    public void setFcAlta(String fcAlta) {
        this.fcAlta = fcAlta;
    }

    public String getFcBaja() {
        return fcBaja;
    }

    public void setFcBaja(String fcBaja) {
        this.fcBaja = fcBaja;
    }
}
