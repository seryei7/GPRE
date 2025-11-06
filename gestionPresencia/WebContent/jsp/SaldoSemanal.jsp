<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.giss.gpre.util.LimpiarParametro"%>
<%@ page import="com.giss.gpre.datos.UnidadNode"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Petición del Informe de Saldo Semanal</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/saldoSemanal.css">
	<style>
        /* Spinner estilo básico */
        #spinner {
            display: none;
            text-align: center;
            margin-top: 15px;
        }
        .loader {
            border: 6px solid #f3f3f3;
            border-top: 6px solid #3498db;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            animation: spin 1s linear infinite;
            margin: auto;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>
<body>
	<% String tokenInforme = LimpiarParametro.getOwaspCsrfTokenGet(request, "/gestionPresencia/InformeSaldoSemanal"); 
	String tokenNT = LimpiarParametro.getOwaspCsrfTokenGet(request, "/gestionPresencia/NodosTrabajos" ); %>
	<div class="container">
		<h2>Petición del Informe de Saldo Semanal</h2>
		<!-- Códigos -->
		<div class="form-row">
			<label for="codigoEntidad">Código Entidad:</label> 
			<input type="text" id="codigoEntidad" name="codigoEntidad" value="<c:out value='${sessionScope.entGesEp}'/>" readonly class="readonly-field">
			
			<label for="codigoProvincia">Código Provincia:</label>
			<input type="text" id="codigoProvincia" name="codigoProvincia" value="<c:out value='${sessionScope.provincia}'/>" readonly class="readonly-field">
			
			<label for="codigoCgpe">Código Cgpe:</label>
			<input type="text" id="codigoCgpe" name="codigoCgpe" value="<c:out value='${sessionScope.cenGesEp}'/>" readonly class="readonly-field">
		</div>

		<!-- Fechas -->
		<div class="form-row">
			<label for="fechaDesde">Fecha Desde:</label> <input type="date"
				id="fechaDesde" name="fechaDesde" value="<c:out value='${fechaDesde}'/>"> <label
				for="fechaHasta">Fecha Hasta:</label> <input type="date"
				id="fechaHasta" name="fechaHasta" value="<c:out value='${fechaHasta}'/>">
		</div>

		<!-- Columnas lado a lado -->
		<div class="columns-container">
			<!-- Columna Centros y Unidades -->
			<div class="column centros tree">
				<h3>Centros y Unidades</h3>
				<ul class="checkbox-list">
					<%
						Map<Object, Object> mapaCentros = (Map<Object, Object>) request.getAttribute("mapaCentros");
						
						if (mapaCentros != null && !mapaCentros.isEmpty()) {
							// CORRECCIÓN KIUWAN: Límite explícito con contador
							int centroCount = 0;
							final int MAX_CENTROS = 1000;
							
							for (Map.Entry<Object, Object> entry : mapaCentros.entrySet()) {
								// Condición de salida explícita
								if (centroCount >= MAX_CENTROS) {
									break;
								}
								centroCount++;
								
								// Casting seguro
								Object centroObj = entry.getKey();
								if (centroObj == null) continue;
								
								// Usar reflection o toString según tu estructura
								pageContext.setAttribute("centro", centroObj);
					%>
								<c:set var="centro" value="${centro}" />
								<li class="collapsed parent-item">
									<span class="toggle-btn-saldo">+</span>
									<input type="checkbox" class="parent-checkbox" name="centros"
										value="<c:out value='${centro.enti_ges_ep}'/> <c:out value='${centro.prov_ep}'/> <c:out value='${centro.cen_ges_ep}'/> <c:out value='${centro.idcentro}'/>">
									<c:out value="${centro.enti_ges_ep}"/> <c:out value="${centro.prov_ep}"/> <c:out value="${centro.cen_ges_ep}"/>
									<c:out value="${centro.idcentro}"/> - <c:out value="${centro.dscentro}"/> 
									
					<%
								Object unidadesObj = entry.getValue();
								if (unidadesObj instanceof List) {
									List<UnidadNode> unidades = (List<UnidadNode>) unidadesObj;
									
									if (unidades != null && !unidades.isEmpty()) {
					%>
										<ul class="child-list">
					<%
										// CORRECCIÓN KIUWAN: Límite explícito con for tradicional
										int unidadCount = 0;
										final int MAX_UNIDADES = 1000;
										
										for (int i = 0; i < unidades.size() && unidadCount < MAX_UNIDADES; i++) {
											unidadCount++;
											UnidadNode node = unidades.get(i);
											if (node != null) {
												request.setAttribute("currentNode", node);
												request.setAttribute("nivelProfundidad", 0);
					%>
												<jsp:include page="UnidadTreeNode.jsp" />
					<%
											}
										}
					%>
										</ul>
					<%
									}
								}
					%>
								</li>
					<%
							}
						}
					%>
				</ul>
			</div>

			<!-- Columna Personal y Empresas -->
			<div class="column personal tree">
				<h3>Personal y Empresas</h3>
				<ul class="checkbox-list">
					<li class="collapsed parent-item">
						<span class="toggle-btn-saldo">+</span>
						<input type="checkbox" name="personalEmpresas" class="parent-checkbox"
							value="AdministracionPublica"> Administración Pública
						<ul class="child-list">
					<%
						List<Object> tipoPersonalList = (List<Object>) request.getAttribute("tipoPersonal");
						
						if (tipoPersonalList != null && !tipoPersonalList.isEmpty()) {
							// CORRECCIÓN KIUWAN: Límite explícito con for tradicional
							int tipoCount = 0;
							final int MAX_TIPOS = 500;
							
							for (int i = 0; i < tipoPersonalList.size() && tipoCount < MAX_TIPOS; i++) {
								tipoCount++;
								Object tipoObj = tipoPersonalList.get(i);
								if (tipoObj != null) {
									pageContext.setAttribute("tipoPersonal", tipoObj);
					%>
									<li class="child-item">
										<input type="checkbox" class="child-checkbox" name="tipoPersonal"
											value="<c:out value='${tipoPersonal.idtipopersonal}'/>">
										<c:out value="${tipoPersonal.dstipopersonal}"/>
									</li>
					<%
								}
							}
						}
					%>
						</ul>
					</li>
					<li class="collapsed parent-item">
						<span class="toggle-btn-saldo">+</span>
						<input type="checkbox" name="empresas" class="parent-checkbox"
							value="AsistenciaTecnica"> Asistencia Técnica
						<ul class="child-list">
					<%
						List<Object> empresasList = (List<Object>) request.getAttribute("empresas");
						
						if (empresasList != null && !empresasList.isEmpty()) {
							// CORRECCIÓN KIUWAN: Límite explícito con for tradicional
							int empresaCount = 0;
							final int MAX_EMPRESAS = 500;
							
							for (int i = 0; i < empresasList.size() && empresaCount < MAX_EMPRESAS; i++) {
								empresaCount++;
								Object empresaObj = empresasList.get(i);
								if (empresaObj != null) {
									pageContext.setAttribute("empresa", empresaObj);
					%>
									<li class="child-item">
										<input type="checkbox" class="child-checkbox" name="empresas"
											value="<c:out value='${empresa.idfiscal}'/>">
										<c:out value="${empresa.nombre}"/> - <c:out value="${empresa.idfiscal}"/>
									</li>
					<%
								}
							}
						}
					%>
						</ul>
					</li>
				</ul>
			</div>
		</div>

		<!-- Botones -->
		<div class="buttons">
			<input type="button" id="btnInforme" data-token="<%= tokenInforme %>" value="Informe">
			<input type="button" id="btnSalir" value="Salir">
		</div>
		
		<!-- Spinner -->
		<div id="spinner">
		    <div class="loader"></div>
		    <p>Consultando datos...</p>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/InfSaldo.js" defer></script>
</body>
</html>