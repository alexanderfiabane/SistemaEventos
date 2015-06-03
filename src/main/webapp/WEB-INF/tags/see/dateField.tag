<%@tag pageEncoding="UTF-8" body-content="scriptless"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@attribute name="id"%>
<%@attribute name="path"%>
<%@attribute name="cssClass"%>
<%@attribute name="isDisabled" type="java.lang.Boolean"%>
<%@attribute name="isShowFormat" type="java.lang.Boolean"%>

<%-- defaults --%>
<c:if test="${empty isDisabled}"><c:set var="isDisabled" value="${false}"/></c:if>
<c:if test="${empty isShowFormat}"><c:set var="isShowFormat" value="${true}"/></c:if>

<c:choose>
    <c:when test="${!empty id}">
        <c:choose>
            <c:when test="${!empty path}"><form:input id="${id}" path="${path}" cssClass="textfield date ${cssClass}" disabled="${isDisabled}"/></c:when>
            <c:otherwise><input type="text" id="${id}" class="textfield date" <c:if test="${isDisabled}">disabled</c:if>/></c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${!empty path}"><form:input path="${path}" cssClass="textfield date ${cssClass}" disabled="${isDisabled}"/></c:when>
            <c:otherwise><input type="text" class="textfield date" <c:if test="${isDisabled}">disabled</c:if>/></c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
<c:if test="${isShowFormat}">
    <span class="dimmed">(<spring:message code="i18n.dateFormat.user"/>)</span>
</c:if>
