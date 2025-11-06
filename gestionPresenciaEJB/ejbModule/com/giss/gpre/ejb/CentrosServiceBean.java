package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.giss.gpre.datos.DatosCentro;
import com.giss.gpre.entidades.Centros;
import com.giss.gpre.entidades.Centros_Home;

@Stateless
public class CentrosServiceBean implements CentrosService		{
	
	@PersistenceContext(unitName="gestionPresencia")
	private EntityManager em;

	@Override
	public List<DatosCentro> obtenerCentros(String dni) {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetVistaCentro")
				.setParameter("pCdUsuario", dni);
		
		query.execute();
		
		List<Object[]> rawResults = query.getResultList();
		List<DatosCentro> dtos = new ArrayList<>();

		for (Object[] row : rawResults) {
			DatosCentro dto = new DatosCentro((BigDecimal) row[0], (BigDecimal) row[1], (BigDecimal) row[2],
					(String) row[3], (String) row[4], (String) row[5], (String) row[6], (String) row[7],
					(String) row[8], (String) row[9], (BigDecimal) row[10]);
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Override
	public List<Centros> findByIdCentro(String idcentro) {
		Centros_Home centros_home = new Centros_Home(em);
		return centros_home.findByIdCentro(idcentro);
	}

	@Override
	public List<Centros> findByEntiProvCen(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp) {
		Centros_Home centros_home = new Centros_Home(em);
		return centros_home.findByEntiProvCen(entiGesEp, provEp, cenGesEp);
	}

	@Override
	public List<Centros> findAll() {
		Centros_Home centros_home = new Centros_Home(em);
		return centros_home.findAll();
	}

	
}
