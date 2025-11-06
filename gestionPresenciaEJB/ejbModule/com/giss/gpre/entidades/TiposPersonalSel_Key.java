package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class TiposPersonalSel_Key implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idUsuario;
	private BigDecimal entiGesEp;
	private BigDecimal provEp;
	private BigDecimal cenGesEp;
	private BigDecimal cdTipoPersonal;

	public TiposPersonalSel_Key() {
	}

	public TiposPersonalSel_Key(String idUsuario, BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp,
			BigDecimal cdTipoPersonal) {
		this.idUsuario = idUsuario;
		this.entiGesEp = entiGesEp;
		this.provEp = provEp;
		this.cenGesEp = cenGesEp;
		this.cdTipoPersonal = cdTipoPersonal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUsuario, entiGesEp, provEp, cenGesEp, cdTipoPersonal);
	}
	
}
