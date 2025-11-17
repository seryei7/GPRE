package com.giss.gpre.util;

import java.io.IOException;

//import utilejb.DfaException;

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
//import javax.servlet.http.HttpSession;

import org.owasp.csrfguard.CsrfGuard;
import org.owasp.csrfguard.session.LogicalSession;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.AccessControlException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Miguel �ngel Rodr�guez Men�ndez
 * 
 */
public final class LimpiarParametro implements Serializable {

	// ------- Clases de Factor�as y Logs -------
	private final static Logger LOGGER = LoggerFactory.getLogger("gpre.General");

	private static final long serialVersionUID = 2010052800000001111L;

	protected final static DateFormat DF_1 = new SimpleDateFormat("dd/MM/yyyy");
	protected final static DateFormat DF_2 = new SimpleDateFormat("dd-MM-yyyy");
	protected final static DecimalFormat DF_DECIMAL = new DecimalFormat("###,##0.00");

	private final static CsrfGuard xyz0 = CsrfGuard.getInstance();

	private LimpiarParametro() {
	} // previene instanciacion

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

	public static final void setSafeContentType(HttpServletResponse response, String tipo) {
		String fTipo = filtrar(tipo);
		// LOGGER.debug("setSafeContentType[" + tipo + "] -> [" + fTipo + "]");
		// response.setContentType(fTipo); // <-- ESAPI kiuwan 60
		ESAPI.httpUtilities().getCurrentResponse().setContentType(fTipo);
	}

	public static final void addSafeHeader(HttpServletResponse response, String name, String value) {
		String fName = filtrar(name);
		String fValue = filtrar(value);
		// LOGGER.debug("addSafeHeader name[" + name + "] value[" + value +
		// "]");
		// response.addHeader(fName, fValue); // <-- ESAPI kiuwan 67
		ESAPI.httpUtilities().addHeader(fName, fValue);
	}

	public static final void setSafeHeader(HttpServletResponse response, String name, String value) {
		String fName = filtrar(name);
		String fValue = filtrar(value);
		// LOGGER.debug("setSafeHeader[" + name + "][" + value + "] -> [" +
		// fName + "][" + fValue + "]");
		// response.setHeader(fName, fValue); // <-- ESAPI kiuwan 74
		ESAPI.httpUtilities().setHeader(fName, fValue);
	}

	//
	// ��� ESAPI obliga a que las direcciones empiezen por WEB-INF !!!
	//
	// This method performs a forward to any resource located inside the WEB-INF
	// directory. Forwarding to publicly accessible resources can be dangerous,
	// as the request will have already passed the URL based access control
	// check.
	// This method ensures that you can only forward to non-publicly accessible
	// resources.
	//
	public static final void safeSendForward(HttpServletRequest request, HttpServletResponse response, String nextPage)
			throws IOException, ServletException, AccessControlException {
		// String st1 = "WEB-INF/classes/servlets/" +
		// LimpiarParametro.filtrar(nextPage);
		String st1 = LimpiarParametro.filtrar(nextPage);
		// httpreq.getRequestDispatcher(st1).forward(httpreq, httpresp); // <--
		// ESAPI kiuwan 81
		// LOGGER.debug("LimpiarParametro.safeSendForward[" + st1 + "]");
		// ESAPI.httpUtilities().sendForward("/jsp/FormulariosGuardados.jsp");
		RequestDispatcher dispatcher = ESAPI.currentRequest().getRequestDispatcher(st1);
		dispatcher.forward(ESAPI.currentRequest(), ESAPI.currentResponse());
	}

	// OWASP
	/**
	 * 
	 * @param request
	 * @param uri
	 * @return
	 */
	public static final String getOwaspCsrfTokenGet(HttpServletRequest request, String uri) {
		final LogicalSession logicalSession = xyz0.getLogicalSessionExtractor().extract(request);
		String reqToken = xyz0.getTokenService().getTokenValue(logicalSession.getKey(), uri);
//		LOGGER.debug("LimpiarParametro.getOwaspCsrfTokenGet.token[" + uri + "]='" + reqToken + "' <- [" + request.getRequestURL() + "]" );
		reqToken = xyz0.getTokenName() + "=" + reqToken;
		return reqToken;
	}

	/**
	 * 
	 * @param request
	 * @param uri
	 * @return
	 */
	public static final String getOwaspCsrfURLToken(HttpServletRequest request, String uri) {
		final LogicalSession logicalSession = xyz0.getLogicalSessionExtractor().extract(request);
		String reqToken = xyz0.getTokenService().getTokenValue(logicalSession.getKey(), uri);
//		LOGGER.debug("LimpiarParametro.getOwaspCsrfTokenGet.token[" + uri + "]='" + reqToken + "' <- [" + request.getRequestURL() + "]" );
		return uri + "?" + xyz0.getTokenName() + "=" + reqToken;
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

	/*
	 * public static String encode(String codeHTML){ StringBuffer sbuf = new
	 * StringBuffer(); char[] chars = codeHTML.toCharArray(); for(int i=0;
	 * i<chars.length; i++) { sbuf.append("&#" + (int) chars[i] + ";"); } return
	 * sbuf.toString(); }
	 */

	public static final String htmlEncode(String codeHTML) {
		StringBuffer sbuf = new StringBuffer();
		char[] chars = codeHTML.toCharArray();
		int c;
		for (int i = 0; i < chars.length; i++) {
			c = (int) chars[i];
			if ((c >= 160 && c <= 255) ||
			// comprueba (", &, ', <, >)
					c == 34 || c == 38 || c == 39 || c == 60 || c == 62) {
				// (c>=32 && c<=64) ) {
				sbuf.append("&#" + (int) chars[i] + ";");
			} else {
				sbuf.append(chars[i]);
			}
		}
		return sbuf.toString();
	}

	//
	//
	//
	public static String dameMensaje(Object elObjeto) {
		java.lang.reflect.Method elGet = null;
		String mensaje = null;
		try {
			elGet = elObjeto.getClass().getMethod("getMessage", (Class<?>[]) null);
			if (elGet != null) {
				mensaje = (String) elGet.invoke(elObjeto, (Object[]) null);
			}
		} catch (Exception e) {
			// LOGGER.error("No existe el metodo en la clase" +
			// elObjeto.getClass().getName(), e);
			mensaje = "Error getMessage() en " + elObjeto.getClass().getName();
		}
		return mensaje;
	}

	//
	//
	//
	public static final String getMensajeError(Throwable t) {
		String mensaje = "";
		mensaje = (t == null) ? "La session no es valida"
				: (dameMensaje(t) == null) ? t.toString() : htmlEncode(dameMensaje(t));
		mensaje = (mensaje.equals("java.lang.NullPointerException")) ? "Error de aplicaci�n" : mensaje;
		mensaje = (mensaje.indexOf("java.lang.NullPointerException") >= 0)
				? "Se ha producido un error procesando la petici�n" : mensaje;
		mensaje = (mensaje.indexOf("TransactionRolledbackException") >= 0) ? "Error de acceso a base de datos"
				: mensaje;
		mensaje = (mensaje.indexOf("CORBA") >= 0) ? "Error de acceso a base de datos" : mensaje;
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
