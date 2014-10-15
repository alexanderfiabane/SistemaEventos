<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<%@ include file="/WEB-INF/includes/dwr.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Cadastro de Dormitórios">
        <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea" isLabelKey="true"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastrar Evento" isLabelKey="false"><msf:url><c:url value="/admin/formEvento.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastrar Edição" isLabelKey="false"><msf:url><c:url value="/admin/formEdicao.html?idEvento=${command.edicaoEvento.evento.id}"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Dormitório" isLabelKey="false"><msf:url><c:url value="/admin/menuDormitorio.html?idEdicao=${command.edicaoEvento.id}"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<bs:notice type="success" visible="${!empty message}" closeable="true">${message}</bs:notice>
<msf:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form  commandName="command">
    <div class="row-fluid">
        <div class="span3">
            <bs:formField label="label.dormitoryname" isLabelKey="true" isMandatory="true" path="nome" maxlength="40"/>
        </div>
        <div class="span3">
            <bs:formField label="label.vacancies" isLabelKey="true" isMandatory="true" path="vagas" maxlength="3"/>
        </div>
        <div class="span3">
            <bs:formField label="label.gender" isLabelKey="true" isMandatory="true" path="sexo" type="select" itens="${sexos}" itemLabel="descricao" selectNullItemLabel="Selecione"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span3">
            <bs:formField type="hidden" label="label.coordinator" isLabelKey="true" isMandatory="true" path="coordenador" readonly="true"/>
        </div>
        <div class="span3">
            <bs:formField type="hidden" label="label.vicecoordinator" isLabelKey="true" isMandatory="true" path="viceCoordenador" readonly="true"/>
        </div>
    </div>
    <bs:formButtonGroup formUrl="/admin/formEdicao.html?idEdicao=${command.edicaoEvento.id}"/>
</form:form>

<div class="row-fluid">
    <display:table id="dormitorio" name="dormitorios" pagesize="10" requestURI="/admin/formDormitorio.html" class="table table-striped table-condensed">
        <c:url var="edit_url" value="/admin/formDormitorio.html"><c:param name="idDormitorio" value="${dormitorio.id}"/></c:url>
        <c:url var="delete_url" value="/admin/deleteDormitorio.html"><c:param name="idDormitorio" value="${dormitorio.id}"/></c:url>
        <display:column media="html" titleKey="label.options" class="twoOption centered" headerClass="centered">
            <button  type="button" class="btn btn-mini" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
            <button  type="button" class="btn btn-mini" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
        </display:column>
        <display:column titleKey="label.name" property="nome" class="centered" headerClass="centered"/>
        <display:column titleKey="label.vacancies" property="vagas" class="centered" headerClass="centered"/>
        <display:column titleKey="label.gender" property="sexo.descricao" class="centered" headerClass="centered"/>
        <display:column titleKey="label.coordinator" property="coordenador.pessoa.nome" class="centered" headerClass="centered"/>
        <display:column titleKey="label.vicecoordinator" property="viceCoordenador.pessoa.nome" class="centered" headerClass="centered"/>
    </display:table>            
</div>

<div class="form-actions">
    <c:url var="aloca_url" value="/admin/alocaDormitorio.html"><c:param name="idEdicao" value="${dormitorio.edicaoEvento.id}"/></c:url>
    <button type="button" class="btn btn-primary" onclick="location.href = '${aloca_url}';">Alocar Confraternistas nos Dormitórios</button>
</div>

<div id="localizar" title="Localizar Confraternista" style="display: none;">
    <input type="hidden" id="field"/>
    <br/>
    <msf:label label="Nome do Confraternista" for="nomeLocalizar"/>
    <input type="text" id="nomeLocalizar"/>
    <br/><br/>
    <bs:notice id="msgLocalizar" closeable="true" type="warning">
        Nenhum confraternista foi encontrado!
    </bs:notice>
    <bs:notice id="msgSelecionar" closeable="true" type="warning">
        Nenhum confraternista foi selecionado!
    </bs:notice>
    <table id="resultadosLocalizar" class="table table-striped table-condensed">
        <thead>
            <tr>
                <td class="tableTitle" colspan="3"><msf:message key="label.participants"/></td>
            </tr>
            <tr>
                <td></td>
                <td><msf:message key="label.name"/></td>
                <td><msf:message key="label.sex"/></td>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<script type="text/javascript" src="<c:url value="/dwr/interface/confraternistaAjaxService.js"/>"></script>
<script type="text/javascript">
    jQuery(function() {
        jQuery(document).ready(function() {
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
                        click: function() {
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
                                                .append(jQuery('<td>').append(jQuery('<input>').attr('type', 'radio').attr('name', 'rdSelect').attr('value', confraternista.id)))
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
                        click: function() {
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
                        click: function() {
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