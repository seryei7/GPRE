package com.giss.gpre.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Empresas_Key implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idFiscal;

    public Empresas_Key() {
    }

    public Empresas_Key(String idFiscal) {
        this.idFiscal = idFiscal;
    }

    public String getIdFiscal() {
        return idFiscal;
    }

    public void setIdFiscal(String idFiscal) {
        this.idFiscal = idFiscal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFiscal);
    }
}
