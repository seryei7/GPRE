package com.giss.gpre.constants;

/**
 * Constantes para lookups JNDI de la aplicación.
 */
public final class JNDIConstants {

    private JNDIConstants() {
        // Previene instanciación
    }

    // Prefijo común para todos los EJBs
    private static final String JNDI_PREFIX = "java:global/GPRE/gestionPresenciaEJB/";

    // Nombres JNDI de los servicios
    public static final String AAN_SERVICE = JNDI_PREFIX + "AANServiceBean";
    public static final String USUARIOS_SERVICE = JNDI_PREFIX + "UsuariosServiceBean";
    public static final String INCIDENCIAS_SERVICE = JNDI_PREFIX + "IncidenciasServiceBean";
    public static final String LISTADO_SERVICE = JNDI_PREFIX + "ListadoServiceBean";
    public static final String PERSONA_BASICO_SERVICE = JNDI_PREFIX + "PersonaBasicoServiceBean";
    public static final String EMPRESAS_SERVICE = JNDI_PREFIX + "EmpresasServiceBean";
    public static final String CENTROS_SERVICE = JNDI_PREFIX + "CentrosServiceBean";
    public static final String TIPO_PERSONAL_SERVICE = JNDI_PREFIX + "TipoPersonalServiceBean";
    public static final String INFORME_SALDO_MES_SERVICE = JNDI_PREFIX + "InformeSaldoMesServiceBean";
    public static final String PERSONAS_NIVEL_SERVICE = JNDI_PREFIX + "PersonasNivelServiceBean";
}
