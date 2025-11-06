package com.giss.gpre.servlet.informes.impresion;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import com.giss.gpre.constants.JNDIConstants;
import com.giss.gpre.constants.SessionAttributes;
import com.giss.gpre.constants.URLConstants;
import com.giss.gpre.datos.DatosImpresionInformes;
import com.giss.gpre.ejb.ListadoService;
import com.giss.gpre.entidades.GpreListados;
import com.giss.gpre.servlet.BaseServlet;
import com.giss.gpre.util.LimpiarParametro;

/**
 * Servlet para gestión de impresión de informes.
 * Muestra el estado de los informes generados por el usuario.
 */
@WebServlet(name = "impresionInformes", urlPatterns = { "/gstImpresion" })
public class ImpresionInformesServlet extends BaseServlet {

    private static final long serialVersionUID = 5510675939061088043L;
    private static final String FORMATO_HORA = "HH:mm";
    private static final String ESTADO_SI = "Sí";
    private static final String ESTADO_NO = "No";
    private static final String ESTADO_ERROR = "Error";
    
    // Mapa de códigos de listado a nombres descriptivos
    private static final Map<String, String> NOMBRES_LISTADOS = new HashMap<>();
    static {
        NOMBRES_LISTADOS.put("SALDOMENFUN", "Saldo Individual");
        NOMBRES_LISTADOS.put("SALDOSEMANAL", "Saldo Semanal");
        NOMBRES_LISTADOS.put("RESSALDOMES", "Resumen de Saldo Semanal");
        NOMBRES_LISTADOS.put("RESSALDO", "Resumen de Saldo");
        NOMBRES_LISTADOS.put("SALDOHORARIO", "Saldo por Horarios");
        NOMBRES_LISTADOS.put("FICHAJESSINSALDO_N", "Fichajes sin Saldo");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        initializeESAPI(req, resp);
        HttpSession session = ESAPI.currentRequest().getSession(false);
        
        // Obtener DNI del usuario de la sesión
        String dniUsuario = (String) session.getAttribute(SessionAttributes.DNI);

        try {
            Context ctx = new InitialContext();
            ListadoService listadoService = (ListadoService) ctx.lookup(JNDIConstants.LISTADO_SERVICE);
            
            // Obtener lista de informes del usuario
            List<GpreListados> informes = listadoService.findByUsuario(dniUsuario);
            
            // Procesar informes usando Stream API de Java 8
            SimpleDateFormat formatoHora = new SimpleDateFormat(FORMATO_HORA);
            List<DatosImpresionInformes> datosInformes = informes.stream()
                .map(informe -> convertirADatosImpresion(informe, formatoHora))
                .collect(Collectors.toList());
            
            // Establecer atributos
            req.setAttribute("listaInformes", datosInformes);
            
            String csrfToken = LimpiarParametro.getOwaspCsrfTokenGet(req, URLConstants.IMPRESION_INFORMES_JSP);
            doForward(req, resp, "/jsp/ImpresionInformes.jsp?" + csrfToken);

        } catch (Exception e) {
            handleError(req, resp, e, "Error al cargar lista de informes");
        }
    }

    /**
     * Convierte un informe a un DTO con información adicional.
     */
    private DatosImpresionInformes convertirADatosImpresion(GpreListados informe, SimpleDateFormat formatoHora) {
        DatosImpresionInformes dto = new DatosImpresionInformes();
        dto.setIdListado(informe.getIdListado());
        dto.setListado(mapearNombreListado(informe.getListado()));
        dto.setCErrDes(informe.getcErrDes());
        dto.setEstadoGenerado(determinarEstadoGenerado(informe));
        
        // Formatear hora si existe
        if (informe.gettFinal() != null) {
            dto.setHoraFormateada(formatoHora.format(informe.gettFinal()));
        } else {
            dto.setHoraFormateada("");
        }
        
        if (informe.getImpreso() != null) {
            dto.setImpreso(true);
        }
        
        return dto;
    }

    /**
     * Determina el estado de generación del informe.
     * 
     * @param informe Informe a evaluar
     * @return Estado: "Sí", "No" o "Error"
     */
    private String determinarEstadoGenerado(GpreListados informe) {
        // Si hay código de error, mostrar "Error"
        if (informe.getcErrCod() != null && !informe.getcErrCod().trim().isEmpty()) {
            return ESTADO_ERROR;
        }
        
        // Si ambos timestamps están presentes, está completado
        if (informe.gettInicio() != null && informe.gettFinal() != null) {
            return ESTADO_SI;
        }
        
        // En cualquier otro caso, no está generado o está en proceso
        return ESTADO_NO;
    }
    
    /**
     * Mapea el código de listado a un nombre descriptivo.
     * 
     * @param codigo Código del listado
     * @return Nombre descriptivo o el código original si no se encuentra
     */
    private String mapearNombreListado(String codigo) {
        return NOMBRES_LISTADOS.getOrDefault(codigo, codigo);
    }
}
