package com.giss.gpre.entidades;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Incidencias_Key implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -125186673150887111L;
	
	private Integer ntincidencia;
	private Integer idincidencia;
	
	/**
	 * @param ntincidencia
	 * @param idincidencia
	 */
	public Incidencias_Key(Integer ntincidencia, Integer idincidencia) {
		this.ntincidencia = ntincidencia;
		this.idincidencia = idincidencia;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idincidencia == null) ? 0 : idincidencia.hashCode());
		result = prime * result + ((ntincidencia == null) ? 0 : ntincidencia.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		boolean retorno = true;
		if (this == obj)
			retorno = true;
		if (obj == null)
			retorno = false;
		if (getClass() != obj.getClass())
			retorno = false;
		Incidencias_Key other = (Incidencias_Key) obj;
		if (idincidencia == null) {
			if (other.idincidencia != null)
				retorno = false;
		} else if (!idincidencia.equals(other.idincidencia))
			retorno = false;
		if (ntincidencia == null) {
			if (other.ntincidencia != null)
				retorno = false;
		} else if (!ntincidencia.equals(other.ntincidencia))
			retorno = false;
		return retorno;
	}

	

	
	
	

}