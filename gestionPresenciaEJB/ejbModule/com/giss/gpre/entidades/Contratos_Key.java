package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Contratos_Key implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal entiGesEp;
    private BigDecimal provEp;
    private BigDecimal cenGesEp;
    private BigDecimal idcontrato;

    public Contratos_Key() {
    }

    public Contratos_Key(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, BigDecimal idcontrato) {
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.idcontrato = idcontrato;
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

    public BigDecimal getIdcontrato() {
        return idcontrato;
    }

    public void setIdcontrato(BigDecimal idcontrato) {
        this.idcontrato = idcontrato;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entiGesEp, provEp, cenGesEp, idcontrato);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contratos_Key other = (Contratos_Key) obj;
        return Objects.equals(entiGesEp, other.entiGesEp) 
            && Objects.equals(provEp, other.provEp)
            && Objects.equals(cenGesEp, other.cenGesEp) 
            && Objects.equals(idcontrato, other.idcontrato);
    }
}
