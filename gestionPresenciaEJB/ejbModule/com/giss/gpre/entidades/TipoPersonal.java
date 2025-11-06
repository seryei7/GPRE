package com.giss.gpre.entidades;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Bean de implementacion de la tabla de la base de datos TIPOPERSONAL
 * 
 * @author GISS
 *
 */
@Stateless
@LocalBean
@Entity
@Table(name = "TIPOPERSONAL")
@IdClass(TipoPersonal_Key.class)
@NamedQueries({
    @NamedQuery(name = "TipoPersonal.findAll", query = "SELECT t FROM TipoPersonal t"),
    @NamedQuery(name = "TipoPersonal.findByIdTipoPersonal", query = "SELECT t FROM TipoPersonal t WHERE t.idTipoPersonal = ?1"),
    @NamedQuery(name = "TipoPersonal.findByPersonalExterno", query = "SELECT t FROM TipoPersonal t WHERE t.personalExterno = ?1")
})
@NamedStoredProcedureQuery(
	    name = "callGetVistaTipoPersonal",
	    procedureName = "packtipopersonal.GetVistaTipoPersonal",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "Rc1", type = Void.class),
	    }
	)
public class TipoPersonal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPOPERSONAL", precision = 6, scale = 0, nullable = false)
    private BigDecimal idTipoPersonal;

    @Column(name = "DSTIPOPERSONAL", length = 50, nullable = false)
    private String dsTipoPersonal;

    @Column(name = "PERSONALEXTERNO", length = 1, nullable = false)
    private String personalExterno;

    public TipoPersonal() {
    }

    public TipoPersonal(BigDecimal idTipoPersonal, String dsTipoPersonal, String personalExterno) {
        this.idTipoPersonal = idTipoPersonal;
        this.dsTipoPersonal = dsTipoPersonal;
        this.personalExterno = personalExterno;
    }

    // Getters y Setters

    public BigDecimal getIdTipoPersonal() {
        return idTipoPersonal;
    }

    public void setIdTipoPersonal(BigDecimal idTipoPersonal) {
        this.idTipoPersonal = idTipoPersonal;
    }

    public String getDsTipoPersonal() {
        return dsTipoPersonal;
    }

    public void setDsTipoPersonal(String dsTipoPersonal) {
        this.dsTipoPersonal = dsTipoPersonal;
    }

    public String getPersonalExterno() {
        return personalExterno;
    }

    public void setPersonalExterno(String personalExterno) {
        this.personalExterno = personalExterno;
    }
}
