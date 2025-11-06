package com.giss.gpre.constants;

/**
 * Constantes para validación de entrada de usuarios.
 */
public final class ValidationConstants {

    private ValidationConstants() {
        // Previene instanciación
    }

    // Expresiones regulares para validación
    public static final String ALPHANUMERIC_PATTERN = "[a-zA-Z0-9]+";
    public static final String NUMERIC_PATTERN = "[0-9]+";
    public static final String DNI_PATTERN = "^[0-9]{6}[A-Z]$";

    // Longitudes máximas
    public static final int MAX_DNI_LENGTH = 7;
    public static final int MAX_USUARIO_LENGTH = 10;
    public static final int MAX_AREA_LENGTH = 8;
    public static final int MAX_FECHA_LENGTH = 10;

    // Códigos de error de validación de usuario
    public static final int ERROR_USUARIO_NO_ALTA = 22001;
    public static final int ERROR_USUARIO_NO_AUTORIZADO = 22003;
    public static final int ERROR_USUARIO_OK = 0;
}
