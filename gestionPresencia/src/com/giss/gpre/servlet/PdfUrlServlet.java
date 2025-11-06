package com.giss.gpre.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/gestionPresencia/getPdfUrl")
public class PdfUrlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Obtener la sesión
        HttpSession session = req.getSession(false); // false para no crear una nueva sesión si no existe

        // Configurar la respuesta como JSON
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (session != null) {
            // Obtener la URL del PDF desde la sesión
            String pdfUrl = (String) session.getAttribute("pdf");

            if (pdfUrl != null) {
                // Devolver la URL del PDF como JSON
                out.print("{\"pdf\": \"" + pdfUrl + "\"}");
            } else {
                // Si no hay URL, devolver un mensaje de error
                out.print("{\"error\": \"No se encontró la URL del PDF\"}");
            }
        } else {
            // Si no hay sesión, devolver un mensaje de error
            out.print("{\"error\": \"No hay sesión activa\"}");
        }

        out.close();
    }
}
