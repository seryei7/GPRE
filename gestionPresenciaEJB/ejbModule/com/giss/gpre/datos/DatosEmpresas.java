package com.giss.gpre.datos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad presentacion del procedimiento GetVistaEmpresa
 * 
 * @author GISS
 *
 */
public class DatosEmpresas implements Serializable{
	
	private static final long serialVersionUID = 347853847904190915L;
	private String nombre;
	private String idfiscal;
	private String localidad;
	private String telefono;
	private BigDecimal enti_ges_ep;
	private BigDecimal prov_ep;
	private BigDecimal cen_ges_ep;
	private BigDecimal resultado;
	
	public DatosEmpresas(String nombre, String idfiscal, String localidad, String telefono, BigDecimal enti_ges_ep,
			BigDecimal prov_ep, BigDecimal cen_ges_ep, BigDecimal resultado) {
		super();
		this.nombre = nombre;
		this.idfiscal = idfiscal;
		this.localidad = localidad;
		this.telefono = telefono;
		this.enti_ges_ep = enti_ges_ep;
		this.prov_ep = prov_ep;
		this.cen_ges_ep = cen_ges_ep;
		this.resultado = resultado;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getIdfiscal() {
		return idfiscal;
	}
	
	public void setIdfiscal(String idfiscal) {
		this.idfiscal = idfiscal;
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
	
	public BigDecimal getResultado() {
		return resultado;
	}
	
	public void setResultado(BigDecimal resultado) {
		this.resultado = resultado;
	}
		
}