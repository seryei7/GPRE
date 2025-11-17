<%@ page import="com.giss.gpre.util.LimpiarParametro"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
	<head>
	    <meta charset="UTF-8">
	    <title>Petición de Informe de Cálculo de Saldo Individual</title>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/saldoIndividual.css">
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
	<%
	    String tokenConsultar = LimpiarParametro.getOwaspCsrfTokenGet(request, "/gestionPresencia/ConsultarSaldoInd");
	    String tokenInforme = LimpiarParametro.getOwaspCsrfTokenGet(request, "/gestionPresencia/InformeSaldoInd");
	%>
		<div class="container">
			<h2>Petición de Informe de Cálculo de Saldo Individual</h2>
			
			<!-- Datos Generales -->
			<fieldset>
			    <legend>Datos Generales</legend>
			    <label for="nif">NIF:</label>
			    <input type="text" id="nif" name="nif" value="${sessionScope.tDni}" required><br>
			
			    <div class="fecha-rango">
			        <label>Rango de Fechas:</label>
			        <span>Desde:</span>
			        <input type="date" id="fechaDesde" name="fechaDesde" value="${fechaDesde}" ${empty fechaDesde ? 'disabled' : ''}>
			        <span>Hasta:</span>
			        <input type="date" id="fechaHasta" name="fechaHasta" value="${fechaHasta}" ${empty fechaHasta ? 'disabled' : ''}>
			    </div>
			</fieldset>
			
			<!-- Datos del Informe -->
			<fieldset>
			    <legend>Datos del Informe</legend>
			    <label for="nombre">Nombre:</label>
			    <input type="text" id="nombre" name="nombre" value="${nombre}" ${empty nombre ? 'disabled' : ''} readonly>
			
			    <label for="empresa">Empresa:</label>
			    <input type="text" id="empresa" name="empresa" value="${empresa}" ${empty empresa ? 'disabled' : ''} readonly>
			
			    <label for="contrato">Contrato:</label>
			    <input type="text" id="contrato" name="contrato" value="${contrato}" ${empty contrato ? 'disabled' : ''} readonly>
			
			    <label for="categoria">Categoría:</label>
			    <input type="text" id="categoria" name="categoria" value="${categoria}" ${empty categoria ? 'disabled' : ''} readonly>
			
			    <label for="proyecto">Proyecto:</label>
			    <input type="text" id="proyecto" name="proyecto" value="${proyecto}" ${empty proyecto ? 'disabled' : ''} readonly>
			
			    <label for="centro">Centro:</label>
			    <input type="text" id="centro" name="centro" value="${centro}" ${empty centro ? 'disabled' : ''} readonly>
			
			    <label for="unidad">Unidad:</label>
			    <input type="text" id="unidad" name="unidad" value="${unidad}" ${empty unidad ? 'disabled' : ''} readonly>
			    
			    <!-- Inputs ocultos para parámetros numéricos -->
				<input type="hidden" id="entiGesEp" name="entiGesEp">
				<input type="hidden" id="provEp" name="provEp">
				<input type="hidden" id="cenGesEp" name="cenGesEp">
				<input type="hidden" id="idTipoPersonal" name="idTipoPersonal">
				<input type="hidden" id="idCentro" name="idCentro">
				<input type="hidden" id="idUnidad" name="idUnidad">
				<input type="hidden" id="idCifNif" name="idCifNif">
			</fieldset>
			
			<!-- Botones -->
			<div class="buttons">
			    <input type="button" name="btnConsultar" id="btnConsultar" data-token="<%= tokenConsultar %>" value="Consultar">
			    <input type="button" name="btnInforme" id="btnInforme" data-token="<%= tokenInforme %>" value="Informe" disabled>
			    <input type="button" name="btnSalir" id="btnSalir" value="Salir">
			</div>
			
			<!-- Spinner -->
			<div id="spinner">
			    <div class="loader"></div>
			    <p>Consultando datos...</p>
			</div>
		</div>
	</body>
</html>
