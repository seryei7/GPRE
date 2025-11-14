<%@page import="com.giss.gpre.util.LimpiarParametro"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.owasp.esapi.ESAPI"%>
<%
	// Initialize ESAPI request and response
	ESAPI.httpUtilities().setCurrentHTTP(request, response);

	// Obtener DNI del usuario
    String dniUsuario = (String) LimpiarParametro.getAttributeObject(ESAPI.currentRequest(), 
        ESAPI.encoder().encodeForHTMLAttribute("dniUsuario"));
    Integer numeroResultados = (Integer) LimpiarParametro.getAttributeObject(ESAPI.currentRequest(), 
        ESAPI.encoder().encodeForHTMLAttribute("numeroResultados"));
%>

<div class="page">
	<div class="table-wrap">
		<div class="table-scroll">
			<div id="elementCountDisplay" class="controls-page-title" aria-live="polite">
				<div class="controls-header">
					<div class="controls-title">
						<span id="elementCount"><%= numeroResultados != null ? numeroResultados : 0 %></span> 
						Registros de Situación de Usuarios
					</div>
					<div class="controls-subtitle">
						Usuario: <%= dniUsuario != null ? dniUsuario : "" %>
					</div>
				</div>
			</div>
			<table id="dataTable">
				<caption class="invisible">Datos de situación de usuarios</caption>
				<thead>
					<tr id="theadRow">
						<th>Apellido 1</th>
						<th>Apellido 2</th>
						<th>Nombre</th>
						<th>DNI</th>
						<th>Tarjeta</th>
						<th>Entidad</th>
						<th>Provincia</th>
						<th>Centro</th>
						<th>Unidad</th>
						<th>Código Empresa</th>
						<th>Nombre Empresa</th>
						<th>Contrato</th>
						<th>Contrato Red</th>
						<th>NT Codden</th>
						<th>CD Codden</th>
						<th>Descripción Codden</th>
						<th>Descripción Codden Emp</th>
					</tr>
				</thead>
				<tbody id="tbody" data-json='${listaSituaciones}'>
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

<script type="text/javascript">
// Script para renderizar la tabla desde el JSON
document.addEventListener('DOMContentLoaded', function() {
    const tbody = document.getElementById('tbody');
    const jsonData = tbody.getAttribute('data-json');
    
    if (jsonData) {
        try {
            const data = JSON.parse(jsonData);
            renderTable(data);
        } catch (e) {
            console.error('Error al parsear JSON:', e);
        }
    }
});

function renderTable(data) {
    const tbody = document.getElementById('tbody');
    tbody.innerHTML = '';
    
    data.forEach(function(item) {
        const tr = document.createElement('tr');
        tr.innerHTML = 
            '<td>' + escapeHtml(item.apellido1 || '') + '</td>' +
            '<td>' + escapeHtml(item.apellido2 || '') + '</td>' +
            '<td>' + escapeHtml(item.nombre || '') + '</td>' +
            '<td>' + escapeHtml(item.iddni || '') + '</td>' +
            '<td>' + escapeHtml(item.cdtarjeta || '') + '</td>' +
            '<td>' + (item.entiGesEp || '') + '</td>' +
            '<td>' + (item.provEp || '') + '</td>' +
            '<td>' + (item.cenGesEp || '') + '</td>' +
            '<td>' + escapeHtml(item.dsunidad || '') + '</td>' +
            '<td>' + escapeHtml(item.cdempresa || '') + '</td>' +
            '<td>' + escapeHtml(item.nombreEmpresa || '') + '</td>' +
            '<td>' + escapeHtml(item.dscontrato || '') + '</td>' +
            '<td>' + escapeHtml(item.dscontratored || '') + '</td>' +
            '<td>' + (item.ntcodden || '') + '</td>' +
            '<td>' + (item.cdcodden || '') + '</td>' +
            '<td>' + escapeHtml(item.dscodden || '') + '</td>' +
            '<td>' + escapeHtml(item.dscoddenEmp || '') + '</td>';
        tbody.appendChild(tr);
    });
    
    updateElementCount(data.length);
}

function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function updateElementCount(count) {
    const elementCount = document.getElementById('elementCount');
    if (elementCount) {
        elementCount.textContent = count;
    }
}
</script>
