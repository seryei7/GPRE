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
@Table(name = "NODOS")
@NamedQueries({
	@NamedQuery(name="nodosNivelPorNivel", 
			query="SELECT e "
					+ "FROM Nodos e "
					+ "INNER JOIN NodosNivel n "
					+ "ON e.idnodo = n.cdnodo WHERE n.escalon = ?1 "
					+ "ORDER BY e.idnodo"
					)
})
@IdClass(Nodos_Key.class)
public class Nodos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3872046225301559641L;

	@Id
	private BigDecimal idnodo;
	@Column
	private String dsnodo;
	@Column
	private BigDecimal cdnodopadre;
	@Column
	private String funcionalidad;
	@Column
	private String iconopen;
	@Column
	private String iconclosed;
	
	public Nodos() {}

	/**
	 * @param idNodo
	 * @param dsNodo
	 * @param cdNodoPadre
	 * @param funcionalidad
	 * @param iconOpen
	 * @param iconClosed
	 * @param nodosNivel
	 */
	public Nodos(BigDecimal idNodo, String dsNodo, BigDecimal cdNodoPadre, String funcionalidad, String iconOpen,
			String iconClosed) {
		this.idnodo = idNodo;
		this.dsnodo = dsNodo;
		this.cdnodopadre = cdNodoPadre;
		this.funcionalidad = funcionalidad;
		this.iconopen = iconOpen;
		this.iconclosed = iconClosed;
	}

	/**
	 * @return el idNodo
	 */
	public BigDecimal getIdNodo() {
		return idnodo;
	}

	/**
	 * @param idNodo el idNodo a establecer
	 */
	public void setIdNodo(BigDecimal idNodo) {
		this.idnodo = idNodo;
	}

	/**
	 * @return el dsNodo
	 */
	public String getDsNodo() {
		return dsnodo;
	}

	/**
	 * @param dsNodo el dsNodo a establecer
	 */
	public void setDsNodo(String dsNodo) {
		this.dsnodo = dsNodo;
	}

	/**
	 * @return el cdNodoPadre
	 */
	public BigDecimal getCdNodoPadre() {
		return cdnodopadre;
	}

	/**
	 * @param cdNodoPadre el cdNodoPadre a establecer
	 */
	public void setCdNodoPadre(BigDecimal cdNodoPadre) {
		this.cdnodopadre = cdNodoPadre;
	}

	/**
	 * @return el funcionalidad
	 */
	public String getFuncionalidad() {
		return funcionalidad;
	}

	/**
	 * @param funcionalidad el funcionalidad a establecer
	 */
	public void setFuncionalidad(String funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	/**
	 * @return el iconOpen
	 */
	public String getIconOpen() {
		return iconopen;
	}

	/**
	 * @param iconOpen el iconOpen a establecer
	 */
	public void setIconOpen(String iconOpen) {
		this.iconopen = iconOpen;
	}

	/**
	 * @return el iconClosed
	 */
	public String getIconClosed() {
		return iconclosed;
	}

	/**
	 * @param iconClosed el iconClosed a establecer
	 */
	public void setIconClosed(String iconClosed) {
		this.iconclosed = iconClosed;
	}


		

}