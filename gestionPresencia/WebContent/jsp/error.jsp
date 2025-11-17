<%@page import="org.owasp.esapi.ESAPI"%>
<%@page import="com.giss.gpre.util.LimpiarParametro"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	// Initialize ESAPI request and response
	ESAPI.httpUtilities().setCurrentHTTP(request, response);
%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="-1">
	<meta http-equiv="pragma" content="no-cache">
	<title>GPRE - Error</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css?v=<c:out value="${sessionScope.version}"/>">
</head>
<body class="error-page">
	<div class="error-container">
		<h1 class="error-title">¡Ups! Ha ocurrido un error</h1>
		<p class="error-message">
			<%=LimpiarParametro.getMensajeError(exception)%></p>
		<button onclick="cerrarPestana()" class="error-btn">Cierra la pestaña</button>
	</div>
</body>
<script>
function cerrarPestana() {
    // Intenta cerrar la ventana/pestaña
    window.close();

    // Si no se cierra (caso pestaña abierta manualmente), redirige
    setTimeout(function() {
        if (!window.closed) {
            window.location.href = 'about:blank';
        }
    }, 100);
}
</script>
</html>

