package com.giss.gpre.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excepción personalizada para errores de aplicación en GPRE.
 * Permite propagar mensajes de error de negocio de forma consistente.
 */
public final class GpreException extends Exception {
	
	private static final long serialVersionUID = 4862967396430794865L;
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

	/**
	 * Constructor con mensaje de error.
	 * 
	 * @param mensaje Mensaje descriptivo del error
	 */
	public GpreException(String mensaje) {
		super(mensaje);
		LOGGER.warn("GpreException: {}", mensaje);
	}
	
	/**
	 * Constructor con mensaje y causa.
	 * 
	 * @param mensaje Mensaje descriptivo del error
	 * @param causa Excepción que causó este error
	 */
	public GpreException(String mensaje, Throwable causa) {
		super(mensaje, causa);
		LOGGER.warn("GpreException: {}", mensaje, causa);
	}

	/**
	 * Obtiene el mensaje de error.
	 * 
	 * @return Mensaje de error o mensaje por defecto
	 */
	public String dameMensaje() {
		String mensaje = getMessage();
		return mensaje != null ? mensaje : "Error en la aplicación";
	}
}
