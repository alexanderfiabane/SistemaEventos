<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<%--
<content tag="titlebarContent">
    <javalek:pagetitle label="Grupo por Idade" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Evento" isLabelKey="false"><javalek:url><c:url value="/admin/formEvento.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Edição" isLabelKey="false"><javalek:url><c:url value="/admin/formEdicao.html?idEvento=${edicao.evento.id}"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>
--%>

<mocca:title title="Grupo por Idade"/>

<mocca:menu>
    <mocca:menuItem iconClass="icon-edit" label="Cadastrar Grupo" isLabelKey="false"
                    url="/admin/formGrupoIdade.html?idEdicao=${edicao.id}"/>
    <mocca:menuItem iconClass="icon-exchange" label="Trocar Confraternista de Grupo" isLabelKey="false"
                    url="/admin/trocaGrupoIdade.html?idEdicao=${edicao.id}"/>
</mocca:menu>

