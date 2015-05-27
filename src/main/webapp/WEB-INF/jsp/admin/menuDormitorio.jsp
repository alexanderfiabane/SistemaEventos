<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<%--
<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Dormit�rio" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Evento" isLabelKey="false"><javalek:url><c:url value="/admin/formEvento.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Edi��o" isLabelKey="false"><javalek:url><c:url value="/admin/formEdicao.html?idEvento=${edicao.evento.id}"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>
--%>
<mocca:title title="Dormit�rio"/>
<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div class="small-font-size">
    <mocca:menu>
        <mocca:menuItem iconClass="icon-edit" label="Cadastrar Dormit�rio" isLabelKey="false"
                            url="/admin/formDormitorio.html?idEdicao=${edicao.id}"/>

        <mocca:menuItem iconClass="icon-exchange" label="Trocar Confraternista de Dormit�rio" isLabelKey="false"
                        url="/admin/trocaDormitorio.html?idEdicao=${edicao.id}"/>
    </mocca:menu>
</div>
