<%@ tag body-content="empty" description="T�tulo"%>

<%--
  - Componente que define um t�tulo de uma p�gina
  -
  - @author Adriano Pereira (adriano@ufsm.br)
---%>

<%@ attribute name="title" required="true" type="java.lang.String" description="Valor do t�tulo."%>
<%@ attribute name="extraCss" required="false" type="java.lang.String" description="Classes extras de css a serem adicionadas."%>
<%@ attribute name="level" required="false" type="java.lang.Integer" description="N�vel do t�tulo. Default == 1 (prim�rio)."%>
<%@ attribute name="isTitleKey" required="false" type="java.lang.Boolean" description="Define se o valor do t�tulo � uma chave. Default = false"%>

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