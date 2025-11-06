<%@ page import="com.giss.gpre.util.LimpiarParametro"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Control de Impresión de Informes</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/base.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/impresionInformes.css">
</head>
<body>
	<%
		String tokenImprimir = LimpiarParametro.getOwaspCsrfTokenGet(request, "/gestionPresencia/ImprimirInforme");
		String tokenVisualizar = LimpiarParametro.getOwaspCsrfTokenGet(request,
				"/gestionPresencia/InformePantalla");
	%>
	<div class="main-content">
		<div class="container-impresion">
			<h2 class="titulo-principal">CONTROL DE IMPRESIÓN DE INFORMES</h2>

			<!-- Estado de los informes -->
			<fieldset class="seccion-estado">
				<legend>ESTADO DE LOS INFORMES</legend>

				<div class="table-wrap">
					<div class="table-scroll">
						<table id="tablaInformes">
							<thead>
								<tr>
									<th class="w-sm align-center">Selección</th>
									<th class="w-lg align-center">Nombre del Informe</th>
									<th class="w-sm align-center">Generado</th>
									<th class="w-sm align-center">Impreso</th>
									<th class="w-sm align-center">Hora</th>
									<th class="w-md align-center">NIF</th>
									<th class="w-xl align-center">Descripción del error</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${listaInformes ne null and fn:length(listaInformes) gt 0}">
										<%
											java.util.List<?> informesList = (java.util.List<?>) request.getAttribute("listaInformes");
													if (informesList != null && !informesList.isEmpty()) {
														int informeCount = 0;
														final int MAX_INFORMES = 1000;
														for (int i = 0; i < informesList.size() && informeCount < MAX_INFORMES; i++) {
															informeCount++;
															Object informeObj = informesList.get(i);
															if (informeObj != null) {
																pageContext.setAttribute("informe", informeObj);
										%>
										<c:set var="estadoGenerado" value="${informe.estadoGenerado}" />
										<c:set var="horaFormateada" value="${informe.horaFormateada}" />
										<c:set var="impreso"
											value="${informe.impreso ? 'Sí' : 'No'}" />
										<c:set var="descripcionError"
											value="${informe.CErrDes != null ? informe.CErrDes : ''}" />
										<c:set var="esSeleccionable"
											value="${estadoGenerado == 'Sí'}" />

										<tr>
											<td class="align-center"><input type="radio"
												name="selInforme" value="${informe.idListado}"
												data-listado="${informe.listado}"
												${!esSeleccionable ? 'disabled' : ''} class="radio-informe">
											</td>
											<td class="align-center">${informe.listado}</td>
											<td
												class="align-center ${estadoGenerado == 'Error' ? 'estado-error' : ''}">${estadoGenerado}</td>
											<td class="align-center">${impreso}</td>
											<td class="align-center">${horaFormateada}</td>
											<td class="align-center">${sessionScope.tDni}</td>
											<td
												class="align-center ${not empty descripcionError ? 'celda-error' : ''}">${descripcionError}</td>
										</tr>
										<%
											}
														}
													}
										%>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="7" class="align-center"
												style="padding: 20px; color: #666;">No hay informes
												disponibles para mostrar</td>
										</tr>
									</c:otherwise>
								</c:choose>

							</tbody>
						</table>
					</div>
				</div>
			</fieldset>

			<!-- Opciones de la impresión -->
			<fieldset class="seccion-opciones">
				<legend>OPCIONES DE LA IMPRESIÓN</legend>

				<div class="opciones-grid">
					<div class="opcion-item">
						<input type="radio" id="opcionPantalla" name="tipoImpresion"
							value="pantalla"> <label for="opcionPantalla"> <span
							class="icono-fallback">🖥️</span> <span class="texto-opcion">Pantalla</span>
						</label>
					</div>

					<div class="opcion-item">
						<input type="radio" id="opcionFichero" name="tipoImpresion"
							value="pdf"> <label for="opcionFichero"> <span
							class="icono-fallback">📄</span> <span class="texto-opcion">PDF</span>
						</label>
					</div>

					<div class="opcion-item">
						<input type="radio" id="opcionExcel" name="tipoImpresion"
							value="excel"> <label for="opcionExcel"> <span
							class="icono-fallback">📊</span> <span class="texto-opcion">Excel</span>
						</label>
					</div>
				</div>
			</fieldset>

			<!-- Botones de acción -->
			<div class="buttons">
				<input type="button" name="btnEnviar" id="btnEnviar" value="Enviar"
					disabled> <input type="button" name="btnSalir"
					id="btnSalir" value="Salir">
			</div>

			<!-- Spinner para actualización -->
			<div id="spinner" style="display: none;">
				<div class="spinner"></div>
				<p style="text-align: center;">Procesando...</p>
			</div>
		</div>
	</div>

	<input type="hidden" id="tokenVisualizar"
		value="<%=tokenVisualizar != null ? tokenVisualizar : ""%>">
	<input type="hidden" id="tokenImprimir"
		value="<%=tokenImprimir != null ? tokenImprimir : ""%>">
	<script src="${pageContext.request.contextPath}/js/gstImpresion.js"></script>
</body>
</html>
