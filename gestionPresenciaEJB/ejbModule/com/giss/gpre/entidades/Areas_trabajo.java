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
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Stateless
@LocalBean
@Entity
@Table(name = "AREAS_TRABAJO")
@NamedQueries({
		@NamedQuery(name = "findById", query = "select e from Areas_trabajo e where e.enti_ges_ep = ?1 and e.prov_ep = ?2 and e.cen_ges_ep = ?3")
})
@NamedStoredProcedureQueries({
@NamedStoredProcedureQuery(
    name = "callGetAreasTrabajo",
    procedureName = "packacceso.GetAreasTrabajo",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "Rc1", type = Void.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pIdDNI", type = String.class)
    }),
@NamedStoredProcedureQuery(
	    name = "callGetAreasTrabajoSel",
	    procedureName = "packacceso.GetAreasTrabajoAsc",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "Rc1", type = Void.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pIdDNI", type = String.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pIdEntiGesEp", type = BigDecimal.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pIdProvEp", type = BigDecimal.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pIdCenGesEp", type = BigDecimal.class)
	    })
})
@IdClass(Areas_trabajo_Key.class)
public class Areas_trabajo implements Serializable {

	/**
	 * SELECT a.enti_ges_ep AS entidad, a.prov_ep AS provincia, a.cen_ges_ep AS
	 * centro, a.denominacion AS denominacion, b.cdnivel AS nivel FROM
	 * areas_trabajo a JOIN acceso_area_nivel b ON a.enti_ges_ep = b.enti_ges_ep
	 * AND a.prov_ep = b.prov_ep AND a.cen_ges_ep = b.cen_ges_ep WHERE b.iddni =
	 * '000413207N'
	 */
	private static final long serialVersionUID = -1557661644099640238L;
	@Id
	private BigDecimal enti_ges_ep;
	@Id
	private BigDecimal prov_ep;
	@Id
	private BigDecimal cen_ges_ep;
	@Column
	private String denominacion;

	public Areas_trabajo() {
	}

	public Areas_trabajo(BigDecimal enti_ges_ep, BigDecimal prov_ep, BigDecimal cen_ges_ep, String denominacion) {
		this.enti_ges_ep = enti_ges_ep;
		this.prov_ep = prov_ep;
		this.cen_ges_ep = cen_ges_ep;
		this.denominacion = denominacion;
	}

	/**
	 * @return el enti_ges_ep
	 */
	public BigDecimal getEnti_ges_ep() {
		return enti_ges_ep;
	}

	/**
	 * @param enti_ges_ep
	 *            el enti_ges_ep a establecer
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
	 * @param prov_ep
	 *            el prov_ep a establecer
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
	 * @param cen_ges_ep
	 *            el cen_ges_ep a establecer
	 */
	public void setCen_ges_ep(BigDecimal cen_ges_ep) {
		this.cen_ges_ep = cen_ges_ep;
	}

	/**
	 * @return el denominacion
	 */
	public String getDenominacion() {
		return denominacion;
	}

	/**
	 * @param denominacion
	 *            el denominacion a establecer
	 */
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

//	/**
//	 * @return el acceso
//	 */
//	public Acceso_area_nivel getAcceso() {
//		return acceso;
//	}
//
//	/**
//	 * @param acceso el acceso a establecer
//	 */
//	public void setAcceso(Acceso_area_nivel acceso) {
//		this.acceso = acceso;
//	}
	
	

}
