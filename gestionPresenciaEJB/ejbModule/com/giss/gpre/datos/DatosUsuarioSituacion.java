package com.giss.gpre.datos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO para el resultado de la consulta de situación de usuarios
 * Contiene información combinada de PERSONAS, HISTORICOSITUACIONES, UNIDADES, 
 * EMPRESAS, CONTRATOS, CODDEN y CODDEN_EMP
 * 
 * @author GISS
 */
public class DatosUsuarioSituacion implements Serializable {

    private static final long serialVersionUID = 1L;

    // Datos de PERSONAS
    private String apellido1;
    private String apellido2;
    private String nombre;
    private String iddni;
    private String cdtarjeta;

    // Datos de HISTORICOSITUACIONES (entidad, provincia, centro)
    private BigDecimal entiGesEp;
    private BigDecimal provEp;
    private BigDecimal cenGesEp;

    // Datos de UNIDADES
    private String dsunidad;

    // Datos de EMPRESAS
    private String cdempresa;
    private String nombreEmpresa;

    // Datos de CONTRATOS
    private String dscontrato;
    private String dscontratored;

    // Datos de CODDEN
    private BigDecimal ntcodden;
    private BigDecimal cdcodden;
    private String dscodden;

    // Datos de CODDEN_EMP
    private String dscoddenEmp;

    public DatosUsuarioSituacion() {
    }

    /**
     * Constructor para mapear resultados de la query nativa
     */
    public DatosUsuarioSituacion(String apellido1, String apellido2, String nombre, String iddni, 
                                 String cdtarjeta, BigDecimal entiGesEp, BigDecimal provEp, 
                                 BigDecimal cenGesEp, String dsunidad, String cdempresa, 
                                 String nombreEmpresa, String dscontrato, String dscontratored, 
                                 BigDecimal ntcodden, BigDecimal cdcodden, String dscodden, 
                                 String dscoddenEmp) {
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.nombre = nombre;
        this.iddni = iddni;
        this.cdtarjeta = cdtarjeta;
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.dsunidad = dsunidad;
        this.cdempresa = cdempresa;
        this.nombreEmpresa = nombreEmpresa;
        this.dscontrato = dscontrato;
        this.dscontratored = dscontratored;
        this.ntcodden = ntcodden;
        this.cdcodden = cdcodden;
        this.dscodden = dscodden;
        this.dscoddenEmp = dscoddenEmp;
    }

    // Getters y Setters

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIddni() {
        return iddni;
    }

    public void setIddni(String iddni) {
        this.iddni = iddni;
    }

    public String getCdtarjeta() {
        return cdtarjeta;
    }

    public void setCdtarjeta(String cdtarjeta) {
        this.cdtarjeta = cdtarjeta;
    }

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

    public String getDsunidad() {
        return dsunidad;
    }

    public void setDsunidad(String dsunidad) {
        this.dsunidad = dsunidad;
    }

    public String getCdempresa() {
        return cdempresa;
    }

    public void setCdempresa(String cdempresa) {
        this.cdempresa = cdempresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
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

    public String getDscodden() {
        return dscodden;
    }

    public void setDscodden(String dscodden) {
        this.dscodden = dscodden;
    }

    public String getDscoddenEmp() {
        return dscoddenEmp;
    }

    public void setDscoddenEmp(String dscoddenEmp) {
        this.dscoddenEmp = dscoddenEmp;
    }

    @Override
    public String toString() {
        return "DatosUsuarioSituacion [" +
                "nombre=" + nombre + 
                ", apellido1=" + apellido1 + 
                ", apellido2=" + apellido2 + 
                ", iddni=" + iddni + 
                ", cdtarjeta=" + cdtarjeta + 
                ", nombreEmpresa=" + nombreEmpresa + 
                "]";
    }
}
