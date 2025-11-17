package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
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
 * Bean de implementacion de la tabla de la base de datos Acesso_area_nivel
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "ACCESO_AREA_NIVEL")
@NamedQueries({
	@NamedQuery(name="findByIdDni",	query="select e from Acceso_area_nivel e where e.docNacional = ?1 order by e.enti_ges_ep, e.prov_ep, e.cen_ges_ep, e.escalon"),
	@NamedQuery(name="findByEntProCen",	query="select e from Acceso_area_nivel e where e.enti_ges_ep = ?1 and e.prov_ep = ?2 and e.cen_ges_ep = ?3")
})

@NamedStoredProcedureQuery(
		 name = "callObtenerAreasTrabajo",
	     procedureName = "gpre_personas.ObtenerAreasTrabajo",
	     resultClasses = Acceso_area_nivel.class,
	     parameters = {
	         @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = Void.class),
	         @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_iddni", type = String.class)
})

@IdClass(Acceso_area_nivel_Key.class)
public class Acceso_area_nivel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9042262478723252211L;
	
	@Id
	@Column(name="iddni")
	private String docNacional;
	@Id
	private BigDecimal enti_ges_ep;
	@Id
	private BigDecimal prov_ep;
	@Id
	private BigDecimal cen_ges_ep;
	@Id
	@Column(name="cdnivel")
	private BigDecimal escalon;

	public Acceso_area_nivel() {}

	public Acceso_area_nivel(String docNacional, BigDecimal enti_ges_ep, BigDecimal prov_ep, BigDecimal cen_ges_ep, BigDecimal escalon) {
		setDocNacional(docNacional);
		setEnti_ges_ep(enti_ges_ep);
		setProv_ep(prov_ep);
		setCen_ges_ep(cen_ges_ep);
		setEscalon(escalon);
	}

	/**
	 * @return el idDni
	 */
	public String getDocNacional() {
		return docNacional;
	}

	/**
	 * @param idDni el idDni a establecer
	 */
	public void setDocNacional(String docNacional) {
		this.docNacional = docNacional;
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
	 * @return el cdNivel
	 */
	public BigDecimal getEscalon() {
		return escalon;
	}

	/**
	 * @param cdNivel el cdNivel a establecer
	 */
	public void setEscalon(BigDecimal escalon) {
		this.escalon = escalon;
	}

//	/**
//	 * @return el areas
//	 */
//	public Areas_trabajo getAreas() {
//		return areas;
//	}
//
//	/**
//	 * @param areas el areas a establecer
//	 */
//	public void setAreas(Areas_trabajo areas) {
//		this.areas = areas;
//	}
	
	

}