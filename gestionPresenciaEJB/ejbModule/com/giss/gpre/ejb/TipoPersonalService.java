package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.datos.DatosTipoPersonal;
import com.giss.gpre.entidades.TipoPersonal;

@Remote
public interface TipoPersonalService {
	
	public List<DatosTipoPersonal> obtenerTipoPersonal();
	
	public List<TipoPersonal> findAll();
	
	public TipoPersonal findByIdTipoPersonal(BigDecimal idTipoPersonal);
	
	public List<TipoPersonal> findByPersonalExterno(String personalExterno);
	
	public void save(TipoPersonal tipoPersonal);
	
	public void delete(BigDecimal idTipoPersonal);
	
}
