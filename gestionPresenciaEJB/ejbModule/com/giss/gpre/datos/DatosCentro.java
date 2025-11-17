package com.giss.gpre.datos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad presentacion del procedimiento GetVistaCentro
 * 
 * @author GISS
 *
 */
public class DatosCentro implements Serializable{
	
	private static final long serialVersionUID = 8868971921737327572L;
	private BigDecimal enti_ges_ep;
	private BigDecimal prov_ep;
	private BigDecimal cen_ges_ep;
	private String idcentro;
	private String dscentro;
	private String clasevia;
	private String calle;
	private String numero;
	private String codigopostal;
	private String localidad;
	private BigDecimal ntincidencia;
	
	public DatosCentro(BigDecimal enti_ges_ep, BigDecimal prov_ep, BigDecimal cen_ges_ep, String idcentro,
			String dscentro, String clasevia, String calle, String numero, String codigopostal, String localidad,
			BigDecimal ntincidencia) {
		super();
		this.enti_ges_ep = enti_ges_ep;
		this.prov_ep = prov_ep;
		this.cen_ges_ep = cen_ges_ep;
		this.idcentro = idcentro;
		this.dscentro = dscentro;
		this.clasevia = clasevia;
		this.calle = calle;
		this.numero = numero;
		this.codigopostal = codigopostal;
		this.localidad = localidad;
		this.ntincidencia = ntincidencia;
	}
	
	public BigDecimal getEnti_ges_ep() {
		return enti_ges_ep;
	}
	
	public void setEnti_ges_ep(BigDecimal enti_ges_ep) {
		this.enti_ges_ep = enti_ges_ep;
	}
	
	public BigDecimal getProv_ep() {
		return prov_ep;
	}
	
	public void setProv_ep(BigDecimal prov_ep) {
		this.prov_ep = prov_ep;
	}
	
	public BigDecimal getCen_ges_ep() {
		return cen_ges_ep;
	}
	
	public void setCen_ges_ep(BigDecimal cen_ges_ep) {
		this.cen_ges_ep = cen_ges_ep;
	}
	
	public String getIdcentro() {
		return idcentro;
	}
	
	public void setIdcentro(String idcentro) {
		this.idcentro = idcentro;
	}
	
	public String getDscentro() {
		return dscentro;
	}
	
	public void setDscentro(String dscentro) {
		this.dscentro = dscentro;
	}
	
	public String getClasevia() {
		return clasevia;
	}
	
	public void setClasevia(String clasevia) {
		this.clasevia = clasevia;
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
	
	public String getCodigopostal() {
		return codigopostal;
	}
	
	public void setCodigopostal(String codigopostal) {
		this.codigopostal = codigopostal;
	}
	
	public String getLocalidad() {
		return localidad;
	}
	
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	public BigDecimal getNtincidencia() {
		return ntincidencia;
	}
	
	public void setNtincidencia(BigDecimal ntincidencia) {
		this.ntincidencia = ntincidencia;
	}
	
}