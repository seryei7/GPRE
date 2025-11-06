package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

/**
 * Bean de implementacion de la tabla de la base de datos GrupoUsuarios
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "GRUPOUSUARIOS")
@NamedQueries({ 
	@NamedQuery(name = "findByPersonalNivelAcceso", query = "select e from GrupoUsuarios e where e.docNacional = ?1 and e.enti_ges_ep = ?2 AND e.prov_ep = ?3 AND e.cen_ges_ep = ?4"),
	})

@NamedStoredProcedureQuery(
		 name = "callGetNivelVistaPersona",
	     procedureName = "packpersonas.getnivelvistapersona",
	     parameters = {
	         @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "Rc1", type = Void.class),
	         @StoredProcedureParameter(mode = ParameterMode.IN, name = "pEntiGesEp", type = BigDecimal.class),
	         @StoredProcedureParameter(mode = ParameterMode.IN, name = "pProvEp", type = BigDecimal.class),
	         @StoredProcedureParameter(mode = ParameterMode.IN, name = "pCenGesEp", type = BigDecimal.class),
	         @StoredProcedureParameter(mode = ParameterMode.IN, name = "pCdUsuario", type = String.class)
})
@IdClass(GrupoUsuarios_Key.class)
public class GrupoUsuarios implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2989233745092637476L;

	
	@Id
	private BigDecimal enti_ges_ep;
	@Id
	private BigDecimal prov_ep;
	@Id
	private BigDecimal cen_ges_ep;
	@Id
	private String docNacional;
	@Id
	private String codigoU;

	public GrupoUsuarios() {
	}

	/**
	 * @param enti_ges_ep
	 * @param prov_ep
	 * @param cen_ges_ep
	 * @param cddni
	 * @param cdusuario
	 */
	public GrupoUsuarios(BigDecimal enti_ges_ep, BigDecimal prov_ep, BigDecimal cen_ges_ep, String docNacional,
			String codigoU) {
		super();
		this.enti_ges_ep = enti_ges_ep;
		this.prov_ep = prov_ep;
		this.cen_ges_ep = cen_ges_ep;
		this.docNacional = docNacional;
		this.codigoU = codigoU;
	}

	/**
	 * @return el enti_ges_ep
	 */
	public BigDecimal getEnti_ges_ep() {
		return enti_ges_ep;
	}

	/**
	 * @param enti_ges_ep el enti_ges_ep a establecer
	 */
	public void setEnti_ges_ep(BigDecimal enti_ges_ep) {
		this.enti_ges_ep = enti_ges_ep;
	}

	/**
	 * @return el prov_ep
	 */
	public BigDecimal getProv_ep() {
		return prov_ep;
	}

	/**
	 * @param prov_ep el prov_ep a establecer
	 */
	public void setProv_ep(BigDecimal prov_ep) {
		this.prov_ep = prov_ep;
	}

	/**
	 * @return el cen_ges_ep
	 */
	public BigDecimal getCen_ges_ep() {
		return cen_ges_ep;
	}

	/**
	 * @param cen_ges_ep el cen_ges_ep a establecer
	 */
	public void setCen_ges_ep(BigDecimal cen_ges_ep) {
		this.cen_ges_ep = cen_ges_ep;
	}

	/**
	 * @return el cddni
	 */
	public String getCddni() {
		return docNacional;
	}

	/**
	 * @param cddni el cddni a establecer
	 */
	public void setCddni(String docNacional) {
		this.docNacional = docNacional;
	}

	/**
	 * @return el cdusuario
	 */
	public String getCdusuario() {
		return codigoU;
	}

	/**
	 * @param cdusuario el cdusuario a establecer
	 */
	public void setCdusuario(String codigoU) {
		this.codigoU = codigoU;
	}

	
}