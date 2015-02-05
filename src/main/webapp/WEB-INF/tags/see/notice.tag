<%@tag pageEncoding="UTF-8" body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="msf" uri="http://code.google.com/msf/commons-tags" %>

<%@attribute name="id"              type="java.lang.String" %>
<%@attribute name="type"            type="java.lang.String" %>
<%@attribute name="visible"         type="java.lang.Boolean" %>
<%@attribute name="closeable"       type="java.lang.Boolean" %>

<%-- defaults --%>
<c:set var="id"><msf:idGenerator prefix="NoticeTag" defaultValue="${id}"/></c:set>
<c:if test="${empty visible}"><c:set var="visible" value="${true}"/></c:if>
<c:if test="${empty closeable}"><c:set var="closeable" value="${false}"/></c:if>
<c:if test="${empty type}"><c:set var="type" value="info"/></c:if>
<c:if test="${type != 'success' && type != 'warning' && type != 'error' && type != 'info'}"><c:set var="type" value="info"/></c:if>
<c:if test="${type == 'warning'}"><c:set var="type" value="block"/></c:if>

<%-- source --%>
<c:if test="${visible}">
    <div id="${id}" class="box bordered tip ${type}">
        <c:if test="${closeable}"><button type="button" class="close" data-dismiss="box">&times;</button></c:if>
        <jsp:doBody/>
    </div>
</c:if>
