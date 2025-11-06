package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.giss.gpre.datos.DatosPersonaBasico;
import com.giss.gpre.entidades.Persona;
import com.giss.gpre.entidades.CentrosUnidadesSel;
import com.giss.gpre.entidades.CentrosUnidadesSel_Home;
import com.giss.gpre.entidades.CentrosUnidadesSel_Key;
import com.giss.gpre.entidades.TiposPersonalSel;
import com.giss.gpre.entidades.TiposPersonalSel_Home;
import com.giss.gpre.entidades.TiposPersonalSel_Key;
import com.giss.gpre.entidades.EmpresasSel;
import com.giss.gpre.entidades.EmpresasSel_Home;
import com.giss.gpre.entidades.EmpresasSel_Key;

@Stateless
public class PersonaBasicoServiceBean implements PersonaBasicoService {

	@PersistenceContext(unitName = "gestionPresencia")
	private EntityManager em;

	// ---------------- Persona ----------------
	@Override
	public List<DatosPersonaBasico> obtenerDatosBasicos(String dni) {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetBasicoPersona").setParameter("pCdIddni",
				dni);
		List<Object[]> rawResults = query.getResultList();
		List<DatosPersonaBasico> dtos = new ArrayList<>();

		for (Object[] row : rawResults) {
			DatosPersonaBasico dto = new DatosPersonaBasico((String) row[0], (BigDecimal) row[1], (BigDecimal) row[2],
					(BigDecimal) row[3], (String) row[4], (BigDecimal) row[5], (String) row[6], (BigDecimal) row[7],
					(String) row[8], (BigDecimal) row[9], (BigDecimal) row[10], (String) row[11], (BigDecimal) row[12],
					(String) row[13], (String) row[14], (String) row[15], (BigDecimal) row[16], (String) row[17],
					(BigDecimal) row[18], (String) row[19], (String) row[20], (String) row[21], (String) row[22],
					(String) row[23], (String) row[24], (String) row[25], (BigDecimal) row[26], (String) row[27],
					(String) row[28], (BigDecimal) row[29], (String) row[30], (String) row[31], (String) row[32],
					(String) row[33], (String) row[34], (String) row[35], (String) row[36], (String) row[37],
					(String) row[38], (BigDecimal) row[39]);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public Persona findPersonaByDNI(String dni) {
		return em.createNamedQuery("personaByDNI", Persona.class).setParameter(1, dni).getSingleResult();
	}

	@Override
	public List<Persona> findAllPersonas() {
		return em.createNamedQuery("allUsers", Persona.class).getResultList();
	}

	@Override
	public void savePersona(Persona persona) {
		if (em.find(Persona.class, persona.getDocNacional()) == null) {
			em.persist(persona);
		} else {
			em.merge(persona);
		}
	}

	@Override
	public void deletePersona(String dni) {
		Persona p = em.find(Persona.class, dni);
		if (p != null) {
			em.remove(p);
		}
	}

	// ---------------- CentrosUnidadesSel ----------------
	@Override
	public List<CentrosUnidadesSel> findAllCentrosUnidadesSel() {
		CentrosUnidadesSel_Home centrosUnidadesSel_Home = new CentrosUnidadesSel_Home(em);
		return centrosUnidadesSel_Home.findAll();
	}
	
	@Override
	public List<CentrosUnidadesSel> findCentrosByUsuario(String usuario) {
		CentrosUnidadesSel_Home centrosUnidadesSel_Home = new CentrosUnidadesSel_Home(em);
		return centrosUnidadesSel_Home.findByUsuario(usuario);
	}

	@Override
	public void saveCentrosUnidadesSel(CentrosUnidadesSel entity) {
		CentrosUnidadesSel_Key key = new CentrosUnidadesSel_Key(entity.getIdUsuario(), entity.getEntiGesEp(),
				entity.getProvEp(), entity.getCenGesEp(), entity.getCdCentro(), entity.getCdUnidad());
		if (em.find(CentrosUnidadesSel.class, key) == null) {
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		em.flush();
	}

	@Override
	public void deleteCentrosUnidadesSel(String usuario, BigDecimal enti, BigDecimal prov, BigDecimal cen,
			String cdCentro, String cdUnidad) {
		CentrosUnidadesSel_Key key = new CentrosUnidadesSel_Key(usuario, enti, prov, cen, cdCentro, cdUnidad);
		CentrosUnidadesSel entity = em.find(CentrosUnidadesSel.class, key);
		if (entity != null) {
			em.remove(entity);
		}
		em.flush();
	}
	
	@Override
	public void deleteCentrosByUsuario(String usuario) {
		CentrosUnidadesSel_Home centrosUnidadesSel_Home = new CentrosUnidadesSel_Home(em);
		List<CentrosUnidadesSel> entities = centrosUnidadesSel_Home.findByUsuario(usuario);
		for (Iterator<CentrosUnidadesSel> iterator = entities.iterator(); iterator.hasNext();) {
			CentrosUnidadesSel entity = (CentrosUnidadesSel) iterator.next();
			em.remove(entity);
		}
		em.flush();
	}

	// ---------------- TiposPersonalSel ----------------
	@Override
	public List<TiposPersonalSel> findAllTiposPersonalSel() {
		TiposPersonalSel_Home tiposPersonalSel_Home = new TiposPersonalSel_Home(em);
		return tiposPersonalSel_Home.findAll();
	}
	
	@Override
	public List<TiposPersonalSel> findTiposByUsuario(String usuario) {
		TiposPersonalSel_Home tiposPersonalSel_Home = new TiposPersonalSel_Home(em);
		return tiposPersonalSel_Home.findByUsuario(usuario);
	}

	@Override
	public void saveTiposPersonalSel(TiposPersonalSel entity) {
		TiposPersonalSel_Key key = new TiposPersonalSel_Key(entity.getIdUsuario(), entity.getEntiGesEp(),
				entity.getProvEp(), entity.getCenGesEp(), entity.getCdTipoPersonal());
		if (em.find(TiposPersonalSel.class, key) == null) {
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		em.flush();
	}

	@Override
	public void deleteTiposPersonalSel(String usuario, BigDecimal enti, BigDecimal prov, BigDecimal cen,
			BigDecimal cdTipoPersonal) {
		TiposPersonalSel_Key key = new TiposPersonalSel_Key(usuario, enti, prov, cen, cdTipoPersonal);
		TiposPersonalSel entity = em.find(TiposPersonalSel.class, key);
		if (entity != null) {
			em.remove(entity);
		}
	}
	
	@Override
	public void deleteTiposByUsuario(String usuario) {
		TiposPersonalSel_Home tiposPersonalSel_Home = new TiposPersonalSel_Home(em);
		List<TiposPersonalSel> entities = tiposPersonalSel_Home.findByUsuario(usuario);
		for (Iterator<TiposPersonalSel> iterator = entities.iterator(); iterator.hasNext();) {
			TiposPersonalSel entity = (TiposPersonalSel) iterator.next();
			em.remove(entity);
		}
		em.flush();
	}

	// ---------------- EmpresasSel ----------------
	@Override
	public List<EmpresasSel> findAllEmpresasSel() {
		EmpresasSel_Home empresasSel_Home = new EmpresasSel_Home(em);
		return empresasSel_Home.findAll();
	}
	
	@Override
	public List<EmpresasSel> findEmpresasByUsuario(String usuario) {
		EmpresasSel_Home empresasSel_Home = new EmpresasSel_Home(em);
		return empresasSel_Home.findByUsuario(usuario);
	}
	

	@Override
	public void saveEmpresasSel(EmpresasSel entity) {
		EmpresasSel_Key key = new EmpresasSel_Key(entity.getIdUsuario(), entity.getEntiGesEp(), entity.getProvEp(),
				entity.getCenGesEp(), entity.getCdFiscal());
		if (em.find(EmpresasSel.class, key) == null) {
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		em.flush();
	}

	@Override
	public void deleteEmpresasSel(String usuario, BigDecimal enti, BigDecimal prov, BigDecimal cen, String cdCifNif) {
		EmpresasSel_Key key = new EmpresasSel_Key(usuario, enti, prov, cen, cdCifNif);
		EmpresasSel entity = em.find(EmpresasSel.class, key);
		if (entity != null) {
			em.remove(entity);
		}
		em.flush();
	}
	
	@Override
	public void deleteEmpresasByUsuario(String usuario) {
		EmpresasSel_Home empresasSel_Home = new EmpresasSel_Home(em);
		List<EmpresasSel> entities = empresasSel_Home.findByUsuario(usuario);
		for (Iterator<EmpresasSel> iterator = entities.iterator(); iterator.hasNext();) {
			EmpresasSel entity = (EmpresasSel) iterator.next();
			em.remove(entity);
		}
		em.flush();
	}
	
}
