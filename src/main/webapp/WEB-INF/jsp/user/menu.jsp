<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<%-- este elemento <content> passa o breadcrumbs para o titlebar do layout --%>
<content tag="titlebarContent">
    <msf:pagetitle label="label.page.mainMenu" isLabelKey="true" defaultIsLabelKey="true">
        <%-- TODO : trocar icone! --%>
        <msf:icon><c:url value="/assets/application/img/icons/iconBemVindoUsuarios.png"/></msf:icon>
    </msf:pagetitle>
</content>

<%-- Estas duas divs implementam um workaround para centralizar o menu --%>
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <msf:menu id="mainMenu" defaultIconSize="64" defaultIsLabelKey="true">
            <!--Minhas Inscrições (requer login usuário)-->
            <msf:menuItem id="item_inscricoesUsuario" label="label.menu.usersubscriptions">
                <msf:icon><c:url value="/assets/application/img/icons/iconMinhasInscricoes.png"/></msf:icon>
                <msf:url><c:url value="/user/listUsuarioInscricoes.html"/></msf:url>
            </msf:menuItem>            
            <!--Editar Senha-->
            <msf:menuItem id="item_senha" label="label.menu.changePasswd" disabled="true">
                <msf:icon><c:url value="/assets/application/img/icons/iconEditSenha.png"/></msf:icon>
                <msf:url><c:url value="/user/formEditSenha.html"/></msf:url>
            </msf:menuItem>
        </msf:menu>
    </div>
</div>

