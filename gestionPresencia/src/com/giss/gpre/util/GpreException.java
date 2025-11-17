package com.giss.gpre.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GpreException extends Exception {
	
	private static final long serialVersionUID = 4862967396430794865L;
	
	private final static Logger LOGGER = LoggerFactory.getLogger("gpre.General");

	public GpreException(String message) {
		super(message);
		LOGGER.debug("GpreException: message[" + message + "]");
	}
	

	public String dameMensaje() {
	  java.lang.reflect.Method elGet = null;
	  String mensaje = null;
	  try {
	    elGet = this.getClass().getMethod("getMessage" , (Class<?>[]) null);
	    if (elGet != null) {
	      mensaje = (String)elGet.invoke(this, (Object[])null);
	    }
	  } catch (Exception e) {
	    mensaje = "Error en GpreException.dameMensaje()";
	  }
	  return mensaje;
	}

}
