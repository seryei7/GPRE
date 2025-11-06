package com.giss.gpre.datos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad presentacion del procedimiento GetBasicoPersona
 * 
 * @author GISS
 *
 */
public class DatosPersonaBasico implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cdDocNacional;

    private BigDecimal entiGesEp;

    private BigDecimal provEp;

    private BigDecimal cenGesEp;

    private String cdTarjeta;

    private BigDecimal cdHorario;

    private String dsHorarioSem;

    private BigDecimal cdCalendario;

    private String dsCalendario;

    private BigDecimal ntCodDen;

    private BigDecimal idCodDen;

    private String dsCodDen;

    private BigDecimal idTipoPersonal;

    private String dsTipoPersonal;

    private String idFiscal;

    private String nombreEmpresa;

    private BigDecimal idContrato;

    private String dsContrato;

    private BigDecimal idProyecto;

    private String dsProyecto;

    private String idCentro;

    private String dsCentro;

    private String idUnidad;

    private String dsUnidad;

    private String codEdificio;

    private String codPlanta;

    private BigDecimal ntCuerpo;

    private String idCuerpo;

    private String dsCuerpo;

    private BigDecimal idAccesoPuertas;

    private String dsAccesoPuertas;

    private String dptoSilcon;

    private String contextoSilcon;
    
    private String fcInicio;

    private String fcFin;

    private String autorizarHoras;

    private String nombre;

    private String apellido1;

    private String apellido2;
    
    private BigDecimal resultado;

	public DatosPersonaBasico(String cdDocNacional, BigDecimal entiGesEp, BigDecimal provEp, BigDecimal cenGesEp, String cdTarjeta,
			BigDecimal cdHorario, String dsHorarioSem, BigDecimal cdCalendario, String dsCalendario,
			BigDecimal ntCodDen, BigDecimal idCodDen, String dsCodDen, BigDecimal idTipoPersonal, String dsTipoPersonal,
			String idFiscal, String nombreEmpresa, BigDecimal idContrato, String dsContrato, BigDecimal idProyecto,
			String dsProyecto, String idCentro, String dsCentro, String idUnidad, String dsUnidad,
			String codEdificio, String codPlanta, BigDecimal ntCuerpo, String idCuerpo, String dsCuerpo,
			BigDecimal idAccesoPuertas, String dsAccesoPuertas, String dptoSilcon, String contextoSilcon,
			String fcInicio, String fcFin, String autorizarHoras, String nombre, String apellido1, String apellido2,
			BigDecimal resultado) {
		super();
		this.cdDocNacional = cdDocNacional;
		this.entiGesEp = entiGesEp;
		this.provEp = provEp;
		this.cenGesEp = cenGesEp;
		this.cdTarjeta = cdTarjeta;
		this.cdHorario = cdHorario;
		this.dsHorarioSem = dsHorarioSem;
		this.cdCalendario = cdCalendario;
		this.dsCalendario = dsCalendario;
		this.ntCodDen = ntCodDen;
		this.idCodDen = idCodDen;
		this.dsCodDen = dsCodDen;
		this.idTipoPersonal = idTipoPersonal;
		this.dsTipoPersonal = dsTipoPersonal;
		this.idFiscal = idFiscal;
		this.nombreEmpresa = nombreEmpresa;
		this.idContrato = idContrato;
		this.dsContrato = dsContrato;
		this.idProyecto = idProyecto;
		this.dsProyecto = dsProyecto;
		this.idCentro = idCentro;
		this.dsCentro = dsCentro;
		this.idUnidad = idUnidad;
		this.dsUnidad = dsUnidad;
		this.codEdificio = codEdificio;
		this.codPlanta = codPlanta;
		this.ntCuerpo = ntCuerpo;
		this.idCuerpo = idCuerpo;
		this.dsCuerpo = dsCuerpo;
		this.idAccesoPuertas = idAccesoPuertas;
		this.dsAccesoPuertas = dsAccesoPuertas;
		this.dptoSilcon = dptoSilcon;
		this.contextoSilcon = contextoSilcon;
		this.fcInicio = fcInicio;
		this.fcFin = fcFin;
		this.autorizarHoras = autorizarHoras;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.resultado = resultado;
	}

	public DatosPersonaBasico() {}

	public String getCdDocNacional() {
		return cdDocNacional;
	}

	public void setCdDocNacional(String cdDocNacional) {
		this.cdDocNacional = cdDocNacional;
	}

	public String getFcInicio() {
		return fcInicio;
	}

	public void setFcInicio(String fcInicio) {
		this.fcInicio = fcInicio;
	}

	public String getFcFin() {
		return fcFin;
	}

	public void setFcFin(String fcFin) {
		this.fcFin = fcFin;
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

	public String getCdTarjeta() {
		return cdTarjeta;
	}

	public void setCdTarjeta(String cdTarjeta) {
		this.cdTarjeta = cdTarjeta;
	}

	public BigDecimal getCdHorario() {
		return cdHorario;
	}

	public void setCdHorario(BigDecimal cdHorario) {
		this.cdHorario = cdHorario;
	}

	public String getDsHorarioSem() {
		return dsHorarioSem;
	}

	public void setDsHorarioSem(String dsHorarioSem) {
		this.dsHorarioSem = dsHorarioSem;
	}

	public BigDecimal getCdCalendario() {
		return cdCalendario;
	}

	public void setCdCalendario(BigDecimal cdCalendario) {
		this.cdCalendario = cdCalendario;
	}

	public String getDsCalendario() {
		return dsCalendario;
	}

	public void setDsCalendario(String dsCalendario) {
		this.dsCalendario = dsCalendario;
	}

	public BigDecimal getNtCodDen() {
		return ntCodDen;
	}

	public void setNtCodDen(BigDecimal ntCodDen) {
		this.ntCodDen = ntCodDen;
	}

	public BigDecimal getIdCodDen() {
		return idCodDen;
	}

	public void setIdCodDen(BigDecimal idCodDen) {
		this.idCodDen = idCodDen;
	}

	public String getDsCodDen() {
		return dsCodDen;
	}

	public void setDsCodDen(String dsCodDen) {
		this.dsCodDen = dsCodDen;
	}

	public BigDecimal getIdTipoPersonal() {
		return idTipoPersonal;
	}

	public void setIdTipoPersonal(BigDecimal idTipoPersonal) {
		this.idTipoPersonal = idTipoPersonal;
	}

	public String getDsTipoPersonal() {
		return dsTipoPersonal;
	}

	public void setDsTipoPersonal(String dsTipoPersonal) {
		this.dsTipoPersonal = dsTipoPersonal;
	}

	public String getIdFiscal() {
		return idFiscal;
	}

	public void setIdFiscal(String idFiscal) {
		this.idFiscal = idFiscal;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public String getDsContrato() {
		return dsContrato;
	}

	public void setDsContrato(String dsContrato) {
		this.dsContrato = dsContrato;
	}

	public BigDecimal getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(BigDecimal idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getDsProyecto() {
		return dsProyecto;
	}

	public void setDsProyecto(String dsProyecto) {
		this.dsProyecto = dsProyecto;
	}

	public String getIdCentro() {
		return idCentro;
	}

	public void setIdCentro(String idCentro) {
		this.idCentro = idCentro;
	}

	public String getDsCentro() {
		return dsCentro;
	}

	public void setDsCentro(String dsCentro) {
		this.dsCentro = dsCentro;
	}

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getDsUnidad() {
		return dsUnidad;
	}

	public void setDsUnidad(String dsUnidad) {
		this.dsUnidad = dsUnidad;
	}

	public String getCodEdificio() {
		return codEdificio;
	}

	public void setCodEdificio(String codEdificio) {
		this.codEdificio = codEdificio;
	}

	public String getCodPlanta() {
		return codPlanta;
	}

	public void setCodPlanta(String codPlanta) {
		this.codPlanta = codPlanta;
	}

	public BigDecimal getNtCuerpo() {
		return ntCuerpo;
	}

	public void setNtCuerpo(BigDecimal ntCuerpo) {
		this.ntCuerpo = ntCuerpo;
	}

	public String getIdCuerpo() {
		return idCuerpo;
	}

	public void setIdCuerpo(String idCuerpo) {
		this.idCuerpo = idCuerpo;
	}

	public String getDsCuerpo() {
		return dsCuerpo;
	}

	public void setDsCuerpo(String dsCuerpo) {
		this.dsCuerpo = dsCuerpo;
	}

	public BigDecimal getIdAccesoPuertas() {
		return idAccesoPuertas;
	}

	public void setIdAccesoPuertas(BigDecimal idAccesoPuertas) {
		this.idAccesoPuertas = idAccesoPuertas;
	}

	public String getDsAccesoPuertas() {
		return dsAccesoPuertas;
	}

	public void setDsAccesoPuertas(String dsAccesoPuertas) {
		this.dsAccesoPuertas = dsAccesoPuertas;
	}

	public String getDptoSilcon() {
		return dptoSilcon;
	}

	public void setDptoSilcon(String dptoSilcon) {
		this.dptoSilcon = dptoSilcon;
	}

	public String getContextoSilcon() {
		return contextoSilcon;
	}

	public void setContextoSilcon(String contextoSilcon) {
		this.contextoSilcon = contextoSilcon;
	}

	public String getAutorizarHoras() {
		return autorizarHoras;
	}

	public void setAutorizarHoras(String autorizarHoras) {
		this.autorizarHoras = autorizarHoras;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public BigDecimal getResultado() {
		return resultado;
	}

	public void setResultado(BigDecimal resultado) {
		this.resultado = resultado;
	}
	
}
