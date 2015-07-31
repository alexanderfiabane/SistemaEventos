<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<c:set var="exceptionCode"      value="${pageContext.errorData.statusCode}"/>
<c:set var="showLogoutButton"   value="${false}"/>

<mocca:title title="Ocorreu um erro ao tentar realizar a operação solicitada."/>
<c:choose>
    <c:when test="${!empty exceptionCode && exceptionCode == 403}">
        <c:set var="texto">${exceptionCode} Acesso negado: Você não possui acesso a esta página.</c:set>
        <c:set var="showLogoutButton"   value="${true}"/>
    </c:when>
    <c:when test="${!empty exceptionCode && exceptionCode == 404}">
        <c:set var="texto">${exceptionCode} Página não encontada.</c:set>
    </c:when>
    <c:otherwise>
        <c:set var="texto">
            <c:choose>
                <c:when test="${!empty error}">
                    ${error}.
                </c:when>
                <c:otherwise>
                    Não foi possível concluir a operação.
                </c:otherwise>
            </c:choose>
        </c:set>
    </c:otherwise>
</c:choose>
<div class="row">
    <strong>Motivo:</strong>
    <span class="error">${texto}</span>
</div>
<c:if test="${not empty showLogoutButton && showLogoutButton}">
    <c:url var="url_logout" value='/logout'/>
    <button type="button" class="btn control" onclick="location.href = '${url_logout}'">Efetuar login com outro usuário</button>
</c:if>