<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Grupo por Idade" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastrar Evento" isLabelKey="false"><msf:url><c:url value="/admin/formEvento.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastrar Edição" isLabelKey="false"><msf:url><c:url value="/admin/formEdicao.html?idEvento=${edicao.evento.id}"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <msf:menu id="menu" defaultIconSize="64" >

            <msf:menuItem label="Cadastrar Grupo">
                <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
                <msf:url><c:url value="/admin/formGrupoIdade.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>

            <msf:menuItem label="Trocar Confraternista de Grupo">
                <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
                <msf:url><c:url value="/admin/trocaGrupoIdade.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>
        </msf:menu>
    </div>
</div>
