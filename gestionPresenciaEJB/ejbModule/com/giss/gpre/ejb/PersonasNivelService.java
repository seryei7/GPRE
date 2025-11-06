package com.giss.gpre.ejb;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.datos.DatosPersonaNivelAcceso;
import com.giss.gpre.entidades.Persona;

@Remote
public interface PersonasNivelService {
	
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
	
	/**
	 * 
	 * @param dni
	 * @param entidad
	 * @param provincia
	 * @param centro
	 * @return
	 * @throws Exception
	 */
	public List<DatosPersonaNivelAcceso> GetNivelVistaPersona(String dni, BigDecimal entidad, BigDecimal provincia, BigDecimal centro) throws IOException;
	
}
