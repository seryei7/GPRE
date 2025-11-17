package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.datos.DatosUnidades;
import com.giss.gpre.entidades.Unidades;

@Remote
public interface UnidadesService {
	
	public List<DatosUnidades> obtenerUnidades(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String centro, String dni);

	public List<Unidades> findByIdUnidad(String idunidad);
	
	public List<Unidades> findBySituacion(String situacion);
	
	public List<Unidades> findByEntiProvCen(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String cdCentro);

	public List<Unidades> findAll();
	
}
