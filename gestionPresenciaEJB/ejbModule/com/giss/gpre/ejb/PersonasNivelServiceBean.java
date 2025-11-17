package com.giss.gpre.ejb;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.giss.gpre.datos.DatosPersonaNivelAcceso;
import com.giss.gpre.entidades.Persona;
import com.giss.gpre.entidades.Persona_Home;

@Stateless
public class PersonasNivelServiceBean implements PersonasNivelService		{
	
	@PersistenceContext(unitName="gestionPresencia")
	private EntityManager em;

	@Override
	public List<Persona> allUsuarios() {
		List<Persona> listaPersonas = new ArrayList<>();
		Persona_Home personas_home = new Persona_Home(em);
		listaPersonas = personas_home.allUsuarios();		
		
		return listaPersonas;
	}

	@Override
	public List<Persona> personaForGestor(BigDecimal entidad, BigDecimal provincia, BigDecimal centro) {
		List<Persona> listaPersonas = new ArrayList<>();
		Persona_Home personas_home = new Persona_Home(em);
		listaPersonas = personas_home.personasForGestor(entidad, provincia, centro);
		
		return listaPersonas;
	}
	
	@Override
	public List<DatosPersonaNivelAcceso> GetNivelVistaPersona(String dni, BigDecimal entidad, BigDecimal provincia, BigDecimal centro) throws IOException {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callGetNivelVistaPersona").setParameter("pEntiGesEp", entidad).setParameter("pProvEp", provincia).setParameter("pCenGesEp", centro).setParameter("pCdUsuario", dni);
        List<Object[]> rawResults = query.getResultList();
        List<DatosPersonaNivelAcceso> dtos = new ArrayList<>();

        for (Object[] row : rawResults) {
        	DatosPersonaNivelAcceso dto = new DatosPersonaNivelAcceso((String) row[1], (String) row[2], (String) row[3], (String) row[0], (BigDecimal) row[6], (BigDecimal) row[7], (BigDecimal) row[8], (String) row[5]);
            dtos.add(dto);
        }

        return dtos;
	}
	
}
