package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
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
@Table(name = "PERSONAS")
@NamedQueries({ 
	@NamedQuery(name = "personaByDNI", query = "select e from Persona e where e.docNacional = ?1"),
	@NamedQuery(name = "allUsers", query = "select e from Persona e"),
	@NamedQuery(name = "personasForGestor", query = "select p from Persona p "
			+ "WHERE p.enti_ges_ep = ?1 AND p.prov_ep = ?2 AND p.cen_ges_ep = ?3 "
			+ "ORDER BY p.apellido1, p.apellido2, p.nombre")
	})
@NamedStoredProcedureQueries({
@NamedStoredProcedureQuery(
	    name = "callGetBasicoPersona",
	    procedureName = "packpersonas.GetBasicoPersona",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "Rc1", type = Void.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pCdIddni", type = String.class)
	    }
	),
@NamedStoredProcedureQuery(
	    name = "callGetPralAccesoNew",
	    procedureName = "packacceso.GetPralAccesoNew",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "Rc1", type = Void.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pCdDNI", type = String.class)
	    }
	)
})
@IdClass(Persona_Key.class)
public class Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2989233745092637476L;

	@Id
	@Column(name="iddni")
	private String docNacional;
	@Column
	private String nombre;
	@Column
	private String apellido1;
	@Column
	private String apellido2;
	@Column
	private String cdempresa;
	@Column
	private String sexo;
	@Column
	private String fcnacimiento;
	@Column
	private String cdcentro;
	@Column
	private String cdunidad;
	@Column
	private BigDecimal ntcuerpo;
	@Column
	private String cdcuerpo;
	@Column
	private BigDecimal ntcodden;
	@Column
	private BigDecimal cdcodden;
	@Column
	private String telcontacto;
	@Column
	private String telinterno;
	@Column
	private BigDecimal cdtipopersonal;
	@Column
	private String fcalta;
	@Column
	private String fcbaja;
	@Column
	private String autorizarhoras;
	@Column
	private BigDecimal cdcalendario;
	@Column
	private BigDecimal cdhorario;
	@Column
	private String cdtarjeta;
	@Column
	private BigDecimal cdcontrato;
	@Column
	private BigDecimal cdproyecto;
	@Column(name="cdnivel")
	private BigDecimal escalon;
	@Column(name="cdnivelweb")
	private BigDecimal escalonweb;
	@Column
	private BigDecimal enti_ges_ep;
	@Column
	private BigDecimal prov_ep;
	@Column
	private BigDecimal cen_ges_ep;

	public Persona() {
	}

	/**
	 * @param iddni
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param cdempresa
	 * @param sexo
	 * @param fcnacimiento
	 * @param cdcentro
	 * @param cdunidad
	 * @param ntcuerpo
	 * @param cdcuerpo
	 * @param ntcodden
	 * @param cdcodden
	 * @param telcontacto
	 * @param telinterno
	 * @param cdtipopersonal
	 * @param fcalta
	 * @param fcbaja
	 * @param autorizarhoras
	 * @param cdcalendario
	 * @param cdhorario
	 * @param cdtarjeta
	 * @param cdcontrato
	 * @param cdproyecto
	 * @param cdnivel
	 * @param cdnivelweb
	 * @param entigesep
	 * @param provep
	 * @param cengesep
	 */
	public Persona(String docNacional, String nombre, String apellido1, String apellido2, String cdempresa, String sexo,
			String fcnacimiento, String cdcentro, String cdunidad, BigDecimal ntcuerpo, String cdcuerpo,
			BigDecimal ntcodden, BigDecimal cdcodden, String telcontacto, String telinterno, BigDecimal cdtipopersonal,
			String fcalta, String fcbaja, String autorizarhoras, BigDecimal cdcalendario, BigDecimal cdhorario,
			String cdtarjeta, BigDecimal cdcontrato, BigDecimal cdproyecto, BigDecimal escalon, BigDecimal escalonweb,
			BigDecimal entigesep, BigDecimal provep, BigDecimal cengesep) {
		super();
		this.docNacional = docNacional;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.cdempresa = cdempresa;
		this.sexo = sexo;
		this.fcnacimiento = fcnacimiento;
		this.cdcentro = cdcentro;
		this.cdunidad = cdunidad;
		this.ntcuerpo = ntcuerpo;
		this.cdcuerpo = cdcuerpo;
		this.ntcodden = ntcodden;
		this.cdcodden = cdcodden;
		this.telcontacto = telcontacto;
		this.telinterno = telinterno;
		this.cdtipopersonal = cdtipopersonal;
		this.fcalta = fcalta;
		this.fcbaja = fcbaja;
		this.autorizarhoras = autorizarhoras;
		this.cdcalendario = cdcalendario;
		this.cdhorario = cdhorario;
		this.cdtarjeta = cdtarjeta;
		this.cdcontrato = cdcontrato;
		this.cdproyecto = cdproyecto;
		this.escalon = escalon;
		this.escalonweb = escalonweb;
		this.enti_ges_ep = entigesep;
		this.prov_ep = provep;
		this.cen_ges_ep = cengesep;
	}

	/**
	 * @return el iddni
	 */
	public String getDocNacional() {
		return docNacional;
	}

	/**
	 * @param iddni
	 *            el iddni a establecer
	 */
	public void setDocNacional(String docNacional) {
		this.docNacional = docNacional;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * @param apellido1
	 *            el apellido1 a establecer
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * @return el apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * @param apellido2
	 *            el apellido2 a establecer
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * @return el cdempresa
	 */
	public String getCdempresa() {
		return cdempresa;
	}

	/**
	 * @param cdempresa
	 *            el cdempresa a establecer
	 */
	public void setCdempresa(String cdempresa) {
		this.cdempresa = cdempresa;
	}

	/**
	 * @return el sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo
	 *            el sexo a establecer
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return el fcnacimiento
	 */
	public String getFcnacimiento() {
		return fcnacimiento;
	}

	/**
	 * @param fcnacimiento
	 *            el fcnacimiento a establecer
	 */
	public void setFcnacimiento(String fcnacimiento) {
		this.fcnacimiento = fcnacimiento;
	}

	/**
	 * @return el cdcentro
	 */
	public String getCdcentro() {
		return cdcentro;
	}

	/**
	 * @param cdcentro
	 *            el cdcentro a establecer
	 */
	public void setCdcentro(String cdcentro) {
		this.cdcentro = cdcentro;
	}

	/**
	 * @return el cdunidad
	 */
	public String getCdunidad() {
		return cdunidad;
	}

	/**
	 * @param cdunidad
	 *            el cdunidad a establecer
	 */
	public void setCdunidad(String cdunidad) {
		this.cdunidad = cdunidad;
	}

	/**
	 * @return el ntcuerpo
	 */
	public BigDecimal getNtcuerpo() {
		return ntcuerpo;
	}

	/**
	 * @param ntcuerpo
	 *            el ntcuerpo a establecer
	 */
	public void setNtcuerpo(BigDecimal ntcuerpo) {
		this.ntcuerpo = ntcuerpo;
	}

	/**
	 * @return el cdcuerpo
	 */
	public String getCdcuerpo() {
		return cdcuerpo;
	}

	/**
	 * @param cdcuerpo
	 *            el cdcuerpo a establecer
	 */
	public void setCdcuerpo(String cdcuerpo) {
		this.cdcuerpo = cdcuerpo;
	}

	/**
	 * @return el ntcodden
	 */
	public BigDecimal getNtcodden() {
		return ntcodden;
	}

	/**
	 * @param ntcodden
	 *            el ntcodden a establecer
	 */
	public void setNtcodden(BigDecimal ntcodden) {
		this.ntcodden = ntcodden;
	}

	/**
	 * @return el cdcodden
	 */
	public BigDecimal getCdcodden() {
		return cdcodden;
	}

	/**
	 * @param cdcodden
	 *            el cdcodden a establecer
	 */
	public void setCdcodden(BigDecimal cdcodden) {
		this.cdcodden = cdcodden;
	}

	/**
	 * @return el telcontacto
	 */
	public String getTelcontacto() {
		return telcontacto;
	}

	/**
	 * @param telcontacto
	 *            el telcontacto a establecer
	 */
	public void setTelcontacto(String telcontacto) {
		this.telcontacto = telcontacto;
	}

	/**
	 * @return el telinterno
	 */
	public String getTelinterno() {
		return telinterno;
	}

	/**
	 * @param telinterno
	 *            el telinterno a establecer
	 */
	public void setTelinterno(String telinterno) {
		this.telinterno = telinterno;
	}

	/**
	 * @return el cdtipopersonal
	 */
	public BigDecimal getCdtipopersonal() {
		return cdtipopersonal;
	}

	/**
	 * @param cdtipopersonal
	 *            el cdtipopersonal a establecer
	 */
	public void setCdtipopersonal(BigDecimal cdtipopersonal) {
		this.cdtipopersonal = cdtipopersonal;
	}

	/**
	 * @return el fcalta
	 */
	public String getFcalta() {
		return fcalta;
	}

	/**
	 * @param fcalta
	 *            el fcalta a establecer
	 */
	public void setFcalta(String fcalta) {
		this.fcalta = fcalta;
	}

	/**
	 * @return el fcbaja
	 */
	public String getFcbaja() {
		return fcbaja;
	}

	/**
	 * @param fcbaja
	 *            el fcbaja a establecer
	 */
	public void setFcbaja(String fcbaja) {
		this.fcbaja = fcbaja;
	}

	/**
	 * @return el autorizarhoras
	 */
	public String getAutorizarhoras() {
		return autorizarhoras;
	}

	/**
	 * @param autorizarhoras
	 *            el autorizarhoras a establecer
	 */
	public void setAutorizarhoras(String autorizarhoras) {
		this.autorizarhoras = autorizarhoras;
	}

	/**
	 * @return el cdcalendario
	 */
	public BigDecimal getCdcalendario() {
		return cdcalendario;
	}

	/**
	 * @param cdcalendario
	 *            el cdcalendario a establecer
	 */
	public void setCdcalendario(BigDecimal cdcalendario) {
		this.cdcalendario = cdcalendario;
	}

	/**
	 * @return el cdhorario
	 */
	public BigDecimal getCdhorario() {
		return cdhorario;
	}

	/**
	 * @param cdhorario
	 *            el cdhorario a establecer
	 */
	public void setCdhorario(BigDecimal cdhorario) {
		this.cdhorario = cdhorario;
	}

	/**
	 * @return el cdtarjeta
	 */
	public String getCdtarjeta() {
		return cdtarjeta;
	}

	/**
	 * @param cdtarjeta
	 *            el cdtarjeta a establecer
	 */
	public void setCdtarjeta(String cdtarjeta) {
		this.cdtarjeta = cdtarjeta;
	}

	/**
	 * @return el cdcontrato
	 */
	public BigDecimal getCdcontrato() {
		return cdcontrato;
	}

	/**
	 * @param cdcontrato
	 *            el cdcontrato a establecer
	 */
	public void setCdcontrato(BigDecimal cdcontrato) {
		this.cdcontrato = cdcontrato;
	}

	/**
	 * @return el cdproyecto
	 */
	public BigDecimal getCdproyecto() {
		return cdproyecto;
	}

	/**
	 * @param cdproyecto
	 *            el cdproyecto a establecer
	 */
	public void setCdproyecto(BigDecimal cdproyecto) {
		this.cdproyecto = cdproyecto;
	}

	/**
	 * @return el cdnivel
	 */
	public BigDecimal getCdnivel() {
		return escalon;
	}

	/**
	 * @param cdnivel
	 *            el cdnivel a establecer
	 */
	public void setCdnivel(BigDecimal escalon) {
		this.escalon = escalon;
	}

	/**
	 * @return el cdnivelweb
	 */
	public BigDecimal getCdnivelweb() {
		return escalonweb;
	}

	/**
	 * @param cdnivelweb
	 *            el cdnivelweb a establecer
	 */
	public void setCdnivelweb(BigDecimal escalonweb) {
		this.escalonweb = escalonweb;
	}

	/**
	 * @return el entigesep
	 */
	public BigDecimal getEntigesep() {
		return enti_ges_ep;
	}

	/**
	 * @param entigesep
	 *            el entigesep a establecer
	 */
	public void setEntigesep(BigDecimal entigesep) {
		this.enti_ges_ep = entigesep;
	}

	/**
	 * @return el provep
	 */
	public BigDecimal getProvep() {
		return prov_ep;
	}

	/**
	 * @param provep
	 *            el provep a establecer
	 */
	public void setProvep(BigDecimal provep) {
		this.prov_ep = provep;
	}

	/**
	 * @return el cengesep
	 */
	public BigDecimal getCengesep() {
		return cen_ges_ep;
	}

	/**
	 * @param cengesep
	 *            el cengesep a establecer
	 */
	public void setCengesep(BigDecimal cengesep) {
		this.cen_ges_ep = cengesep;
	}
}