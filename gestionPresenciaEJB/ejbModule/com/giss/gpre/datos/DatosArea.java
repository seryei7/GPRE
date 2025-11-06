package com.giss.gpre.datos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad presentacion de Acceso_area_nivel y Area_trabajo
 * 
 * @author GISS
 *
 */
public class DatosArea implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9042262478723252211L;
	
	private String docNacional;
	private BigDecimal enti_ges_ep;
	private BigDecimal prov_ep;
	private BigDecimal cen_ges_ep;
	private BigDecimal rango;
	private String denominacion;
	
	/**
	 * @param iddni
	 * @param enti_ges_ep
	 * @param prov_ep
	 * @param cen_ges_ep
	 * @param cdnivel
	 * @param denominacion
	 */
	public DatosArea(String docNacional, BigDecimal enti_ges_ep, BigDecimal prov_ep, BigDecimal cen_ges_ep, BigDecimal rango, String denominacion) {
		this.docNacional = docNacional;
		this.enti_ges_ep = enti_ges_ep;
		this.prov_ep = prov_ep;
		this.cen_ges_ep = cen_ges_ep;
		this.rango = rango;
		this.denominacion = denominacion;
	}

	/**
	 * 
	 */
	public DatosArea() {
	}

	public String getIddni() {
		return docNacional;
	}

	public void setIddni(String docNacional) {
		this.docNacional = docNacional;
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

	public BigDecimal getCdnivel() {
		return rango;
	}

	public void setCdnivel(BigDecimal rango) {
		this.rango = rango;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	
	
}