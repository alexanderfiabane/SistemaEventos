<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<%@ include file="/WEB-INF/includes/dwr.jspf" %>

<script type="text/javascript" src="<c:url value="/js/form.js"/>"></script>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Detalhes da Inscrição" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconFormInsc.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Gerenciamento de Inscrições" isLabelKey="false"><msf:url><c:url value="/admin/inscricao/list.html"><c:param name="idEdicao" value="${command.edicaoEvento.id}"/></c:url></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<bs:notice type="success" visible="${!empty message}" closeable="true">${message}</bs:notice>
<bs:notice type="error" visible="${!empty erro}" closeable="true">${erro}</bs:notice>

<form:form commandName="command">
    <fieldset>
        <legend><msf:message key="label.eventdetails"/></legend>
        <div class="row-fluid">
            <div class="span6">
                <msf:label label="label.subscriptiontype" isLabelKey="true" isMandatory="true" breakAfter="false" cssClass="control-label"/>
                <c:forEach var="item" items="${tiposConfraternista}">
                    <msf:label label="${item.descricao}" colonAfter="false" cssClass="radio inline" breakAfter="false">
                        <form:radiobutton path="confraternista.tipo" value="${item.name}"/>
                    </msf:label>
                </c:forEach>
            </div>
        </div>
    </fieldset>

    <fieldset>
        <legend><msf:message key="label.personaldetails"/></legend>
        <div class="row-fluid">
            <div class="span3">
                <bs:formField label="label.fullname" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.nome" maxlength="100"/>
            </div>
            <div class="span3">
                <bs:formField label="label.badge" isLabelKey="true" isMandatory="true" path="confraternista.nomeCracha" maxlength="100"/>
            </div>
            <div class="span3">
                <bs:formField label="label.birthday" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.dataNascimento" maxlength="10"/>
            </div>
            <div class="span3">
                <bs:formField label="label.gender" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.sexo" type="select" itens="${sexos}" itemLabel="descricao" selectNullItemLabel="-- Selecione --"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span3">
                <bs:formField label="label.cpf" isLabelKey="true" isMandatory="false" path="confraternista.pessoa.documentos.cpf" maxlength="14"/>
            </div>
            <div class="span3">
                <bs:formField label="label.id" isLabelKey="true" isMandatory="false" path="confraternista.pessoa.documentos.rg" maxlength="10"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span3">
                <bs:formField label="label.street" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.endereco.logradouro" maxlength="100"/>
            </div>
            <div class="span3">
                <bs:formField label="label.number" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.endereco.numero" maxlength="6"/>
            </div>
            <div class="span3">
                <bs:formField label="label.complement" isLabelKey="true" isMandatory="false" path="confraternista.pessoa.endereco.complemento" maxlength="60"/>
            </div>
            <div class="span3">
                <bs:formField label="label.district" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.endereco.bairro" maxlength="60"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span3">
                <msf:label label="label.state" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
                <select id="estado">
                    <option value="">-- Selecione um estado --</option>
                    <c:forEach var="estado" items="${estados}">
                        <option value="${estado.id}" <c:if test="${command.confraternista.pessoa.endereco.cidade.estado.id == estado.id}">selected="selected"</c:if>>${estado.nome}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="span3">
                <bs:formField label="label.city" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.endereco.cidade" id="cidade" type="select" itemValue="id" itemLabel="nome" selectNullItemLabel="-- Selecione primeiro um estado --"/>
            </div>
            <div class="span3">
                <bs:formField label="label.zipcode" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.endereco.cep" maxlength="9"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span3">
                <bs:formField label="label.email" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.endereco.email" maxlength="100"/>
            </div>
            <div class="span3">
                <bs:formField label="label.phone" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.endereco.telefone" maxlength="16"/>
            </div>
            <div class="span3">
                <bs:formField label="label.phoneatevent" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.endereco.telefoneEvento" maxlength="16"/>
            </div>
        </div>
    </fieldset>

    <fieldset>
        <legend><msf:message key="label.healthdetails"/></legend>
        <div class="row-fluid">
            <div class="span3">
                <msf:label label="Faz uso rotineiro de medicação?" isMandatory="false" isLabelKey="false" breakAfter="false" cssClass="control-label"/>
                <msf:label label="Não" for="medicacaoNao" breakAfter="false" cssClass="radio inline"><input type="radio" id="medicacaoNao" name="medicacao" value="false"/></msf:label>
                <msf:label label="Sim" for="medicacaoSim" breakAfter="false" cssClass="radio inline"><input type="radio" id="medicacaoSim" name="medicacao" value="true"/></msf:label>
                </div>
                <div class="span3">
                <bs:formField label="Qual(is)" isLabelKey="false" isMandatory="false" id="medicacao" path="confraternista.pessoa.informacoesSaude.medicacao" maxlength="255"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span3">
                <msf:label label="Tem algum tipo de convênio?" isMandatory="false" isLabelKey="false" breakAfter="false" cssClass="control-label"/>
                <msf:label label="Não" for="convenioNao" breakAfter="false" cssClass="radio inline"><input type="radio" id="convenioNao" name="convenio" value="false"/></msf:label>
                <msf:label label="Sim" for="convenioSim" breakAfter="false" cssClass="radio inline"><input type="radio" id="convenioSim" name="convenio" value="true"/></msf:label>
                </div>
                <div class="span3">
                <bs:formField label="Qual(is)" isLabelKey="false" isMandatory="false" id="convenio" path="confraternista.pessoa.informacoesSaude.convenio" maxlength="255"/>
            </div>
            <div class="span3">
                <bs:formField label="Telefone" isLabelKey="false" isMandatory="false" id="foneConvenio" path="confraternista.pessoa.informacoesSaude.convenioTelefone" maxlength="255"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span3">
                <msf:label label="Possui algum tipo de alergia?" isMandatory="false" isLabelKey="false" breakAfter="false" cssClass="control-label"/>
                <msf:label label="Não" for="alergiaNao" breakAfter="false" cssClass="radio inline"><input type="radio" id="alergiaNao" name="alergia" value="false"/></msf:label>
                <msf:label label="Sim" for="alergiaSim" breakAfter="false" cssClass="radio inline"><input type="radio" id="alergiaSim" name="alergia" value="true"/></msf:label>
                </div>
                <div class="span3">
                <bs:formField label="Qual(is)" isLabelKey="false" isMandatory="false" id="alergia" path="confraternista.pessoa.informacoesSaude.alergia" maxlength="255"/>
            </div>
        </div>
    </fieldset>

    <fieldset>
        <legend><msf:message key="label.housedetails"/></legend>
        <div class="row-fluid">
            <div class="span3">
                <bs:formField label="Nome" isLabelKey="false" isMandatory="true" path="confraternista.casaEspirita.nome" maxlength="100"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span3">
                <bs:formField label="label.street" isLabelKey="true" isMandatory="true" path="confraternista.casaEspirita.endereco.logradouro" maxlength="100"/>
            </div>
            <div class="span3">
                <bs:formField label="label.number" isLabelKey="true" isMandatory="true" path="confraternista.casaEspirita.endereco.numero" maxlength="6"/>
            </div>
            <div class="span3">
                <bs:formField label="label.complement" isLabelKey="true" isMandatory="false" path="confraternista.casaEspirita.endereco.complemento" maxlength="60"/>
            </div>
            <div class="span3">
                <bs:formField label="label.district" isLabelKey="true" isMandatory="true" path="confraternista.casaEspirita.endereco.bairro" maxlength="60"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span3">
                <msf:label label="label.state" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
                <select id="estadoCasa">
                    <option value="">-- Selecione um estado --</option>
                    <c:forEach var="estado" items="${estados}">
                        <option value="${estado.id}" <c:if test="${command.confraternista.casaEspirita.endereco.cidade.estado.id == estado.id}">selected="selected"</c:if>>${estado.nome}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="span3">
                <bs:formField label="label.city" isLabelKey="true" isMandatory="true" path="confraternista.casaEspirita.endereco.cidade" id="cidadeCasa" type="select" itemValue="id" itemLabel="nome" selectNullItemLabel="-- Selecione primeiro um estado --"/>
            </div>
            <div class="span3">
                <bs:formField label="label.zipcode" isLabelKey="true" isMandatory="true" path="confraternista.casaEspirita.endereco.cep" maxlength="9"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <bs:formField label="label.activityatie" isLabelKey="true" isMandatory="true" path="confraternista.atividadeCasaEspirita" type="textarea" maxlength="500" inputClass="span12"/>
            </div>
        </div>
    </fieldset>

    <c:if test="${not empty command.edicaoEvento.oficinas}">
        <fieldset>
            <legend><msf:message key="label.workshopdetails"/></legend>
            <div class="row-fluid">
                <div class="span3">
                    <msf:label label="label.workshopname" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
                    <c:forEach var="oficina" items="${command.edicaoEvento.oficinas}">
                        <msf:label label="${oficina.nome} (${oficina.saldoVagas} vagas)" breakAfter="false" cssClass="radio">
                            <form:radiobutton path="confraternista.oficina" value="${oficina.id}" disabled="${oficina.saldoVagas <= 0}"/>
                        </msf:label>
                    </c:forEach>
                    <form:errors path="confraternista.oficina" cssClass="fieldError"/>
                </div>
            </div>
        </fieldset>
    </c:if>
    <c:if test="${not empty command.edicaoEvento.tiposCamiseta}">
        <fieldset>
            <legend><msf:message key="label.shirtdetails"/></legend>
            <div class="row-fluid">
                <div class="span3">
                    <msf:label label="label.shirttype" isMandatory="false" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
                    <select id="tipoCamiseta">
                        <option value="">-- Tipo --</option>
                        <c:forEach var="tipo" items="${command.edicaoEvento.tiposCamiseta}">
                            <option value="${tipo.id}">${tipo.descricao}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="span3">
                    <msf:label label="label.shirtcolor" isMandatory="false" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
                    <select id="corCamiseta">
                        <option value="">-- Cor --</option>
                        <c:forEach var="cor" items="${command.edicaoEvento.coresCamiseta}">
                            <option value="${cor.id}">${cor.descricao}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="span3">
                    <msf:label label="label.shirtsize" isMandatory="false" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
                    <select id="tamanhoCamiseta">
                        <option value="">-- Tamanho --</option>
                        <c:forEach var="tamanho" items="${command.edicaoEvento.tamanhosCamiseta}">
                            <option value="${tamanho.id}">${tamanho.descricao}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="span2">
                    <msf:label label="label.shirtquant" isMandatory="false" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
                    <input type="text" id="quantCamiseta" maxlength="2" class="input-mini"/>
                </div>
                <div class="span1">
                    <msf:label label="label.options" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
                    <div>
                        <button type="button" class="btn btn-mini" title="Adicionar" id="addCamiseta"><i class="icon-plus"></i></button>
                        <button type="button" class="btn btn-mini" title="Remover" id="removeCamiseta"><i class="icon-minus"></i></button>
                    </div>
                </div>
            </div>
            <div class="row-fluid">
                <table id="camisetas" class="table table-striped table-condensed">
                    <thead>
                        <tr><th class="centered" colspan="5"><msf:message key="label.shirts"/></th></tr>
                        <tr>
                            <th><msf:message key="label.options"/></th>
                            <th><msf:message key="label.shirttype"/></th>
                            <th><msf:message key="label.shirtcolor"/></th>
                            <th><msf:message key="label.shirtsize"/></th>
                            <th><msf:message key="label.shirtquant"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="camiseta" items="${command.confraternista.camisetas}" varStatus="status">
                            <tr id="camiseta_${status.index}">
                                <td class="centered">
                                    <button  type="button" class="btn btn-mini" title="Remover" id="removeCamiseta" onclick="jQuery(this).parent().parent().remove();">Remover Camiseta</button>
                                </td>
                                <td>
                                    <input type="hidden" name="confraternista.camisetas[${status.index}].tipoCamiseta" value="${camiseta.tipoCamiseta.id}"/>
                                    ${camiseta.tipoCamiseta.descricao}
                                </td>
                                <td>
                                    <input type="hidden" name="confraternista.camisetas[${status.index}].corCamiseta" value="${camiseta.corCamiseta.id}"/>
                                    ${camiseta.corCamiseta.descricao}
                                </td>
                                <td>
                                    <input type="hidden" name="confraternista.camisetas[${status.index}].tamanhoCamiseta" value="${camiseta.tamanhoCamiseta.id}"/>
                                    ${camiseta.tamanhoCamiseta.descricao}
                                </td>
                                <td>
                                    <input type="hidden" name="confraternista.camisetas[${status.index}].quantidadeCamiseta" value="${camiseta.quantidadeCamiseta}"/>
                                    ${camiseta.quantidadeCamiseta}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <span class="span" style="font-size:8pt;">
                    * Caso deseje adquirir camiseta escolha a combinação de tipo, tamanho e cor e então clique em ' Adicionar camiseta '.<br/>
                    * Caso não deseje adquirir a camiseta agora, basta clicar em 'Apagar' ou não alterar os campos referentes à camiseta!<br/>
                    * <b>Valor:</b> Camiseta - <fmt:formatNumber value="${command.edicaoEvento.valorCamiseta}" type="currency" currencySymbol="R$" minFractionDigits="2"/>.
                </span>
            </div>
        </fieldset>
    </c:if>
    <bs:formButtonGroup formUrl="/admin/inscricao/form.html?idInscricao=${command.id}"/>
