package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.giss.gpre.datos.DatosEmpresas;
import com.giss.gpre.entidades.Empresas;
import com.giss.gpre.entidades.Empresas_Home;

@Stateless
public class EmpresasServiceBean implements EmpresasService {
	
	@PersistenceContext(unitName="gestionPresencia")
	private EntityManager em;

	@Override
	public List<Empresas> findAll() {
		Empresas_Home empresasHome = new Empresas_Home(em);
		return empresasHome.findAll();
	}

	@Override
	public Empresas findByIdCifNif(String idCifNif) {
		Empresas_Home empresasHome = new Empresas_Home(em);
		return empresasHome.findByIdCifNif(idCifNif);
	}

	@Override
	public List<Empresas> findByNombre(String nombre) {
		Empresas_Home empresasHome = new Empresas_Home(em);
		return empresasHome.findByNombre(nombre);
	}

	@Override
	public List<Empresas> findActivas() {
		Empresas_Home empresasHome = new Empresas_Home(em);
		return empresasHome.findActivas();
	}

	@Override
	public void save(Empresas empresas) {
		Empresas_Home empresasHome = new Empresas_Home(em);
		empresasHome.save(empresas);
	}

	@Override
	public void delete(String idCifNif) {
		Empresas_Home empresasHome = new Empresas_Home(em);
		empresasHome.delete(idCifNif);
	}

	@Override
	public List<DatosEmpresas> obtenerEmpresas(String pCdUsuario) {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetVistaEmpresa")
				.setParameter("pCdUsuario", pCdUsuario);
		
		query.execute();
		
		List<Object[]> rawResults = query.getResultList();
		List<DatosEmpresas> dtos = new ArrayList<>();

		for (Object[] row : rawResults) {
			DatosEmpresas dto = new DatosEmpresas((String) row[0], (String) row[1], (String) row[2],
					(String) row[3], (BigDecimal) row[4], (BigDecimal) row[5], (BigDecimal) row[6], (BigDecimal) row[7]);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
}
