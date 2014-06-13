<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<page:applyDecorator name="geral">
    <content tag="titlebarContent">
        <msf:pagetitle label="Oooops!!!">
            <msf:icon><c:url value="/assets/application/img/icons/fatalError.png"/></msf:icon>
        </msf:pagetitle>
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
</page:applyDecorator>
