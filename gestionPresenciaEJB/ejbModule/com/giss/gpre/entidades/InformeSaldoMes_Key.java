package com.giss.gpre.entidades;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class InformeSaldoMes_Key implements Serializable {

    private static final long serialVersionUID = -125186673150887112L;
    
    private String cdusuario;
    private Integer entiGesEp;
    private Integer provEp;
    private Integer cenGesEp;
    private String cdDocNacional;
    private String fcfichada;
    private String cdempresa;
    private String fcinisem;
    private String fcinimes;
    
    public InformeSaldoMes_Key() {
    }
    
    public InformeSaldoMes_Key(String cdusuario, Integer entiGesEp, Integer provEp, 
                               Integer cenGesEp, String cdDocNacional, String fcfichada, 
                               String cdempresa, String fcinisem, String fcinimes) {
        this.cdusuario = cdusuario;
        this.entiGesEp = entiGesEp;
        this.provEp = provEp;
        this.cenGesEp = cenGesEp;
        this.cdDocNacional = cdDocNacional;
        this.fcfichada = fcfichada;
        this.cdempresa = cdempresa;
        this.fcinisem = fcinisem;
        this.fcinimes = fcinimes;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cdusuario == null) ? 0 : cdusuario.hashCode());
        result = prime * result + ((entiGesEp == null) ? 0 : entiGesEp.hashCode());
        result = prime * result + ((provEp == null) ? 0 : provEp.hashCode());
        result = prime * result + ((cenGesEp == null) ? 0 : cenGesEp.hashCode());
        result = prime * result + ((cdDocNacional == null) ? 0 : cdDocNacional.hashCode());
        result = prime * result + ((fcfichada == null) ? 0 : fcfichada.hashCode());
        result = prime * result + ((cdempresa == null) ? 0 : cdempresa.hashCode());
        result = prime * result + ((fcinisem == null) ? 0 : fcinisem.hashCode());
        result = prime * result + ((fcinimes == null) ? 0 : fcinimes.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean retorno = true;
        if (this == obj)
            retorno = true;
        if (obj == null)
            retorno = false;
        if (getClass() != obj.getClass())
            retorno = false;
        InformeSaldoMes_Key other = (InformeSaldoMes_Key) obj;
        if (cdusuario == null) {
            if (other.cdusuario != null)
                retorno = false;
        } else if (!cdusuario.equals(other.cdusuario))
            retorno = false;
        if (entiGesEp == null) {
            if (other.entiGesEp != null)
                retorno = false;
        } else if (!entiGesEp.equals(other.entiGesEp))
            retorno = false;
        if (provEp == null) {
            if (other.provEp != null)
                retorno = false;
        } else if (!provEp.equals(other.provEp))
            retorno = false;
        if (cenGesEp == null) {
            if (other.cenGesEp != null)
                retorno = false;
        } else if (!cenGesEp.equals(other.cenGesEp))
            retorno = false;
        if (cdDocNacional == null) {
            if (other.cdDocNacional != null)
                retorno = false;
        } else if (!cdDocNacional.equals(other.cdDocNacional))
            retorno = false;
        if (fcfichada == null) {
            if (other.fcfichada != null)
                retorno = false;
        } else if (!fcfichada.equals(other.fcfichada))
            retorno = false;
        if (cdempresa == null) {
            if (other.cdempresa != null)
                retorno = false;
        } else if (!cdempresa.equals(other.cdempresa))
            retorno = false;
        if (fcinisem == null) {
            if (other.fcinisem != null)
                retorno = false;
        } else if (!fcinisem.equals(other.fcinisem))
            retorno = false;
        if (fcinimes == null) {
            if (other.fcinimes != null)
                retorno = false;
        } else if (!fcinimes.equals(other.fcinimes))
            retorno = false;
        return retorno;
    }
}