</form:form>

<script type="text/javascript" src="<c:url value="/dwr/interface/enderecoAjaxService.js"/>"></script>
<script type="text/javascript">
    jQuery(function() {
        var camisetaIndex = ${fn:length(command.confraternista.camisetas)};
        jQuery(document).ready(function() {
            $('.accordion').accordion({
                //                    collapsible: true,
                heightStyle: 'content',
                icons: {
                    header: 'ui-icon-circle-arrow-e',
                    activeHeader: 'ui-icon-circle-arrow-s'
                }
            });

            jQuery('[name="confraternista.pessoa.documentos.cpf"]').mask('999.999.999-99');
            jQuery('[name="confraternista.pessoa.dataNascimento"]').mask('99/99/9999');
            jQuery('[name="confraternista.pessoa.endereco.cep"]').mask('99999-999');
            jQuery('[name="confraternista.casaEspirita.endereco.cep"]').mask('99999-999');
            jQuery('[name="confraternista.pessoa.endereco.telefone"]').mask('(99)9999-9999');
            jQuery('[name="confraternista.pessoa.endereco.telefoneEvento"]').mask('(99)9999-9999');
            jQuery('[name="confraternista.pessoa.informacoesSaude.convenioTelefone"]').mask('(99)9999-9999');

            jQuery('#addCamiseta').click(addCamiseta);
            jQuery('.delete').click(removeCamiseta);
            jQuery('#removeCamiseta').hide();

            jQuery('[name="confraternista.oficina"]').each(function() {
                jQuery(this).attr('checked', jQuery(this).val() == '${command.confraternista.oficina.id}');
            });

            jQuery('[name=medicacao]').change(function() {
                if (jQuery(this).val() == 'true') {
                    jQuery('#medicacao').attr('readonly', false);
                } else {
                    jQuery('#medicacao').attr('readonly', true).val('');
                }
            });
            jQuery('[name=convenio]').change(function() {
                if (jQuery(this).val() == 'true') {
                    jQuery('#convenio').attr('readonly', false);
                    jQuery('#foneConvenio').attr('readonly', false);
                } else {
                    jQuery('#convenio').attr('readonly', true).val('');
                    jQuery('#foneConvenio').attr('readonly', true).val('');
                }
            });
            jQuery('[name=alergia]').change(function() {
                if (jQuery(this).val() == 'true') {
                    jQuery('#alergia').attr('readonly', false);
                } else {
                    jQuery('#alergia').attr('readonly', true).val('');
                }
            });

            if (jQuery('#medicacao').val() == '') {
                jQuery('#medicacaoNao').click();
            } else {
                jQuery('#medicacaoSim').click();
            }
            if (jQuery('#convenio').val() == '') {
                jQuery('#convenioNao').click();
            } else {
                jQuery('#convenioSim').click();
            }
            if (jQuery('#alergia').val() == '') {
                jQuery('#alergiaNao').click();
            } else {
                jQuery('#alergiaSim').click();
            }


            jQuery('#estado').change(function() {
                loadCidades(jQuery(this), jQuery('#cidade'));
            });
            jQuery('#estadoCasa').change(function() {
                loadCidades(jQuery(this), jQuery('#cidadeCasa'));
            });

            loadCidades(jQuery('#estado'), jQuery('#cidade'), '${command.confraternista.pessoa.endereco.cidade.id}');
            loadCidades(jQuery('#estadoCasa'), jQuery('#cidadeCasa'), '${command.confraternista.casaEspirita.endereco.cidade.id}');
        });

        function loadCidades(inputEstado, inputCidade, idCidadeAtual) {
            var estadoSelecionado = inputEstado.val();
            inputCidade.empty();
            if (estadoSelecionado == '') {
                inputCidade.append(jQuery('<option>').append('-- Selecione primeiro um estado --'));
            } else {
                enderecoAjaxService.getCidades(estadoSelecionado, function callback(cidades) {
                    inputCidade.append(jQuery('<option>').append('-- Selecione uma cidade --'));
                    jQuery.each(cidades, function(index, value) {
                        inputCidade.append(jQuery('<option>').val(value.id).append(value.nome));
                    });
                    if (idCidadeAtual) {
                        inputCidade.val(idCidadeAtual);
                    }
                });
            }
        }
        
        function removeCamiseta() {
            jQuery(this).parent().parent().find('td input').val('');
            jQuery(this).parent().parent().hide();
        }

        function addCamiseta() {
            var tipo = jQuery('#tipoCamiseta').val();
            var cor = jQuery('#corCamiseta').val();
            var tam = jQuery('#tamanhoCamiseta').val();
            var quant = jQuery('#quantCamiseta').val();

            var tipoDescr = jQuery('#tipoCamiseta :selected').html();
            var corDescr = jQuery('#corCamiseta :selected').text();
            var tamDescr = jQuery('#tamanhoCamiseta :selected').text();
            var quantDescr = jQuery('#quantCamiseta').val();

            if (tipo == '') {
                alert('Escolha um tipo de camiseta!');
                return;
            }
            if (cor == '') {
                alert('Escolha uma cor de camiseta!');
                return;
            }
            if (tam == '') {
                alert('Escolha um tamanho de camiseta!');
                return;
            }
            if (quant == '') {
                alert('Defina a quantidade de camisetas!');
                return;
            }

            var index = camisetaIndex++;

            jQuery('#camisetas tbody').append(jQuery('<tr>').attr('id', 'camiseta_' + index)
                    .append(jQuery('<td>').append(createButton()))
                    .append(jQuery('<td>').append(createInput('t', tipo, index)).append(tipoDescr))
                    .append(jQuery('<td>').append(createInput('c', cor, index)).append(corDescr))
                    .append(jQuery('<td>').append(createInput('s', tam, index)).append(tamDescr))
                    .append(jQuery('<td>').append(createInput('q', quant, index)).append(quantDescr))
                    );

            jQuery('#tipoCamiseta').val('');
            jQuery('#corCamiseta').val('');
            jQuery('#tamanhoCamiseta').val('');
            jQuery('#quantCamiseta').val('');
        }

        function createInput(key, value, index) {
            return jQuery('<input>').attr('type', 'hidden').attr('name', getName(key, index)).val(value);
        }

        function createButton() {
            return jQuery("#removeCamiseta").clone(true).show();
        }

        function getName(key, index) {
            var path = 'confraternista.camisetas';
            var field;
            switch (key) {
                case 't':
                    field = 'tipoCamiseta';
                    break;
                case 'c':
                    field = 'corCamiseta';
                    break;
                case 's':
                    field = 'tamanhoCamiseta';
                    break;
                case 'q':
                    field = 'quantidadeCamiseta';
                    break;
            }
            return path + '[' + index + '].' + field;
        }
    });
</script>
