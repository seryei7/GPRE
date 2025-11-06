package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.giss.gpre.entidades.Persona;
import com.giss.gpre.entidades.Persona_Home;

@Stateless
public class UsuariosServiceBean implements UsuariosService	{
	
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
	
}
