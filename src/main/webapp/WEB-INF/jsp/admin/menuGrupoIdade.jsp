<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Grupo por Idade" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Evento" isLabelKey="false"><javalek:url><c:url value="/admin/formEvento.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Edição" isLabelKey="false"><javalek:url><c:url value="/admin/formEdicao.html?idEvento=${edicao.evento.id}"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <javalek:menu id="menu" defaultIconSize="64" >

            <javalek:menuItem label="Cadastrar Grupo">
                <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/formGrupoIdade.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>

            <javalek:menuItem label="Trocar Confraternista de Grupo">
                <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/trocaGrupoIdade.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>
        </javalek:menu>
    </div>
</div>
