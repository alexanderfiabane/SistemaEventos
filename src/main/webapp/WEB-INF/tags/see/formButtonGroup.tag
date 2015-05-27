<%@tag pageEncoding="UTF-8" body-content="scriptless"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="formUrl"     type="java.lang.String" %>

<%-- source --%>
<div class="form-actions stroked-top mini-padding">
    <jsp:doBody/>
    <c:if test="${!empty formUrl}">
        <c:url var="clear_url" value="${formUrl}"/>
        <button type="button" class="btn" onclick="location.href = '${clear_url}';"><fmt:message key="label.clear"/></button>
    </c:if>
    <button type="submit" class="btn primary"><fmt:message key="label.save"/></button>
</div>
