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
@Table(name = "NODOSNIVEL")
@NamedQueries({
	@NamedQuery(name="nodosNivel",
			query="select e from NodosNivel e")
})
@IdClass(NodosNivel_Key.class)
public class NodosNivel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3733857286946365134L;
	
	
	@Id
	@Column(name="cdnivel")
	private BigDecimal escalon;
	@Id
	private BigDecimal cdnodo;

	/**
	 * constructor de la clase sin arg
	 */
	public NodosNivel() {}

	/** 
	 * Constructor de la clase con arg
	 * 
	 * @param cdNivel
	 * @param cdNodo
	 */
	public NodosNivel(BigDecimal escalon, BigDecimal cdNodo) {
		this.escalon = escalon;
		this.cdnodo = cdNodo;
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

	/**
	 * @return el cdNodo
	 */
	public BigDecimal getCdNodo() {
		return cdnodo;
	}

	/**
	 * @param cdNodo el cdNodo a establecer
	 */
	public void setCdNodo(BigDecimal cdNodo) {
		this.cdnodo = cdNodo;
	}

}