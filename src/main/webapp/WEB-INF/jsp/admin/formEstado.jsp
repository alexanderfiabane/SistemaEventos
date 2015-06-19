<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Cadastrar Estado"/>
<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <form:hidden path="id"/>
    <div class="row">
        <div class="span6">
            <see:formField label="label.statename" isLabelKey="true" isMandatory="true" path="nome" maxlength="100"/>
        </div>
        <div class="span6">
            <see:formField label="label.stateacronym" isLabelKey="true" isMandatory="true" path="sigla" maxlength="2"/>
        </div>
    </div>
    <see:formButtonGroup putSubmit="true" clearUrl="formEstado.html"/>
</form:form>

<div class="row">
    <display:table id="state" name="listEstados" pagesize="10" requestURI="/admin/formEstado.html" class="table stroked striped hovered small-font-size">
        <c:url var="edit_url" value="formEstado.html"><c:param name="idEstado" value="${state.id}"/></c:url>
        <c:url var="delete_url" value="deleteEstado.html"><c:param name="idEstado" value="${state.id}"/></c:url>
        <c:url var="cidade_url" value="formCidade.html"><c:param name="idEstado" value="${state.id}"/></c:url>
        <display:column media="html" titleKey="label.options" class="centered" headerClass="centered">
            <div class="btn-group small">
                <button type="button" class="btn" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
                <button type="button" class="btn" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-trash"></i></button>
                <button type="button" class="btn" title="Adicionar cidade deste Estado" onclick="location.href = '${cidade_url}';"><i class="icon-plus"></i></button>
            </div>
        </display:column>
        <display:column property="nome"/>
        <display:column property="sigla" style="witdh: 2em;"/>
    </display:table>
</div>
<see:formButtonGroup putSubmit="false" backUrl="menuCadastrosBasicos.html"/>

<script type="text/javascript">
    onload = function() {
        document.getElementById("nome").focus();
    };
</script>
