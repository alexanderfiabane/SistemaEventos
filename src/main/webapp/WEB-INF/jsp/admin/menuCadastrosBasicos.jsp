<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Cadastros Básicos" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconCadastrosBasicos2.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <javalek:menu id="cadastros" itensPerRow="4" defaultIconSize="64" >
            <javalek:menuItem label="Cadastro de Camisetas">
                <javalek:icon><c:url value="/assets/application/img/icons/iconCamiseta.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/menuCamisetas.html"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Cadastro de Estados e Cidades">
                <javalek:icon><c:url value="/assets/application/img/icons/iconStateCity.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/formEstado.html"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Cadastro de Notícias">
                <javalek:icon><c:url value="/assets/application/img/icons/iconNoticias.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/formNoticia.html"/></javalek:url>
            </javalek:menuItem>
        </javalek:menu>
    </div>
</div>

