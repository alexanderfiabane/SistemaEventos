<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<!--<content tag="titlebarContent">
    <javalek:pagetitle label="Cadastro de Dormitórios">
        <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea" isLabelKey="true"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Evento" isLabelKey="false"><javalek:url><c:url value="/admin/formEvento.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Edição" isLabelKey="false"><javalek:url><c:url value="/admin/formEdicao.html?idEvento=${command.edicaoEvento.evento.id}"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Dormitório" isLabelKey="false"><javalek:url><c:url value="/admin/menuDormitorio.html?idEdicao=${command.edicaoEvento.id}"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>-->

<mocca:title title="Cadastro de Dormitórios"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>


<mocca:title title="Cadastrar Dormitório" level="2"/>
<form:form  commandName="command">
    <div class="row">
        <div class="span4">
            <see:formField label="label.dormitoryname" isLabelKey="true" isMandatory="true" path="nome" maxlength="40" inputClass="textfield"/>
        </div>
        <div class="span4">
            <see:formField label="label.vacancies" isLabelKey="true" isMandatory="true" path="vagas" maxlength="3" inputClass="textfield"/>
        </div>
        <div class="span4">
            <see:formField label="label.gender" isLabelKey="true" isMandatory="true" path="sexo" type="select" itens="${sexos}" itemLabel="descricao" selectNullItemLabel="Selecione" inputClass="textfield"/>
        </div>
    </div>
    <div class="row">
        <div class="span6">
            <see:formField type="hidden" label="label.coordinator" isLabelKey="true" isMandatory="true" path="coordenador" readonly="true" inputClass="textfield"/>
        </div>
        <div class="span6">
            <see:formField type="hidden" label="label.vicecoordinator" isLabelKey="true" isMandatory="true" path="viceCoordenador" readonly="true" inputClass="textfield"/>
        </div>
    </div>
    <see:formButtonGroup putSubmit="true" clearUrl="/admin/formEdicao.html?idEdicao=${command.edicaoEvento.id}"/>
</form:form>

<mocca:title title="Dormitórios cadastrados" level="2"/>
<div class="table-wrapper">
    <table class="table bordered rounded hovered striped stroked shadowed">
        <thead>
            <tr>
                <th class="centered" style="width: 8em;"><fmt:message key="label.options"/></th>
                <th><fmt:message key="label.name"/></th>
                <th style="width: 4em;"><fmt:message key="label.vacancies"/></th>
                <th style="width: 6em;"><fmt:message key="label.gender"/></th>
                <th><fmt:message key="label.coordinator"/></th>
                <th><fmt:message key="label.vicecoordinator"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${dormitorios}" var="dormitorio">
                <c:url var="edit_url" value="/admin/formDormitorio.html"><c:param name="idDormitorio" value="${dormitorio.id}"/></c:url>
                <c:url var="delete_url" value="/admin/deleteDormitorio.html"><c:param name="idDormitorio" value="${dormitorio.id}"/></c:url>
                    <tr>
                        <td class="centered">
                            <div class="btn-group">
                                <button type="button" class="btn small" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
                            <button type="button" class="btn small" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
                        </div>
                    </td>
                    <td>${dormitorio.nome}</td>
                    <td class="align-right">${dormitorio.vagas}</td>
                    <td class="centered">${dormitorio.sexo.descricao}</td>
                    <td>${dormitorio.coordenador.pessoa.nome}</td>
                    <td>${dormitorio.viceCoordenador.pessoa.nome}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<see:formButtonGroup putSubmit="false" backUrl="menuDormitorio.html?idEdicao=${command.edicaoEvento.id}">
    <c:url var="aloca_url" value="/admin/alocaDormitorio.html"><c:param name="idEdicao" value="${dormitorio.edicaoEvento.id}"/></c:url>
    <button type="button" class="btn primary" onclick="location.href = '${aloca_url}';">Alocar Confraternistas nos Dormitórios</button>
