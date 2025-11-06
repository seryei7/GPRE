package com.giss.gpre.util;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.csrfguard.CsrfGuard;
import org.owasp.csrfguard.session.LogicalSession;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.AccessControlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilidad para limpieza y validación de parámetros HTTP.
 * Proporciona métodos seguros para manejo de peticiones y respuestas.
 * 
 * @author Miguel Ángel Rodríguez Menéndez
 */
public final class LimpiarParametro implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger("gpre.General");
	private static final long serialVersionUID = 2010052800000001111L;

	private static final DateFormat DF_1 = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat DF_2 = new SimpleDateFormat("dd-MM-yyyy");
	private static final DecimalFormat DF_DECIMAL = new DecimalFormat("###,##0.00");
	private static final CsrfGuard CSRF_GUARD = CsrfGuard.getInstance();

	private LimpiarParametro() {
		// Previene instanciación
	}

	public static final String getParameter(HttpServletRequest request, String parametro) {
		// LOGGER.debug("request.getMParameter(" + parametro + ")=[" +
		// request.getParameter(parametro) + "]");
		String p = null;
		if (parametro != null) {
			String fParam = filtrar(parametro);
			p = request.getParameter(fParam);
			p = filtrar(p);
		}
		return p;
	}

	public static final String getAttribute(HttpServletRequest request, String atributo) {
		String a = null;
		if (atributo != null) {
			String fAtrib = filtrar(atributo);
			a = (String) request.getAttribute(fAtrib);
			a = filtrar(a);
		}
		return a;
	}

	public static final Object getAttributeObject(HttpServletRequest request, String atributo) {
		Object a = null;
		if (atributo != null) {
			String fAtrib = filtrar(atributo);
			a = request.getAttribute(fAtrib);
			// a = filtrar(a);
		}
		return a;
	}

	/**
	 * Establece el Content-Type de forma segura.
	 */
	public static final void setSafeContentType(HttpServletResponse response, String tipo) {
		String tipoFiltrado = filtrar(tipo);
		ESAPI.httpUtilities().getCurrentResponse().setContentType(tipoFiltrado);
	}

	/**
	 * Añade un header HTTP de forma segura.
	 */
	public static final void addSafeHeader(HttpServletResponse response, String name, String value) {
		String nombreFiltrado = filtrar(name);
		String valorFiltrado = filtrar(value);
		ESAPI.httpUtilities().addHeader(nombreFiltrado, valorFiltrado);
	}

	/**
	 * Establece un header HTTP de forma segura.
	 */
	public static final void setSafeHeader(HttpServletResponse response, String name, String value) {
		String nombreFiltrado = filtrar(name);
		String valorFiltrado = filtrar(value);
		ESAPI.httpUtilities().setHeader(nombreFiltrado, valorFiltrado);
	}

	/**
	 * Realiza un forward seguro usando ESAPI.
	 * Previene ataques de tipo open redirect y path traversal.
	 * 
	 * @param request Petición HTTP
	 * @param response Respuesta HTTP
	 * @param nextPage Página destino
	 * @throws IOException Si hay error de I/O
	 * @throws ServletException Si hay error en el servlet
	 * @throws AccessControlException Si hay error de control de acceso
	 */
	public static final void safeSendForward(HttpServletRequest request, HttpServletResponse response, String nextPage)
			throws IOException, ServletException, AccessControlException {
		String paginaFiltrada = LimpiarParametro.filtrar(nextPage);
		RequestDispatcher dispatcher = ESAPI.currentRequest().getRequestDispatcher(paginaFiltrada);
		dispatcher.forward(ESAPI.currentRequest(), ESAPI.currentResponse());
	}

	/**
	 * Obtiene el token CSRF de OWASP para una URI específica.
	 * 
	 * @param request Petición HTTP
	 * @param uri URI para la cual generar el token
	 * @return Token en formato "nombre=valor"
	 */
	public static final String getOwaspCsrfTokenGet(HttpServletRequest request, String uri) {
		final LogicalSession logicalSession = CSRF_GUARD.getLogicalSessionExtractor().extract(request);
		String tokenValue = CSRF_GUARD.getTokenService().getTokenValue(logicalSession.getKey(), uri);
		return CSRF_GUARD.getTokenName() + "=" + tokenValue;
	}

	/**
	 * Obtiene la URL completa con el token CSRF embebido.
	 * 
	 * @param request Petición HTTP
	 * @param uri URI base
	 * @return URL completa con token
	 */
	public static final String getOwaspCsrfURLToken(HttpServletRequest request, String uri) {
		final LogicalSession logicalSession = CSRF_GUARD.getLogicalSessionExtractor().extract(request);
		String tokenValue = CSRF_GUARD.getTokenService().getTokenValue(logicalSession.getKey(), uri);
		return uri + "?" + CSRF_GUARD.getTokenName() + "=" + tokenValue;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static final String filtrar(String s) {
		String valor = null;

		if (s != null) {
			valor = Normalizer.normalize(s, Form.NFKC);

			// Replaces all noncharacter code points with Unicode U+FFFD
			valor = valor.replaceAll("[\\p{Cn}]", "\uFFFD");

			valor = valor.replaceAll("\uFE64", "_");
			valor = valor.replaceAll("\uFE65", "_");

			valor = valor.replace('<', '_');
			valor = valor.replace('>', '_');
			valor = valor.replace('"', '_');
			// valor = valor.replace('(', '_');
			// valor = valor.replace(')', '_');
			valor = valor.replace('&', '_');
			valor = valor.replace('#', '_');
			// valor = valor.replaceAll("_", "");
			// valor = valor.trim(); // NO SE PUEDE HACER TRIM quita los blancos
			// al area
		}

		return valor;
	}

	/**
	 * Codifica HTML escapando caracteres especiales.
	 * 
	 * @param codeHTML Código HTML a codificar
	 * @return HTML codificado
	 */
	public static final String htmlEncode(String codeHTML) {
		StringBuilder builder = new StringBuilder();
		char[] chars = codeHTML.toCharArray();
		
		for (char c : chars) {
			int charValue = (int) c;
			// Codificar caracteres especiales y latinos extendidos
			if ((charValue >= 160 && charValue <= 255) || 
				charValue == 34 || charValue == 38 || charValue == 39 || 
				charValue == 60 || charValue == 62) {
				builder.append("&#").append(charValue).append(";");
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * Extrae el mensaje de error de un objeto Exception.
	 * 
	 * @param objeto Objeto que contiene el error
	 * @return Mensaje del error o mensaje por defecto
	 */
	public static String dameMensaje(Object objeto) {
		try {
			java.lang.reflect.Method metodoGetMessage = objeto.getClass()
				.getMethod("getMessage", (Class<?>[]) null);
			
			if (metodoGetMessage != null) {
				return (String) metodoGetMessage.invoke(objeto, (Object[]) null);
			}
		} catch (Exception e) {
			LOGGER.warn("No se pudo extraer mensaje de: {}", objeto.getClass().getName());
		}
		return "Error en " + objeto.getClass().getSimpleName();
	}

	/**
	 * Obtiene un mensaje de error formateado a partir de un Throwable.
	 * 
	 * @param t Throwable del que extraer el mensaje
	 * @return Mensaje de error formateado
	 */
	public static final String getMensajeError(Throwable t) {
		if (t == null) {
			return "La sesión no es válida";
		}
		
		String mensaje = dameMensaje(t);
		if (mensaje == null) {
			mensaje = t.toString();
		} else {
			mensaje = htmlEncode(mensaje);
		}
		
		// Reemplazar mensajes técnicos por mensajes amigables
		if (mensaje.contains("java.lang.NullPointerException")) {
			return "Se ha producido un error procesando la petición";
		}
		if (mensaje.contains("TransactionRolledbackException") || mensaje.contains("CORBA")) {
			return "Error de acceso a base de datos";
		}
		
		return mensaje;
	}

	//
	//
	//
	public static final boolean miniValido(String mini) {
		boolean ret = true;

		if (mini == null) {
			ret = false;
		} else {
			if (mini.length() == 0 || mini.length() > 7) {
				ret = false;
			} else {
				if (!mini.trim().matches("^[0-9]{6}[A-Z]$")) {
					ret = false;
				}
			}
		}

		return ret;
	}

	public static final boolean areaValida(String area) {
		boolean ret = true;

		if (area == null) {
			ret = false;
		} else {
			if (area.length() == 0 || area.length() > 8) {
				ret = false;
			} else {
				if (!area.trim().matches("[0-9]*")) {
					ret = false;
				}
			}
		}

		return ret;
	}

	public static final boolean usuarioValido(String usuario) {
		boolean ret = true;

		if (usuario == null) {
			ret = false;
		} else {
			if (usuario.length() == 0 || usuario.length() > 10) {
				ret = false;
			} else {
				// System.out.println("[" + usuario + "] usuarioValido[" +
				// usuario.matches("[0-9]*") + "]");
				if (!usuario.matches("[0-9]*")) {
					ret = false;
				}
			}
		}

		return ret;
	}

	public static final synchronized boolean fechaValida(String fecha) {
		boolean ret = true;

		Date date1 = null;

		if (fecha.length() != 10) {
			ret = false;
		} else {
			try {
				date1 = DF_1.parse(fecha);
				if (!DF_1.format(date1).equals(fecha)) {
					ret = false;
				}
			} catch (ParseException pe) {
				try {
					date1 = DF_2.parse(fecha);
					if (!DF_2.format(date1).equals(fecha)) {
						ret = false;
					}
				} catch (ParseException pe2) {
					ret = false;
				}
			}
			// System.out.println("Fecha[" + date1 + "][" + date1.getTime() +
			// "]");
			if (date1 == null) {
				ret = false;
			}
		}

		return ret;
	}

	public static final boolean numeroValido(String numero, int tamanio) {
		boolean ret = true;

		int entero = Integer.MIN_VALUE;

		if (numero.length() > tamanio) {
			ret = false;
		} else {
			try {
				entero = Integer.parseInt(numero);
			} catch (NumberFormatException pe) {
				ret = false;
			}
			if (entero == Integer.MIN_VALUE) {
				ret = false;
			}
		}

		return ret;
	}

	public static final synchronized boolean decimalValido(String numero) {
		boolean ret = true;

		Number decimal = null;

		try {
			decimal = DF_DECIMAL.parse(numero);
		} catch (ParseException pe) {
			ret = false;
		}

		if (decimal == null) {
			ret = false;
		}

		return ret;
	}

	public static final boolean cadenaValida(String cadena, int tamanio) {
		boolean ret = true;

		if (cadena.length() > tamanio) {
			ret = false;
		}

		return ret;
	}

	public static final boolean sortValido(String sort) {
		boolean ret = true;

		if (sort.equals("")) {
			ret = true;
		} else {
			if (sort.length() > 1) {
				ret = false;
			} else {
				if (!sort.matches("[A,D]")) {
					ret = false;
				}
			}
		}

		return ret;
	}

	public static final boolean camposortValido(String camposort) {
		boolean ret = true;

		if (camposort.equals("")) {
			ret = true;
		} else {
			if (!camposort.equals("form") && !camposort.equals("dni") && !camposort.equals("apenom")
					&& !camposort.equals("fecha")) {
				ret = false;
			}
		}

		return ret;
	}

}
