<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Cadastros Básicos" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconCadastrosBasicos2.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <msf:menu id="cadastros" itensPerRow="4" defaultIconSize="64" >
            <msf:menuItem label="Cadastro de Camisetas">
                <msf:icon><c:url value="/assets/application/img/icons/iconCamiseta.png"/></msf:icon>
                <msf:url><c:url value="/admin/menuCamisetas.html"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Cadastro de Estados e Cidades">
                <msf:icon><c:url value="/assets/application/img/icons/iconStateCity.png"/></msf:icon>
                <msf:url><c:url value="/admin/formEstado.html"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Cadastro de Notícias">
                <msf:icon><c:url value="/assets/application/img/icons/iconNoticias.png"/></msf:icon>
                <msf:url><c:url value="/admin/formNoticia.html"/></msf:url>
            </msf:menuItem>
        </msf:menu>
    </div>
</div>

