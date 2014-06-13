<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><msf:message key="application.name"/><c:if test="${!empty pageTitle_label}"> - ${pageTitle_label}</c:if></title>
        
        <%-- Head da pagina, contendo os javascripts e css usados. --%>
        <%@ include file="/themes/bootstrap/page_includes.jspf" %>
    </head>

    <body>
        <%-- Header da pagina, contendo o logo/nome da aplicacao (identidade visual da app). --%>
        <%@ include file="/themes/bootstrap/general_header.jspf" %>

        <%-- Parte que conterah o conteudo propriamente dito. --%>
        <div class="container">
            <decorator:body/>
        </div>

        <%-- Uma barrinha de rodape discreta, contendo  nome/versao do app e o copyright. --%>
        <%@ include file="/themes/bootstrap/general_footer.jspf" %>
    </body>
</html>
