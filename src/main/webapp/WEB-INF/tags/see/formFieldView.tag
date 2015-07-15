<%@tag pageEncoding="UTF-8" body-content="empty"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="msf" uri="http://code.google.com/msf/commons-tags" %>

<%@attribute name="escapeXml"   type="java.lang.String" required="false" %>
<%@attribute name="label"       type="java.lang.String" required="true" %>
<%@attribute name="value"       type="java.lang.Object" required="true"%>
<%@attribute name="isLabelKey"  type="java.lang.Boolean" %>
<%@attribute name="isDate"      type="java.lang.Boolean" %>
<%@attribute name="isCurrency"  type="java.lang.Boolean" %>
<%@attribute name="isBreak"     type="java.lang.Boolean" %>
<%@attribute name="datePattern" type="java.lang.String" %>

<%-- defaults --%>
<c:if test="${empty escapeXml}"><c:set var="escapeXml" value="${true}"/></c:if>
<c:if test="${empty isLabelKey}"><c:set var="isLabelKey" value="${false}"/></c:if>
<c:if test="${empty isBreak}"><c:set var="isBreak" value="${false}"/></c:if>
<c:if test="${empty isDate}"><c:set var="isDate" value="${false}"/></c:if>
<c:if test="${empty isCurrency}"><c:set var="isCurrency" value="${false}"/></c:if>
<c:if test="${empty datePattern}"><c:set var="datePattern" value="dd/MM/yyyy"/></c:if>

<%-- source --%>
<div class="control">
    <label>
        <c:choose>
            <c:when test="${not (isLabelKey == false)}">
                <fmt:message key="${label}"/>:                
            </c:when>            
            <c:otherwise>
                ${label}:
            </c:otherwise>
        </c:choose>
    </label>    
    <c:choose>
        <c:when test="${empty value}">
            <span style="font-weight: normal;"><em>Não informado</em></span>
        </c:when>
        <c:when test="${isDate}">
            <span style="font-weight: normal;"><fmt:formatDate value="${value}" pattern="${datePattern}"/></span>
        </c:when>
        <c:when test="${isCurrency}">
            <span style="font-weight: normal;"><fmt:formatNumber value="${value}" currencySymbol="R$" minFractionDigits="2"/></span>
        </c:when>
        <c:otherwise>
            <span style="font-weight: normal;"><c:out value="${value}" escapeXml="${escapeXml}"/></span>
        </c:otherwise>
    </c:choose>
</div>
