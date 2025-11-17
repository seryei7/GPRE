package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

import com.giss.gpre.datos.DatosPersonaBasico;
import com.giss.gpre.entidades.Persona;
import com.giss.gpre.entidades.CentrosUnidadesSel;
import com.giss.gpre.entidades.TiposPersonalSel;
import com.giss.gpre.entidades.EmpresasSel;

@Remote
public interface PersonaBasicoService {

	/**
	 * Obtiene los datos básicos de una persona por su DNI usando el PL
	 * GetBasicoPersona
	 * 
	 * @param dni
	 *            DNI de la persona
	 * @return Lista de PersonaBasico
	 */
	public List<DatosPersonaBasico> obtenerDatosBasicos(String dni);

	// -------- Persona --------
	public Persona findPersonaByDNI(String dni);

	public List<Persona> findAllPersonas();

	public void savePersona(Persona persona);

	public void deletePersona(String dni);

	// -------- CentrosUnidadesSel --------
	public List<CentrosUnidadesSel> findAllCentrosUnidadesSel();
	
	public List<CentrosUnidadesSel> findCentrosByUsuario(String usuario);

	public void saveCentrosUnidadesSel(CentrosUnidadesSel entity);

	public void deleteCentrosUnidadesSel(String usuario, BigDecimal enti, BigDecimal prov, BigDecimal cen, String cdCentro,
			String cdUnidad);
	
	public void deleteCentrosByUsuario(String usuario);

	// -------- TiposPersonalSel --------
	public List<TiposPersonalSel> findAllTiposPersonalSel();
	
	public List<TiposPersonalSel> findTiposByUsuario(String usuario);

	public void saveTiposPersonalSel(TiposPersonalSel entity);

	public void deleteTiposPersonalSel(String usuario, BigDecimal enti, BigDecimal prov, BigDecimal cen,
			BigDecimal cdTipoPersonal);
	
	public void deleteTiposByUsuario(String usuario);

	// -------- EmpresasSel --------
	public List<EmpresasSel> findAllEmpresasSel();
	
	public List<EmpresasSel> findEmpresasByUsuario(String usuario);

	public void saveEmpresasSel(EmpresasSel entity);

	public void deleteEmpresasSel(String usuario, BigDecimal enti, BigDecimal prov, BigDecimal cen, String cdCifNif);
	
	public void deleteEmpresasByUsuario(String usuario);
}
