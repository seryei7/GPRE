package com.giss.gpre.constants;

/**
 * Constantes para URLs y rutas de la aplicación.
 */
public final class URLConstants {

    private URLConstants() {
        // Previene instanciación
    }

    // Prefijo base de la aplicación
    private static final String BASE_PATH = "/gestionPresencia";
    private static final String JSP_PATH = BASE_PATH + "/jsp/";

    // Páginas principales
    public static final String INDEX_JSP = BASE_PATH + "/index.jsp";
    public static final String ERROR_JSP = "/jsp/error.jsp";

    // Servlets
    public static final String ACCESO_SERVLET = BASE_PATH + "/acceso";
    public static final String ELECCION_AREA_SERVLET = BASE_PATH + "/eleccionArea";

    // JSPs específicas
    public static final String USUARIOS_JSP = JSP_PATH + "Usuarios.jsp";
    public static final String INCIDENCIAS_JSP = JSP_PATH + "Incidencias.jsp";
    public static final String IMPRESION_INFORMES_JSP = JSP_PATH + "ImpresionInformes.jsp";
    public static final String PERSONAS_NIVEL_ACCESO_JSP = JSP_PATH + "PersonasNivelAcceso.jsp";
    public static final String SALDO_INDIVIDUAL_JSP = JSP_PATH + "SaldoIndividual.jsp";
    public static final String SALDO_SEMANAL_JSP = JSP_PATH + "SaldoSemanal.jsp";
}
