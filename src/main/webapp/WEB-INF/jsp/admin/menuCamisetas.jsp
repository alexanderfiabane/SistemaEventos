<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Cadastro de Camisetas" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconCamiseta.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastros Básicos" isLabelKey="false"><javalek:url><c:url value="/admin/menuCadastrosBasicos.html"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <javalek:menu id="menu" defaultIconSize="64" >

            <javalek:menuItem label="Cadastrar Tipo de camiseta">
                <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/formTipoCamiseta.html"/></javalek:url>
            </javalek:menuItem>

            <javalek:menuItem label="Cadastrar Tamanho de camiseta">
                <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/formTamanhoCamiseta.html"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Cadastrar Cor de camiseta">
                <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/formCorCamiseta.html"/></javalek:url>
            </javalek:menuItem>
        </javalek:menu>
    </div>
</div>
