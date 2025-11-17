package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.entidades.EmpresasDatos;

@Remote
public interface EmpresasDatosService {
	
	public List<EmpresasDatos> findAll();
	
	public List<EmpresasDatos> findByEntiProvCen(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp);
	
	public List<EmpresasDatos> findByIdCifNif(String idCifNif);
	
	public List<EmpresasDatos> findByNombre(String nombre);
	
	public void save(EmpresasDatos empresasDatos);
	
	public void delete(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String idCifNif);
	
}
