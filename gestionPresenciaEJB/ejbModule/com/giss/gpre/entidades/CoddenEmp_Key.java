package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class CoddenEmp_Key implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal entiGesEp;
    private BigDecimal provEp;
    private BigDecimal cenGesEp;
    private BigDecimal ntcodden;
    private BigDecimal idcodden;

    public CoddenEmp_Key() {
    }

    public CoddenEmp_Key(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, 
            BigDecimal ntcodden, BigDecimal idcodden) {
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.ntcodden = ntcodden;
        this.idcodden = idcodden;
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

    public BigDecimal getNtcodden() {
        return ntcodden;
    }

    public void setNtcodden(BigDecimal ntcodden) {
        this.ntcodden = ntcodden;
    }

    public BigDecimal getIdcodden() {
        return idcodden;
    }

    public void setIdcodden(BigDecimal idcodden) {
        this.idcodden = idcodden;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entiGesEp, provEp, cenGesEp, ntcodden, idcodden);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CoddenEmp_Key other = (CoddenEmp_Key) obj;
        return Objects.equals(entiGesEp, other.entiGesEp) 
            && Objects.equals(provEp, other.provEp)
            && Objects.equals(cenGesEp, other.cenGesEp) 
            && Objects.equals(ntcodden, other.ntcodden)
            && Objects.equals(idcodden, other.idcodden);
    }
}
