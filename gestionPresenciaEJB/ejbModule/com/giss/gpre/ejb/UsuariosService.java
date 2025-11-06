package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.entidades.Persona;

@Remote
public interface UsuariosService {
	
	/** 
	 * Recoge toda la lista de incidencias
	 * 
	 * @return
	 */
	public List<Persona> allUsuarios();
	
	/** 
	 * Recoge toda la lista de incidencias
	 * 
	 * @return
	 */
	public List<Persona> personaForGestor(BigDecimal entidad, BigDecimal provincia, BigDecimal centro);
	
}
