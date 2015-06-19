<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Cadastrar Cidade"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <form:hidden path="id"/>
    <div class="row">
        <div class="span4">
            <see:formField label="label.cityname" isLabelKey="true" isMandatory="true" path="nome" maxlength="100"/>
        </div>
        <div class="span4">
            <see:formField label="label.state" isLabelKey="true" isMandatory="true" path="estado" type="select" itens="${estados}" itemLabel="nome" itemValue="id" selectNullItemLabel="-- Selecione um Estado --"/>
        </div>
        <div class="span4">
            <br/>
            <see:formField label="Capital?" path="capital" type="check"/>
        </div>
    </div>
    <see:formButtonGroup putSubmit="true" clearUrl="formCidade.html"/>
</form:form>

<div class="row">
    <div class="table-wrapper scrollable bordered rounded shadowed">
        <table class="table hovered striped stroked narrow small-font-size">
            <thead class="header">
                <tr>
                    <th class="centered" style="width: 3em;"><fmt:message key="label.options"/></th>
                    <th><fmt:message key="label.cityname"/></th>
                    <th style="width: 4em;"><fmt:message key="label.state"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${cidades}" var="cidade">
                <tr>
                    <td>
                        <c:url var="edit_url" value="formCidade.html"><c:param name="idCidade" value="${cidade.id}"/></c:url>
                        <c:url var="delete_url" value="deleteCidade.html"><c:param name="idCidade" value="${cidade.id}"/></c:url>
                        <div class="btn-group small">
                            <button type="button" class="btn" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
                            <button type="button" class="btn" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-trash"></i></button>
                        </div>
                    </td>
                    <td>${cidade.nome} <c:if test="${cidade.capital}">(capital)</c:if></td>
                    <td>${cidade.estado.nome}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<see:formButtonGroup putSubmit="false" backUrl="formEstado.html"/>

<script type="text/javascript">
    onload = function() {
        document.getElementById("nome").focus();
    }
</script>
