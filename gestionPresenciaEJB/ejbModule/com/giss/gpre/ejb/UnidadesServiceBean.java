package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.giss.gpre.datos.DatosUnidades;
import com.giss.gpre.entidades.Unidades;
import com.giss.gpre.entidades.Unidades_Home;

@Stateless
public class UnidadesServiceBean implements UnidadesService		{
	
	@PersistenceContext(unitName="gestionPresencia")
	private EntityManager em;

	@Override
	public List<DatosUnidades> obtenerUnidades(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp,
			String pCdCentro, String pCdUsuario) {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetVistaUnidad")
				.setParameter("pEntiGesEp", entiGesEp)
				.setParameter("pProvEp", provEp)
				.setParameter("PCenGesEp", cenGesEp)
				.setParameter("pCdCentro", pCdCentro)
				.setParameter("pCdUsuario", pCdUsuario);
		
		query.execute();
		
		List<Object[]> rawResults = query.getResultList();
		List<DatosUnidades> dtos = new ArrayList<>();

		for (Object[] row : rawResults) {
			DatosUnidades dto = new DatosUnidades((BigDecimal) row[0], (BigDecimal) row[1], (BigDecimal) row[2],
					(String) row[3], (String) row[4], (String) row[5], (String) row[6], (String) row[7],
					(String) row[8], (String) row[9], (String) row[10], (BigDecimal) row[11]);
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Override
	public List<Unidades> findByIdUnidad(String idunidad) {
		Unidades_Home unidades_home = new Unidades_Home(em);
		return unidades_home.findByIdUnidad(idunidad);
	}

	@Override
	public List<Unidades> findBySituacion(String situacion) {
		Unidades_Home unidades_home = new Unidades_Home(em);
		return unidades_home.findBySituacion(situacion);
	}

	@Override
	public List<Unidades> findByEntiProvCen(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String cdCentro) {
		Unidades_Home unidades_home = new Unidades_Home(em);
		return unidades_home.findByEntiProvCenCentro(entiGesEp, provEp, cenGesEp, cdCentro);
	}

	@Override
	public List<Unidades> findAll() {
		Unidades_Home unidades_home = new Unidades_Home(em);
		return unidades_home.findAll();
	}
	
}
