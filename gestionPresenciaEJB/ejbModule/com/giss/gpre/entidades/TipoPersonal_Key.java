package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class TipoPersonal_Key implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idTipoPersonal;

    public TipoPersonal_Key() {
    }

    public TipoPersonal_Key(BigDecimal idTipoPersonal) {
        this.idTipoPersonal = idTipoPersonal;
    }

    public BigDecimal getIdTipoPersonal() {
        return idTipoPersonal;
    }

    public void setIdTipoPersonal(BigDecimal idTipoPersonal) {
        this.idTipoPersonal = idTipoPersonal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoPersonal);
    }
}
