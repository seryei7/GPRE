package com.giss.gpre.datos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad presentacion de Acceso_area_nivel y Area_trabajo
 * 
 * @author GISS
 *
 */
public class DatosPersonaNivelAcceso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9042262478723252211L;
	
	private String apellido1;
	private String apellido2;
	private String nombre;	
	private String docNacional;
	private BigDecimal enti_ges_ep;
	private BigDecimal prov_ep;
	private BigDecimal cen_ges_ep;
	private String dsnivel;
	/**
	 * @param apellido1
	 * @param apellido2
	 * @param nombre
	 * @param iddni
	 * @param enti_ges_ep
	 * @param prov_ep
	 * @param cen_ges_ep
	 * @param dsnivel
	 */
	public DatosPersonaNivelAcceso(String apellido1, String apellido2, String nombre, String docNacional,	BigDecimal enti_ges_ep, BigDecimal prov_ep, BigDecimal cen_ges_ep, String dsnivel) {
		super();
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nombre = nombre;
		this.docNacional = docNacional;
		this.enti_ges_ep = enti_ges_ep;
		this.prov_ep = prov_ep;
		this.cen_ges_ep = cen_ges_ep;
		this.dsnivel = dsnivel;
	}
	/**
	 * @return el apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}
	/**
	 * @param apellido1 el apellido1 a establecer
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
	 * @param apellido2 el apellido2 a establecer
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre el nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return el iddni
	 */
	public String getIddni() {
		return docNacional;
	}
	/**
	 * @param iddni el iddni a establecer
	 */
	public void setIddni(String docNacional) {
		this.docNacional = docNacional;
	}
	/**
	 * @return el enti_ges_ep
	 */
	public BigDecimal getEnti_ges_ep() {
		return enti_ges_ep;
	}
	/**
	 * @param enti_ges_ep el enti_ges_ep a establecer
	 */
	public void setEnti_ges_ep(BigDecimal enti_ges_ep) {
		this.enti_ges_ep = enti_ges_ep;
	}
	/**
	 * @return el prov_ep
	 */
	public BigDecimal getProv_ep() {
		return prov_ep;
	}
	/**
	 * @param prov_ep el prov_ep a establecer
	 */
	public void setProv_ep(BigDecimal prov_ep) {
		this.prov_ep = prov_ep;
	}
	/**
	 * @return el cen_ges_ep
	 */
	public BigDecimal getCen_ges_ep() {
		return cen_ges_ep;
	}
	/**
	 * @param cen_ges_ep el cen_ges_ep a establecer
	 */
	public void setCen_ges_ep(BigDecimal cen_ges_ep) {
		this.cen_ges_ep = cen_ges_ep;
	}
	/**
	 * @return el dsnivel
	 */
	public String getDsnivel() {
		return dsnivel;
	}
	/**
	 * @param dsnivel el dsnivel a establecer
	 */
	public void setDsnivel(String dsnivel) {
		this.dsnivel = dsnivel;
	}
	
	
	
}