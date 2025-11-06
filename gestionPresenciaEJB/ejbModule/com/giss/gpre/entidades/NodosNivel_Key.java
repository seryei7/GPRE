package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class NodosNivel_Key implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1898110759951466817L;

	private BigDecimal escalon;
	
	private BigDecimal cdnodo;

	/**
	 * @param cdNivel
	 * @param cdNodo
	 */
	public NodosNivel_Key(BigDecimal escalon, BigDecimal cdNodo) {
		super();
		this.escalon = escalon;
		this.cdnodo = cdNodo;
	}

	/* (sin Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((escalon == null) ? 0 : escalon.hashCode());
		result = prime * result + ((cdnodo == null) ? 0 : cdnodo.hashCode());
		return result;
	}

	/* (sin Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean retornoSalida = true;
		if (this == obj)
			retornoSalida =  true;
		if (obj == null)
			retornoSalida =  false;
		if (getClass() != obj.getClass())
			retornoSalida =  false;	
		NodosNivel_Key other = (NodosNivel_Key) obj;
		if (escalon == null) {
			if (other.escalon != null)
				retornoSalida =  false;
		} else if (!escalon.equals(other.escalon))
			retornoSalida =  false;
		if (cdnodo == null) {
			if (other.cdnodo != null)
				retornoSalida =  false;
		} else if (!cdnodo.equals(other.cdnodo))
			retornoSalida =  false;
		return retornoSalida;
	}

}