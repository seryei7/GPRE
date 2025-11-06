package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class GpreListados_Key implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idListado;

    public GpreListados_Key() {
    }

    public GpreListados_Key(BigDecimal idListado) {
        this.idListado = idListado;
    }

    public BigDecimal getId() {
        return idListado;
    }

    public void setIdListado(BigDecimal idListado) {
        this.idListado = idListado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idListado);
    }
}
