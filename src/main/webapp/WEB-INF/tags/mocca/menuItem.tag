<%@ tag body-content="empty" description="Item de menu."%>

<%-- Atributos --%>
<%@ attribute name="fonte" required="false" type="java.lang.String"
              description="Opcional. O nome do fonte do sie."%>

<%@ attribute name="url" required="true" type="java.lang.String"
              description="A url do item."%>

<%@ attribute name="isUrlAbsolute" required="false" type="java.lang.Boolean"
              description="Opcional. Indica se a url deve ser usada duretamente no link (sem usar o <c:url/>). Default = false."%>

<%@ attribute name="urlTarget" required="false" type="java.lang.String"
              description="Opcional. Target, caso se deseje que a url abra em uma nova janela/aba."%>

<%@ attribute name="urlContext" required="false" type="java.lang.String"
              description="Opcional. O contexto da url."%>

<%@ attribute name="iconClass" required="false" type="java.lang.String"
              description="Opcional. A classe que define o ícone do item."%>

<%@ attribute name="icon" required="false" type="java.lang.String"
              description="Opcional. O ícone do item."%>

<%@ attribute name="iconContext" required="false" type="java.lang.String"
              description="Opcional. O contexto do ícone do item."%>

<%@ attribute name="isActive" required="false" type="java.lang.Boolean"
              description="Opcional. Usado quando não é setado um fonte. Default = true."%>

<%@ attribute name="inactiveIcon" required="false" type="java.lang.String"
              description="Opcional. O ícone do item qdo este está inativo."%>

<%@ attribute name="inactiveIconContext" required="false" type="java.lang.String"
              description="Opcional. O contexto do ícone do item qdo este está inativo."%>

<%@ attribute name="label" required="false" type="java.lang.String"
              description="Opcional. O rótulo do link. O valor padrão é a descrição da aplicação."%>

<%@ attribute name="isLabelKey" required="false" type="java.lang.Boolean"
              description="Opcional. Indica se o valor passado para 'label' é uma key para a mensagem. Default = false."%>

<%@ attribute name="style" required="false" type="java.lang.String"
              description="Opcional. Estilo de renderização do item [ menu | list ]. Default = menu."%>

<%-- Taglibs utilizadas --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Source-code --%>
<%-- optional defaults --%>
<c:if test="${empty isUrlAbsolute}"> <c:set var="isUrlAbsolute" value="false"/> </c:if>
<c:if test="${empty isLabelKey}"> <c:set var="isLabelKey" value="false"/> </c:if>
<c:if test="${empty style}"> <c:set var="style" value="menu"/> </c:if>
<c:if test="${empty isActive}"> <c:set var="isActive" value="true"/> </c:if>
<c:set var="hasConfig" value="${not empty fonte && not empty configuracaoAplicacoes}"/>

<%-- foreground icon --%>
<c:if test="${!empty icon}">
    <c:choose>
        <c:when test="${!empty iconContext}"><c:url var="_ico_url" value="${icon}" context="${iconContext}"/></c:when>
        <c:otherwise><c:url var="_ico_url" value="${icon}"/></c:otherwise>
    </c:choose>
</c:if>

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

<%-- active/inactive flag --%>
<c:set var="isActive" value="${hasConfig ? configuracaoAplicacoes[fonte].situacaoAtiva : isActive}"/>

<%-- inactive icon --%>
<c:if test="${!isActive && !empty inactiveIcon}">
    <c:choose>
        <c:when test="${!empty inactiveIconContext}">
            <c:url var="_ico_url" value="${inactiveIcon}" context="${inactiveIconContext}"/>
        </c:when>
        <c:otherwise>
            <c:url var="_ico_url" value="${inactiveIcon}"/>
        </c:otherwise>
    </c:choose>
</c:if>

<%-- render the menu item --%>
<c:choose>
    <c:when test="${style == 'list'}">
        <%-- menu list mode --%>
        <%-- TODO --%>
    </c:when>
    <c:when test="${style == 'menu'}">
        <%-- menu icon mode --%>
        <li>
            <a class="menu-item btn ${isActive ? '' : 'disabled'}" <c:if test="${isActive}">href="${_url_link}"</c:if> target="${urlTarget}">
                <c:choose>
                    <c:when test="${not empty iconClass}">
                        <span class="menu-icon"><i class="${iconClass}"></i></span>
                    </c:when>
                    <c:when test="${not empty _ico_url}">
                        <img class="menu-icon" src="${_ico_url}"/>
                    </c:when>
                </c:choose>
                <span class="menu-label">${_label_var}</span>
            </a>
        </li>
    </c:when>
</c:choose>