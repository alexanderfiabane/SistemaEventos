<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Cadastro de Oficinas">
        <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea" isLabelKey="true"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastrar Edi��o" isLabelKey="false"><msf:url><c:url value="/admin/formEdicao.html?idEvento=${command.edicaoEvento.evento.id}"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<bs:notice type="success" visible="${!empty message}" closeable="true">${message}</bs:notice>
<msf:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <div class="row-fluid">
        <div class="span3">
            <bs:formField label="label.workshopname" isLabelKey="true" isMandatory="true" path="nome" maxlength="80"/>
        </div>
        <div class="span3">
            <bs:formField label="label.vacancies" isLabelKey="true" isMandatory="true" path="vagas" maxlength="3"/>
        </div>
    </div>
    <bs:formButtonGroup formUrl="/admin/formOficina.html?idEdicao=${command.edicaoEvento.id}"/>
</form:form>

<div class="row-fluid">
    <display:table id="oficina" name="oficinas" pagesize="10" requestURI="/admin/formOficina.html" class="table table-striped table-condensed">
        <c:url var="edit_url" value="/admin/formOficina.html"><c:param name="idOficina" value="${oficina.id}"/></c:url>
        <c:url var="delete_url" value="/admin/deleteOficina.html"><c:param name="idOficina" value="${oficina.id}"/></c:url>
        <display:column media="html" titleKey="label.options" class="twoOption centered" headerClass="centered">
            <button  type="button" class="btn btn-mini" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
            <button  type="button" class="btn btn-mini" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
        </display:column>
        <display:column titleKey="label.name" property="nome" class="centered" headerClass="centered"/>
        <display:column titleKey="label.vacancies" property="vagas" class="centered" headerClass="centered"/>
    </display:table>
</div>
