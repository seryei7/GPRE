package com.giss.gpre.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

    // CORRECCION KIUWAN: Gson seguro con límites
    private static final Gson GSON_SAFE = new GsonBuilder()
            .setLenient() // Permite JSON más flexible pero controlado
            .create();

    /**
     * Escapa caracteres peligrosos para JSON.
     */
    public static String escapeJson(String value) {
        String resultado = null;
        if (value != null) {
            StringBuilder sb = new StringBuilder();
            for (char c : value.toCharArray()) {
                switch (c) {
                    case '"': sb.append("\\\""); break;
                    case '\\': sb.append("\\\\"); break;
                    case '\b': sb.append("\\b"); break;
                    case '\f': sb.append("\\f"); break;
                    case '\n': sb.append("\\n"); break;
                    case '\r': sb.append("\\r"); break;
                    case '\t': sb.append("\\t"); break;
                    default:
                        if (c < 0x20 || c > 0x7E) {
                            sb.append(String.format("\\u%04x", (int) c));
                        } else {
                            sb.append(c);
                        }
                }
            }
            resultado = sb.toString();
        }
        return resultado;
    }

    /**
     * CORRECCION KIUWAN: Deserializa un JSON controlado por el usuario de forma segura.
     * Previene JSON Injection mediante validación y sanitización estricta.
     */
    public static <T> T fromJsonSafe(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON vacío o nulo");
        }

        // PROTECCION 1: Limitar tamaño del JSON (prevenir DoS)
        final int MAX_JSON_SIZE = 1_000_000; // 1 MB
        if (json.length() > MAX_JSON_SIZE) {
            throw new IllegalArgumentException("JSON demasiado grande: " + json.length() + " bytes");
        }

        try {
            // PROTECCION 2: Validar que el JSON es sintácticamente correcto
            JsonElement element = JsonParser.parseString(json);

            if (!element.isJsonObject()) {
                throw new IllegalArgumentException("Se esperaba un objeto JSON");
            }

            // PROTECCION 3: Limitar profundidad del JSON
            int profundidad = calcularProfundidad(element);
            final int MAX_DEPTH = 20;
            if (profundidad > MAX_DEPTH) {
                throw new IllegalArgumentException("JSON demasiado profundo: " + profundidad + " niveles");
            }

            // PROTECCION 4: Limitar número de propiedades en el objeto raíz
            JsonObject obj = element.getAsJsonObject();
            final int MAX_PROPERTIES = 100;
            if (obj.size() > MAX_PROPERTIES) {
                throw new IllegalArgumentException("Demasiadas propiedades en JSON: " + obj.size());
            }

            // PROTECCION 5: Sanitizar recursivamente todo el contenido
            JsonObject sanitizedObj = sanitizeElement(obj).getAsJsonObject();

            // PROTECCION 6: Deserializar con Gson seguro
            return GSON_SAFE.fromJson(sanitizedObj, clazz);

        } catch (JsonSyntaxException e) {
            LOGGER.error("JSON mal formado", e);
            throw new IllegalArgumentException("JSON mal formado: " + e.getCause(), e);
        } catch (Exception e) {
            LOGGER.error("Error procesando JSON", e);
            throw new IllegalArgumentException("Error procesando JSON: " + e.getCause(), e);
        }
    }

    private static JsonElement sanitizeElement(JsonElement element) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            return new JsonPrimitive(sanitizeJsonString(element.getAsString()));
        } else if (element.isJsonArray()) {
            JsonArray sanitizedArray = new JsonArray();
            if (element.getAsJsonArray().size() > 1000) {
                throw new IllegalArgumentException("Array demasiado grande");
            }
            for (JsonElement e : element.getAsJsonArray()) {
                sanitizedArray.add(sanitizeElement(e));
            }
            return sanitizedArray;
        } else if (element.isJsonObject()) {
            JsonObject sanitizedObject = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                sanitizedObject.add(sanitizeKey(entry.getKey()), sanitizeElement(entry.getValue()));
            }
            return sanitizedObject;
        }
        return element; // números, booleanos, null
    }

    /**
     * Calcula la profundidad de un árbol JSON
     */
    private static int calcularProfundidad(JsonElement element) {
        int resultado = 1;
        if (element.isJsonPrimitive() || element.isJsonNull()) {
            resultado = 1;
        }
        if (element.isJsonArray()) {
            int maxDepth = 1;
            for (JsonElement item : element.getAsJsonArray()) {
                maxDepth = Math.max(maxDepth, 1 + calcularProfundidad(item));
            }
            resultado = maxDepth;
        }
        if (element.isJsonObject()) {
            int maxDepth = 1;
            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                maxDepth = Math.max(maxDepth, 1 + calcularProfundidad(entry.getValue()));
            }
            resultado = maxDepth;
        }
        return resultado;
    }

    /**
     * Sanitiza claves de objetos JSON
     */
    private static String sanitizeKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Clave JSON vacía");
        }
        if (!key.matches("^[a-zA-Z0-9_]{1,50}$")) {
            throw new IllegalArgumentException("Clave JSON inválida: " + key);
        }
        return key;
    }

    /**
     * Sanitiza valores string en JSON
     */
    private static String sanitizeJsonString(String value) {
        String resultado = null;
        if (value != null) {
            final int MAX_STRING_LENGTH = 10000;
            if (value.length() > MAX_STRING_LENGTH) {
                value = value.substring(0, MAX_STRING_LENGTH);
            }
            resultado = value
                    .replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F]", "")
                    .trim();
        }
        return resultado;
    }

    public static BigDecimal toBigDecimal(String str) {
        BigDecimal resultado = null;
        if (str != null && !str.trim().isEmpty()) {
            String trimmed = str.trim();
            if (!trimmed.matches("\\d+(\\.\\d+)?")) {
                throw new IllegalArgumentException("Entrada no válida para BigDecimal: " + trimmed);
            }
            try {
                resultado = new BigDecimal(trimmed);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Formato numérico inválido: " + trimmed, e);
            }
        }
        return resultado;
    }

    public static String obtenerFechaActual() {
        LocalDateTime ahora = LocalDateTime.now();
        return ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String obtenerHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        return ahora.format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    public static String convertirFecha(String fecha) {
        String resultado = null;
        if (fecha != null && !fecha.isEmpty()) {
            try {
                SimpleDateFormat formatoEntrada;
                if (fecha.indexOf('-') == 4) {
                    formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
                } else {
                    formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
                }
                SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyyMMdd");
                Date date = formatoEntrada.parse(fecha);
                resultado = formatoSalida.format(date);
            } catch (ParseException e) {
                LOGGER.warn("Formato de fecha inválido: " + fecha, e);
            }
        }
        return resultado;
    }
}
