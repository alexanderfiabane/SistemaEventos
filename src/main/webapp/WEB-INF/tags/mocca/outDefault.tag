<%@ tag body-content="empty" description="Sa�da com valor padr�o"%>

<%@ attribute name="value" required="true" type="java.lang.Object" description="Valor a ser mostrado"%>
<%@ attribute name="emptyValue" required="false" type="java.lang.String" description="Valor assumido quando value for vazio. Default 'N�o informado'"%>
<%@ attribute name="dimmed" required="false" type="java.lang.Boolean" description="Indica se a classe dimmed deve ser aplicada. Default true."%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty dimmed}"><c:set var="dimmed" value="${true}"/></c:if>
<c:if test="${empty emptyValue}"><c:set var="emptyValue" value="N�o informado"/></c:if>

<c:choose>
    <c:when test="${empty value}">
        <span class="${dimmed ? 'dimmed' : ''}" style="font-style: italic;">${emptyValue}</span>
    </c:when>
    <c:otherwise>
        <span class="${dimmed ? 'dimmed' : ''}">${value}</span>
    </c:otherwise>
</c:choose>
