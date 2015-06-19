<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>


<mocca:title title="Cadastro de Oficinas"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<mocca:title title="Formulário de cadastro" level="2"/>
<form:form commandName="command">
    <div class="row">
        <div class="span6">
            <see:formField label="label.workshopname" isLabelKey="true" isMandatory="true" path="nome" maxlength="80"/>
        </div>
        <div class="span6">
            <see:formField label="label.vacancies" isLabelKey="true" isMandatory="true" path="vagas" maxlength="4"/>
        </div>
    </div>
    <see:formButtonGroup putSubmit="true" clearUrl="formOficina.html?idEdicao=${command.edicaoEvento.id}"/>
</form:form>

<mocca:title title="Oficinas cadastradas" level="2"/>
<div class="table-wrapper scrollable bordered rounded shadowed">
    <table class="table striped hovered stroked small-font-size">
        <thead class="header">
            <tr>
                <th class="align-center" style="width: 2em;"><fmt:message key="label.options"/></th>
                <th><fmt:message key="label.name"/></th>
                <th class="align-center" style="width: 3em;"><fmt:message key="label.vacancies"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="oficina" items="${oficinas}">
                <tr>
                    <td>
                        <c:url var="edit_url" value="/admin/formOficina.html"><c:param name="idOficina" value="${oficina.id}"/></c:url>
                        <c:url var="delete_url" value="/admin/deleteOficina.html"><c:param name="idOficina" value="${oficina.id}"/></c:url>
                        <div class="btn-group small">
                            <button  type="button" class="btn" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
                            <button  type="button" class="btn" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-trash"></i></button>
                        </div>
                    </td>
                    <td>${oficina.nome}</td>
                    <td class="align-right">${oficina.vagas}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<see:formButtonGroup putSubmit="false" backUrl="formEdicao.html?idEvento=${command.edicaoEvento.evento.id}"/>
<script>
    $(document).ready(function(){
        $("#vagas").mask('9999');
    });
</script>