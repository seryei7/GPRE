<%@page import="com.giss.gpre.util.LimpiarParametro"%>
<%@ page import="java.util.List"%>
<%@ page import="com.giss.gpre.entidades.Persona"%>
<%@ page import="com.giss.gpre.entidades.Areas_trabajo"%>
<%@ page import="com.giss.gpre.entidades.Acceso_area_nivel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="-1">
<meta http-equiv="pragma" content="no-cache">
<title>GPRE - Gestión de Presencia</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css?v=<c:out value="${sessionScope.version}"/>">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=<c:out value="${sessionScope.version}"/>">
<link rel="stylesheet" href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.min.css?v=<c:out value="${sessionScope.version}"/>">
</head>
<%
	String tokenAcceso = LimpiarParametro.getOwaspCsrfTokenGet(request, "/gestionPresencia/" + "eleccionArea" );
	boolean nodos = (Boolean) request.getAttribute("nodos");
%>
<body class="hold-transition skin-blue sidebar-mini">
	<div>
		<header class="logo-style">
			<!-- Header Navbar -->
			<div class="logo-style navbar navbar-static-top padding-15px">
				<div class="col-lg-3 col-md-3 col-sm-4 hidden-xs">
					<img src="${pageContext.request.contextPath}/img/logo-institucional.png"
						class="img-responsive img-rounded logo-height hidden-md hidden-sm hidden-xs"
						alt="Imagen logo institucional">
				</div>

				<div class="col-xs-4 hidden-md hidden-sm hidden-lg">
					<img
						src="${pageContext.request.contextPath}/img/logo-institucional-mini.png"
						class="img-responsive img-rounded logo-height"
						alt="Imagen logo institucional en miniatura">
				</div>

				<!-- Sidebar toggle button-->
				<div
					class="col-lg-6 col-md-6 col-sm-4 col-xs-5 centrarTexto padding-top-10px">
					<span class="hidden-md hidden-sm hidden-xs"> <strong
						class="title-header-one">GPRE - Gestión de Control de
							Presencia</strong>
					</span>
					<h1 class="hidden-lg title-header-one">
						<strong>GPRE</strong>
					</h1>
				</div>

				<!-- Navbar Right Menu -->
				<div class="col-md-3 col-sm-4 col-xs-3 pull-right centrarTexto">
					<div class="user-profile" tabindex="0">
						<div class="col-lg-2 col-xs-6 padding-top-10px">
							<img class="img-circle img-user" id="imagenUsuarioRegistrado"
								alt="Usuario Registrado"
								src="${pageContext.request.contextPath}/img/user.png"></img>
						</div>
						<div
							class="col-lg-8 textoUsuarioLogado hidden-md hidden-sm hidden-xs">

							<c:set var="persona" value="${requestScope.persona}" />
							<div>${fn:escapeXml(persona.nombre)}
								${fn:escapeXml(persona.apellido1)}
								${fn:escapeXml(persona.apellido2)}</div>
							<%
								if (nodos) {
							%>
							<div>
								Nivel
								<c:out value="${sessionScope.cdNivel}" />
							</div>
							<div>
								<c:out value="${sessionScope.entGesEp}" />
								-
								<c:out value="${sessionScope.provincia}" />
								-
								<c:out value="${sessionScope.cenGesEp}" />
							</div>
							<%
								}
							%>
						</div>
						<div class="padding-top-10px homeHeader">
							<a href="${pageContext.request.contextPath}/eleccionArea?<%=tokenAcceso %>">
								<i class="fa fa-home" title="Inicio"></i><span class="invisible">Inicio</span>
							</a>
						</div>
						<div class="padding-top-10px homeHeader">
							<a href="#" id="openModalBtn"> <i
								class="fa fa-info-circle" title="Ayuda"></i><span
								class="invisible">Ayuda</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		</header>
		<%
			if (nodos) {
		%>
		<div class="menuBotones">
			<div class="button-row">
				<div>
					<button id="insertButton" title="Añadir" class="btn-n btn-n-primary" disabled><i class="icon fa fa-file"></i></button>
					<button id="deleteButton" title="Borrar o Eliminar" class="btn-n btn-n-primary" disabled><i class="icon fa fa-trash"></i></button>
				</div>
				<div>
					<button id="printButton" title="Imprimir" class="btn-n btn-n-primary" disabled><i class="icon fa fa-print"></i></button>
				</div>
				<div>
					<button id="newsButton" title="nosee" class="btn-n btn-n-primary" disabled><i class="icon fa fa-newspaper-o"></i></button>
					<button id="keyButton" title="nosee" class="btn-n btn-n-primary" disabled><i class="icon fa fa-key"></i></button>
					<button id="searchButton" title="Buscar" class="btn-n btn-n-primary" disabled><i class="icon fa fa-search"></i></button>
				</div>				
			</div>
		</div>
		<nav class="menu">
			<div class="menu-toggle" onclick="toggleMenu()">
				<span id="menu-icon" class="text">☰</span>
			</div>
			<jsp:include page="jsp/MenuNodos.jsp" />
		</nav>
		<div id="mainContent" class="main"></div>
		<%
			} else {
		%>
		<div class="cajon-table">
			<jsp:include page="jsp/SeleccionCentroEntidad.jsp" />
		</div>
		<%
			}
		%>
	</div>

	<div id="pdfModal" class="modal" role="dialog" aria-modal="true">
		<div class="modal-content" tabindex="-1">
			<a class="modal-close" aria-label="Cerrar modal" id="closeModalBtn">x</a>
			<div id="modalBody">
				<h2 id="modalTitle">Información de la aplicación</h2>
				<div class="pdf-list" id="pdfList">
					<ul class="pd-lf-20">
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
						<li><a href="#" onclick="openPDF('docs/doc1.pdf')">Abrir en otra pantalla</a></li>
					</ul>
					<p id="modalDesc">
						Versión: <strong><c:out value="${sessionScope.version}"/></strong>
					</p>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/paginacion.js?v=<c:out value="${sessionScope.version}"/>"></script>
	<script src="${pageContext.request.contextPath}/js/scriptsBase.js?v=<c:out value="${sessionScope.version}"/>"></script>
</body>
</html>