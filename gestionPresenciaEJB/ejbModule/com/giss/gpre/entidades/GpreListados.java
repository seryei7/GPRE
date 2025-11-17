package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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
 * Bean de implementacion de la tabla de la base de datos GPRE_LISTADOS
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "GPRE_LISTADOS", schema = "CP_ADMIN")
@IdClass(GpreListados_Key.class)
@NamedQueries({ @NamedQuery(name = "GpreListados.findAll", query = "SELECT g FROM GpreListados g"),
		@NamedQuery(name = "GpreListados.findById", query = "SELECT g FROM GpreListados g WHERE g.idListado = ?1"),
		@NamedQuery(name = "GpreListados.findByUsuario", query = "SELECT g FROM GpreListados g WHERE g.cDocNacional = ?1") })
@NamedStoredProcedureQuery(name = "callGeneraListado", procedureName = "CP_ADMIN.GPRE_WEB.genera_listado", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_listado", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_CFINICIO", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_CFFIN", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_CDNIUSR", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_CDETALLE", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_CDNIPER", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_CFECHA", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_NENTIGESEP", type = BigDecimal.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_NPROVEP", type = BigDecimal.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_NCENGESEP", type = BigDecimal.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_NHORAINICIO", type = BigDecimal.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_NHORAFIN", type = BigDecimal.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_CCONJUNTO", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_CTIPO", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_VG_OPCION", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_NOMINFORME", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_NNUMREGISTROS", type = BigDecimal.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_CERRCOD", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_CERRDES", type = String.class) })

public class GpreListados implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	private BigDecimal idListado;

	@Column(name = "LISTADO", length = 50)
	private String listado;

	@Column(name = "CFINICIO", length = 8)
	private String cfInicio;

	@Column(name = "CFFIN", length = 8)
	private String cfFin;

	@Column(name = "CDNIUSR", length = 10)
	private String cDocNacional;

	@Column(name = "CDETALLE", length = 10)
	private String cDetalle;

	@Column(name = "CDNIPER", length = 10)
	private String cDocNacionalPer;

	@Column(name = "CFECHA", length = 8)
	private String cFecha;

	@Column(name = "NENTIGESEP", precision = 2, scale = 0)
	private BigDecimal nEntiGesEp;

	@Column(name = "NPROVEP", precision = 2, scale = 0)
	private BigDecimal nProvEp;

	@Column(name = "NCENGESEP", precision = 2, scale = 0)
	private BigDecimal nCenGesEp;

	@Column(name = "NHORAINICIO", precision = 6, scale = 0)
	private BigDecimal nHoraInicio;

	@Column(name = "NHORAFIN", precision = 6, scale = 0)
	private BigDecimal nHoraFin;

	@Column(name = "CCONJUNTO", length = 9)
	private String cConjunto;

	@Column(name = "CTIPO", length = 10)
	private String cTipo;

	@Column(name = "VG_OPCION", length = 10)
	private String vgOpcion;
	
	@Column(name = "NOMINFORME", length = 20)
	private String nomInforme;

	@Column(name = "NNUMREGISTROS")
	private BigDecimal nNumRegistros;

	@Column(name = "CERRCOD", length = 10)
	private String cErrCod;

	@Column(name = "CERRDES", length = 255)
	private String cErrDes;

	@Column(name = "T_INICIO")
	private Timestamp tInicio;

	@Column(name = "T_FINAL")
	private Timestamp tFinal;
	
	@Column(name = "IMPRESO", length = 1)
	private String impreso;

	public GpreListados() {
	}

	public GpreListados(BigDecimal idListado) {
		this.idListado = idListado;
	}

	// Getters y Setters

	public BigDecimal getIdListado() {
		return idListado;
	}

	public void setIdListado(BigDecimal idListado) {
		this.idListado = idListado;
	}

	public String getListado() {
		return listado;
	}

	public void setListado(String listado) {
		this.listado = listado;
	}

	public String getCfInicio() {
		return cfInicio;
	}

	public void setCfInicio(String cfInicio) {
		this.cfInicio = cfInicio;
	}

	public String getCfFin() {
		return cfFin;
	}

	public void setCfFin(String cfFin) {
		this.cfFin = cfFin;
	}

	public String getcDetalle() {
		return cDetalle;
	}

	public void setcDetalle(String cDetalle) {
		this.cDetalle = cDetalle;
	}

	public String getcDocNacional() {
		return cDocNacional;
	}

	public void setcDocNacional(String cDocNacional) {
		this.cDocNacional = cDocNacional;
	}

	public String getcDocNacionalPer() {
		return cDocNacionalPer;
	}

	public void setcDocNacionalPer(String cDocNacionalPer) {
		this.cDocNacionalPer = cDocNacionalPer;
	}

	public String getcFecha() {
		return cFecha;
	}

	public void setcFecha(String cFecha) {
		this.cFecha = cFecha;
	}

	public BigDecimal getnEntiGesEp() {
		return nEntiGesEp;
	}

	public void setnEntiGesEp(BigDecimal nEntiGesEp) {
		this.nEntiGesEp = nEntiGesEp;
	}

	public BigDecimal getnProvEp() {
		return nProvEp;
	}

	public void setnProvEp(BigDecimal nProvEp) {
		this.nProvEp = nProvEp;
	}

	public BigDecimal getnCenGesEp() {
		return nCenGesEp;
	}

	public void setnCenGesEp(BigDecimal nCenGesEp) {
		this.nCenGesEp = nCenGesEp;
	}

	public BigDecimal getnHoraInicio() {
		return nHoraInicio;
	}

	public void setnHoraInicio(BigDecimal nHoraInicio) {
		this.nHoraInicio = nHoraInicio;
	}

	public BigDecimal getnHoraFin() {
		return nHoraFin;
	}

	public void setnHoraFin(BigDecimal nHoraFin) {
		this.nHoraFin = nHoraFin;
	}

	public String getcConjunto() {
		return cConjunto;
	}

	public void setcConjunto(String cConjunto) {
		this.cConjunto = cConjunto;
	}

	public String getcTipo() {
		return cTipo;
	}

	public void setcTipo(String cTipo) {
		this.cTipo = cTipo;
	}

	public String getVgOpcion() {
		return vgOpcion;
	}

	public void setVgOpcion(String vgOpcion) {
		this.vgOpcion = vgOpcion;
	}

	public BigDecimal getnNumRegistros() {
		return nNumRegistros;
	}

	public void setnNumRegistros(BigDecimal nNumRegistros) {
		this.nNumRegistros = nNumRegistros;
	}

	public String getcErrCod() {
		return cErrCod;
	}

	public void setcErrCod(String cErrCod) {
		this.cErrCod = cErrCod;
	}

	public String getcErrDes() {
		return cErrDes;
	}

	public void setcErrDes(String cErrDes) {
		this.cErrDes = cErrDes;
	}

	public Timestamp gettInicio() {
		return tInicio;
	}

	public void settInicio(Timestamp tInicio) {
		this.tInicio = tInicio;
	}

	public Timestamp gettFinal() {
		return tFinal;
	}

	public void settFinal(Timestamp tFinal) {
		this.tFinal = tFinal;
	}

	public String getNomInforme() {
		return nomInforme;
	}

	public void setNomInforme(String nomInforme) {
		this.nomInforme = nomInforme;
	}

	public String getImpreso() {
		return impreso;
	}

	public void setImpreso(String impreso) {
		this.impreso = impreso;
	}
}
