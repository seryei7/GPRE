package com.giss.gpre.datos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad presentacion del procedimiento GetVistaTipoPersonal
 * 
 * @author GISS
 *
 */
public class DatosTipoPersonal implements Serializable{
	
	private static final long serialVersionUID = -2014934761620543936L;
	private BigDecimal idtipopersonal;
	private String dstipopersonal;
	private String personalexterno;
	private BigDecimal resultado;
	
	public DatosTipoPersonal(BigDecimal idtipopersonal, String dstipopersonal, String personalexterno,
			BigDecimal resultado) {
		super();
		this.idtipopersonal = idtipopersonal;
		this.dstipopersonal = dstipopersonal;
		this.personalexterno = personalexterno;
		this.resultado = resultado;
	}
	
	public BigDecimal getIdtipopersonal() {
		return idtipopersonal;
	}
	
	public void setIdtipopersonal(BigDecimal idtipopersonal) {
		this.idtipopersonal = idtipopersonal;
	}
	
	public String getDstipopersonal() {
		return dstipopersonal;
	}
	
	public void setDstipopersonal(String dstipopersonal) {
		this.dstipopersonal = dstipopersonal;
	}
	
	public String getPersonalexterno() {
		return personalexterno;
	}
	
	public void setPersonalexterno(String personalexterno) {
		this.personalexterno = personalexterno;
	}
	
	public BigDecimal getResultado() {
		return resultado;
	}
	
	public void setResultado(BigDecimal resultado) {
		this.resultado = resultado;
	}
	
}