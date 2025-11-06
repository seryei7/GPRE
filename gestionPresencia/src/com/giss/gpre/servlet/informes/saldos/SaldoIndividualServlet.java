package com.giss.gpre.servlet.informes.saldos;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

import com.giss.gpre.util.LimpiarParametro;

@WebServlet(name = "saldoIndividual", urlPatterns = { "/InfSaldoInd" })
public class SaldoIndividualServlet extends HttpServlet {

    private static final long serialVersionUID = 929777525313542421L;
    private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ESAPI.httpUtilities().setCurrentHTTP(req, resp);
        HttpSession session = ESAPI.currentRequest().getSession(false);
        String mensaje;

        try {

            // 🔹 Calcular semana actual por defecto
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate hoy = LocalDate.now();
            LocalDate lunes = hoy.with(DayOfWeek.MONDAY);
            LocalDate domingo = hoy.with(DayOfWeek.SUNDAY);

            req.setAttribute("fechaDesde", lunes.format(formatter));
            req.setAttribute("fechaHasta", domingo.format(formatter));
            session.setAttribute("fechaDesde", lunes.format(formatter));
            session.setAttribute("fechaHasta", domingo.format(formatter));

            String csrftokenC = LimpiarParametro.getOwaspCsrfTokenGet(req, "/gestionPresencia/jsp/SaldoIndividualServlet.jsp");
            doForward(req, resp, "/jsp/SaldoIndividual.jsp?" + csrftokenC);

        } catch (Exception e) {
            mensaje = "Error al procesar la solicitud {" + e + "}";
            LOGGER.debug(mensaje);
            req.setAttribute("javax.servlet.jsp.jspException", e);
            doForward(req, resp, "/jsp/error.jsp");
        }
    }

    private final void doForward(HttpServletRequest request, HttpServletResponse response, String nextPage) {
        try {
            LimpiarParametro.safeSendForward(request, response, nextPage);
        } catch (IOException | ServletException | AccessControlException e) {
            LOGGER.debug("Error al procesar la solicitud {" + e + "}");
        }
    }
}
