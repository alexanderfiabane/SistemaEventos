<%@ tag body-content="empty" description="Sa�da com valor padr�o"%>

<%@ attribute name="message" required="true" type="java.lang.String" description="Corpo da mensagem ou chave. "%>
<%@ attribute name="isKey" required="false" type="java.lang.String" description="Define se a mensagem � uma chave. Default = false"%>
<%@ attribute name="level" required="false" type="java.lang.String" description="N�vel da mensagem. Default = control"%>
<%@ attribute name="title" required="false" type="java.lang.String" description="T�tulo para a mensagem. Pode ser nulo"%>
<%@ attribute name="extraCss" required="false" type="java.lang.String" description="Classe css extra. Pode ser nulo"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
    <c:when test="${level != null}">
        <c:set var="css_level" value="${level}" />
    </c:when>   
    <c:otherwise>
        <c:set var="css_level" value="control" />
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${isKey != null}">
        <c:set var="is_key" value="${isKey}" />
    </c:when>   
    <c:otherwise>
        <c:set var="is_key" value="false" />
    </c:otherwise>
</c:choose>

<div class="box bordered tip margin-bottom ${css_level} <c:if test="${extraCss != null and !empty(extraCss)}">${extraCss}</c:if>">
    <c:if test="${title != null}">
        <span class="bold block"><c:out value="${title}" /></span>
    </c:if>
    <c:choose>
        <c:when test="${is_key}">
            <fmt:message key="${message}" />
        </c:when>
        <c:otherwise>
            <c:out value="${message}" escapeXml="false" />
        </c:otherwise>
    </c:choose>
</div>