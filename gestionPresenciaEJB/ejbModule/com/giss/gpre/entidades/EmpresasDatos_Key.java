package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class EmpresasDatos_Key implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal entiGesEp;
    private BigDecimal provEp;
    private BigDecimal cenGesEp;
    private String idFiscal;

    public EmpresasDatos_Key() {
    }

    public EmpresasDatos_Key(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String idFiscal) {
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.idFiscal = idFiscal;
    }

    public BigDecimal getEntiGesEp() {
        return entiGesEp;
    }

    public void setEntiGesEp(BigDecimal entiGesEp) {
        this.entiGesEp = entiGesEp;
    }

    public BigDecimal getProvEp() {
        return provEp;
    }

    public void setProvEp(BigDecimal provEp) {
        this.provEp = provEp;
    }

    public BigDecimal getCenGesEp() {
        return cenGesEp;
    }

    public void setCenGesEp(BigDecimal cenGesEp) {
        this.cenGesEp = cenGesEp;
    }

    public String getIdCifNif() {
        return idFiscal;
    }

    public void setIdFiscal(String idFiscal) {
        this.idFiscal = idFiscal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entiGesEp, provEp, cenGesEp, idFiscal);
    }
}
