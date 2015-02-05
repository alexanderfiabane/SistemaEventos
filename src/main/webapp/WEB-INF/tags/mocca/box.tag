<%@ tag description="Box com informações" body-content="scriptless"%>

<%--
  - Componente que define um título de uma página
  -
  - @author Adriano Pereira (adriano@ufsm.br)
---%>

<%@ attribute name="level" required="false" type="java.lang.String" description="Nível do box. Pode ser info, warning, control, danger, error. Default == control"%>
<%@ attribute name="title" required="false" type="java.lang.String" description="Um título para o box. Opcional"%>
<%@ attribute name="extraCss" required="false" type="java.lang.String" description="Classes extras de css a serem adicionadas."%>
<%@ attribute name="id" required="false" type="java.lang.String" description="Identificador."%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:if test="${empty(level)}">
    <c:set var="level" value="control" />
</c:if>
<div class="box bordered tip margin-bottom ${level} <c:if test="${!empty(extraCss)}">${extraCss}</c:if>" <c:if test="${!empty(id)}">id="${id}"</c:if>>
    <c:if test="${!empty(title)}">
        <strong><c:out value="${title}" /></strong>
    </c:if>
    <jsp:doBody />
</div>
