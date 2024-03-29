<%@tag pageEncoding="UTF-8" body-content="scriptless"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="msf" uri="http://code.google.com/msf/commons-tags" %>

<%@attribute name="formUrl"     type="java.lang.String" %>

<%-- source --%>
<div class="form-actions">
    <button type="submit" class="btn btn-primary"><msf:message key="label.save"/></button>
    <c:if test="${!empty formUrl}">
        <c:url var="clear_url" value="${formUrl}"/>
        <button type="button" class="btn" onclick="location.href = '${clear_url}';"><msf:message key="label.clear"/></button>
    </c:if>
    <jsp:doBody/>
</div>
