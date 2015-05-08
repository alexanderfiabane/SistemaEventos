<%@tag pageEncoding="UTF-8" body-content="empty"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="msf" uri="http://code.google.com/msf/commons-tags" %>
<%@ taglib prefix="see" tagdir="/WEB-INF/tags/see" %>

<%@attribute name="label"       type="java.lang.String" required="true" %>
<%@attribute name="path"        type="java.lang.String" required="true"%>
<%@attribute name="id"          type="java.lang.String"%>
<%@attribute name="inputClass"  type="java.lang.String"%>
<%@attribute name="isLabelKey"  type="java.lang.Boolean" %>
<%@attribute name="isMandatory" type="java.lang.Boolean" %>
<%@attribute name="maxlength"   type="java.lang.Integer" %>
<%@attribute name="type"        type="java.lang.String" %>
<%@attribute name="itens" type="java.util.Collection" %>
<%@attribute name="itemValue" type="java.lang.String" %>
<%@attribute name="itemLabel" type="java.lang.String" %>
<%@attribute name="selectNullItemLabel" type="java.lang.String" %>
<%@attribute name="readonly" type="java.lang.Boolean" %>

<%-- defaults --%>
<c:if test="${empty isLabelKey}"><c:set var="isLabelKey" value="${false}"/></c:if>
<c:if test="${empty isMandatory}"><c:set var="isMandatory" value="${false}"/></c:if>
<c:if test="${empty maxlength}"><c:set var="maxlength" value="${255}"/></c:if>
<c:if test="${empty type}"><c:set var="type" value="text"/></c:if>
<c:if test="${empty id}"><c:set var="id" value="${path}"/></c:if>
<c:if test="${empty readonly}"><c:set var="readonly" value="false"/></c:if>

<%-- source --%>
<c:if test="${type != 'check'}">
    <msf:label label="${label}" isLabelKey="${isLabelKey}" for="${id}"
               isMandatory="${isMandatory}" breakAfter="false" cssClass="label"/>
</c:if>

<c:choose>
    <c:when test="${type == 'select'}">
        <form:select path="${path}" id="${id}" cssClass="${inputClass}">
            <c:if test="${!empty selectNullItemLabel}">
                <form:option label="${selectNullItemLabel}" value=""/>
            </c:if>
            <c:choose>
                <c:when test="${empty itemValue}">
                    <form:options items="${itens}" itemLabel="${itemLabel}"/>
                </c:when>
                <c:otherwise>
                    <form:options items="${itens}" itemLabel="${itemLabel}" itemValue="${itemValue}"/>
                </c:otherwise>
            </c:choose>
        </form:select>
    </c:when>
    <c:when test="${type == 'check'}">
        <msf:label label="${label}" isLabelKey="${isLabelKey}" colonAfter="false" cssClass="checkbox" breakAfter="false">
            <form:checkbox path="${path}" id="${id}" />
        </msf:label>        
    </c:when>
    <c:when test="${type == 'date'}">
        <see:dateField id="${id}" path="${path}" isShowFormat="false"/>
    </c:when>
    <c:when test="${type == 'textarea'}">
        <form:textarea path="${path}" cols="80" maxlength="${maxlength}" id="${id}" cssClass="${inputClass}" rows="5"/>
    </c:when>
    <c:when test="${type == 'hidden'}">
        <form:hidden path="${path}" maxlength="${maxlength}" readonly="${readonly}" id="${id}"/>
        <input id="${path}Descr" type="text" readonly="${readonly}" class="${inputClass}"/>
    </c:when>
    <c:otherwise>
        <form:input path="${path}" maxlength="${maxlength}" readonly="${readonly}" id="${id}" cssClass="${inputClass}"/>
    </c:otherwise>
</c:choose>

<form:errors path="${path}" cssClass="pill error"/>

