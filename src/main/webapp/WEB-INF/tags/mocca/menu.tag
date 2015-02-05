<%@ tag description="Menu" body-content="scriptless"%>

<%@ attribute name="fixedWidth" required="false" type="java.lang.Boolean" description="Opcional. Default = true."%>
<%@ attribute name="fixedHeigth" required="false" type="java.lang.Boolean" description="Opcional. Default = true."%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty fixedWidth}"> <c:set var="fixedWidth" value="true"/> </c:if>
<c:if test="${empty fixedHeigth}"> <c:set var="fixedHeigth" value="true"/> </c:if>

<ul class="nav menu ${fixedWidth ? 'fixed-width' : ''} ${fixedHeigth ? 'fixed-heigth' : ''}">
    <jsp:doBody />
</ul>
