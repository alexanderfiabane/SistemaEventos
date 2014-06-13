<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Cadastro de Camisetas" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconCamiseta.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastros Básicos" isLabelKey="false"><msf:url><c:url value="/admin/menuCadastrosBasicos.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <msf:menu id="menu" defaultIconSize="64" >

            <msf:menuItem label="Cadastrar Tipo de camiseta">
                <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
                <msf:url><c:url value="/admin/formTipoCamiseta.html"/></msf:url>
            </msf:menuItem>

            <msf:menuItem label="Cadastrar Tamanho de camiseta">
                <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
                <msf:url><c:url value="/admin/formTamanhoCamiseta.html"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Cadastrar Cor de camiseta">
                <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
                <msf:url><c:url value="/admin/formCorCamiseta.html"/></msf:url>
            </msf:menuItem>
        </msf:menu>
    </div>
</div>
