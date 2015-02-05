<%@ tag body-content="empty" description="Título"%>

<%--
  - Componente que define um título de uma página
  -
  - @author Adriano Pereira (adriano@ufsm.br)
---%>

<%@ attribute name="title" required="true" type="java.lang.String" description="Valor do título."%>
<%@ attribute name="extraCss" required="false" type="java.lang.String" description="Classes extras de css a serem adicionadas."%>
<%@ attribute name="level" required="false" type="java.lang.Integer" description="Nível do título. Default == 1 (primário)."%>
<%@ attribute name="isTitleKey" required="false" type="java.lang.Boolean" description="Define se o valor do título é uma chave. Default = false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${level == null}">
    <c:set var="level" value="1" />
</c:if>

<c:choose>
    <c:when test="${level == 1}">
        <c:set var="cssStyle" value="primary stroked-bottom text-shadowed margin-bottom" />
        <c:set var="hValue" value="2" />
    </c:when>
    <c:when test="${level == 2}">
        <c:set var="cssStyle" value="stroked-bottom text-shadowed large-margin-bottom" />        
        <c:set var="hValue" value="3" />
    </c:when>
    <c:when test="${level == 3}">
        <c:set var="cssStyle" value="stroked-bottom text-shadowed large-margin-bottom" />        
        <c:set var="hValue" value="4" />
    </c:when>
    <c:when test="${level == 4}">
        <c:set var="cssStyle" value="stroked-bottom text-shadowed large-margin-bottom" />        
        <c:set var="hValue" value="5" />
    </c:when>
    <c:when test="${level == 5}">
        <c:set var="cssStyle" value="stroked-bottom text-shadowed large-margin-bottom" />        
        <c:set var="hValue" value="6" />
    </c:when>
    <c:otherwise>
        <c:set var="cssStyle" value="stroked-bottom text-shadowed large-margin-bottom" />        
        <c:set var="hValue" value="2" />
    </c:otherwise >
</c:choose>

<h${hValue} class="${cssStyle} <c:if test="${extraCss != null}">${extraCss}</c:if>">
    <c:choose>
        <c:when test="${!empty (isTitleKey) && isTitleKey}">
            <fmt:message key="${title}" />
        </c:when>
        <c:otherwise>
            ${title}
        </c:otherwise>
    </c:choose>
</h${hValue}>