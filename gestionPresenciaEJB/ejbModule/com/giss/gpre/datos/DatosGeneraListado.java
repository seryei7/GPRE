package com.giss.gpre.datos;

import java.io.Serializable;
import java.math.BigDecimal;

public class DatosGeneraListado implements Serializable {
	
	private static final long serialVersionUID = 3011809129500443177L;
	
	private BigDecimal numRegistros;
	
    private String codigoError;
    
    private String descripcionError;
    
	public BigDecimal getNumRegistros() {
		return numRegistros;
	}
	public void setNumRegistros(BigDecimal numRegistros) {
		this.numRegistros = numRegistros;
	}
	public String getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
    
    

}
