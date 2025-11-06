<%@page import="com.giss.gpre.util.LimpiarParametro"%>
<%@page import="com.giss.gpre.entidades.Incidencias"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.owasp.esapi.ESAPI"%>
<%
	// Initialize ESAPI request and response
	ESAPI.httpUtilities().setCurrentHTTP(request, response);
%>

<div class="page">
	<div class="table-wrap">
		<div class="table-scroll">
			<div id="elementCountDisplay" class="controls-page-title"
				aria-live="polite">
				<div class="controls-header">
					<div class="controls-title">
						<span id="elementCount">0</span> Personas
					</div>
				</div>
			</div>
			<table id="dataTable">
				<caption class="invisible">Usuarios</caption>
				<thead>
					<tr id="theadRow">
					</tr>
				</thead>
				<tbody id="tbody" data-json='${listUsuarios}'>
				</tbody>
			</table>
			<div class="pagination-container" id="paginationContainer">
				<button class="pagination-button" id="firstPage" title="Primera página">«</button>
				<button class="pagination-button" id="prevPage" title="Página anterior">‹</button>
				<span class="pagination-info" id="pageInfo">Página 1 de 1</span>
				<button class="pagination-button" id="nextPage" title="Página siguiente">›</button>
				<button class="pagination-button" id="lastPage" title="Última página">»</button>
				<select class="page-size-selector" id="pageSizeSelector" aria-label="Filas por página"></select>
			</div>
		</div>
	</div>
	<div class="controls" aria-label="Controles de tabla">
		<div class="controls-header">
			<div class="controls-title-container">
				<div class="controls-title">Mostrar/ocultar columnas</div>
				<button type="button" class="controls-toggle" id="toggleControls"
					aria-expanded="false">▲</button>
			</div>
		</div>
		<div class="controls-grid" id="controlsGrid" role="group"
			aria-label="Columnas"></div>
	</div>
</div>