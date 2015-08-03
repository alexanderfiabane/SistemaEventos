<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="label.page.subscription" isTitleKey="true"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

<form:form commandName="command">
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h4><fmt:message key="label.eventdetails"/></h4>
        </legend>
        <div class="row">
            <div class="span12">
                <label class="label">
                    <fmt:message key="label.subscriptiontype"/>
                </label>
                <ul class="no-bullet no-padding">
                    <c:forEach var="item" items="${tiposConfraternista}">
                        <c:choose>
                            <c:when test="${command.inscricao.edicaoEvento.tipo == 'FAIXA_ETARIA'}">
                                <c:if test="${item != 'OFICINEIRO'}">
                                    <li class="mini-padding">
                                        <label>
                                            <form:radiobutton path="inscricao.confraternista.tipo" value="${item.name}"/> ${item.descricao}
                                        </label>
                                    </li>
                                </c:if>
                            </c:when>
                            <c:when test="${command.inscricao.edicaoEvento.tipo == 'OFICINA'}">
                                <c:if test="${(item != 'FACILITADOR') and (item != 'EVANGELIZADOR')}">
                                    <li class="mini-padding">
                                        <label>
                                            <form:radiobutton path="inscricao.confraternista.tipo" value="${item.name}"/> ${item.descricao}
                                        </label>
                                    </li>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${(item != 'FACILITADOR') and (item != 'EVANGELIZADOR') and (item != 'OFICINEIRO')}">
                                    <li class="mini-padding">
                                        <label>
                                            <form:radiobutton path="inscricao.confraternista.tipo" value="${item.name}"/> ${item.descricao}
                                        </label>
                                    </li>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
                <div class="control">
                    <form:errors path="inscricao.confraternista.tipo" cssClass="pill error"/>
                </div>
            </div>
        </div>
    </fieldset>
    <c:if test="${(not empty command.inscricao.edicaoEvento.gruposIdade) && (command.inscricao.edicaoEvento.tipo == 'FAIXA_ETARIA')}">
        <div id="grupoFacilitador" style="display: none;">
            <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
                <legend class="label">
                    <h4><fmt:message key="label.eventcoordgroupdetails"/></h4>
                </legend>
                <div class="row">
                    <div class="span12">
                        <label class="label">
                            <fmt:message key="label.groupname"/>
                        </label>
                        <ul class="no-bullet no-padding">
                            <c:forEach var="grupo" items="${command.inscricao.edicaoEvento.gruposIdade}">
                                <li class="mini-padding">
                                    <label class="radio">
                                        <form:radiobutton path="inscricao.confraternista.grupoIdade" value="${grupo.id}"/> ${grupo.nome}
                                    </label>
                                </li>
                            </c:forEach>
                        </ul>
                        <form:errors path="inscricao.confraternista.grupoIdade" cssClass="pill error"/>
                    </div>
                </div>
            </fieldset>
        </div>
    </c:if>
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h4><fmt:message key="label.userdetails"/></h4>
        </legend>
        <div class="row">
            <div class="span4">
                <see:formField id="loginConfraternista" label="label.user" isLabelKey="true" isMandatory="true" path="usuario.username" maxlength="80" hint="Digite aqui o login desejado para futuro acesso ao sistema. Caracteres aceitos: letras, números, hifén e underscore (mín: 3 e máx: 15 caracteres)" inputClass="textfield width-100"/>
            </div>
        </div>
    </fieldset>
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h4><fmt:message key="label.personaldetails"/></h4>
        </legend>
        <div class="row">
            <div class="span3">
                <see:formField label="label.fullname" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.nome" maxlength="100" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.badge" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.nomeCracha" maxlength="100" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField id="dataNascimento" label="label.birthday" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.dataNascimento" maxlength="10" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.gender" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.sexo" type="select" itens="${sexos}" itemLabel="descricao" selectNullItemLabel="Selecione" inputClass="selectfield width-100"/>
            </div>
        </div>
        <div class="row">
            <div class="span3">
                <see:formField label="label.cpf" isLabelKey="true" isMandatory="false" path="inscricao.confraternista.pessoa.documentos.cpf" maxlength="14" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.id" isLabelKey="true" isMandatory="false" path="inscricao.confraternista.pessoa.documentos.rg" maxlength="10" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.birthcertificate" isLabelKey="true" isMandatory="false" path="inscricao.confraternista.pessoa.documentos.certidaoNascimento" maxlength="20" inputClass="textfield width-100"/>
            </div>
        </div>
        <div class="row">
            <div class="span3">
                <see:formField label="label.street" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.endereco.logradouro" maxlength="100" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.number" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.endereco.numero" maxlength="6" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.complement" isLabelKey="true" isMandatory="false" path="inscricao.confraternista.pessoa.endereco.complemento" maxlength="60" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.district" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.endereco.bairro" maxlength="60" inputClass="textfield width-100"/>
            </div>
            <div class="row">
                <div class="span3">
                    <!--<javalek:label label="label.state" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>-->
                    <label class="label">
                        <fmt:message key="label.state"/>
                    </label>
                    <select id="estado" class="selectfield width-100">
                        <option value="">Selecione um estado</option>
                        <c:forEach var="estado" items="${estados}">
                            <option value="${estado.id}" <c:if test="${command.inscricao.confraternista.pessoa.endereco.cidade.estado.id == estado.id}">selected="selected"</c:if>>${estado.nome}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="span3">
                    <see:formField label="label.city" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.endereco.cidade" id="cidade" type="select" itemValue="id" itemLabel="nome" selectNullItemLabel="Selecione primeiro um estado" inputClass="selectfield width-100"/>
                </div>
                <div class="span3">
                    <see:formField label="label.zipcode" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.endereco.cep" maxlength="9" inputClass="textfield width-100"/>
                </div>
            </div>
            <div class="row">
                <div class="span3">
                    <see:formField label="label.email" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.endereco.email" maxlength="100" inputClass="textfield width-100"/>
                </div>
                <div class="span3">
                    <see:formField label="label.email.confirm" isLabelKey="true" isMandatory="true" path="emailConfirm" maxlength="100" inputClass="textfield width-100"/>
                </div>
                <div class="span3">
                    <see:formField label="label.phone" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.endereco.telefone" maxlength="16" inputClass="textfield width-100"/>
                </div>
                <div class="span3">
                    <see:formField label="label.phoneatevent" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.endereco.telefoneEvento" maxlength="16" inputClass="textfield width-100"/>
                </div>
            </div>
            <div id="responsavel" class="row" style="display:none;">
                <div class="span6">
                    <see:formField label="label.responsible" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.responsavel.nome" maxlength="100" inputClass="textfield width-100"/>
                </div>
                <div class="span3">
                    <see:formField label="label.phone" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.pessoa.responsavel.telefone" maxlength="16" inputClass="textfield width-100"/>
                </div>
            </div>
    </fieldset>

    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h4><fmt:message key="label.healthfooddetails"/></h4>
        </legend>
        <div class="row">
            <div class="span4">
                <label class="label">
                    Faz uso rotineiro de medicação?
                </label>
                <label class="radio inline"><br>
                    <label><input type="radio" id="medicacaoNao" name="medicacao" value="false"/>Não</label>
                    <label><input type="radio" id="medicacaoSim" name="medicacao" value="true"/>Sim</label>
                </label>
            </div>
            <div class="span8">
                <see:formField label="Qual(is)" isLabelKey="false" isMandatory="false" id="medicacao" path="inscricao.confraternista.pessoa.informacoesSaude.medicacao" maxlength="255" inputClass="textfield width-100"/>
            </div>
        </div>
        <div class="row">
            <div class="span4">
                <label class="label">
                    Tem algum tipo de convênio?
                </label>
                <label class="radio inline"><br>
                    <label><input type="radio" id="convenioNao" name="convenio" value="false"/>Não</label>
                    <label><input type="radio" id="convenioSim" name="convenio" value="true"/>Sim</label>
                </label>
            </div>
            <div class="span5">
                <see:formField label="Qual(is)" isLabelKey="false" isMandatory="false" id="convenio" path="inscricao.confraternista.pessoa.informacoesSaude.convenio" maxlength="255" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="Telefone" isLabelKey="false" isMandatory="false" id="foneConvenio" path="inscricao.confraternista.pessoa.informacoesSaude.convenioTelefone" maxlength="255" inputClass="textfield width-100"/>
            </div>
        </div>
        <div class="row">
            <div class="span4">
                <label class="label">
                    Possui algum tipo de alergia?
                </label>
                <label class="radio inline"><br>
                    <label><input type="radio" id="alergiaNao" name="alergia" value="false"/>Não</label>
                    <label><input type="radio" id="alergiaSim" name="alergia" value="true"/>Sim</label>
                </label>
            </div>
            <div class="span8">
                <see:formField label="Qual(is)" isLabelKey="false" isMandatory="false" id="alergia" path="inscricao.confraternista.pessoa.informacoesSaude.alergia" maxlength="255" inputClass="textfield width-100"/>
            </div>
        </div>
        <div class="row">
            <div class="span4">
                <label class="label">
                    Possui algum tipo de restrição alimentar?
                </label>
                <label class="radio inline"><br>
                    <label><input type="radio" id="dietaNao" name="dieta" value="false"/>Não</label>
                    <label><input type="radio" id="dietaSim" name="dieta" value="true"/>Sim</label>
                </label>
            </div>
            <div class="span8">
                <see:formField label="Qual(is)" isLabelKey="false" isMandatory="false" id="dieta" path="inscricao.confraternista.pessoa.informacoesSaude.dieta" maxlength="255" inputClass="textfield width-100"/>
            </div>
        </div>
    </fieldset>

    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h4><fmt:message key="label.housedetails"/></h4>
        </legend>
        <div class="row">
            <div class="span12">
                <see:formField label="Nome" isLabelKey="false" isMandatory="true" path="inscricao.confraternista.casaEspirita.nome" maxlength="100" inputClass="textfield width-100"/>
            </div>
        </div>
        <div class="row">
            <div class="span3">
                <see:formField label="label.street" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.casaEspirita.endereco.logradouro" maxlength="100" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.number" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.casaEspirita.endereco.numero" maxlength="6" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.complement" isLabelKey="true" isMandatory="false" path="inscricao.confraternista.casaEspirita.endereco.complemento" maxlength="60" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.district" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.casaEspirita.endereco.bairro" maxlength="60" inputClass="textfield width-100"/>
            </div>
        </div>
        <div class="row">
            <div class="span3">
                <label class="label">
                    <fmt:message key="label.state"/>
                </label>
                <select id="estadoCasa" class="selectfield width-100">
                    <option value="">Selecione um estado</option>
                    <c:forEach var="estado" items="${estados}">
                        <option value="${estado.id}" <c:if test="${command.inscricao.confraternista.casaEspirita.endereco.cidade.estado.id == estado.id}">selected="selected"</c:if>>${estado.nome}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="span3">
                <see:formField label="label.city" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.casaEspirita.endereco.cidade" id="cidadeCasa" type="select" itemValue="id" itemLabel="nome" selectNullItemLabel="Selecione primeiro um estado" inputClass="selectfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.zipcode" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.casaEspirita.endereco.cep" maxlength="9" inputClass="textfield width-100"/>
            </div>
        </div>
        <!--TODO: Aqui fazer verificação por tipo *avaliar para os outros tipos de evento-->
        <div id="evangelizadorResponsavel" class="row" style="display:none;">
            <div class="span6">
                <see:formField label="label.responsibleevent" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.responsavelEvento.nome" maxlength="100" inputClass="textfield width-100"/>
            </div>
            <div class="span3">
                <see:formField label="label.phone" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.responsavelEvento.telefone" maxlength="16" inputClass="textfield width-100"/>
            </div>
        </div>
        <div class="row">
            <div class="span12">
                <see:formField id="atividadeIE" label="label.activityatie" isLabelKey="true" isMandatory="true" path="inscricao.confraternista.atividadeCasaEspirita" type="textarea" maxlength="500" inputClass="textarea width-100"/>
            </div>
        </div>
    </fieldset>

    <c:if test="${(not empty command.inscricao.edicaoEvento.oficinas) && (command.inscricao.edicaoEvento.tipo == 'OFICINA')}">
    <div id="ocupaVaga">
        <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
            <legend class="label">
                <h4><fmt:message key="label.workshopdetails"/></h4>
            </legend>
            <div class="row">
                <div class="span3">
                    <label class="label">
                        <fmt:message key="label.workshopname"/>
                    </label>
                    <ul class="no-bullet no-padding">
                        <c:forEach var="oficina" items="${command.inscricao.edicaoEvento.oficinas}">
                            <li class="mini-padding">
                                <label>
                                    <form:radiobutton path="inscricao.confraternista.oficina" value="${oficina.id}" disabled="${oficina.saldoVagas <= 0}"/> ${oficina.nome} (${oficina.saldoVagas} vagas)
                                </label>
                            </li>
                        </c:forEach>
                    </ul>
                    <form:errors path="inscricao.confraternista.oficina" cssClass="pill error"/>
                </div>
            </div>
        </fieldset>
    </div>
    </c:if>

    <c:if test="${not empty command.inscricao.edicaoEvento.tiposCamiseta}">
        <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
            <legend class="label">
                <h4><fmt:message key="label.shirtdetails"/></h4>
            </legend>
            <div class="row">
                <div class="span3">
                    <label class="label">
                        <fmt:message key="label.shirttype"/>
                    </label>
                    <select id="tipoCamiseta" class="selectfield width-100">
                        <option value="">Tipo</option>
                        <c:forEach var="tipo" items="${command.inscricao.edicaoEvento.tiposCamiseta}">
                            <option value="${tipo.id}">${tipo.descricao}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="span3">
                    <label class="label">
                        <fmt:message key="label.shirtcolor"/>
                    </label>
                    <select id="corCamiseta" class="selectfield width-100">
                        <option value="">Cor</option>
                        <c:forEach var="cor" items="${command.inscricao.edicaoEvento.coresCamiseta}">
                            <option value="${cor.id}">${cor.descricao}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="span3">
                    <label class="label">
                        <fmt:message key="label.shirtsize"/>
                    </label>
                    <select id="tamanhoCamiseta" class="selectfield width-100">
                        <option value="">Tamanho</option>
                        <c:forEach var="tamanho" items="${command.inscricao.edicaoEvento.tamanhosCamiseta}">
                            <option value="${tamanho.id}">${tamanho.descricao}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="span2">
                    <label class="label">
                        <fmt:message key="label.shirtquant"/>
                    </label>
                    <input type="text" id="quantCamiseta" maxlength="2" class="textfield width-100" placeholder="Quantidade"/>
                </div>
                <div class="span1">
                    <label class="label">
                        <fmt:message key="label.options"/>
                    </label>
                    <div>
                        <button type="button" class="btn small" title="Adicionar" id="addCamiseta">Adicionar</button>
                        <button type="button" class="btn small delete" title="Remover" id="removeCamiseta">Remover</button>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="table-wrapper scrollable bordered rounded">
                    <table id="camisetas" class="table striped hovered stroked narrow">
                        <thead class="header">
                            <tr><th class="centered" colspan="5"><fmt:message key="label.shirts"/></th></tr>
                        <tr>
                            <th class="centered" style="width: 2em;"><fmt:message key="label.options"/></th>
                        <th class="centered"><fmt:message key="label.shirttype"/></th>
                        <th class="centered"><fmt:message key="label.shirtcolor"/></th>
                        <th class="centered"><fmt:message key="label.shirtsize"/></th>
                        <th class="centered"><fmt:message key="label.shirtquant"/></th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="camiseta" items="${command.inscricao.confraternista.camisetas}" varStatus="status">
                                <tr id="camiseta_${status.index}">
                                    <td class="centered">
                                        <button  type="button" class="btn small delete centered" title="Remover" id="removeCamiseta">Remover</button>
                                    </td>
                                    <td class="centered">
                                        <input type="hidden" name="inscricao.confraternista.camisetas[${status.index}].tipoCamiseta" value="${camiseta.tipoCamiseta.id}"/>
                                        ${camiseta.tipoCamiseta.descricao}
                                    </td>
                                    <td class="centered">
                                        <input type="hidden" name="inscricao.confraternista.camisetas[${status.index}].corCamiseta" value="${camiseta.corCamiseta.id}"/>
                                        ${camiseta.corCamiseta.descricao}
                                    </td>
                                    <td class="centered">
                                        <input type="hidden" name="inscricao.confraternista.camisetas[${status.index}].tamanhoCamiseta" value="${camiseta.tamanhoCamiseta.id}"/>
                                        ${camiseta.tamanhoCamiseta.descricao}
                                    </td>
                                    <td class="align-right">
                                        <input type="hidden" name="inscricao.confraternista.camisetas[${status.index}].quantidadeCamiseta" value="${camiseta.quantidadeCamiseta}"/>
                                        ${camiseta.quantidadeCamiseta}
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot class="footer">
                            <tr>
                                <td colspan="5">
                                    <p class="small-padding  mini-font-size">
                                        * Caso deseje adquirir camiseta escolha a combinação de tipo, tamanho e cor e então clique em ' Adicionar '.<br/>
                                        * Caso não deseje adquirir a camiseta agora, basta clicar em ' Remover camiseta ' ou não alterar os campos referentes à camiseta!<br/>
                                        * <b>Valor:</b> Camiseta - <fmt:formatNumber value="${command.inscricao.edicaoEvento.valorCamiseta}" type="currency" currencySymbol="R$" minFractionDigits="2"/>.
                                    </p>
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </fieldset>
    </c:if>
    <c:url var="cleanUrl" value="/guest/formInscricao.html">
        <c:param name="idEdicao" value="${command.inscricao.edicaoEvento.id}"/>
    </c:url>
    <c:url var="backUrl" value="/guest/listInscricoesAbertas.html"/>
    <see:formButtonGroup clearUrl="${cleanUrl}" backUrl="${backUrl}" putSubmit="true"/>
