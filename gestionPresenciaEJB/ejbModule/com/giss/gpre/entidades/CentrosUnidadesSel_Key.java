package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class CentrosUnidadesSel_Key implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idUsuario;
	private BigDecimal entiGesEp;
	private BigDecimal provEp;
	private BigDecimal cenGesEp;
	private String cdCentro;
	private String cdUnidad;

	public CentrosUnidadesSel_Key() {
	}

	public CentrosUnidadesSel_Key(String idUsuario, BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp,
			String cdCentro, String cdUnidad) {
		this.idUsuario = idUsuario;
		this.entiGesEp = entiGesEp;
		this.provEp = provEp;
		this.cenGesEp = cenGesEp;
		this.cdCentro = cdCentro;
		this.cdUnidad = cdUnidad;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUsuario, entiGesEp, provEp, cenGesEp, cdCentro, cdUnidad);
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public BigDecimal getEntiGesEp() {
		return entiGesEp;
	}

	public void setEntiGesEp(BigDecimal entiGesEp) {
		this.entiGesEp = entiGesEp;
	}

	public BigDecimal getProvEp() {
		return provEp;
	}

	public void setProvEp(BigDecimal provEp) {
		this.provEp = provEp;
	}

	public BigDecimal getCenGesEp() {
		return cenGesEp;
	}

	public void setCenGesEp(BigDecimal cenGesEp) {
		this.cenGesEp = cenGesEp;
	}

	public String getCdCentro() {
		return cdCentro;
	}

	public void setCdCentro(String cdCentro) {
		this.cdCentro = cdCentro;
	}

	public String getCdUnidad() {
		return cdUnidad;
	}

	public void setCdUnidad(String cdUnidad) {
		this.cdUnidad = cdUnidad;
	}

}
