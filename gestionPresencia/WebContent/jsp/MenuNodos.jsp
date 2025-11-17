<%@page import="com.giss.gpre.util.LimpiarParametro"%>
<%@page import="org.owasp.esapi.ESAPI"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.giss.gpre.entidades.Nodos"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<% 	ESAPI.httpUtilities().setCurrentHTTP(request, response);
//   	String tokenNT = LimpiarParametro.getOwaspCsrfTokenGet(request, "/gestionPresencia/Incidencias" );

	List<Nodos> nRaiz = (List<Nodos>) LimpiarParametro.getAttributeObject(ESAPI.currentRequest(), ESAPI.encoder().encodeForHTMLAttribute("nRaiz"));
	List<Nodos> n1list = (List<Nodos>) LimpiarParametro.getAttributeObject(ESAPI.currentRequest(), ESAPI.encoder().encodeForHTMLAttribute("n1"));
	List<Nodos> n2list = (List<Nodos>) LimpiarParametro.getAttributeObject(ESAPI.currentRequest(), ESAPI.encoder().encodeForHTMLAttribute("n2")); %>
<ul>
<% 
for (Nodos n : nRaiz) { 
	BigDecimal cdNP = n.getCdNodoPadre();
	BigDecimal nN = n.getIdNodo();
	String dsN = n.getDsNodo();
	if (cdNP.intValue() == 1) {	%>
	<li>
		<i data-icon="<%= n.getIconOpen() %>" class="icon fa "></i><span class="textOption"><%= dsN %></span>
		<button id="button_<%= nN %>" type="button" class="toggleBtn" onclick="toggleSubmenu('<%= nN %>', 'button_<%= nN %>'); event.stopPropagation();" aria-controls="<%= nN %>" aria-expanded="false">▼</button>
		<ul class="submenu" id="<%= nN %>">
			<% for (Nodos nH : n1list) { %>
			<% if (nH.getCdNodoPadre().intValue() == nN.intValue()) {
				String tokenNT = LimpiarParametro.getOwaspCsrfTokenGet(request, "/gestionPresencia/" + nH.getFuncionalidad().toString() );
			%>
			<li id="li_<%= nH.getIdNodo() %>">	
			<i data-icon="<%= nH.getIconOpen() %>" class="icon fa fa-ul fa "></i>
			<a onclick="onMenuLinkClick(event, this, 'li_<%= nH.getIdNodo() %>'); changeContent('<%= nH.getCdNodoPadre().intValue() %>', '<%= nH.getIdNodo().intValue() %>', '<%= tokenNT %>', '<%= nH.getFuncionalidad().toString() %>')"
			 class="textOption"><%= nH.getDsNodo() %></a>
			<% if (nH.getFuncionalidad().equals("-")) {%>
			<button id="button_<%= nH.getIdNodo() %>" type="button" class="toggleBtn" onclick="toggleSubmenu('<%= nH.getIdNodo() %>', 'button_<%= nH.getIdNodo() %>'); event.stopPropagation();" aria-controls="<%= nN %>" aria-expanded="false">▼</button>
			<% } %>
			<ul class="subMenuHijo" id="<%= nH.getIdNodo() %>">
					<% for (Nodos nNi : n2list) { %>
					<% if (nNi.getCdNodoPadre().intValue() == nH.getIdNodo().intValue()) { 
						String tokenNh = LimpiarParametro.getOwaspCsrfTokenGet(request, "/gestionPresencia/" + nNi.getFuncionalidad().toString());
					%>
					<li id="li_li_<%= nNi.getIdNodo() %>"><i class="icon margenSubList fa fa-wrench"></i>
						<a onclick="onMenuLinkClick(event, this, 'li_li_<%= nNi.getIdNodo() %>'); changeContent('<%= nNi.getCdNodoPadre().intValue() %>', '<%= nNi.getIdNodo() %>', '<%= tokenNh %>', '<%= nNi.getFuncionalidad().toString() %>')" class="textOption"><%= nNi.getDsNodo() %></a>
					</li>
					<% }} %>
				</ul>
			</li>
			<% }} %>
		</ul>
	</li>
<% }} %>

</ul>