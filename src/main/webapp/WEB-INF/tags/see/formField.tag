<%@tag pageEncoding="UTF-8" body-content="empty"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mocca" tagdir="/WEB-INF/tags/mocca" %>
<%@ taglib prefix="see" tagdir="/WEB-INF/tags/see" %>

<%@attribute name="label"       type="java.lang.String" required="true" %>
<%@attribute name="path"        type="java.lang.String" required="true"%>
<%@attribute name="hint"       type="java.lang.String" %>
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
    <c:choose>        
        <c:when test="${!isLabelKey}">            
            <label class="label control">
                ${label} <c:if test="${not empty hint}"><i id="${id}hint" class="icon-info-sign"></i></c:if>
            </label>        
        </c:when>
        <c:otherwise>
            <label class="label control">
                <fmt:message key="${label}"/> <c:if test="${not empty hint}"><i id="${id}hint" class="icon-info-sign"></i></c:if>
            </label> 
        </c:otherwise>
    </c:choose>
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
        <c:choose>            
            <c:when test="${!isLabelKey}">
                <label class="label checkbox">
                    ${label} <c:if test="${not empty hint}"><i id="${id}hint" class="icon-info-sign"></i></c:if>
                </label>        
                <form:checkbox path="${path}" id="${id}" />
            </c:when>
            <c:otherwise>
                <label class="label checkbox">
                    <fmt:message key="${label}"/> <c:if test="${not empty hint}"><i id="${id}hint" class="icon-info-sign"></i></c:if>
                    <form:checkbox path="${path}" id="${id}" />
                </label> 
            </c:otherwise>
        </c:choose>        
    </c:when>
    <c:when test="${type == 'date'}">
        <see:dateField id="${id}" path="${path}" isShowFormat="false" cssClass="${inputClass}"/>
    </c:when>
    <c:when test="${type == 'textarea'}">
        <form:textarea path="${path}" cols="80" maxlength="${maxlength}" id="${id}" cssClass="${inputClass}" rows="5"/>
    </c:when>
    <c:when test="${type == 'hidden'}">
        <form:hidden path="${path}" maxlength="${maxlength}" readonly="${readonly}" id="${id}"/>
        <input id="${path}Descr" type="text" readonly="${readonly}" class="${inputClass}"/>
    </c:when>
    <c:when test="${type == 'password'}">
        <input type="password" name="${path}" maxlength="${maxlength}" id="${id}" class="${inputClass}"/>
    </c:when>
    <c:otherwise>
        <form:input path="${path}" maxlength="${maxlength}" readonly="${readonly}" id="${id}" cssClass="${inputClass}"/>
    </c:otherwise>
</c:choose>

<form:errors path="${path}" cssClass="pill error"/>
<c:if test="${not empty hint}">    
<script>
    $(document).ready(function(){
        $("#${id}hint").qtip({
            'content': "${hint}"                            
        });        
    });
</script>
</c:if>