</form:form>

<script type="text/javascript" src="<c:url value="/dwr/interface/enderecoAjaxService.js"/>"></script>
<script type="text/javascript">
    var camisetaIndex = ${fn:length(command.inscricao.confraternista.camisetas)};
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
            inputCidade.append($('<option value="">').append('Selecione primeiro um estado'));
        } else {
            enderecoAjaxService.getCidades(
                    estadoSelecionado,
                    {
                        'callback': function(cidades) {
                            inputCidade.append($('<option value="">').append('Selecione uma cidade'));
                            $.each(cidades, function(index, value) {
                                inputCidade.append($('<option>').val(value.id).append(value.nome));
                            });
                            if (idCidadeAtual) {
                                inputCidade.val(idCidadeAtual);
                            }
                        },
                        'preHook': function() {
                            //lock
                            $.WidgetUtils.blockUI('Aguarde...');
                        },
                        'postHook': function() {
                            //unlock
                            $.WidgetUtils.unblockUI();
                        }
                    }
            );
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
            var tipoAlert = new AlertJS({
                'theme': "warning",
                'title': "Atenção",
                'content': "Escolha um tipo de camiseta!",
                'buttons': {
                    'ok': {
                        label: "Fechar"
                    }
                }
            });
            tipoAlert.open();
            return;
        }
        if (cor == '') {
            var corAlert = new AlertJS({
                'theme': "warning",
                'title': "Atenção",
                'content': "Escolha uma cor de camiseta!",
                'buttons': {
                    'ok': {
                        label: "Fechar"
                    }
                }
            });
            corAlert.open();
            return;
        }
        if (tam == '') {
            var tamAlert = new AlertJS({
                'theme': "warning",
                'title': "Atenção",
                'content': "Escolha um tamanho de camiseta!",
                'buttons': {
                    'ok': {
                        label: "Fechar"
                    }
                }
            });
            tamAlert.open();
            return;
        }
        if (quant == '') {
            var quantAlert = new AlertJS({
                'theme': "warning",
                'title': "Atenção",
                'content': "Defina a quantidade de camisetas!",
                'buttons': {
                    'ok': {
                        label: "Fechar"
                    }
                }
            });
            quantAlert.open();
            return;
        }

        var index = camisetaIndex++;

        $('#camisetas tbody').append($('<tr>').attr('id', 'camiseta_' + index)
                .append($('<td class="centered">').append(createButton()))
                .append($('<td class="centered">').append(createInput('t', tipo, index)).append(tipoDescr))
                .append($('<td class="centered">').append(createInput('c', cor, index)).append(corDescr))
                .append($('<td class="centered">').append(createInput('s', tam, index)).append(tamDescr))
                .append($('<td class="align-right">').append(createInput('q', quant, index)).append(quantDescr))
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
        var path = 'inscricao.confraternista.camisetas';
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

    $(document).ready(function() {

        $("#atividadeIE").textCounter({
            maxChars: 500
        });

        $('[name="inscricao.confraternista.pessoa.documentos.cpf"]').mask('999.999.999-99');
        $('[name="inscricao.confraternista.pessoa.dataNascimento"]').mask('99/99/9999');
        $('[name="inscricao.confraternista.pessoa.endereco.cep"]').mask('99999-999');
        $('[name="inscricao.confraternista.casaEspirita.endereco.cep"]').mask('99999-999');
        $('[name="inscricao.confraternista.pessoa.endereco.telefone"]').mask('(99)9999-9999');
        $('[name="inscricao.confraternista.pessoa.endereco.telefoneEvento"]').mask('(99)9999-9999');
        $('[name="inscricao.confraternista.pessoa.informacoesSaude.convenioTelefone"]').mask('(99)9999-9999');
        $('[name="inscricao.confraternista.pessoa.responsavel.telefone"]').mask('(99)9999-9999');
        $('[name="inscricao.confraternista.responsavelEvento.telefone"]').mask('(99)9999-9999');

        $('#addCamiseta').click(addCamiseta);
        $('.delete').click(removeCamiseta);
        $('#removeCamiseta').hide();

        $('[name="inscricao.confraternista.oficina"]').each(function() {
            $(this).attr('checked', $(this).val() == '${command.inscricao.confraternista.oficina.id}');
        });

        $('[name="inscricao.confraternista.tipo"]').change(function() {
            if ($(this).val() == 'FACILITADOR') {
                $('#grupoFacilitador').show();
            } else {
                $('#grupoFacilitador').hide();
            }
        });

        $('[name=medicacao]').change(function() {
            if ($(this).val() == 'true') {
                $('#medicacao').attr('readonly', false);
            } else {
                $('#medicacao').attr('readonly', true).val('');
            }
        });
        $('[name=convenio]').change(function() {
            if ($(this).val() == 'true') {
                $('#convenio').attr('readonly', false);
                $('#foneConvenio').attr('readonly', false);
            } else {
                $('#convenio').attr('readonly', true).val('');
                $('#foneConvenio').attr('readonly', true).val('');
            }
        });
        $('[name=alergia]').change(function() {
            if ($(this).val() == 'true') {
                $('#alergia').attr('readonly', false);
            } else {
                $('#alergia').attr('readonly', true).val('');
            }
        });
        $('[name=dieta]').change(function() {
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

        $('#estado').change(function() {
            loadCidades($(this), $('#cidade'));
        });
        $('#estadoCasa').change(function() {
            loadCidades($(this), $('#cidadeCasa'));
        });
        $('[name=inscricao\\.confraternista\\.tipo]').change(function() {
            var tipoEvento = '${command.inscricao.edicaoEvento.tipo}';
            if ((tipoEvento == 'FAIXA_ETARIA') && ($("input:radio[value=CONFRATERNISTA]").is(':checked'))) {
                $('#evangelizadorResponsavel').show();
            } else {
                $('#evangelizadorResponsavel').hide();
            }
        });
        $('[name=inscricao\\.confraternista\\.tipo]:checked').change();
        $('#dataNascimento').blur(function() {
            var texto = $(this).val();
            if (typeof texto !== 'undefined' && texto !== '') {
                var dataNascimento = parseDate(texto);
                if (!calculaMaiorIdade(dataNascimento)) {
                    $('#responsavel').show();
                } else {
                    $('#responsavel').hide();
                }
            } else {
                $('#responsavel').hide();
            }
        });

        loadCidades($('#estado'), $('#cidade'), '${command.inscricao.confraternista.pessoa.endereco.cidade.id}');
        loadCidades($('#estadoCasa'), $('#cidadeCasa'), '${command.inscricao.confraternista.casaEspirita.endereco.cidade.id}');
    });
</script>
