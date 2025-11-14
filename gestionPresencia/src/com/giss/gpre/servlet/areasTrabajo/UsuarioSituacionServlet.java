package com.giss.gpre.servlet.areasTrabajo;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import com.giss.gpre.constants.JNDIConstants;
import com.giss.gpre.constants.SessionAttributes;
import com.giss.gpre.constants.URLConstants;
import com.giss.gpre.datos.DatosUsuarioSituacion;
import com.giss.gpre.ejb.UsuarioSituacionService;
import com.giss.gpre.servlet.BaseServlet;
import com.giss.gpre.util.LimpiarParametro;
import com.google.gson.Gson;

/**
 * Servlet para consultar la situación de usuarios.
 * Permite visualizar información detallada de usuarios combinando datos
 * de PERSONAS, HISTORICOSITUACIONES, UNIDADES, EMPRESAS, etc.
 */
@WebServlet(name = "usuarioSituacion", urlPatterns = { "/usuarioSituacion" })
public class UsuarioSituacionServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private static final Gson GSON = new Gson();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        initializeESAPI(req, resp);
        HttpSession session = ESAPI.currentRequest().getSession(false);

        try {
            // Obtener el DNI del usuario desde la sesión
            String dniUsuario = (String) session.getAttribute(SessionAttributes.DNI);
            
            if (dniUsuario == null || dniUsuario.isEmpty()) {
                handleError(req, resp, new ServletException("Usuario no autenticado"), 
                    "No se encontró información del usuario en la sesión");
                return;
            }

            // Lookup del servicio EJB
            Context ctx = new InitialContext();
            UsuarioSituacionService usuarioSituacionService = 
                (UsuarioSituacionService) ctx.lookup(JNDIConstants.USUARIO_SITUACION_SERVICE);

            // Obtener la situación de usuarios
            List<DatosUsuarioSituacion> listaSituaciones = 
                usuarioSituacionService.obtenerSituacionUsuarios(dniUsuario);

            // Serializar a JSON de forma segura
            String situacionesJson = GSON.toJson(listaSituaciones);
            req.setAttribute("listaSituaciones", situacionesJson);
            req.setAttribute("numeroResultados", listaSituaciones.size());
            req.setAttribute("dniUsuario", dniUsuario);

            // Generar token CSRF y hacer forward
            String csrfToken = LimpiarParametro.getOwaspCsrfTokenGet(req, 
                URLConstants.USUARIO_SITUACION_JSP);
            doForward(req, resp, "/jsp/UsuarioSituacion.jsp?" + csrfToken);

        } catch (Exception e) {
            handleError(req, resp, e, "Error al consultar situación de usuarios");
        }
    }
}
