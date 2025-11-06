package com.giss.gpre.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.entidades.Incidencias;

@Remote
public interface IncidenciasService {
	
	/** 
	 * Recoge toda la lista de incidencias
	 * 
	 * @return
	 */
	public List<Incidencias> allIncidencias();
	
}
