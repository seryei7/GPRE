package com.giss.gpre.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.giss.gpre.constants.SessionAttributes;
import com.google.gson.Gson;

/**
 * Servlet para obtener la URL del PDF almacenada en sesión.
 * Retorna la respuesta en formato JSON de forma segura.
 */
@WebServlet("/gestionPresencia/getPdfUrl")
public class PdfUrlServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Gson GSON = new Gson();

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        // Configurar respuesta como JSON con charset UTF-8
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        Map<String, String> response = new HashMap<>();

        if (session != null) {
            String pdfUrl = (String) session.getAttribute(SessionAttributes.PDF);
            
            if (pdfUrl != null && !pdfUrl.trim().isEmpty()) {
                response.put("pdf", pdfUrl);
            } else {
                response.put("error", "No se encontró la URL del PDF");
            }
        } else {
            response.put("error", "No hay sesión activa");
        }

        // Usar Gson para serializar de forma segura (previene XSS)
        try (PrintWriter out = resp.getWriter()) {
            out.print(GSON.toJson(response));
        }
    }
}
