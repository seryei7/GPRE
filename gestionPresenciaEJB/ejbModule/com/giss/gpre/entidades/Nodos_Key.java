package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Nodos_Key implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -125186673150887111L;
	
	private BigDecimal idnodo;

	
	
	/**
	 * @param idNodo
	 */
	public Nodos_Key(BigDecimal idNodo) {
		super();
		this.idnodo = idNodo;
	}

	/* (sin Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idnodo == null) ? 0 : idnodo.hashCode());
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
		Nodos_Key other = (Nodos_Key) obj;
		if (idnodo == null) {
			if (other.idnodo != null)
				retornoSalida =  false;
		} else if (!idnodo.equals(other.idnodo))
			retornoSalida =  false;
		return retornoSalida;
	}

	
	
	

}