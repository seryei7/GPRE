package com.giss.gpre.util;

import java.io.InputStream;
import java.util.List;

import org.gi.infra.wsimpresion.cliente.WSIClienteImpresion;
import org.gi.infra.wsimpresion.cliente.WSIDocumento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerarInformes {
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String NOMBRE_APLICACION = "GPRE";
    private static final String ORIGEN = WSIClienteImpresion.ORIGEN_INTRANET;
    private static final String IDIOMA = "ES";

    private static String escapeXml(String value) {
    	String resultado = "";
        if (value != null) {
        	resultado = value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&apos;");
        }
        return resultado;
    }

    public static String generateXml(List<?> objetos) {
        StringBuilder xml = new StringBuilder(XML_HEADER);
        xml.append("<INFORME>");
        for (Object obj : objetos) {
            Class<?> clazz = obj.getClass();
            xml.append("<").append(clazz.getSimpleName().toUpperCase()).append(">");
            try {
                for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                    // Procesar solo si NO es static y NO es transient
                    if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                        !java.lang.reflect.Modifier.isTransient(field.getModifiers())) {

                        field.setAccessible(true);
                        Object valor = field.get(obj);
                        xml.append("<").append(field.getName().toUpperCase()).append(">")
                           .append(escapeXml(valor != null ? valor.toString() : ""))
                           .append("</").append(field.getName().toUpperCase()).append(">");
                    }
                    // Si es static o transient, simplemente no entra al bloque
                }
            } catch (IllegalAccessException e) {
                LOGGER.debug("Error al procesar la solicitud {" + e + "}");
            }
            xml.append("</").append(clazz.getSimpleName().toUpperCase()).append(">");
        }
        xml.append("</INFORME>");
        return xml.toString();
    }

    public static String generarUrlFichero(List<?> objetos, String usuario, String informe, String tipo) {
    	String url = null;
        try {
        	if (tipo.equalsIgnoreCase("pdf")) {
        		WSIDocumento doc = WSIClienteImpresion.getPDF(
                    NOMBRE_APLICACION,
                    usuario,
                    ORIGEN,
                    informe,
                    IDIOMA,
                    null,
                    null,
                    null,
                    generateXml(objetos)
                );
                url = doc.getUrl();
			} else if (tipo.equalsIgnoreCase("excel")) {
				WSIDocumento doc = WSIClienteImpresion.getExcel(
                    NOMBRE_APLICACION,
                    usuario,
                    ORIGEN,
                    informe,
                    IDIOMA,
                    null,
                    null,
                    null,
                    generateXml(objetos)
                );
                url = doc.getUrl();
			}
            
        } catch (Exception e) {
        	LOGGER.debug("Error al procesar la solicitud {" + e + "}");
        }
        return url;
    }
    
    public static InputStream generarInputStreamFichero(List<?> objetos, String usuario, String informe, String tipo) {
    	InputStream is = null;
        try {
        	if (tipo.equalsIgnoreCase("pdf")) {
        		WSIDocumento doc = WSIClienteImpresion.getPDF(
                    NOMBRE_APLICACION,
                    usuario,
                    ORIGEN,
                    informe,
                    IDIOMA,
                    null,
                    null,
                    null,
                    generateXml(objetos)
                );
        		is = doc.getInputStream();
			} else if (tipo.equalsIgnoreCase("excel")) {
				WSIDocumento doc = WSIClienteImpresion.getExcel(
                    NOMBRE_APLICACION,
                    usuario,
                    ORIGEN,
                    informe,
                    IDIOMA,
                    null,
                    null,
                    null,
                    generateXml(objetos)
                );
				is = doc.getInputStream();
			}
            
        } catch (Exception e) {
        	LOGGER.debug("Error al procesar la solicitud {" + e + "}");
        }
        return is;
    }
}
