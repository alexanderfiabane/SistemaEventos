<%@ tag body-content="empty" description="Saída com valor padrão"%>

<%@ attribute name="message" required="true" type="br.ufsm.cpd.commons.message.FlashMessage"
              description="Objeto que contém a mensagem."%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
    <c:when test="${message.level eq 'WARNING'}">
        <c:set var="css_level" value="warning" />
    </c:when>
    <c:when test="${message.level eq 'DANGER'}">
        <c:set var="css_level" value="danger" />
    </c:when>
    <c:when test="${message.level eq 'ERROR'}">
        <c:set var="css_level" value="error" />
    </c:when>
    <c:when test="${message.level eq 'INFO'}">
        <c:set var="css_level" value="info" />
    </c:when>
    <c:when test="${message.level eq 'CONTROL'}">
        <c:set var="css_level" value="control" />
    </c:when>
    <c:when test="${message.level eq 'SUCCESS'}">
        <c:set var="css_level" value="success" />
    </c:when>
    <c:otherwise>
        <c:set var="css_level" value="control" />
    </c:otherwise>
</c:choose>

<c:if test="${!empty message}">
    <div id="flashMessage" class="box bordered tip margin-bottom ${css_level}">
        <c:choose>
            <c:when test="${message.isKey}">
                <fmt:message key="${message.message}" />
            </c:when>
            <c:otherwise>
                <c:out value="${message.message}" />
            </c:otherwise>
        </c:choose>
    </div>
</c:if>