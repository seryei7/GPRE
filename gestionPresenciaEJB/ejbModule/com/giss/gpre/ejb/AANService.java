package com.giss.gpre.ejb;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.datos.DatosArea;
import com.giss.gpre.entidades.Acceso_area_nivel;
import com.giss.gpre.entidades.Areas_trabajo;
import com.giss.gpre.entidades.Nodos;
import com.giss.gpre.entidades.Persona;

@Remote
public interface AANService {

	public boolean testConnection();
	
	/** 
	 * 
	 * @param iddni
	 * @return
	 */
	public Persona personaByDNI(String iddni);
	
	/**
	 * 
	 * @param dni
	 * @return
	 */
	public List<DatosArea> areasDeTrabajoNivel(String dni);
	
	/**
	 * 
	 * @param nivel
	 * @return
	 */
	public List<Nodos> nodosTrabajo(BigDecimal nivel);
	
	/**
	 * 
	 * @param acc
	 * @return
	 */
	public Areas_trabajo areasTrabajo(Acceso_area_nivel acc);
	
	/** 
	 * 
	 * @param value
	 * @return
	 */
	public List<Acceso_area_nivel> ObtenerAreasDeTrabajoNivel(String dni) throws IOException;
	
	/**
	 * 
	 * @param dni
	 * @return
	 * @throws IOException
	 */
	public Object ValidarUsuario(String dni) throws IOException;
	
	/**
	 * 
	 * @param dni
	 * @param entidad
	 * @param provincia
	 * @param centro
	 * @return
	 * @throws IOException
	 */
	public Object IncluirAreaSeleccionada(String dni, BigDecimal entidad, BigDecimal provincia, BigDecimal centro);

}
