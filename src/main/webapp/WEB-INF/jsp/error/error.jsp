<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

    <content tag="titlebarContent">
        <mocca:title title="Oooops"/>
        <%--
        <javalek:pagetitle label="Oooops!!!">
            <javalek:icon><c:url value="/assets/application/img/icons/fatalError.png"/></javalek:icon>
        </javalek:pagetitle>
        --%>
    </content>

    <c:set var="exception" value="${requestScope['javax.servlet.error.exception'].cause}"/>
    <c:set var="exceptionName" value="${exception.class.simpleName}"/>
    <div>
        <p>Ocorreu um erro ao tentar realizar a operação solicitada.</p>
        <c:if test="${not empty exception.message}">
            <p class="fatalErrorText">
                <strong>Motivo:</strong>
                <c:out value="${exception.message}"/>
            </p>
        </c:if>
    </div>
