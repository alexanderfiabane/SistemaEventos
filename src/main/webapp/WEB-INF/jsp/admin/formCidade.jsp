<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Cadastrar Cidade" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconStateCity.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastros Básicos" isLabelKey="false"><javalek:url><c:url value="/admin/menuCadastrosBasicos.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Estado" isLabelKey="false"><javalek:url><c:url value="/admin/formEstado.html"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<javalek:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <form:hidden path="id"/>
    <div class="row-fluid">
        <div class="span3">
            <see:formField label="label.cityname" isLabelKey="true" isMandatory="true" path="nome" maxlength="100"/>
        </div>
        <div class="span3">
            <see:formField label="label.state" isLabelKey="true" isMandatory="true" path="estado" type="select" itens="${estados}" itemLabel="nome" itemValue="id" selectNullItemLabel="-- Selecione um Estado --"/>
        </div>
        <div class="span3">
            <br/>
            <see:formField label="Capital?" path="capital" type="check"/>
        </div>
    </div>
    <see:formButtonGroup formUrl="/admin/formCidade.html"/>
</form:form>

<div class="row-fluid">
     <!--style="width: 100%; margin-bottom: 10px;"-->
    <display:table id="cidade" name="cidades" pagesize="10" requestURI="/admin/formCidade.html" class="table table-striped table-condensed">
        <c:url var="edit_url" value="/admin/formCidade.html"><c:param name="idCidade" value="${cidade.id}"/></c:url>
        <c:url var="delete_url" value="/admin/deleteCidade.html"><c:param name="idCidade" value="${cidade.id}"/></c:url>
        <display:column media="html" titleKey="label.options" class="twoOption centered" headerClass="centered">
            <button type="button" class="btn btn-mini" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
            <button type="button" class="btn btn-mini" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
        </display:column>
        <display:column media="html" titleKey="label.name" class="centered" headerClass="centered">
            ${cidade.nome} <c:if test="${cidade.capital}">(capital)</c:if>
        </display:column>
        <display:column titleKey="label.state" property="estado.nome" class="centered" headerClass="centered"/>
    </display:table>        
</div>

<script type="text/javascript">
    onload = function() {
        document.getElementById("nome").focus();
    }
</script>
