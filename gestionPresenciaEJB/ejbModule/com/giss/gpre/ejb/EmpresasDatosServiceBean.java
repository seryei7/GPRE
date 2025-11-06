package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.giss.gpre.entidades.EmpresasDatos;
import com.giss.gpre.entidades.EmpresasDatos_Home;

@Stateless
public class EmpresasDatosServiceBean implements EmpresasDatosService {
	
	@PersistenceContext(unitName="gestionPresencia")
	private EntityManager em;

	@Override
	public List<EmpresasDatos> findAll() {
		EmpresasDatos_Home empresasDatosHome = new EmpresasDatos_Home(em);
		return empresasDatosHome.findAll();
	}

	@Override
	public List<EmpresasDatos> findByEntiProvCen(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp) {
		EmpresasDatos_Home empresasDatosHome = new EmpresasDatos_Home(em);
		return empresasDatosHome.findByEntiProvCen(entiGesEp, provEp, cenGesEp);
	}

	@Override
	public List<EmpresasDatos> findByIdCifNif(String idCifNif) {
		EmpresasDatos_Home empresasDatosHome = new EmpresasDatos_Home(em);
		return empresasDatosHome.findByIdCifNif(idCifNif);
	}

	@Override
	public List<EmpresasDatos> findByNombre(String nombre) {
		EmpresasDatos_Home empresasDatosHome = new EmpresasDatos_Home(em);
		return empresasDatosHome.findByNombre(nombre);
	}

	@Override
	public void save(EmpresasDatos empresasDatos) {
		EmpresasDatos_Home empresasDatosHome = new EmpresasDatos_Home(em);
		empresasDatosHome.save(empresasDatos);
	}

	@Override
	public void delete(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String idCifNif) {
		EmpresasDatos_Home empresasDatosHome = new EmpresasDatos_Home(em);
		empresasDatosHome.delete(entiGesEp, provEp, cenGesEp, idCifNif);
	}
	
}
