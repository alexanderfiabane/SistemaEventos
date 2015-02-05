<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Dormitório" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastrar Evento" isLabelKey="false"><msf:url><c:url value="/admin/formEvento.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastrar Edição" isLabelKey="false"><msf:url><c:url value="/admin/formEdicao.html?idEvento=${edicao.evento.id}"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div class="small-font-size">
    <mocca:menu>
        <mocca:menuItem iconClass="icon-edit" label="Cadastrar Dormitório" isLabelKey="false"
                            url="/admin/formDormitorio.html?idEdicao=${edicao.id}"/>

        <mocca:menuItem iconClass="icon-exchange" label="Trocar Confraternista de Dormitório" isLabelKey="false"
                        url="/admin/trocaDormitorio.html?idEdicao=${edicao.id}"/>
    </mocca:menu>
</div>
