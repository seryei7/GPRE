package com.giss.gpre.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class EmpresasSel_Key implements Serializable {

  private static final long serialVersionUID = 1L;

  private String idUsuario;
  private BigDecimal entiGesEp;
  private BigDecimal provEp;
  private BigDecimal cenGesEp;
  private String cdFiscal;

  public EmpresasSel_Key() {}

  public EmpresasSel_Key(String idUsuario, BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String cdFiscal) {
      this.idUsuario = idUsuario;
      this.entiGesEp = entiGesEp;
      this.provEp = provEp;
      this.cenGesEp = cenGesEp;
      this.cdFiscal = cdFiscal;
  }

  @Override
  public int hashCode() {
      return Objects.hash(idUsuario, entiGesEp, provEp, cenGesEp, cdFiscal);
  }
}
