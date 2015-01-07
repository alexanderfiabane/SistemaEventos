<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<%@ include file="/WEB-INF/includes/dwr.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="label.page.subscription" isLabelKey="true" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconFormInsc.png"/></msf:icon>
        <msf:breadcrumb label="label.page.mainMenu"><msf:url><c:url value="/index.html"/></msf:url></msf:breadcrumb>
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
                    <c:choose>
                        <c:when test="${command.edicaoEvento.tipo == 'FAIXA_ETARIA'}">
                            <%--<c:if test="${(item == 'EVANGELIZADOR') || (item == 'CONFRATERNISTA')}">--%>
                            <c:if test="${not (item == 'OFICINEIRO')}">
                                <msf:label label="${item.descricao}" colonAfter="false" cssClass="radio" breakAfter="false">
                                    <form:radiobutton path="confraternista.tipo" value="${item.name}"/>
                                </msf:label>
                            </c:if>                                                    
                        </c:when>
                        <c:when test="${command.edicaoEvento.tipo == 'OFICINA'}">                            
                            <c:if test="${(not (item == 'FACILITADOR')) and (not (item == 'EVANGELIZADOR'))}">
                                <msf:label label="${item.descricao}" colonAfter="false" cssClass="radio" breakAfter="false">
                                    <form:radiobutton path="confraternista.tipo" value="${item.name}"/>
                                </msf:label>
                            </c:if>                                                    
                        </c:when>
                        <c:otherwise>
                            <c:if test="${(not (item == 'FACILITADOR')) and (not (item == 'EVANGELIZADOR')) and (not (item == 'OFICINEIRO'))}">
                                <msf:label label="${item.descricao}" colonAfter="false" cssClass="radio" breakAfter="false">
                                    <form:radiobutton path="confraternista.tipo" value="${item.name}"/>
                                </msf:label>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <div class="control">
                    <form:errors path="confraternista.tipo" cssClass="label label-important"/>
                </div>
            </div>
        </div>
    </fieldset>
    <c:if test="${(not empty command.edicaoEvento.gruposIdade) && (command.edicaoEvento.tipo == 'FAIXA_ETARIA')}">
        <div id="grupoFacilitador" style="display: none;">    
            <fieldset>
                <legend><msf:message key="label.eventcoordgroupdetails"/></legend>
                <div class="row-fluid">
                    <div class="span3">
                        <msf:label label="label.groupname" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
                        <c:forEach var="grupo" items="${command.edicaoEvento.gruposIdade}">
                            <msf:label label="${grupo.nome}" colonAfter="false" breakAfter="false" cssClass="radio">
                                <form:radiobutton path="confraternista.grupoIdade" value="${grupo.id}"/>
                            </msf:label>
                        </c:forEach>
                        <form:errors path="confraternista.grupoIdade" cssClass="fieldError"/>
                    </div>
                </div>
            </fieldset>
        </div>        
    </c:if>
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
                <bs:formField id="dataNascimento" label="label.birthday" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.dataNascimento" maxlength="10"/>
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
            <div class="span3">
                <bs:formField label="label.birthcertificate" isLabelKey="true" isMandatory="false" path="confraternista.pessoa.documentos.certidaoNascimento" maxlength="20"/>
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
            <div class="row-fluid">
                <div class="span3">
                    <msf:label label="label.state" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
                    <select id="estado">
                        <option value="">Selecione um estado</option>
                        <c:forEach var="estado" items="${estados}">
                            <option value="${estado.id}" <c:if test="${command.confraternista.pessoa.endereco.cidade.estado.id == estado.id}">selected="selected"</c:if>>${estado.nome}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="span3">
                    <bs:formField label="label.city" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.endereco.cidade" id="cidade" type="select" itemValue="id" itemLabel="nome" selectNullItemLabel="Selecione primeiro um estado"/>
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
            <div id="responsavel" class="row-fluid" style="display:none;">
                <div class="span6">
                    <bs:formField label="label.responsible" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.responsavel.nome" maxlength="100" inputClass="span12"/>                
                </div>            
                <div class="span3">
                    <bs:formField label="label.phone" isLabelKey="true" isMandatory="true" path="confraternista.pessoa.responsavel.telefone" maxlength="16"/>                
                </div>            
            </div>
    </fieldset>

    <fieldset>
        <legend><msf:message key="label.healthfooddetails"/></legend>
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
        <div class="row-fluid">
            <div class="span3">
                <msf:label label="Possui algum tipo de restrição alimentar?" isMandatory="false" isLabelKey="false" breakAfter="false" cssClass="control-label"/>
                <msf:label label="Não" for="dietaNao" breakAfter="false" cssClass="radio inline"><input type="radio" id="dietaNao" name="dieta" value="false"/></msf:label>
                <msf:label label="Sim" for="dietaSim" breakAfter="false" cssClass="radio inline"><input type="radio" id="dietaSim" name="dieta" value="true"/></msf:label>
                </div>
                <div class="span3">
                <bs:formField label="Qual(is)" isLabelKey="false" isMandatory="false" id="dieta" path="confraternista.pessoa.informacoesSaude.dieta" maxlength="255"/>
            </div>
        </div>
    </fieldset>

    <fieldset>
        <legend><msf:message key="label.housedetails"/></legend>
        <div class="row-fluid">
            <div class="span12">
                <bs:formField label="Nome" isLabelKey="false" isMandatory="true" path="confraternista.casaEspirita.nome" maxlength="100" inputClass="span12"/>
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
                    <option value="">Selecione um estado</option>
                    <c:forEach var="estado" items="${estados}">
                        <option value="${estado.id}" <c:if test="${command.confraternista.casaEspirita.endereco.cidade.estado.id == estado.id}">selected="selected"</c:if>>${estado.nome}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="span3">
                <bs:formField label="label.city" isLabelKey="true" isMandatory="true" path="confraternista.casaEspirita.endereco.cidade" id="cidadeCasa" type="select" itemValue="id" itemLabel="nome" selectNullItemLabel="Selecione primeiro um estado"/>
            </div>
            <div class="span3">
                <bs:formField label="label.zipcode" isLabelKey="true" isMandatory="true" path="confraternista.casaEspirita.endereco.cep" maxlength="9"/>
            </div>
        </div>
        <!--TODO: Aqui fazer verificação por tipo *avaliar para os outros tipos de evento-->
        <%--<c:if test="${command.confraternista.tipo == 'CONFRATERNISTA'}">--%>                    
        <div id="evangelizadorResponsavel" class="row-fluid" style="display:none;">
            <div class="span6">
                <bs:formField label="label.responsibleevent" isLabelKey="true" isMandatory="true" path="confraternista.responsavelEvento.nome" maxlength="100" inputClass="span12"/>                
            </div>            
            <div class="span3">
                <bs:formField label="label.phone" isLabelKey="true" isMandatory="true" path="confraternista.responsavelEvento.telefone" maxlength="16"/>                
            </div> 
        </div>    
        <%--</c:if>--%>    
        <div class="row-fluid">
            <div class="span12">
                <bs:formField label="label.activityatie" isLabelKey="true" isMandatory="true" path="confraternista.atividadeCasaEspirita" type="textarea" maxlength="500" inputClass="span12"/>
            </div>
        </div>
    </fieldset>

    <c:if test="${(not empty command.edicaoEvento.oficinas) && (command.edicaoEvento.tipo == 'OFICINA')}">
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
                        <button type="button" class="btn btn" title="Adicionar" id="addCamiseta"><i class="icon-plus"></i></button>
                        <button type="button" class="btn btn-mini delete" title="Remover" id="removeCamiseta">Remover camiseta</button>
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
                                <td>                                    
                                    <button  type="button" class="btn btn-mini delete" title="Remover" id="removeCamiseta">Remover camiseta</button>
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
                    * Caso deseje adquirir camiseta escolha a combinação de tipo, tamanho e cor e então clique em ' + '.<br/>
                    * Caso não deseje adquirir a camiseta agora, basta clicar em ' Remover camiseta ' ou não alterar os campos referentes à camiseta!<br/>
                    * <b>Valor:</b> Camiseta - <fmt:formatNumber value="${command.edicaoEvento.valorCamiseta}" type="currency" currencySymbol="R$" minFractionDigits="2"/>.
                </span>
            </div>
        </fieldset>
    </c:if>
    <bs:formButtonGroup formUrl="/admin/inscricao/form.html?idInscricao=${command.id}"/>
