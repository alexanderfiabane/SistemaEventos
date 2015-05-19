<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Cadastrar Evento" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>        
    </msf:pagetitle>
</content>

<bs:notice type="success" visible="${!empty message}" closeable="true">${message}</bs:notice>
<msf:message var="confirmDeleteMsg" key="message.confirm.delete"/>    

<form:form commandName="command">
    <form:hidden path="id"/>
    <div class="row-fluid">
        <div class="span4">
            <bs:formField label="label.eventname" isLabelKey="true" isMandatory="true" path="nome" maxlength="100"/>
        </div>
        <div class="span4">
            <bs:formField label="label.eventacronym" isLabelKey="true" isMandatory="true" path="sigla" maxlength="30"/>
        </div>
        <div class="span4">
            <bs:formField label="label.eventsite" isLabelKey="true" isMandatory="true" path="site" maxlength="150"/>
        </div>
    </div>
    <bs:formButtonGroup formUrl="/admin/formEvento.html"/>
</form:form>

<div class="row-fluid">
    <display:table id="evento" name="eventos" pagesize="10" requestURI="/admin/formEdicao.html" class="table table-striped table-condensed">
        <c:url var="edit_url" value="/admin/formEdicao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
        <c:url var="delete_url" value="/admin/deleteEdicao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>            
        <c:url var="oficina_url" value="/admin/formOficina.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>            
        <c:url var="dormitorio_url" value="/admin/formDormitorio.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>            
        <display:column media="html" titleKey="label.options" class="threeOption centered" headerClass="centered">
            <c:url var="edit_url" value="/admin/formEvento.html"><c:param name="idEvento" value="${evento.id}"/></c:url>
            <c:url var="delete_url" value="/admin/deleteEvento.html"><c:param name="idEvento" value="${evento.id}"/></c:url>
            <c:url var="edicao_url" value="/admin/formEdicao.html"><c:param name="idEvento" value="${evento.id}"/></c:url>
            <button type="button" class="btn btn-mini" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
            <button type="button" class="btn btn-mini" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
            <button type="button" class="btn btn-mini" title="Criar edição deste evento" onclick="location.href = '${edicao_url}';"><i class="icon-share-alt"></i></button>
        </display:column>
        <display:column titleKey="label.name" property="nome" class="centered" headerClass="centered"/>
        <display:column titleKey="label.acronym" property="sigla" class="centered" headerClass="centered"/>
        <display:column titleKey="label.site" property="site" class="centered" headerClass="centered"/>
    </display:table>
</div>

<script type="text/javascript">
    onload = function() {
        document.getElementById("nome").focus();
    };
</script>
