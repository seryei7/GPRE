package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Areas_trabajo_Key implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1314043747793280651L;
	/**
	 * Implementation field for persistent attribute
	 */
	public BigDecimal enti_ges_ep;
	/**
	 * Implementation field for persistent attribute
	 */
	public BigDecimal prov_ep;
	/**
	 * Implementation field for persistent attribute
	 */
	public BigDecimal cen_ges_ep;
	
	
	/**
	 * 
	 */
	public Areas_trabajo_Key() {	}


	/**
	 * @param enti_ges_ep
	 * @param prov_ep
	 * @param cen_ges_ep
	 * @param denominacion
	 */
	public Areas_trabajo_Key(BigDecimal enti_ges_ep, BigDecimal prov_ep, BigDecimal cen_ges_ep) {
		super();
		this.enti_ges_ep = enti_ges_ep;
		this.prov_ep = prov_ep;
		this.cen_ges_ep = cen_ges_ep;

	}


	/* (sin Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		boolean retornoSalida = true;
		if (this == obj)
			retornoSalida = true;
		if (obj == null)
			retornoSalida = false;
		if (getClass() != obj.getClass())
			retornoSalida = false;
		Areas_trabajo_Key other = (Areas_trabajo_Key) obj;
		if (cen_ges_ep == null) {
			if (other.cen_ges_ep != null)
				retornoSalida = false;
		} else if (!cen_ges_ep.equals(other.cen_ges_ep))
			retornoSalida = false;
		if (enti_ges_ep == null) {
			if (other.enti_ges_ep != null)
				retornoSalida = false;
		} else if (!enti_ges_ep.equals(other.enti_ges_ep))
			retornoSalida = false;
		if (prov_ep == null) {
			if (other.prov_ep != null)
				retornoSalida = false;
		} else if (!prov_ep.equals(other.prov_ep))
			retornoSalida = false;
		return retornoSalida;
	}
	
	

}