</form:form>

<script type="text/javascript" src="<c:url value="/dwr/interface/enderecoAjaxService.js"/>"></script>
<script type="text/javascript">
    var camisetaIndex = ${fn:length(command.confraternista.camisetas)};
    function calculaMaiorIdade(nasc) {
        var hoje = new Date(), idade;
        idade = (
                (hoje.getMonth() > nasc.getMonth())
                ||
                (hoje.getMonth() == nasc.getMonth() && hoje.getDate() >= nasc.getDate())
                ) ? hoje.getFullYear() - nasc.getFullYear() : hoje.getFullYear() - nasc.getFullYear() - 1;
        if (idade > 18) {
            return true;
        }
    }

    function loadCidades(inputEstado, inputCidade, idCidadeAtual) {
        var estadoSelecionado = inputEstado.val();
        inputCidade.empty();
        if (estadoSelecionado == '') {
            inputCidade.append($('<option>').append('-- Selecione primeiro um estado --'));
        } else {
            enderecoAjaxService.getCidades(estadoSelecionado, function callback(cidades) {
                inputCidade.append($('<option>').append('-- Selecione uma cidade --'));
                $.each(cidades, function (index, value) {
                    inputCidade.append($('<option>').val(value.id).append(value.nome));
                });
                if (idCidadeAtual) {
                    inputCidade.val(idCidadeAtual);
                }
            });
        }
    }

    function removeCamiseta() {
        $(this).parent().parent().find('td input').val('');
        $(this).parent().parent().hide();
    }

    function addCamiseta() {
        var tipo = $('#tipoCamiseta').val();
        var cor = $('#corCamiseta').val();
        var tam = $('#tamanhoCamiseta').val();
        var quant = $('#quantCamiseta').val();

        var tipoDescr = $('#tipoCamiseta :selected').html();
        var corDescr = $('#corCamiseta :selected').text();
        var tamDescr = $('#tamanhoCamiseta :selected').text();
        var quantDescr = $('#quantCamiseta').val();

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

        $('#camisetas tbody').append($('<tr>').attr('id', 'camiseta_' + index)
                .append($('<td>').append(createButton()))
                .append($('<td>').append(createInput('t', tipo, index)).append(tipoDescr))
                .append($('<td>').append(createInput('c', cor, index)).append(corDescr))
                .append($('<td>').append(createInput('s', tam, index)).append(tamDescr))
                .append($('<td>').append(createInput('q', quant, index)).append(quantDescr))
                );

        $('#tipoCamiseta').val('');
        $('#corCamiseta').val('');
        $('#tamanhoCamiseta').val('');
        $('#quantCamiseta').val('');
    }

    function createInput(key, value, index) {
        return $('<input>').attr('type', 'hidden').attr('name', getName(key, index)).val(value);
    }

    function createButton() {
        return $("#removeCamiseta").clone(true).show();
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

    function parseDate(str) {
        var array = str.split('/');
        for (var i = 0; i < array.length; i++) {
            array[i] = Number(array[i]);
        }
        array[1] = array[1] - 1;
        return new Date(array[2], array[1], array[0]);
    }

    $(document).ready(function () {
        $('.accordion').accordion({
            //                    collapsible: true,
            heightStyle: 'content',
            icons: {
                header: 'ui-icon-circle-arrow-e',
                activeHeader: 'ui-icon-circle-arrow-s'
            }
        });

        $('[name="confraternista.pessoa.documentos.cpf"]').mask('999.999.999-99');
        $('[name="confraternista.pessoa.dataNascimento"]').mask('99/99/9999');
        $('[name="confraternista.pessoa.endereco.cep"]').mask('99999-999');
        $('[name="confraternista.casaEspirita.endereco.cep"]').mask('99999-999');
        $('[name="confraternista.pessoa.endereco.telefone"]').mask('(99)9999-9999');
        $('[name="confraternista.pessoa.endereco.telefoneEvento"]').mask('(99)9999-9999');
        $('[name="confraternista.pessoa.informacoesSaude.convenioTelefone"]').mask('(99)9999-9999');
        $('[name="confraternista.pessoa.responsavel.telefone"]').mask('(99)9999-9999');
        $('[name="confraternista.responsavelEvento.telefone"]').mask('(99)9999-9999');

        $('#addCamiseta').click(addCamiseta);
        $('.delete').click(removeCamiseta);
        $('#removeCamiseta').hide();

        $('[name="confraternista.oficina"]').each(function () {
            $(this).attr('checked', $(this).val() == '${command.confraternista.oficina.id}');
        });

        $('[name="confraternista.tipo"]').change(function () {
            if ($(this).val() == 'FACILITADOR') {
                $('#grupoFacilitador').show();
            } else {
                $('#grupoFacilitador').hide();
            }
        });

        $('[name=medicacao]').change(function () {
            if ($(this).val() == 'true') {
                $('#medicacao').attr('readonly', false);
            } else {
                $('#medicacao').attr('readonly', true).val('');
            }
        });
        $('[name=convenio]').change(function () {
            if ($(this).val() == 'true') {
                $('#convenio').attr('readonly', false);
                $('#foneConvenio').attr('readonly', false);
            } else {
                $('#convenio').attr('readonly', true).val('');
                $('#foneConvenio').attr('readonly', true).val('');
            }
        });
        $('[name=alergia]').change(function () {
            if ($(this).val() == 'true') {
                $('#alergia').attr('readonly', false);
            } else {
                $('#alergia').attr('readonly', true).val('');
            }
        });
        $('[name=dieta]').change(function () {
            if ($(this).val() == 'true') {
                $('#dieta').attr('readonly', false);
            } else {
                $('#dieta').attr('readonly', true).val('');
            }
        });

        if ($('#medicacao').val() == '') {
            $('#medicacaoNao').click();
        } else {
            $('#medicacaoSim').click();
        }
        if ($('#convenio').val() == '') {
            $('#convenioNao').click();
        } else {
            $('#convenioSim').click();
        }
        if ($('#alergia').val() == '') {
            $('#alergiaNao').click();
        } else {
            $('#alergiaSim').click();
        }
        if ($('#dieta').val() == '') {
            $('#dietaNao').click();
        } else {
            $('#dietaSim').click();
        }

        $('#estado').change(function () {
            loadCidades($(this), $('#cidade'));
        });
        $('#estadoCasa').change(function () {
            loadCidades($(this), $('#cidadeCasa'));
        });
        $('#dataNascimento').blur(function () {
            var texto = $(this).val();
            var tipoEvento = '${command.edicaoEvento.tipo}';

            if (typeof texto !== 'undefined' && texto !== '') {
                var dataNascimento = parseDate(texto);
                if ((tipoEvento == 'FAIXA_ETARIA') && ($("input:radio[value=CONFRATERNISTA]").is(':checked'))){
                    $('#evangelizadorResponsavel').show();
                }else{
                    $('#evangelizadorResponsavel').hide();
                }    
                if (!calculaMaiorIdade(dataNascimento)) {
                    $('#responsavel').show();
                } else {
                    $('#responsavel').hide();
                }
            } else {
                $('#responsavel').hide();                
            }
        });

        loadCidades($('#estado'), $('#cidade'), '${command.confraternista.pessoa.endereco.cidade.id}');
        loadCidades($('#estadoCasa'), $('#cidadeCasa'), '${command.confraternista.casaEspirita.endereco.cidade.id}');
    });
</script>
