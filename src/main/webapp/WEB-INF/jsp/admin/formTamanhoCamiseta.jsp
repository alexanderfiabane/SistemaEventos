<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Cadastrar Tamanho de Camiseta" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconCamiseta.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastros Básicos" isLabelKey="false"><javalek:url><c:url value="/admin/menuCadastrosBasicos.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastro de Camisetas" isLabelKey="false"><javalek:url><c:url value="/admin/menuCamisetas.html"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<javalek:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <div class="row-fluid">
        <div class="span3">
            <see:formField label="label.description" isLabelKey="true" isMandatory="true" path="descricao" maxlength="30"/>
        </div>
    </div>
    <see:formButtonGroup formUrl="/admin/formTamanhoCamiseta.html"/>
</form:form>

<div class="row-fluid">
    <display:table id="tamanho" name="tamanhosCamiseta" pagesize="10" requestURI="/admin/formTamanhoCamiseta.html" class="table table-striped table-condensed">
        <c:url var="edit_url" value="/admin/formTamanhoCamiseta.html"><c:param name="idTamanho" value="${tamanho.id}"/></c:url>
        <c:url var="delete_url" value="/admin/deleteTamanhoCamiseta.html"><c:param name="idTamanho" value="${tamanho.id}"/></c:url>
        <display:column media="html" titleKey="label.options" class="twoOption centered" headerClass="centered">
            <button type="button" class="btn btn-mini" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
            <button type="button" class="btn btn-mini" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
        </display:column>
        <display:column titleKey="label.description" property="descricao" class="centered" headerClass="centered"/>
    </display:table>
</div>

<script type="text/javascript">
    onload = function() {
        document.getElementById("descricao").focus();
    };
</script>
