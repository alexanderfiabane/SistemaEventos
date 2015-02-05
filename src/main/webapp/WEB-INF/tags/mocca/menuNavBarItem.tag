<%@ tag description="NavBarItem" body-content="scriptless"%>

<%@ attribute name="dropMenu" required="false" type="java.lang.Boolean"
              description="Opcional. Default = false."%>

<%@ attribute name="dropSubMenu" required="false" type="java.lang.Boolean"
              description="Opcional. Default = false."%>

<%@ attribute name="fonte" required="false" type="java.lang.String"
              description="Opcional. O nome do fonte do sie."%>

<%@ attribute name="isActive" required="false" type="java.lang.Boolean"
              description="Opcional. Usado quando não é setado um fonte. Default = true."%>

<%@ attribute name="iconClass" required="false" type="java.lang.String"
              description="Opcional. A classe que define o ícone do item."%>

<%@ attribute name="url" required="false" type="java.lang.String"
              description="A url do item."%>

<%@ attribute name="isUrlAbsolute" required="false" type="java.lang.Boolean"
              description="Opcional. Indica se a url deve ser usada duretamente no link (sem usar o <c:url/>). Default = false."%>

<%@ attribute name="urlTarget" required="false" type="java.lang.String"
              description="Opcional. Target, caso se deseje que a url abra em uma nova janela/aba."%>

<%@ attribute name="urlContext" required="false" type="java.lang.String"
              description="Opcional. O contexto da url."%>

<%@ attribute name="label" required="false" type="java.lang.String"
              description="Opcional. O rótulo do link. O valor padrão é a descrição da aplicação."%>

<%@ attribute name="isLabelKey" required="false" type="java.lang.Boolean"
              description="Opcional. Indica se o valor passado para 'label' é uma key para a mensagem. Default = false."%>

<%-- Taglibs utilizadas --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty dropMenu}"> <c:set var="dropMenu" value="false"/> </c:if>
<c:if test="${empty dropSubMenu}"> <c:set var="dropSubMenu" value="false"/> </c:if>
<c:if test="${empty isUrlAbsolute}"> <c:set var="isUrlAbsolute" value="false"/> </c:if>
<c:if test="${empty isLabelKey}"> <c:set var="isLabelKey" value="false"/> </c:if>
<c:if test="${empty isActive}"> <c:set var="isActive" value="true"/> </c:if>
<c:if test="${empty url}">
    <c:set var="url" value="#"/>
    <c:set var="isUrlAbsolute" value="true"/>
</c:if>
<c:set var="hasConfig" value="${not empty fonte && not empty configuracaoAplicacoes}"/>
<c:set var="isActive" value="${hasConfig ? configuracaoAplicacoes[fonte].situacaoAtiva : isActive}"/>

<%-- label --%>
<c:choose>
    <c:when test="${!empty label}">
        <c:choose>
            <c:when test="${isLabelKey}"><fmt:message var="_label_var" key="${label}"/></c:when>
            <c:otherwise><c:set var="_label_var" value="${label}"/></c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${hasConfig}"><c:set var="_label_var" value="${configuracaoAplicacoes[fonte].descricao}"/></c:when>
            <c:otherwise><c:set var="_label_var" value="??sem-rotulo??"/></c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<%-- url --%>
<c:choose>
    <c:when test="${isUrlAbsolute}">
        <c:set var="_url_link" value="${url}"/>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${!empty urlContext}">
                <c:url var="_url_link" value="${url}" context="${urlContext}"/>
            </c:when>
            <c:otherwise>
                <c:url var="_url_link" value="${url}"/>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${dropMenu}">
        <li class="${isActive ? '' : 'disabled'}">
            <div class="btn-group block">
                <a class="dropdown-toggle" data-toggle="dropdown">
                    <c:if test="${not empty iconClass}"> <i class="${iconClass}"></i> </c:if> ${_label_var} <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                    <jsp:doBody />
                </ul>
            </div>
        </li>
    </c:when>
    <c:when test="${dropSubMenu}">
        <li class="dropdown-submenu ${isActive ? '' : 'disabled'}">
            <a <c:if test="${isActive}"> href="${_url_link}" target="${urlTarget}" </c:if>>
                <c:if test="${not empty iconClass}"> <i class="${iconClass}"></i> </c:if> ${_label_var}
            </a>
            <ul class="dropdown-menu">
                <jsp:doBody />
            </ul>
        </li>
    </c:when>
    <c:otherwise>
        <li class="${isActive ? '' : 'disabled'}">
            <a <c:if test="${isActive}"> href="${_url_link}" target="${urlTarget}" </c:if> >
                <c:if test="${not empty iconClass}"> <i class="${iconClass}"></i> </c:if> ${_label_var}
            </a>
        </li>
    </c:otherwise>
</c:choose>