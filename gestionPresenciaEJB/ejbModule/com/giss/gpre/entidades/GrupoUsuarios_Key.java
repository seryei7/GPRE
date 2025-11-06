package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

public class GrupoUsuarios_Key implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6712469232292973179L;
	
	private BigDecimal enti_ges_ep;
	private BigDecimal prov_ep;
	private BigDecimal cen_ges_ep;
	private String docNacional;
	private String codigoU;
	
    /**
	 * 
	 */
	public GrupoUsuarios_Key() {}
	/**
	 * @param enti_ges_ep
	 * @param prov_ep
	 * @param cen_ges_ep
	 * @param cddni
	 * @param codigoU
	 */
	public GrupoUsuarios_Key(BigDecimal enti_ges_ep, BigDecimal prov_ep, BigDecimal cen_ges_ep, String docNacional,
			String codigoU) {
		super();
		this.enti_ges_ep = enti_ges_ep;
		this.prov_ep = prov_ep;
		this.cen_ges_ep = cen_ges_ep;
		this.docNacional = docNacional;
		this.codigoU = codigoU;
	}
	/* (sin Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docNacional == null) ? 0 : docNacional.hashCode());
		result = prime * result + ((codigoU == null) ? 0 : codigoU.hashCode());
		result = prime * result + ((cen_ges_ep == null) ? 0 : cen_ges_ep.hashCode());
		result = prime * result + ((enti_ges_ep == null) ? 0 : enti_ges_ep.hashCode());
		result = prime * result + ((prov_ep == null) ? 0 : prov_ep.hashCode());
		return result;
	}
	/* (sin Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean resultado = true;
		if (this == obj)
			resultado = true;
		if (obj == null)
			resultado = false;
		if (getClass() != obj.getClass())
			resultado = false;
		GrupoUsuarios_Key other = (GrupoUsuarios_Key) obj;
		if (docNacional == null) {
			if (other.docNacional != null)
				resultado = false;
		} else if (!docNacional.equals(other.docNacional))
			resultado = false;
		if (codigoU == null) {
			if (other.codigoU != null)
				resultado = false;
		} else if (!codigoU.equals(other.codigoU))
			resultado = false;
		if (cen_ges_ep == null) {
			if (other.cen_ges_ep != null)
				resultado = false;
		} else if (!cen_ges_ep.equals(other.cen_ges_ep))
			resultado = false;
		if (enti_ges_ep == null) {
			if (other.enti_ges_ep != null)
				resultado = false;
		} else if (!enti_ges_ep.equals(other.enti_ges_ep))
			resultado = false;
		if (prov_ep == null) {
			if (other.prov_ep != null)
				resultado = false;
		} else if (!prov_ep.equals(other.prov_ep))
			resultado = false;
		return resultado;
	}

	

    

}