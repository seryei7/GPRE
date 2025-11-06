package com.giss.gpre.entidades;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Bean de implementacion de la tabla de la base de datos TIPOSPERSONALSEL
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "TIPOSPERSONALSEL")
@IdClass(TiposPersonalSel_Key.class)
@NamedQueries({ @NamedQuery(name = "TipoPersonasSel.findAll", query = "SELECT t FROM TiposPersonalSel t"),
		@NamedQuery(name = "TipoPersonasSel.findByUsuario", query = "SELECT t FROM TiposPersonalSel t WHERE t.idUsuario = ?1") })
public class TiposPersonalSel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDUSUARIO", length = 10, nullable = false)
	private String idUsuario;

	@Id
	@Column(name = "ENTI_GES_EP", nullable = false)
	private BigDecimal entiGesEp;

	@Id
	@Column(name = "PROV_EP", nullable = false)
	private BigDecimal provEp;

	@Id
	@Column(name = "CEN_GES_EP", nullable = false)
	private BigDecimal cenGesEp;

	@Id
	@Column(name = "CDTIPOPERSONAL", nullable = false)
	private BigDecimal cdTipoPersonal;

	@Column(name = "FECHA", length = 8, nullable = false)
	private String fecha;

	@Column(name = "HORA", length = 6, nullable = false)
	private String hora;

	public TiposPersonalSel() {
	}

	public TiposPersonalSel(String idUsuario, BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp,
			BigDecimal cdTipoPersonal, String fecha, String hora) {
		super();
		this.idUsuario = idUsuario;
		this.entiGesEp = entiGesEp;
		this.provEp = provEp;
		this.cenGesEp = cenGesEp;
		this.cdTipoPersonal = cdTipoPersonal;
		this.fecha = fecha;
		this.hora = hora;
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

	public BigDecimal getCdTipoPersonal() {
		return cdTipoPersonal;
	}

	public void setCdTipoPersonal(BigDecimal cdTipoPersonal) {
		this.cdTipoPersonal = cdTipoPersonal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

}
