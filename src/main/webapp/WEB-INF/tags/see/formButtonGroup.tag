<%@tag pageEncoding="UTF-8" body-content="scriptless"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="clearUrl"    type="java.lang.String" required="false" %>
<%@attribute name="backUrl"     type="java.lang.String" required="false" %>
<%@attribute name="putSubmit"   type="java.lang.Boolean" required="true" %>

<%-- source --%>
<div class="form-actions stroked-top mini-padding padding-bottom">
    <div class="span2 align-left">
        <c:if test="${!empty backUrl}">            
            <span class="align-left">            
                <button type="button" class="btn" onclick="location.href = '${backUrl}';"><i class="icon-arrow-left"></i> Voltar</button>
            </span>
        </c:if>        
    </div>
    <div class="span10">
        <jsp:doBody/>
        <c:if test="${!empty clearUrl}">        
            <button type="button" class="btn" onclick="location.href = '${clearUrl}';"><fmt:message key="label.clear"/></button>
        </c:if>
        <c:if test="${putSubmit}">        
            <button id="saveBtn" type="submit" class="btn primary"><fmt:message key="label.save"/></button>
        </c:if>        
    </div>
</div>
<script>
    $(document).ready(function(){
        $("#saveBtn").lockOnClick();
    });
</script>