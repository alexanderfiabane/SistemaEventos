<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Cadastrar Estado" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconStateCity.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastros Básicos" isLabelKey="false"><javalek:url><c:url value="/admin/menuCadastrosBasicos.html"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<javalek:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <form:hidden path="id"/>
    <div class="row-fluid">
        <div class="span3">
            <see:formField label="label.statename" isLabelKey="true" isMandatory="true" path="nome" maxlength="100"/>
        </div>
        <div class="span3">
            <see:formField label="label.stateacronym" isLabelKey="true" isMandatory="true" path="sigla" maxlength="2"/>
        </div>
    </div>
    <see:formButtonGroup formUrl="/admin/formEstado.html"/>
</form:form>

<div class="row-fluid">
    <display:table id="state" name="listEstados" pagesize="10" requestURI="/admin/formEstado.html" class="table table-striped table-condensed">
        <c:url var="edit_url" value="/admin/formEstado.html"><c:param name="idEstado" value="${state.id}"/></c:url>
        <c:url var="delete_url" value="/admin/deleteEstado.html"><c:param name="idEstado" value="${state.id}"/></c:url>
        <c:url var="cidade_url" value="/admin/formCidade.html"><c:param name="idEstado" value="${state.id}"/></c:url>
        <display:column media="html" titleKey="label.options" class="threeOption centered" headerClass="centered">
            <button type="button" class="btn btn-mini" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
            <button type="button" class="btn btn-mini" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
            <button type="button" class="btn btn-mini" title="Adicionar cidade deste Estado" onclick="location.href = '${cidade_url}';"><i class="icon-plus"></i></button>
        </display:column>
        <display:column property="nome" class="centered" headerClass="centered"/>
        <display:column property="sigla" class="centered" headerClass="centered"/>
    </display:table>        
</div>

<script type="text/javascript">
    onload = function() {
        document.getElementById("nome").focus();
    };
</script>
