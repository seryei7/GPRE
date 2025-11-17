package com.giss.gpre.servlet.informes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giss.gpre.entidades.Persona;
import com.giss.gpre.util.GenerarInformes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet(name = "generarPdfPersonal", urlPatterns = { "/generarPdfPersonal" })
public class GenerarPdfPersonalServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Initialize ESAPI request and response
		ESAPI.httpUtilities().setCurrentHTTP(req, resp);

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session == null) {
				out.print("{\"error\": \"No hay sesión activa\"}");
				return;
			}

			// Obtener el usuario de la sesión
			String usuario = (String) session.getAttribute("tDni");
			if (usuario == null || usuario.isEmpty()) {
				out.print("{\"error\": \"Usuario no encontrado en la sesión\"}");
				return;
			}

			// Obtener el subsection del request
			String subSection = req.getParameter("subsection");
			if (subSection == null || subSection.isEmpty()) {
				out.print("{\"error\": \"Código subsection no proporcionado\"}");
				return;
			}

			// Leer el JSON con la lista de personas desde el body
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = req.getReader().readLine()) != null) {
				sb.append(line);
			}
			String jsonData = sb.toString();

			if (jsonData == null || jsonData.isEmpty()) {
				out.print("{\"error\": \"No se recibieron datos para el informe\"}");
				return;
			}

			// Convertir JSON a lista de objetos Persona
			Gson gson = new Gson();
			List<Persona> listaPersonas = gson.fromJson(jsonData, new TypeToken<ArrayList<Persona>>(){}.getType());

			if (listaPersonas == null || listaPersonas.isEmpty()) {
				out.print("{\"error\": \"La lista de personas está vacía\"}");
				return;
			}

			// Generar la URL del PDF usando GenerarInformes
			// El nombre del informe se construye con el código subsection
			String nombreInforme = "PERSONAL_" + subSection;
			String pdfUrl = GenerarInformes.generarUrlFichero(listaPersonas, usuario, nombreInforme, "pdf");

			if (pdfUrl != null && !pdfUrl.isEmpty()) {
				out.print("{\"pdfUrl\": \"" + pdfUrl + "\"}");
			} else {
				out.print("{\"error\": \"No se pudo generar la URL del PDF\"}");
			}

		} catch (Exception e) {
			LOGGER.error("Error al generar PDF de personal: " + e.getMessage(), e);
			out.print("{\"error\": \"Error al procesar la solicitud: " + e.getMessage() + "\"}");
		} finally {
			out.close();
		}
	}
}
