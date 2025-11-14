# Implementación de Consulta de Situación de Usuarios

## Resumen

Se ha implementado una nueva funcionalidad para ejecutar consultas complejas que combinan información de múltiples tablas relacionadas con la situación de los usuarios en el sistema de gestión de presencia.

## Query Implementada

La consulta implementada combina información de las siguientes tablas:
- **PERSONAS**: Datos personales (apellidos, nombre, DNI, tarjeta)
- **HISTORICOSITUACIONES**: Situación histórica del usuario
- **UNIDADES**: Unidades organizativas
- **EMPRESAS**: Información de empresas
- **CONTRATOS**: Contratos asociados
- **CODDEN**: Códigos de denominación
- **CODDEN_EMP**: Códigos de denominación por empresa
- **GRUPOUSUARIOS**: Grupos de usuarios con permisos
- **AREAS_TRABAJO_SEL**: Áreas de trabajo seleccionadas

### Características Específicas de la Query

1. **DISTINCT**: Elimina registros duplicados del resultado
2. **Exclusión de ADMONPUBLICA**: `HISTORICOSITUACIONES.CDEMPRESA <> 'ADMONPUBLICA'`
3. **Exclusión del usuario consultor**: `GRUPOUSUARIOS.CDDNI <> :cdUsuario` - El usuario que realiza la consulta no aparece en los resultados

## Componentes Creados

### 1. Capa de Datos (DTO)
**Archivo**: `/workspace/gestionPresenciaEJB/ejbModule/com/giss/gpre/datos/DatosUsuarioSituacion.java`

DTO (Data Transfer Object) que encapsula el resultado de la consulta compleja con todos los campos necesarios.

### 2. Capa de Servicio (EJB)

#### Interfaz del Servicio
**Archivo**: `/workspace/gestionPresenciaEJB/ejbModule/com/giss/gpre/ejb/UsuarioSituacionService.java`

Define el contrato del servicio con el método:
```java
List<DatosUsuarioSituacion> obtenerSituacionUsuarios(String cdUsuario);
```

#### Implementación del Servicio
**Archivo**: `/workspace/gestionPresenciaEJB/ejbModule/com/giss/gpre/ejb/UsuarioSituacionServiceBean.java`

EJB Stateless que implementa la lógica de negocio:
- Ejecuta la query nativa usando EntityManager
- Mapea los resultados al DTO
- Maneja errores de forma apropiada
- Convierte tipos de datos de Oracle a Java de forma segura

### 3. Capa Web (Servlet)
**Archivo**: `/workspace/gestionPresencia/src/com/giss/gpre/servlet/areasTrabajo/UsuarioSituacionServlet.java`

Servlet que:
- Obtiene el DNI del usuario desde la sesión
- Invoca el servicio EJB mediante JNDI lookup
- Serializa los resultados a JSON
- Reenvía a la JSP correspondiente con los datos

**URL del Servlet**: `/gestionPresencia/usuarioSituacion`

### 4. Capa de Presentación (JSP)
**Archivo**: `/workspace/gestionPresencia/WebContent/jsp/UsuarioSituacion.jsp`

Página JSP que:
- Renderiza los datos en una tabla HTML
- Incluye JavaScript para parsear y mostrar el JSON
- Implementa escapado HTML para prevenir XSS
- Muestra el número de resultados y el usuario consultado
- Permite mostrar/ocultar columnas
- Incluye paginación (si se integra el script correspondiente)

### 5. Constantes Actualizadas

#### JNDIConstants.java
Añadida constante para el lookup JNDI:
```java
public static final String USUARIO_SITUACION_SERVICE = JNDI_PREFIX + "UsuarioSituacionServiceBean";
```

#### URLConstants.java
Añadida constante para la JSP:
```java
public static final String USUARIO_SITUACION_JSP = JSP_PATH + "UsuarioSituacion.jsp";
```

## Características de la Query

La consulta implementa las siguientes características:

1. **DISTINCT**: Elimina filas duplicadas del conjunto de resultados
2. **Filtrado por Usuario**: Utiliza el DNI del usuario autenticado para filtrar los resultados
3. **Exclusión del Consultor**: El propio usuario que realiza la consulta NO aparece en los resultados (`GRUPOUSUARIOS.CDDNI <> :cdUsuario`)
4. **Exclusión de ADMONPUBLICA**: Filtra empresas con código 'ADMONPUBLICA' (`HISTORICOSITUACIONES.CDEMPRESA <> 'ADMONPUBLICA'`)
5. **Filtrado por Fecha**: Solo muestra situaciones vigentes (FCFIN >= fecha actual OR FCFIN='99991231')
6. **Permisos**: Respeta los permisos del usuario basándose en GRUPOUSUARIOS y AREAS_TRABAJO_SEL
7. **Outer Joins**: Utiliza Oracle outer join syntax (+) para CONTRATOS, CODDEN y CODDEN_EMP
8. **Joins Complejos**: Combina múltiples tablas con claves compuestas (ENTI_GES_EP, PROV_EP, CEN_GES_EP)

## Cómo Usar

### Desde el Código Java

```java
// Obtener el servicio mediante JNDI
Context ctx = new InitialContext();
UsuarioSituacionService service = (UsuarioSituacionService) 
    ctx.lookup(JNDIConstants.USUARIO_SITUACION_SERVICE);

// Ejecutar la consulta
List<DatosUsuarioSituacion> resultados = service.obtenerSituacionUsuarios("049000241Y");

// Procesar resultados
for (DatosUsuarioSituacion dato : resultados) {
    System.out.println("Usuario: " + dato.getNombre() + " " + dato.getApellido1());
    System.out.println("Empresa: " + dato.getNombreEmpresa());
    System.out.println("Unidad: " + dato.getDsunidad());
    // ... más campos
}
```

### Desde la Aplicación Web

1. Acceder a la URL: `http://[servidor]/gestionPresencia/usuarioSituacion`
2. El servlet automáticamente:
   - Obtiene el DNI del usuario de la sesión
   - Ejecuta la consulta
   - Muestra los resultados en formato tabla

## Seguridad Implementada

- ✅ **Autenticación**: Verifica que el usuario esté autenticado (DNI en sesión)
- ✅ **CSRF Protection**: Utiliza tokens CSRF mediante OWASP ESAPI
- ✅ **XSS Prevention**: Escapado HTML en la JSP
- ✅ **SQL Injection Prevention**: Utiliza parámetros preparados en la query
- ✅ **Session Management**: Validación de sesión mediante ESAPI

## Manejo de Errores

El servicio implementa manejo robusto de errores:
- Captura excepciones de base de datos
- Registra errores en logs
- Retorna lista vacía en caso de error (nunca null)
- Convierte tipos de datos de forma segura con null-safety

## Extensibilidad

La implementación permite fácil extensión:

1. **Añadir filtros adicionales**: Modificar la query en `UsuarioSituacionServiceBean`
2. **Añadir campos al resultado**: Actualizar `DatosUsuarioSituacion` y la query
3. **Crear nuevas vistas**: Añadir JSPs que consuman el mismo servicio
4. **Exportar datos**: Integrar con servicios de generación de informes (PDF, Excel, etc.)

## Dependencias

El código requiere:
- Java EE (EJB 3.x, JPA 2.x, Servlets 3.x)
- Oracle Database (usa sintaxis específica de Oracle)
- OWASP ESAPI para seguridad
- Gson para serialización JSON
- SLF4J para logging

## Testing

Para probar la funcionalidad:

```java
@Test
public void testObtenerSituacionUsuarios() {
    // Configurar contexto de prueba
    UsuarioSituacionService service = ...; // Obtener servicio
    
    // Ejecutar
    List<DatosUsuarioSituacion> resultado = service.obtenerSituacionUsuarios("049000241Y");
    
    // Verificar
    assertNotNull(resultado);
    assertFalse(resultado.isEmpty());
    // ... más aserciones
}
```

## Notas Técnicas

1. **Oracle Syntax**: La query usa sintaxis específica de Oracle:
   - `TO_CHAR(SYSDATE,'YYYYMMDD')` para fechas
   - `(+)` para outer joins
   
2. **BigDecimal**: Los campos numéricos de Oracle se mapean a `BigDecimal` en Java

3. **Null Safety**: El método `convertToBigDecimal()` maneja conversiones seguras

4. **Performance**: La query incluye múltiples joins; considerar índices apropiados en las tablas

## Próximos Pasos Sugeridos

1. Añadir tests unitarios e integración
2. Implementar caché para mejorar performance
3. Añadir exportación a PDF/Excel
4. Implementar filtros adicionales en la UI
5. Añadir ordenamiento por columnas
6. Considerar paginación en base de datos para grandes volúmenes

## Autor

GISS - Sistema de Gestión de Presencia

## Fecha de Implementación

2025-11-14
