package com.giss.gpre.servlet.informes.impresion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.AccessControlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giss.gpre.datos.DatosImpresionInformes;
import com.giss.gpre.ejb.ListadoService;
import com.giss.gpre.entidades.GpreListados;
import com.giss.gpre.util.LimpiarParametro;

@WebServlet(name = "impresionInformes", urlPatterns = { "/gstImpresion" })
public class ImpresionInformesServlet extends HttpServlet {

    private static final long serialVersionUID = 5510675939061088043L;
    private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ESAPI.httpUtilities().setCurrentHTTP(req, resp);
        HttpSession session = ESAPI.currentRequest().getSession(false);
        String mensaje;
        
        // DNI del usuario de la sesión
        String dniSesion = (String) session.getAttribute("tDni");

        try {
            Context ctx = new InitialContext();
            ListadoService listadoService = (ListadoService) ctx.lookup("java:global/GPRE/gestionPresenciaEJB/ListadoServiceBean");
            String csrftokenC = LimpiarParametro.getOwaspCsrfTokenGet(req, "/gestionPresencia/jsp/ImpresionInformes.jsp");
            
            // Obtener lista de informes del usuario
            List<GpreListados> informes = listadoService.findByUsuario(dniSesion);
            
            // Procesar los informes para agregar información adicional
            List<DatosImpresionInformes> datosImpresionInformes = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            
            for (GpreListados informe : informes) {
            	DatosImpresionInformes dto = new DatosImpresionInformes();
                dto.setIdListado(informe.getIdListado());
                dto.setListado(mapearNombreListado(informe.getListado()));
                dto.setCErrDes(informe.getcErrDes());
                
                // Determinar el estado "Generado"
                String estadoGenerado = determinarEstadoGenerado(informe);
                dto.setEstadoGenerado(estadoGenerado);
                
                // Formatear la hora si existe T_FINAL
                if (informe.gettFinal() != null) {
                    dto.setHoraFormateada(sdf.format(informe.gettFinal()));
                } else {
                    dto.setHoraFormateada("");
                }
                
                if (informe.getImpreso() != null) {
					dto.setImpreso(true);
				}
                
                datosImpresionInformes.add(dto);
            }
            
            // Pasar la lista procesada al JSP
            req.setAttribute("listaInformes", datosImpresionInformes);
            doForward(req, resp, "/jsp/ImpresionInformes.jsp?" + csrftokenC);

        } catch (Exception e) {
            mensaje = "Error al procesar la solicitud {" + e + "}";
            LOGGER.debug(mensaje);
            req.setAttribute("javax.servlet.jsp.jspException", e);
            doForward(req, resp, "/jsp/error.jsp");
        }
    }

    /**
     * Determina el estado "Generado" basado en T_INICIO, T_FINAL y CERRCOD
     * 
     * @param informe
     * @return "Sí", "No" o "Error"
     */
    private String determinarEstadoGenerado(GpreListados informe) {
        // Si hay código de error, mostrar "Error"
    	String resultado = "No";
        if (informe.getcErrCod() != null && !informe.getcErrCod().trim().isEmpty()) {
            resultado = "Error";
        }
        
        // Si existe inicio pero no final, está en proceso: "No"
        if (informe.gettInicio() != null && informe.gettFinal() == null) {
        	resultado = "No";
        }
        
        // Si ambos están rellenos, está completado: "Sí"
        if (informe.gettInicio() != null && informe.gettFinal() != null) {
        	resultado = "Sí";
        }
        
        // Por defecto, no generado
        return resultado;
    }
    
	private String mapearNombreListado(String codigo) {
		String resultado = null;
		switch (codigo) {
		case "SALDOMENFUN":
			resultado = "Saldo Individual";
			break;
		case "SALDOSEMANAL":
			resultado = "Saldo Semanal";
			break;
		case "RESSALDOMES":
			resultado = "Resumen de Saldo Semanal";
			break;
		case "RESSALDO":
			resultado = "Resumen de Saldo";
			break;
		case "SALDOHORARIO":
			resultado = "Saldo por Horarios";
			break;
		case "FICHAJESSINSALDO_N":
			resultado = "Fichajes sin Saldo";
			break;
		default:
			resultado = codigo; // Si no coincide, se deja el original
			break;
		}
		return resultado;
	}


    private final void doForward(HttpServletRequest request, HttpServletResponse response, String nextPage) {
        try {
            LimpiarParametro.safeSendForward(request, response, nextPage);
        } catch (IOException | ServletException | AccessControlException e) {
            LOGGER.debug("Error al procesar la solicitud {" + e + "}");
        }
    }
}
