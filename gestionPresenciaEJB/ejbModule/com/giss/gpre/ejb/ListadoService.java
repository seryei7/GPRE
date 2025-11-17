package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.datos.DatosGeneraListado;
import com.giss.gpre.entidades.GpreListados;

@Remote
public interface ListadoService {
	
	public DatosGeneraListado generarListado(String listado, String cfInicio, String cfFin, String cdniUsr, String cDetalle,
			String cdniPer, String cFecha, BigDecimal nEntiGesEp, BigDecimal nProvEp, BigDecimal nCenGesEp,
			BigDecimal nHoraInicio, BigDecimal nHoraFin, String cConjunto, String cTipo, String vgOpcion, String nomInforme);

	public GpreListados findById(BigDecimal id);
	
	public List<GpreListados> findByUsuario(String usuario);

	public List<GpreListados> findAll();
	
	public void saveListado(GpreListados gpreListados);

}
