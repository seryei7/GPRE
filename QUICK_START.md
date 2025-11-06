# Guía Rápida de Cambios - GPRE Refactorizado

## Para Desarrolladores

### Nuevas Clases que Debes Conocer

1. **BaseServlet** - Todos los servlets ahora heredan de esta clase
   - Proporciona `doForward()`, `handleError()`, `initializeESAPI()`
   
2. **Constantes**
   - `JNDIConstants` - Nombres de servicios EJB
   - `SessionAttributes` - Atributos de sesión HTTP
   - `URLConstants` - URLs de la aplicación
   - `ValidationConstants` - Patrones de validación

### Cómo Crear un Nuevo Servlet

```java
@WebServlet(name = "miServlet", urlPatterns = { "/miUrl" })
public class MiServlet extends BaseServlet {
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        initializeESAPI(req, resp);
        
        try {
            // Tu lógica aquí
            Context ctx = new InitialContext();
            MiService service = (MiService) ctx.lookup(JNDIConstants.MI_SERVICE);
            
            // Usar constantes
            String dni = (String) session.getAttribute(SessionAttributes.DNI);
            
            // Forward seguro
            String csrf = LimpiarParametro.getOwaspCsrfTokenGet(req, URLConstants.MI_JSP);
            doForward(req, resp, "/jsp/miPagina.jsp?" + csrf);
            
        } catch (Exception e) {
            handleError(req, resp, e, "Error en mi operación");
        }
    }
}
```

### Logging Correcto

```java
// ✅ CORRECTO
LOGGER.info("Usuario accedió: {}", dni);
LOGGER.error("Error al procesar: {}", dni, exception);

// ❌ INCORRECTO
LOGGER.debug("Error: " + exception);  // No usar concatenación
LOGGER.info("Password: " + pass);     // Nunca loggear passwords
```

### Serialización JSON Segura

```java
// ✅ CORRECTO
Gson gson = new Gson();
String json = gson.toJson(objeto);

// ❌ INCORRECTO
String json = "{\"campo\": \"" + valor + "\"}";  // Vulnerable a XSS
```

## Compilación y Despliegue

```bash
# Compilar proyecto
mvn clean install

# Verificar que no hay errores
mvn compile

# Ejecutar tests (si existen)
mvn test
```

## Checklist para Code Review

- [ ] ¿El servlet extiende BaseServlet?
- [ ] ¿Se usan constantes en lugar de strings mágicos?
- [ ] ¿Se validan todos los inputs del usuario?
- [ ] ¿Se usa Gson para JSON?
- [ ] ¿El logging usa placeholders {}?
- [ ] ¿Los errores muestran mensajes genéricos al usuario?
- [ ] ¿Se incluye token CSRF en los forwards?
- [ ] ¿Hay JavaDoc en métodos públicos?

## Migración de Código Antiguo

### Si tienes código antiguo:

1. Cambiar herencia:
   ```java
   // Antes
   public class MiServlet extends HttpServlet
   
   // Después
   public class MiServlet extends BaseServlet
   ```

2. Eliminar método doForward():
   ```java
   // Antes
   private void doForward(...) { ... }  // ❌ Eliminar
   
   // Después
   // Usar heredado de BaseServlet ✅
   ```

3. Usar constantes:
   ```java
   // Antes
   ctx.lookup("java:global/GPRE/...")
   
   // Después
   ctx.lookup(JNDIConstants.MI_SERVICE)
   ```

## Contacto

Para dudas sobre la refactorización, consultar:
- REFACTORING_SUMMARY.md
- SECURITY_IMPROVEMENTS.md
