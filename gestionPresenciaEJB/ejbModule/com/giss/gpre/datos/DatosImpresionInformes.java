package com.giss.gpre.datos;

public class DatosImpresionInformes {
	private java.math.BigDecimal idListado;
	private String listado;
	private String estadoGenerado;
	private boolean impreso;
	private String horaFormateada;
	private String cErrDes;

	public java.math.BigDecimal getIdListado() {
		return idListado;
	}

	public void setIdListado(java.math.BigDecimal idListado) {
		this.idListado = idListado;
	}

	public String getListado() {
		return listado;
	}

	public void setListado(String listado) {
		this.listado = listado;
	}

	public String getEstadoGenerado() {
		return estadoGenerado;
	}

	public void setEstadoGenerado(String estadoGenerado) {
		this.estadoGenerado = estadoGenerado;
	}

	public boolean isImpreso() {
		return impreso;
	}

	public void setImpreso(boolean impreso) {
		this.impreso = impreso;
	}

	public String getHoraFormateada() {
		return horaFormateada;
	}

	public void setHoraFormateada(String horaFormateada) {
		this.horaFormateada = horaFormateada;
	}

	public String getCErrDes() {
		return cErrDes;
	}

	public void setCErrDes(String cErrDes) {
		this.cErrDes = cErrDes;
	}
}
