package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Codden_Key implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal ntcodden;
    private BigDecimal idcodden;

    public Codden_Key() {
    }

    public Codden_Key(BigDecimal ntcodden, BigDecimal idcodden) {
        this.ntcodden = ntcodden;
        this.idcodden = idcodden;
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
        return Objects.hash(ntcodden, idcodden);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Codden_Key other = (Codden_Key) obj;
        return Objects.equals(ntcodden, other.ntcodden) 
            && Objects.equals(idcodden, other.idcodden);
    }
}
