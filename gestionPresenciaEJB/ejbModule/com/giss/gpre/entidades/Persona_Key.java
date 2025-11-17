package com.giss.gpre.entidades;

import java.io.Serializable;

public class Persona_Key implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6712469232292973179L;
	
	private String docNacional;
	
    /**
	 * 
	 */
	public Persona_Key() {}

	/**
	 * @param idDni
	 */
	public Persona_Key(String docNacional) {
		super();
		this.docNacional = docNacional;
	}

	/* (sin Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docNacional == null) ? 0 : docNacional.hashCode());
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
		Persona_Key other = (Persona_Key) obj;
		if (docNacional == null) {
			if (other.docNacional != null)
				retornoSalida =  false;
		} else if (!docNacional.equals(other.docNacional))
			retornoSalida =  false;
		return retornoSalida;
	}

    

}