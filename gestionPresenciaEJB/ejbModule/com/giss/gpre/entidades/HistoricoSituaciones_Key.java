package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class HistoricoSituaciones_Key implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal entiGesEp;
    private BigDecimal provEp;
    private BigDecimal cenGesEp;
    private String cddni;

    public HistoricoSituaciones_Key() {
    }

    public HistoricoSituaciones_Key(BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String cddni) {
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.cddni = cddni;
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

    public String getCddni() {
        return cddni;
    }

    public void setCddni(String cddni) {
        this.cddni = cddni;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entiGesEp, provEp, cenGesEp, cddni);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HistoricoSituaciones_Key other = (HistoricoSituaciones_Key) obj;
        return Objects.equals(entiGesEp, other.entiGesEp) 
            && Objects.equals(provEp, other.provEp)
            && Objects.equals(cenGesEp, other.cenGesEp) 
            && Objects.equals(cddni, other.cddni);
    }
}
