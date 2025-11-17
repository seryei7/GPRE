package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.datos.DatosCentro;
import com.giss.gpre.entidades.Centros;

@Remote
public interface CentrosService {
	
	public List<DatosCentro> obtenerCentros(String dni);

	public List<Centros> findByIdCentro(String idcentro);
	
	public List<Centros> findByEntiProvCen(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp);

	public List<Centros> findAll();
	
}
