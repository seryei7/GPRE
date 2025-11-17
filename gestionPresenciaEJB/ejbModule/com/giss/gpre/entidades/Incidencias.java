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
 * Bean de implementacion de la tabla de la base de datos Acesso_area_nivel
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "INCIDENCIAS")
@NamedQueries({ @NamedQuery(name = "allIncidencias", query = "SELECT e FROM Incidencias e ") })
@IdClass(Incidencias_Key.class)
public class Incidencias implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3872046225301559641L;
	
	@Id
	private Integer ntincidencia;
	@Id
	private Integer idincidencia;
	@Column
	private String dsincidencia;
	@Column
	private String dsincidenciared;
	@Column
	private Integer acumula;
	@Column
	private String absentismo;
	@Column
	private String productividad;
	@Column
	private String diashoras;
	@Column
	private String reloj;
	@Column
	private String apertautom;
	@Column
	private Integer lapsoentrafija;
	@Column
	private Integer lapsoentrafijaasoc;
	@Column
	private String aperautmn;
	@Column
	private Integer aperautmnasoc;
	@Column
	private String cierreautom;
	@Column
	private Integer lapsosalfija;
	@Column
	private Integer lapsosalfijaasoc;
	@Column
	private String cierreautmn;
	@Column
	private Integer cierreautmnasoc;
	@Column
	private Integer cdincidenciaasociada;
	@Column
	private Integer numdiasincidenciaasociada;
	@Column
	private String fcinicio;
	@Column
	private String fcfin;
	
	public Incidencias() {
	}

	public Integer getNtincidencia() {
		return ntincidencia;
	}

	public void setNtincidencia(Integer ntincidencia) {
		this.ntincidencia = ntincidencia;
	}

	public Integer getIdincidencia() {
		return idincidencia;
	}

	public void setIdincidencia(Integer idincidencia) {
		this.idincidencia = idincidencia;
	}

	public String getDsincidencia() {
		return dsincidencia;
	}

	public void setDsincidencia(String dsincidencia) {
		this.dsincidencia = dsincidencia;
	}

	public String getDsincidenciared() {
		return dsincidenciared;
	}

	public void setDsincidenciared(String dsincidenciared) {
		this.dsincidenciared = dsincidenciared;
	}

	public Integer getAcumula() {
		return acumula;
	}

	public void setAcumula(Integer acumula) {
		this.acumula = acumula;
	}

	public String getAbsentismo() {
		return absentismo;
	}

	public void setAbsentismo(String absentismo) {
		this.absentismo = absentismo;
	}

	public String getProductividad() {
		return productividad;
	}

	public void setProductividad(String productividad) {
		this.productividad = productividad;
	}

	public String getDiashoras() {
		return diashoras;
	}

	public void setDiashoras(String diashoras) {
		this.diashoras = diashoras;
	}

	public String getReloj() {
		return reloj;
	}

	public void setReloj(String reloj) {
		this.reloj = reloj;
	}

	public String getApertautom() {
		return apertautom;
	}

	public void setApertautom(String apertautom) {
		this.apertautom = apertautom;
	}

	public Integer getLapsoentrafija() {
		return lapsoentrafija;
	}

	public void setLapsoentrafija(Integer lapsoentrafija) {
		this.lapsoentrafija = lapsoentrafija;
	}

	public Integer getLapsoentrafijaasoc() {
		return lapsoentrafijaasoc;
	}

	public void setLapsoentrafijaasoc(Integer lapsoentrafijaasoc) {
		this.lapsoentrafijaasoc = lapsoentrafijaasoc;
	}

	public String getAperautmn() {
		return aperautmn;
	}

	public void setAperautmn(String aperautmn) {
		this.aperautmn = aperautmn;
	}

	public Integer getAperautmnasoc() {
		return aperautmnasoc;
	}

	public void setAperautmnasoc(Integer aperautmnasoc) {
		this.aperautmnasoc = aperautmnasoc;
	}

	public String getCierreautom() {
		return cierreautom;
	}

	public void setCierreautom(String cierreautom) {
		this.cierreautom = cierreautom;
	}

	public Integer getLapsosalfija() {
		return lapsosalfija;
	}

	public void setLapsosalfija(Integer lapsosalfija) {
		this.lapsosalfija = lapsosalfija;
	}

	public Integer getLapsosalfijaasoc() {
		return lapsosalfijaasoc;
	}

	public void setLapsoasalfijaasoc(Integer lapsosalfijaasoc) {
		this.lapsosalfijaasoc = lapsosalfijaasoc;
	}

	public String getCierreautmn() {
		return cierreautmn;
	}

	public void setCierreautmn(String cierreautmn) {
		this.cierreautmn = cierreautmn;
	}

	public Integer getCierreautmnasoc() {
		return cierreautmnasoc;
	}

	public void setCierreautmnasoc(Integer cierreautmnasoc) {
		this.cierreautmnasoc = cierreautmnasoc;
	}

	public Integer getCdincidenciaasociada() {
		return cdincidenciaasociada;
	}

	public void setCdincidenciaasociada(Integer cdincidenciaasociada) {
		this.cdincidenciaasociada = cdincidenciaasociada;
	}

	public Integer getNumdiasincidenciaasociada() {
		return numdiasincidenciaasociada;
	}

	public void setNumdiasincidenciaasociada(Integer numdiasincidenciaasociada) {
		this.numdiasincidenciaasociada = numdiasincidenciaasociada;
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

	
}