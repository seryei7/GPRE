package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.giss.gpre.datos.DatosTipoPersonal;
import com.giss.gpre.entidades.TipoPersonal;
import com.giss.gpre.entidades.TipoPersonal_Home;

@Stateless
public class TipoPersonalServiceBean implements TipoPersonalService {
	
	@PersistenceContext(unitName="gestionPresencia")
	private EntityManager em;

	@Override
	public List<TipoPersonal> findAll() {
		TipoPersonal_Home tipoPersonalHome = new TipoPersonal_Home(em);
		return tipoPersonalHome.findAll();
	}

	@Override
	public TipoPersonal findByIdTipoPersonal(BigDecimal idTipoPersonal) {
		TipoPersonal_Home tipoPersonalHome = new TipoPersonal_Home(em);
		return tipoPersonalHome.findByIdTipoPersonal(idTipoPersonal);
	}

	@Override
	public List<TipoPersonal> findByPersonalExterno(String personalExterno) {
		TipoPersonal_Home tipoPersonalHome = new TipoPersonal_Home(em);
		return tipoPersonalHome.findByPersonalExterno(personalExterno);
	}

	@Override
	public void save(TipoPersonal tipoPersonal) {
		TipoPersonal_Home tipoPersonalHome = new TipoPersonal_Home(em);
		tipoPersonalHome.save(tipoPersonal);
	}

	@Override
	public void delete(BigDecimal idTipoPersonal) {
		TipoPersonal_Home tipoPersonalHome = new TipoPersonal_Home(em);
		tipoPersonalHome.delete(idTipoPersonal);
	}

	@Override
	public List<DatosTipoPersonal> obtenerTipoPersonal() {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetVistaTipoPersonal");
		
		query.execute();
		
		List<Object[]> rawResults = query.getResultList();
		List<DatosTipoPersonal> dtos = new ArrayList<>();

		for (Object[] row : rawResults) {
			DatosTipoPersonal dto = new DatosTipoPersonal((BigDecimal) row[0], (String) row[1], (String) row[2], (BigDecimal) row[3]);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
}
