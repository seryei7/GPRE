package com.giss.gpre.datos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad presentacion del procedimiento GetVistaUnidad
 * 
 * @author GISS
 *
 */
public class DatosUnidades implements Serializable{
	
	private static final long serialVersionUID = 347853847904190915L;
	private BigDecimal enti_ges_ep;
	private BigDecimal prov_ep;
	private BigDecimal cen_ges_ep;
	private String cdcentro;
	private String idunidad;
	private String dsunidad;
	private String cdunidadpadre;
	private String cdunidadord;
	private String situacion;
	private String fechasituacion;
	private String dsunidadpadre;
	private BigDecimal ntincidencia;
	
	public DatosUnidades(BigDecimal enti_ges_ep, BigDecimal prov_ep, BigDecimal cen_ges_ep, String cdcentro,
			String idunidad, String dsunidad, String cdunidadpadre, String cdunidadord, String situacion,
			String fechasituacion, String dsunidadpadre, BigDecimal ntincidencia) {
		super();
		this.enti_ges_ep = enti_ges_ep;
		this.prov_ep = prov_ep;
		this.cen_ges_ep = cen_ges_ep;
		this.cdcentro = cdcentro;
		this.idunidad = idunidad;
		this.dsunidad = dsunidad;
		this.cdunidadpadre = cdunidadpadre;
		this.cdunidadord = cdunidadord;
		this.situacion = situacion;
		this.fechasituacion = fechasituacion;
		this.dsunidadpadre = dsunidadpadre;
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
	
	public String getCdcentro() {
		return cdcentro;
	}
	
	public void setCdcentro(String cdcentro) {
		this.cdcentro = cdcentro;
	}
	
	public String getIdunidad() {
		return idunidad;
	}
	
	public void setIdunidad(String idunidad) {
		this.idunidad = idunidad;
	}
	
	public String getDsunidad() {
		return dsunidad;
	}
	
	public void setDsunidad(String dsunidad) {
		this.dsunidad = dsunidad;
	}
	
	public String getCdunidadpadre() {
		return cdunidadpadre;
	}
	
	public void setCdunidadpadre(String cdunidadpadre) {
		this.cdunidadpadre = cdunidadpadre;
	}
	
	public String getCdunidadord() {
		return cdunidadord;
	}
	
	public void setCdunidadord(String cdunidadord) {
		this.cdunidadord = cdunidadord;
	}
	
	public String getSituacion() {
		return situacion;
	}
	
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	
	public String getFechasituacion() {
		return fechasituacion;
	}
	
	public void setFechasituacion(String fechasituacion) {
		this.fechasituacion = fechasituacion;
	}
	
	public String getDsunidadpadre() {
		return dsunidadpadre;
	}
	
	public void setDsunidadpadre(String dsunidadpadre) {
		this.dsunidadpadre = dsunidadpadre;
	}
	
	public BigDecimal getNtincidencia() {
		return ntincidencia;
	}
	
	public void setNtincidencia(BigDecimal ntincidencia) {
		this.ntincidencia = ntincidencia;
	}
		
}