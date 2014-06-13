<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<%-- este elemento <content> passa o breadcrumbs para o titlebar do layout --%>
<content tag="titlebarContent">
    <msf:pagetitle label="label.page.mainMenu" isLabelKey="true" defaultIsLabelKey="true">
        <%-- TODO : trocar icone! --%>
        <msf:icon><c:url value="/assets/application/img/icons/iconBemVindo.png"/></msf:icon>
    </msf:pagetitle>
</content>

<%-- Estas duas divs implementam um workaround para centralizar o menu --%>
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <msf:menu id="mainMenu" defaultIconSize="64" defaultIsLabelKey="true">

            <!--Inscrições Abertas-->
            <msf:menuItem id="item_inscricoesAbertas" label="label.menu.openedsubscriptions">
                <msf:icon><c:url value="/assets/application/img/icons/iconInscricoesAbertas.png"/></msf:icon>
                <msf:url><c:url value="/guest/listInscricoesAbertas.html"/></msf:url>
            </msf:menuItem>
            <!--Minhas Inscrições (requer login usuário)-->
            <msf:menuItem id="item_inscricoesUsuario" label="label.menu.usersubscriptions">
                <msf:icon><c:url value="/assets/application/img/icons/iconMinhasInscricoes.png"/></msf:icon>
                <msf:url><c:url value="/user/listUsuarioInscricoes.html"/></msf:url>
            </msf:menuItem>
            <%--
            <msf:menuItem id="item_pagamento" label="label.menu.payment">
                <msf:icon><c:url value="/assets/application/img/icons/iconFormPag.png"/></msf:icon>
                <msf:url><c:url value="/user/formPagamento.html"/></msf:url>
            </msf:menuItem>

            <msf:menuItem id="item_senha" label="label.menu.changePasswd">
                <msf:icon><c:url value="/assets/application/img/icons/iconEditSenha.png"/></msf:icon>
                <msf:url><c:url value="/user/formEditSenha.html"/></msf:url>
            </msf:menuItem>
            --%>
            <!--Funções Administrativas (requer login administrador)-->
            <msf:menuItem id="item_admin" label="label.menu.adminArea">
                <%-- TODO : trocar icone! --%>
                <msf:icon><c:url value="/assets/application/img/icons/iconFuncoesAdmin.png"/></msf:icon>
                <msf:url><c:url value="/admin/menu.html"/></msf:url>
            </msf:menuItem>

        </msf:menu>
    </div>
</div>
