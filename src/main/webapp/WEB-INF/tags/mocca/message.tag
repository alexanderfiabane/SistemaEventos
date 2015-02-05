<%@ tag body-content="scriptless" description="Saída com valor padrão"%>

<%@ attribute name="message"        required="false" type="java.lang.String"  description="Corpo da mensagem ou chave. "%>
<%@ attribute name="isMessageKey"   required="false" type="java.lang.Boolean" description="Define se a mensagem é uma chave. Default = false"%>
<%@ attribute name="title"          required="false" type="java.lang.String"  description="Título para a mensagem ou chave"%>
<%@ attribute name="isTitleKey"     required="false" type="java.lang.Boolean" description="Define se o titulo é uma chave. Default = false"%>
<%@ attribute name="closeable"      required="false" type="java.lang.Boolean" description="Define se a mensagem pode ser fechada."%>
<%@ attribute name="hidden"         required="false" type="java.lang.Boolean" description="Define se a mensagem deve ficar oculta."%>
<%@ attribute name="type"           required="false" type="java.lang.String"  description="Tipo da mensagem [control, info, primary, error, danger, warning, success, inverse]. Default = control"%>
<%@ attribute name="id"             required="false" type="java.lang.String"  description="O ID da div que conterá a mensagem."%>
<%@ attribute name="rounded"        required="false" type="java.lang.Boolean" description="Define se a mensagem tera os cantos arredondados."%>
<%@ attribute name="cssClass"       required="false" type="java.lang.String"  description="Classes css extras."%>
<%@ attribute name="cssStyle"       required="false" type="java.lang.String"  description="Estilos css extras."%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://portal.ufsm.br/cpd-commons-tags" prefix="cpd" %>

<%-- optional defaults --%>
<c:if test="${empty isMessageKey}"><c:set var="isMessageKey" value="false"/></c:if>
<c:if test="${empty isTitleKey}"><c:set var="isTitleKey" value="false"/></c:if>
<c:if test="${empty closeable}"><c:set var="closeable" value="false"/></c:if>
<c:if test="${empty hidden}"><c:set var="hidden" value="false"/></c:if>
<c:if test="${empty type}"><c:set var="type" value="control"/></c:if>
<c:if test="${empty rounded}"><c:set var="rounded" value="true"/></c:if>
<c:if test="${rounded}"><c:set var="_extraCss" value="rounded"/></c:if>
<c:if test="${hidden}"><c:set var="_extraCss" value="hidden ${_extraCss}"/></c:if>

<%-- resolve msgs --%>
<cpd:resolveMessage var="_message" value="${message}" isKey="${isMessageKey}" />
<cpd:resolveMessage var="_title"   value="${title}"   isKey="${isTitleKey}" />

<div class="box ${type} bordered shadowed margin-v ${_extraCss} ${cssClass}" style="${cssStyle}" id="${id}">
    <c:if test="${closeable}"><button type="button" class="close" data-dismiss="box">&times;</button></c:if>
    <c:if test="${!empty title}"><div class="bold">${title}</div></c:if>
    <c:choose>
        <c:when test="${!empty _message}">${_message}</c:when>
        <c:otherwise><jsp:doBody/></c:otherwise>
    </c:choose>
</div>
