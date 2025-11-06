package com.giss.gpre.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.AccessControlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giss.gpre.util.LimpiarParametro;

/**
 * Clase base para todos los servlets de la aplicación.
 * Proporciona funcionalidad común como forwarding seguro y manejo de errores.
 */
public abstract class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    protected static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");
    protected static final String ERROR_PAGE = "/jsp/error.jsp";

    /**
     * Realiza un forward seguro a la página especificada.
     * 
     * @param request Petición HTTP
     * @param response Respuesta HTTP
     * @param nextPage Página destino
     */
    protected final void doForward(HttpServletRequest request, HttpServletResponse response, String nextPage) {
        try {
            LimpiarParametro.safeSendForward(request, response, nextPage);
        } catch (IOException | ServletException | AccessControlException e) {
            LOGGER.error("Error al realizar forward a {}: {}", nextPage, e.getMessage(), e);
        }
    }

    /**
     * Maneja errores de forma uniforme.
     * 
     * @param request Petición HTTP
     * @param response Respuesta HTTP
     * @param exception Excepción producida
     * @param customMessage Mensaje personalizado
     */
    protected final void handleError(HttpServletRequest request, HttpServletResponse response, 
                                    Exception exception, String customMessage) {
        String mensaje = customMessage != null ? customMessage : "Error al procesar la solicitud";
        LOGGER.error("{}: {}", mensaje, exception.getMessage(), exception);
        request.setAttribute("javax.servlet.jsp.jspException", exception);
        doForward(request, response, ERROR_PAGE);
    }

    /**
     * Inicializa ESAPI con la petición y respuesta actuales.
     * 
     * @param request Petición HTTP
     * @param response Respuesta HTTP
     */
    protected final void initializeESAPI(HttpServletRequest request, HttpServletResponse response) {
        ESAPI.httpUtilities().setCurrentHTTP(request, response);
    }
}
