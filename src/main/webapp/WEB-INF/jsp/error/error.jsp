<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>


<mocca:title title="Oooops"/>

<c:set var="exception" value="${requestScope['javax.servlet.error.exception'].cause}"/>
<c:set var="exceptionName" value="${exception.class.simpleName}"/>
<div class="row">
    <p>Ocorreu um erro ao tentar realizar a operação solicitada.</p>
    <c:if test="${not empty exception.message}">
        <p class="fatalErrorText">
            <strong>Motivo:</strong>
            <c:out value="${exception.message}"/>
        </p>
    </c:if>
</div>
