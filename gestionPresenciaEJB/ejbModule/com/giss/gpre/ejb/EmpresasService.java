package com.giss.gpre.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.datos.DatosEmpresas;
import com.giss.gpre.entidades.Empresas;

@Remote
public interface EmpresasService {
	
	public List<DatosEmpresas> obtenerEmpresas(String pCdUsuario);
	
	public List<Empresas> findAll();
	
	public Empresas findByIdCifNif(String idCifNif);
	
	public List<Empresas> findByNombre(String nombre);
	
	public List<Empresas> findActivas();
	
	public void save(Empresas empresas);
	
	public void delete(String idCifNif);
	
}
