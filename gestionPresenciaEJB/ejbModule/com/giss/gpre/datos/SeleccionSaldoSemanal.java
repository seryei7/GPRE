package com.giss.gpre.datos;

import java.util.List;

public class SeleccionSaldoSemanal {
	private String codigoEntidad;
	private String codigoProvincia;
	private String codigoCgpe;
	private String fechaDesde;
	private String fechaHasta;
	private List<String> centros;
	private List<String> unidades;
	private List<String> empresas;
	private List<String> tipoPersonal;

	public String getCodigoEntidad() {
		return codigoEntidad;
	}

	public void setCodigoEntidad(String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}

	public String getCodigoProvincia() {
		return codigoProvincia;
	}

	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}

	public String getCodigoCgpe() {
		return codigoCgpe;
	}

	public void setCodigoCgpe(String codigoCgpe) {
		this.codigoCgpe = codigoCgpe;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public List<String> getCentros() {
		return centros;
	}

	public void setCentros(List<String> centros) {
		this.centros = centros;
	}

	public List<String> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<String> unidades) {
		this.unidades = unidades;
	}

	public List<String> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<String> empresas) {
		this.empresas = empresas;
	}

	public List<String> getTipoPersonal() {
		return tipoPersonal;
	}

	public void setTipoPersonal(List<String> tipoPersonal) {
		this.tipoPersonal = tipoPersonal;
	}
}
