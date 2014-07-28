<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Cadastrar Tipo de Camiseta" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconCamiseta.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastros Básicos" isLabelKey="false"><msf:url><c:url value="/admin/menuCadastrosBasicos.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastro de Camisetas" isLabelKey="false"><msf:url><c:url value="/admin/menuCamisetas.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<bs:notice type="success" visible="${!empty message}" closeable="true">${message}</bs:notice>
<msf:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <div class="row-fluid">
        <div class="span3">
            <bs:formField label="label.description" isLabelKey="true" isMandatory="true" path="descricao" maxlength="30"/>
        </div>                
    </div>
    <bs:formButtonGroup formUrl="/admin/formTipoCamiseta.html"/>
</form:form>

<div class="row-fluid">
    <display:table id="tipo" name="tiposCamiseta" pagesize="10" requestURI="/admin/formTipoCamiseta.html" class="table table-striped table-condensed">
        <c:url var="edit_url" value="/admin/formTipoCamiseta.html"><c:param name="idTipo" value="${tipo.id}"/></c:url>
        <c:url var="delete_url" value="/admin/deleteTipoCamiseta.html"><c:param name="idTipo" value="${tipo.id}"/></c:url>
        <display:column media="html" titleKey="label.options" class="twoOption centered" headerClass="centered">
            <button  type="button" class="btn btn-mini" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
            <button  type="button" class="btn btn-mini" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
            </display:column>
            <display:column titleKey="label.description" property="descricao" class="centered" headerClass="centered"/>
        </display:table>
</div>

<script type="text/javascript">
                onload = function() {
                    document.getElementById("descricao").focus();
                };
</script>