</see:formButtonGroup>

    <div id="localizar" title="Localizar Confraternista" style="display: none;">
    <div class="row">
        <input type="hidden" id="field"/>
        <label class="label">Nome do Confraternista</label>
        <input type="text" id="nomeLocalizar" class="textfield width-100"/>
    </div>
    <see:notice id="msgLocalizar" closeable="false" type="error">
        Nenhum confraternista foi encontrado!
    </see:notice>
    <see:notice id="msgSelecionar" closeable="false" type="error">
        Nenhum confraternista foi selecionado!
    </see:notice>
    <div class="table-wrapper">
        <table id="resultadosLocalizar" class="table rounded hovered striped bordered stroked" style="display: none;">
            <thead class="header">
                <tr>
                    <th colspan="3" class="centered"><fmt:message key="label.participants"/></th>
                </tr>
                <tr>
                    <th class="centered" style="width: 2em;">#</th>
                    <th class="centered"><fmt:message key="label.name"/></th>
                    <th class="centered" style="width: 5em;"><fmt:message key="label.sex"/></th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/dwr/interface/confraternistaAjaxService.js"/>"></script>
<script type="text/javascript">
                        jQuery(function () {
                            jQuery(document).ready(function () {
                                jQuery('#coordenadorDescr').click(localizar);
                                jQuery('#viceCoordenadorDescr').click(localizar);
                                jQuery('#msgLocalizar').hide();
                                jQuery('#msgSelecionar').hide();
                                if (${command.coordenador != null}) {
                                    jQuery('#coordenadorDescr').val('${command.coordenador.pessoa.nome}');
                                }
                                if (${command.viceCoordenador != null}) {
                                    jQuery('#viceCoordenadorDescr').val('${command.viceCoordenador.pessoa.nome}');
                                }
                            });

                            function localizar() {
                                jQuery('#localizar #field').val(jQuery(this).attr('id').replace('Descr', ''));
                                jQuery('#localizar').dialog({
                                    modal: true,
                                    autoOpen: true,
                                    width: 900,
                                    height: 600,
                                    buttons: [
                                        {
                                            text: "Localizar",
                                            click: function () {
                                                jQuery('#msgLocalizar').hide();
                                                jQuery('#msgSelecionar').hide();
                                                jQuery('#resultadosLocalizar').hide();
                                                jQuery('#resultadosLocalizar tbody').empty();
                                                confraternistaAjaxService.findByNome(jQuery('#nomeLocalizar').val(), function callback(confraternistas) {
                                                    if (confraternistas.length == 0) {
                                                        jQuery('#msgLocalizar').show();
                                                    } else {
                                                        for (var i = 0; i < confraternistas.length; i++) {
                                                            var confraternista = confraternistas[i];
                                                            jQuery('#resultadosLocalizar tbody').append(
                                                                    jQuery('<tr>')
                                                                    .append(jQuery('<td class="centered" style="width: 2em;">').append(jQuery('<input>').attr('type', 'radio').attr('name', 'rdSelect').attr('value', confraternista.id)))
                                                                    .append(jQuery('<td>').attr('id', 'tdNome' + confraternista.id).append(confraternista.pessoa.nome))
                                                                    .append(jQuery('<td>').append(confraternista.pessoa.sexo.descricao)));
                                                        }
                                                        jQuery('#resultadosLocalizar').show();
                                                    }
                                                });
                                            }
                                        },
                                        {
                                            text: "Selecionar",
                                            click: function () {
                                                var fieldId = jQuery('#localizar #field').val();
                                                var selected = jQuery('[name=rdSelect]:checked');
                                                if (selected.size() == 0) {
                                                    jQuery('#msgSelecionar').show();
                                                } else {
                                                    jQuery('#msgSelecionar').hide();
                                                    jQuery('#' + fieldId).val(selected.val());
                                                    jQuery('#' + fieldId + 'Descr').val(jQuery('#tdNome' + selected.val()).text());
                                                    jQuery(this).dialog("close");
                                                }
                                            }
                                        },
                                        {
                                            text: "Fechar",
                                            click: function () {
                                                jQuery(this).dialog("close");
                                                jQuery('#msgLocalizar').hide();
                                                jQuery('#msgSelecionar').hide();
                                            }
                                        }
                                    ]
                                });
                            }
                        });
</script>