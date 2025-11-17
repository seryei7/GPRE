<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.giss.gpre.datos.UnidadNode"%>
<%@ page import="com.giss.gpre.datos.DatosUnidades" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!
    // Método para escapar HTML y prevenir XSS
    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;")
                    .replace("/", "&#x2F;");
    }
%>
<%
    UnidadNode currentNode = (UnidadNode) request.getAttribute("currentNode");
    if (currentNode == null) {
        currentNode = (UnidadNode) pageContext.getAttribute("unidadNode");
    }
    DatosUnidades unidad = currentNode.getDatos();
    boolean tieneHijos = currentNode.tieneHijos();
    
    // Sanitizar TODOS los datos que vienen del request para prevenir XSS
    String cdcentro = escapeHtml(unidad.getCdcentro());
    String idunidad = escapeHtml(unidad.getIdunidad());
    String dsunidad = escapeHtml(unidad.getDsunidad());
    
    // Establecer en pageContext para usar con JSTL
    pageContext.setAttribute("cdcentroEscaped", cdcentro);
    pageContext.setAttribute("idunidadEscaped", idunidad);
    pageContext.setAttribute("dsunidadEscaped", dsunidad);
    pageContext.setAttribute("tieneHijos", tieneHijos);
%>
<li class="<%= tieneHijos ? "collapsed parent-item" : "child-item" %>">
    <% if (tieneHijos) { %>
        <span class="toggle-btn-saldo">+</span>
    <% } %>
    <input type="checkbox" 
           class="<%= tieneHijos ? "parent-checkbox" : "child-checkbox" %>" 
           name="unidades"
           value="<c:out value='${cdcentroEscaped}'/> <c:out value='${idunidadEscaped}'/>">
    <c:out value="${idunidadEscaped}"/> - <c:out value="${dsunidadEscaped}"/>
    
    <% if (tieneHijos) { %>
        <ul class="child-list">
            <%
                for (UnidadNode hijo : currentNode.getHijos()) {
                    request.setAttribute("currentNode", hijo);
            %>
                <jsp:include page="UnidadTreeNode.jsp" />
            <%
                }
            %>
        </ul>
    <% } %>
</li>