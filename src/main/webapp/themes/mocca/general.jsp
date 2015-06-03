<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><fmt:message key="application.name"/><c:if test="${!empty pageTitle_label}"> - ${pageTitle_label}</c:if></title>

        <%-- Head da pagina, contendo os javascripts e css usados. --%>
        <!-- Le styles -->
        <link rel="stylesheet" href="<c:url value='/assets/css/smoothness/jquery-ui-min.css'/>" />
        <link rel="stylesheet" href="<c:url value='/assets/css/browser-detector-min.css'/>" />
        <link rel="stylesheet" href="<c:url value='/assets/css/mocca-pack-min.css'/>" />
        <link rel="stylesheet" href="<c:url value='/assets/application/css/application.css'/>" />

        <!--JS-->
        <script src="<c:url value='/assets/js/browser-detector-min.js'/>"></script>
        <script src="<c:url value='/assets/js/jquery-min.js'/>"></script>
        <script src="<c:url value='/assets/js/jquery-ui-min.js'/>"></script>
        <script src="<c:url value='/assets/js/jquery-mocca-pack-min.js'/>"></script>
        <!-- DWR -->
        <script type="text/javascript" src="<c:url value='/dwr/engine.js'/>"></script>
        <script src="<c:url value='/assets/application/js/SystemUtils.js'/>"></script>
    </head>

    <body>
        <%-- Header da pagina, contendo o logo/nome da aplicacao (identidade visual da app). --%>
        <%-- vars necessarias --%>
        <c:url var="home_url" value="/index.html"/>
        <c:url var="login_url"  value='/login.html'/>
        <c:url var="updateinfo_url" value="/user/atualizarCadastro.html" context="/user"/>
        <c:url var="changepass_url" value="/user/alterarSenha.html" context="/user"/>
        <c:url var="help_url"       value="/ajuda.html"/>
        <c:url var="logout_url"  value='/logout'/>
        <security:authorize var="is_logged" access="isAuthenticated()"/>

        <div class="band stroked-bottom shadowed no-print">
            <!-- main navbar -->
            <nav class="band navbar gradient inverse">
                <div class="container">
                    <ul class="nav">
                        <li><a class="uppercase" href="${home_url}"><spring:message code="application.name"/></a></li>
                    </ul>
                    <ul class="nav pull-right">
                        <li>
                            <div class="btn-group">
                                <c:choose>
                                    <c:when test="${not is_logged}">
                                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                            <i class="icon-user"></i> Anônimo <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" role="menu">
                                            <li role="menuitem"><a tabindex="-1" href="${login_url}"><i class="icon-signin"></i> Login</a></li>
                                            <li role="menuitem"><a tabindex="-1" href="${help_url}"><i class="icon-question-sign"></i> Ajuda</a></li>
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <%-- se tiver usuario logado, mostramos o menu dropdown com acoes de usuario --%>
                                        <security:authentication var="user_name" property="principal.pessoa.nome" />
                                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                            <i class="icon-user"></i> ${user_name} <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" role="menu">
                                            <li role="menuitem"><a tabindex="-1" href="${updateinfo_url}"><i class="icon-pencil"></i> Atualizar cadastro</a></li>
                                            <li role="menuitem"><a tabindex="-1" href="${changepass_url}"><i class="icon-pencil"></i> Alterar senha</a></li>
                                            <li role="menuitem"><a tabindex="-1" href="${help_url}"><i class="icon-question-sign"></i> Ajuda</a></li>
                                            <li role="menuitem" class="divider"></li>
                                            <li role="menuitem"><a tabindex="-1" href="${logout_url}"><i class="icon-signout"></i> Sair</a></li>
                                        </ul>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="band jumbotron masthead stroked-bottom shadowed">
            <div class="jumbotron-inner">
                <div class="container">
                    <div class="row">
                        <div class="span10 offset1">
                            <div class="pull-left">
                                <c:url var="url_logo" value="/assets/application/img/logo-vetorizado.svg"/>
                                <object class="logo-svg" type="image/svg+xml" data="${url_logo}">
                                    <span></span>
                                </object>
                            </div>
                            <div class="logo-txt">
                                <h1><fmt:message key="application.name"/></h1>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <%-- Barra que mostra o icone e o titulo do formulario atual (breadcrumbs). --%>
        <div class="band">
            <div class="container no-padding-bottom">
                <div class="breadcrumb">
                    <sitemesh:write property="page.titlebarContent"/>
                </div>
            </div>
        </div>

        <%-- Conteúdo propriamente dito. --%>
        <div class="band">
            <div class="container">
                <sitemesh:write property='body'/>
            </div>
        </div>

        <%-- Uma barrinha de rodape discreta, contendo  nome/versao do app e o copyright. --%>
        <div class="band footer mini-font-size no-print padding-top">
            <div class="container narrow stroked-top">
                <div class="row">
                    <div class="span6">
                        <span class="dimmed">
                            <spring:message code="application.name"/> - versão <spring:message code="application.version"/>
                        </span>
                    </div>
                    <div class="span6 align-right-tablet-desktop">
                        <span class="dimmed">
                            <spring:message code="application.copyright.web"/>
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $(document).ready(function () {
                $(':text').addClass('textfield');
                $('select').addClass('selectfield');
                $(':text.date:not(.manual-init)').dateTimePicker({mode: 'date'});
            });
        </script>
    </body>
</html>
