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
import javax.persistence.Table;

/**
 * Bean de implementacion de la tabla de la base de datos INFORMESALDOMES
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "INFORMESALDOMES")
@NamedQueries({ 
    @NamedQuery(name = "allInformeSaldoMes", query = "SELECT e FROM InformeSaldoMes e "),
    @NamedQuery(name = "informeByUsuario", query = "SELECT e FROM InformeSaldoMes e WHERE e.cdusuario = :cdusuario"),
    @NamedQuery(name = "informeByFecha", query = "SELECT e FROM InformeSaldoMes e WHERE e.fcfichada = :fcfichada")
})
@IdClass(InformeSaldoMes_Key.class)
public class InformeSaldoMes implements Serializable {

    private static final long serialVersionUID = -3872046225301559642L;
    
    @Id
    @Column(name = "CDUSUARIO")
    private String cdusuario;
    
    @Id
    @Column(name = "ENTI_GES_EP")
    private Integer entiGesEp;
    
    @Id
    @Column(name = "PROV_EP")
    private Integer provEp;
    
    @Id
    @Column(name = "CEN_GES_EP")
    private Integer cenGesEp;
    
    @Id
    @Column(name = "CDDNI")
    private String cdDocNacional;
    
    @Id
    @Column(name = "FCFICHADA")
    private String fcfichada;
    
    @Id
    @Column(name = "CDEMPRESA")
    private String cdempresa;
    
    @Id
    @Column(name = "FCINISEM")
    private String fcinisem;
    
    @Id
    @Column(name = "FCINIMES")
    private String fcinimes;
    
    @Column(name = "DSCENTRO")
    private String dscentro;
    
    @Column(name = "CDUNIDAD")
    private String cdunidad;
    
    @Column(name = "DSUNIDAD")
    private String dsunidad;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "DSCODDEN")
    private String dscodden;
    
    @Column(name = "CDHORARIO")
    private Integer cdhorario;
    
    @Column(name = "NOMBREEMPRESA")
    private String nombreempresa;
    
    @Column(name = "DSCONTRATORED")
    private String dscontratored;
    
    @Column(name = "DSPROYECTO")
    private String dsproyecto;
    
    @Column(name = "MNFICHADA1")
    private String mnfichada1;
    
    @Column(name = "CDINCIDENCIA1")
    private Integer cdincidencia1;
    
    @Column(name = "MNFICHADA2")
    private String mnfichada2;
    
    @Column(name = "CDINCIDENCIA2")
    private Integer cdincidencia2;
    
    @Column(name = "MNFICHADA3")
    private String mnfichada3;
    
    @Column(name = "CDINCIDENCIA3")
    private Integer cdincidencia3;
    
    @Column(name = "MNFICHADA4")
    private String mnfichada4;
    
    @Column(name = "CDINCIDENCIA4")
    private Integer cdincidencia4;
    
    @Column(name = "MNFICHADA5")
    private String mnfichada5;
    
    @Column(name = "CDINCIDENCIA5")
    private Integer cdincidencia5;
    
    @Column(name = "MNFICHADA6")
    private String mnfichada6;
    
    @Column(name = "CDINCIDENCIA6")
    private Integer cdincidencia6;
    
    @Column(name = "MNFICHADA7")
    private String mnfichada7;
    
    @Column(name = "CDINCIDENCIA7")
    private Integer cdincidencia7;
    
    @Column(name = "MNFICHADA8")
    private String mnfichada8;
    
    @Column(name = "CDINCIDENCIA8")
    private Integer cdincidencia8;
    
    @Column(name = "SALDO")
    private String saldo;
    
    @Column(name = "SALDOSEMANA")
    private String saldosemana;
    
    @Column(name = "SALDOMENSUAL")
    private String saldomensual;
    
    @Column(name = "FCFINSEM")
    private String fcfinsem;
    
    @Column(name = "FCFINMES")
    private String fcfinmes;
    
    @Column(name = "TOT_ENTI_GES_EP")
    private Integer totEntiGesEp;
    
    @Column(name = "TOT_PROV_EP")
    private Integer totProvEp;
    
    @Column(name = "TOT_CEN_GES_EP")
    private Integer totCenGesEp;
    
    @Column(name = "FECHA_LIS")
    private String fechaLis;
    
    @Column(name = "HORA_LIS")
    private String horaLis;
    
    public InformeSaldoMes() {
    }

    public String getCdusuario() {
        return cdusuario;
    }

    public void setCdusuario(String cdusuario) {
        this.cdusuario = cdusuario;
    }

    public Integer getEntiGesEp() {
        return entiGesEp;
    }

    public void setEntiGesEp(Integer entiGesEp) {
        this.entiGesEp = entiGesEp;
    }

    public Integer getProvEp() {
        return provEp;
    }

    public void setProvEp(Integer provEp) {
        this.provEp = provEp;
    }

    public Integer getCenGesEp() {
        return cenGesEp;
    }

    public void setCenGesEp(Integer cenGesEp) {
        this.cenGesEp = cenGesEp;
    }

    public String getCdDocNacional() {
        return cdDocNacional;
    }

    public void setCdDocNacional(String cdDocNacional) {
        this.cdDocNacional = cdDocNacional;
    }

    public String getFcfichada() {
        return fcfichada;
    }

    public void setFcfichada(String fcfichada) {
        this.fcfichada = fcfichada;
    }

    public String getCdempresa() {
        return cdempresa;
    }

    public void setCdempresa(String cdempresa) {
        this.cdempresa = cdempresa;
    }

    public String getFcinisem() {
        return fcinisem;
    }

    public void setFcinisem(String fcinisem) {
        this.fcinisem = fcinisem;
    }

    public String getFcinimes() {
        return fcinimes;
    }

    public void setFcinimes(String fcinimes) {
        this.fcinimes = fcinimes;
    }

    public String getDscentro() {
        return dscentro;
    }

    public void setDscentro(String dscentro) {
        this.dscentro = dscentro;
    }

    public String getCdunidad() {
        return cdunidad;
    }

    public void setCdunidad(String cdunidad) {
        this.cdunidad = cdunidad;
    }

    public String getDsunidad() {
        return dsunidad;
    }

    public void setDsunidad(String dsunidad) {
        this.dsunidad = dsunidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDscodden() {
        return dscodden;
    }

    public void setDscodden(String dscodden) {
        this.dscodden = dscodden;
    }

    public Integer getCdhorario() {
        return cdhorario;
    }

    public void setCdhorario(Integer cdhorario) {
        this.cdhorario = cdhorario;
    }

    public String getNombreempresa() {
        return nombreempresa;
    }

    public void setNombreempresa(String nombreempresa) {
        this.nombreempresa = nombreempresa;
    }

    public String getDscontratored() {
        return dscontratored;
    }

    public void setDscontratored(String dscontratored) {
        this.dscontratored = dscontratored;
    }

    public String getDsproyecto() {
        return dsproyecto;
    }

    public void setDsproyecto(String dsproyecto) {
        this.dsproyecto = dsproyecto;
    }

    public String getMnfichada1() {
        return mnfichada1;
    }

    public void setMnfichada1(String mnfichada1) {
        this.mnfichada1 = mnfichada1;
    }

    public Integer getCdincidencia1() {
        return cdincidencia1;
    }

    public void setCdincidencia1(Integer cdincidencia1) {
        this.cdincidencia1 = cdincidencia1;
    }

    public String getMnfichada2() {
        return mnfichada2;
    }

    public void setMnfichada2(String mnfichada2) {
        this.mnfichada2 = mnfichada2;
    }

    public Integer getCdincidencia2() {
        return cdincidencia2;
    }

    public void setCdincidencia2(Integer cdincidencia2) {
        this.cdincidencia2 = cdincidencia2;
    }

    public String getMnfichada3() {
        return mnfichada3;
    }

    public void setMnfichada3(String mnfichada3) {
        this.mnfichada3 = mnfichada3;
    }

    public Integer getCdincidencia3() {
        return cdincidencia3;
    }

    public void setCdincidencia3(Integer cdincidencia3) {
        this.cdincidencia3 = cdincidencia3;
    }

    public String getMnfichada4() {
        return mnfichada4;
    }

    public void setMnfichada4(String mnfichada4) {
        this.mnfichada4 = mnfichada4;
    }

    public Integer getCdincidencia4() {
        return cdincidencia4;
    }

    public void setCdincidencia4(Integer cdincidencia4) {
        this.cdincidencia4 = cdincidencia4;
    }

    public String getMnfichada5() {
        return mnfichada5;
    }

    public void setMnfichada5(String mnfichada5) {
        this.mnfichada5 = mnfichada5;
    }

    public Integer getCdincidencia5() {
        return cdincidencia5;
    }

    public void setCdincidencia5(Integer cdincidencia5) {
        this.cdincidencia5 = cdincidencia5;
    }

    public String getMnfichada6() {
        return mnfichada6;
    }

    public void setMnfichada6(String mnfichada6) {
        this.mnfichada6 = mnfichada6;
    }

    public Integer getCdincidencia6() {
        return cdincidencia6;
    }

    public void setCdincidencia6(Integer cdincidencia6) {
        this.cdincidencia6 = cdincidencia6;
    }

    public String getMnfichada7() {
        return mnfichada7;
    }

    public void setMnfichada7(String mnfichada7) {
        this.mnfichada7 = mnfichada7;
    }

    public Integer getCdincidencia7() {
        return cdincidencia7;
    }

    public void setCdincidencia7(Integer cdincidencia7) {
        this.cdincidencia7 = cdincidencia7;
    }

    public String getMnfichada8() {
        return mnfichada8;
    }

    public void setMnfichada8(String mnfichada8) {
        this.mnfichada8 = mnfichada8;
    }

    public Integer getCdincidencia8() {
        return cdincidencia8;
    }

    public void setCdincidencia8(Integer cdincidencia8) {
        this.cdincidencia8 = cdincidencia8;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getSaldosemana() {
        return saldosemana;
    }

    public void setSaldosemana(String saldosemana) {
        this.saldosemana = saldosemana;
    }

    public String getSaldomensual() {
        return saldomensual;
    }

    public void setSaldomensual(String saldomensual) {
        this.saldomensual = saldomensual;
    }

    public String getFcfinsem() {
        return fcfinsem;
    }

    public void setFcfinsem(String fcfinsem) {
        this.fcfinsem = fcfinsem;
    }

    public String getFcfinmes() {
        return fcfinmes;
    }

    public void setFcfinmes(String fcfinmes) {
        this.fcfinmes = fcfinmes;
    }

    public Integer getTotEntiGesEp() {
        return totEntiGesEp;
    }

    public void setTotEntiGesEp(Integer totEntiGesEp) {
        this.totEntiGesEp = totEntiGesEp;
    }

    public Integer getTotProvEp() {
        return totProvEp;
    }

    public void setTotProvEp(Integer totProvEp) {
        this.totProvEp = totProvEp;
    }

    public Integer getTotCenGesEp() {
        return totCenGesEp;
    }

    public void setTotCenGesEp(Integer totCenGesEp) {
        this.totCenGesEp = totCenGesEp;
    }

    public String getFechaLis() {
        return fechaLis;
    }

    public void setFechaLis(String fechaLis) {
        this.fechaLis = fechaLis;
    }

    public String getHoraLis() {
        return horaLis;
    }

    public void setHoraLis(String horaLis) {
        this.horaLis = horaLis;
    }
}