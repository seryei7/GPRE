<%@page import="com.giss.gpre.entidades.Persona"%>
<%@page import="com.giss.gpre.datos.DatosArea"%>
<%@page import="com.giss.gpre.util.LimpiarParametro"%>
<%@page import="org.owasp.esapi.ESAPI"%>
<%@page import="com.giss.gpre.entidades.Acceso_area_nivel"%>
<%@page import="com.giss.gpre.entidades.Areas_trabajo"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
  	ESAPI.httpUtilities().setCurrentHTTP(request, response);
  	String tokenNT = LimpiarParametro.getOwaspCsrfTokenGet(request, "/gestionPresencia/NodosTrabajos" );

  	List<DatosArea> listaUsers = (List<DatosArea>) LimpiarParametro.getAttributeObject(ESAPI.currentRequest(), ESAPI.encoder().encodeForHTMLAttribute("lista"));
  	Persona persona = (Persona) LimpiarParametro.getAttributeObject(ESAPI.currentRequest(), ESAPI.encoder().encodeForHTMLAttribute("persona"));
%>
<div class="content-header lightblue marco_principal" id="marcoSelArea">
	<section class="content-header">
		<h1>ELECCIÓN ÁREA DE TRABAJO</h1>
	</section>
	<!-- Main content -->
	<section class="content">
		<h2 class="invisible">ELECCIÓN ÁREA DE TRABAJO</h2>
		<!-- Tabla de resultados -->
		<div class="table-responsive table-container">
			<table>
				<caption class="invisible">Datos de areas</caption>
				<thead>
					<tr>
						<th>DENOMINACIÓN</th>
						<th>ENTIDAD</th>
						<th>PROVINCIA</th>
						<th>CENTRO GESTOR</th>
						<th>NIVEL</th>
						<th>SELECCIÓN</th>
					</tr>
				</thead>
				<tbody>
					<% for (int i = 0; i < listaUsers.size(); i++) { %>
					<tr>
						<td><%=listaUsers.get(i).getDenominacion()%></td>
						<td><%=listaUsers.get(i).getEnti_ges_ep()%></td>
						<td><%=listaUsers.get(i).getProv_ep()%></td>
						<td><%=listaUsers.get(i).getCen_ges_ep()%></td>
						<td><%=listaUsers.get(i).getCdnivel()%></td>
						<td class="centrarTexto">
							<form action='/gestionPresencia/NodosTrabajos?<%=tokenNT%>' method="post">
								<input type="hidden" name="provincia" id="prov_<%=i%>" value="<%=listaUsers.get(i).getProv_ep()%>"/>
								<input type="hidden" name="entGesEp" id="entGesEp_<%=i%>" value="<%=listaUsers.get(i).getEnti_ges_ep()%>"/>
								<input type="hidden" name="cenGesEp" id="cenGesEp_<%=i%>" value="<%=listaUsers.get(i).getCen_ges_ep()%>"/>
								<input type="hidden" name="cdNivel" id="nivel_<%=i%>" value="<%=listaUsers.get(i).getCdnivel()%>"/>
								<input type="hidden" name="tDni" id="ind_<%=i%>" value="<%=persona.getDocNacional()%>"/>
								<input type="button" id="check_<%=i%>" name="check_<%=i%>" value="" class="checkbox-phantom" onclick="confirmarArea(<%=i%>, check_<%=i%>)"/>
							</form>
						</td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
	</section>
</div>