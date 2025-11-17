package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.giss.gpre.datos.DatosGeneraListado;
import com.giss.gpre.entidades.GpreListados;
import com.giss.gpre.entidades.GpreListados_Home;
import com.giss.gpre.entidades.GpreListados_Key;

@Stateless
public class ListadoServiceBean implements ListadoService {

	@PersistenceContext(unitName = "gestionPresencia")
	private EntityManager em;

	@Override
	public DatosGeneraListado generarListado(String listado, String cfInicio, String cfFin,
	        String cdniUsr, String cDetalle, String cdniPer, String cFecha, BigDecimal nEntiGesEp,
	        BigDecimal nProvEp, BigDecimal nCenGesEp, BigDecimal nHoraInicio, BigDecimal nHoraFin, 
	        String cConjunto, String cTipo, String vgOpcion, String nomInforme) {
		
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGeneraListado")
				.setParameter("p_listado", listado)
		        .setParameter("p_CFINICIO", cfInicio)
		        .setParameter("p_CFFIN", cfFin)
		        .setParameter("p_CDNIUSR", cdniUsr)
		        .setParameter("p_CDETALLE", cDetalle)
		        .setParameter("p_CDNIPER", cdniPer)
		        .setParameter("p_CFECHA", cFecha)
		        .setParameter("p_NENTIGESEP", nEntiGesEp)
		        .setParameter("p_NPROVEP", nProvEp)
		        .setParameter("p_NCENGESEP", nCenGesEp)
		        .setParameter("p_NHORAINICIO", nHoraInicio)
		        .setParameter("p_NHORAFIN", nHoraFin)
		        .setParameter("p_CCONJUNTO", cConjunto)
		        .setParameter("p_CTIPO", cTipo)
		        .setParameter("p_VG_OPCION", vgOpcion)
		        .setParameter("p_NOMINFORME", nomInforme);
		
		query.execute();
		
		DatosGeneraListado resultado = new DatosGeneraListado();
		resultado.setNumRegistros((BigDecimal) query.getOutputParameterValue("p_NNUMREGISTROS"));
		resultado.setCodigoError((String) query.getOutputParameterValue("p_CERRCOD"));
		resultado.setDescripcionError((String) query.getOutputParameterValue("p_CERRDES"));
		
		return resultado;
	}

	@Override
	public GpreListados findById(BigDecimal id) {
		GpreListados_Home gpreListados_Home = new GpreListados_Home(em);
		return gpreListados_Home.findById(id);
	}

	@Override
	public List<GpreListados> findByUsuario(String usuario) {
		GpreListados_Home gpreListados_Home = new GpreListados_Home(em);
		return gpreListados_Home.findByUsuario(usuario);
	}

	@Override
	public List<GpreListados> findAll() {
		GpreListados_Home gpreListados_Home = new GpreListados_Home(em);
		return gpreListados_Home.findAll();
	}
	
	@Override
	public void saveListado(GpreListados gpreListados) {
		GpreListados_Key key = new GpreListados_Key(gpreListados.getIdListado());
		if (em.find(GpreListados.class, key) == null) {
			em.persist(gpreListados);
		} else {
			em.merge(gpreListados);
		}
		em.flush();
	}

}
