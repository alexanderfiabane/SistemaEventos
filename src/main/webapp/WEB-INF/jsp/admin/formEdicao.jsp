<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Cadastrar Edição de Evento - ${command.evento.sigla}"/>
<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<mocca:title title="Formulário de cadastro" level="2"/>
<form:form commandName="command" enctype="multipart/form-data">
    <!--Conteúdo Tab-->
    <div class="tabbable">
        <!--Tab-->
        <ul class="nav tabs" id="EdicaoTab">
            <li class="active"><a href="#cadastroBasico"><fmt:message key="label.basicData"/></a></li>
            <li><a href="#cobranca"><fmt:message key="label.subscriptionPaymentMethod"/></a></li>
            <li><a href="#local"><fmt:message key="label.subscriptionPlace"/></a></li>
            <li><a href="#fichaInscricao"><fmt:message key="label.applicationForm"/></a></li>
            <li><a href="#crachas"><fmt:message key="label.badge"/></a></li>
            <li><a href="#camisetas"><fmt:message key="label.shirts"/></a></li>
        </ul>
        <div class="content">
            <!--Cadastro Básico-->
            <div class="pane active" id="cadastroBasico">
                <div class="row">
                    <div class="span2 padding-top">
                        <label class="label control">
                            <fmt:message key="label.editiontype"/>
                        </label>
                        <ul class="no-bullet no-padding">
                            <c:forEach var="item" items="${tiposEdicao}">
                                <li class="mini-padding">
                                    <form:radiobutton path="tipo" value="${item.name}"/> ${item.descricao}
                                </li>
                            </c:forEach>
                        </ul>
                        <form:errors path="tipo" cssClass="pill error"/>
                    </div>
                    <div class="span10">
                        <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
                            <legend><label class="label control">Configuração de participantes da edição</label></legend>
                            <div class="table-wrapper">
                                <table class="table hovered stroked narrow small-font-size">
                                    <thead class="header">
                                        <tr>
                                            <th style="width: 12em;">Tipo participante</th>
                                            <th style="width: 10em;" class="centered">Ocupa vaga no evento?</th>
                                            <th style="width: 14em;" class="centered">Ocupa vaga no Grupo/Oficina?</th>
                                            <th style="width: 12em;" class="centered">Isenção de taxa de inscrição</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="selTipoEdicao">
                                            <td colspan="4" class="centered bold control">Selecione um tipo de Edição</td>
                                        </tr>
                                        <c:forEach items="${command.edicaoConfigParticipantes}" var="confraternista" varStatus="status">
                                            <tr id="id${confraternista.tipoParticipante.name}" class="hidden">
                                                <td>${confraternista.tipoParticipante.descricao}</td>
                                                <td class="centered"><form:checkbox path="edicaoConfigParticipantes[${status.index}].ocupaVaga" value="true"/></td>
                                                <td class="centered"><form:checkbox path="edicaoConfigParticipantes[${status.index}].ocupaVagaGp" value="true"/></td>
                                                <td class="centered"><form:checkbox path="edicaoConfigParticipantes[${status.index}].isento" value="true"/></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <div class="row">
                    <div class="span2">
                        <see:formField label="label.number" isLabelKey="true" isMandatory="true" path="numero" maxlength="4" inputClass="textfield"/>
                    </div>
                    <div class="span4">
                        <see:formField label="label.theme" isLabelKey="true" isMandatory="true" path="tema" maxlength="100" inputClass="textfield"/>
                    </div>
                    <div class="span2">
                        <see:formField label="label.subscriptionValue" isLabelKey="true" isMandatory="true" path="valorInscricao" maxlength="4" inputClass="textfield"/>
                    </div>
                    <div class="span2">
                        <see:formField label="label.vacancies" isLabelKey="true" isMandatory="true" path="vagas" maxlength="4" inputClass="textfield"/>
                    </div>
                    <div class="span2">
                        <see:formField label="label.subscriptionminage" isLabelKey="true" isMandatory="true" path="idadeMinima" maxlength="3" inputClass="textfield"/>
                    </div>
                </div>
                <div class="row">
                    <div class="span3">
                        <see:formField id="periodoInscricao_start" label="label.subscriptionPeriodStart" isLabelKey="true" isMandatory="true" type="text" path="periodoInscricao.start" inputClass="manual-init"/>
                    </div>
                    <div class="span3">
                        <see:formField id="periodoInscricao_end" label="label.subscriptionPeriodEnd" isLabelKey="true" isMandatory="true" type="text" path="periodoInscricao.end"/>
                    </div>
                    <div class="span3">
                        <see:formField id="periodoEdicao_start" label="label.editionPeriodStart" isLabelKey="true" isMandatory="true" type="text" path="periodoEdicao.start" inputClass="manual-init"/>
                    </div>
                    <div class="span3">
                        <see:formField id="periodoEdicao_end" label="label.editionPeriodEnd" isLabelKey="true" isMandatory="true" type="text" path="periodoEdicao.end"/>
                    </div>
                </div>
            </div>
            <!--Forma de cobrança-->
            <div class="pane" id="cobranca">
                <div class="row">
                    <div class="span2 padding-top">
                        <label class="label control">
                            <fmt:message key="label.subscriptionPaymentMethodType"/>
                        </label>
                        <ul class="no-bullet no-padding">
                            <c:forEach var="item" items="${tiposFormaCobranca}">
                                <li class="mini-padding">
                                    <form:radiobutton path="formaCobranca.tipoCobranca" value="${item.name}"/> ${item.descricao}
                                </li>
                            </c:forEach>
                            <form:errors path="formaCobranca.tipoCobranca" class="pill error"/>
                        </ul>
                    </div>
                    <div class="span10">
                        <div id="deposito" class="row hidden">
                            <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
                                <legend class="label">
                                    <label class="label control">Detalhes da Forma de Cobrança</label>
                                </legend>
                                <div class="row">
                                    <div class="span4">
                                        <see:formField label="Banco" isMandatory="false" path="formaCobranca.deposito.bancoPlain"/>
                                    </div>
                                    <div class="span8">
                                        <see:formField label="Favorecido" isMandatory="false" path="formaCobranca.deposito.favorecidoPlain"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="span4">
                                        <see:formField label="Agência" isMandatory="false" path="formaCobranca.deposito.agenciaPlain"/>
                                    </div>
                                    <div class="span5">
                                        <see:formField label="Número da conta" isMandatory="false" path="formaCobranca.deposito.numeroContaPlain"/>
                                    </div>
                                    <div class="span3">
                                        <see:formField label="Número da operação" isMandatory="false" path="formaCobranca.deposito.operacao"/>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                        <div id="pagseguro" class="row hidden">
                            <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
                                <legend class="label">
                                    <label class="label control">Detalhes da Forma de Cobrança</label>
                                </legend>
                                <div class="row">
                                    <div class="span3">
                                        <label class="label control">Ambiente de excução</label>
                                        <ul class="no-bullet no-padding list-h">
                                            <li><form:radiobutton label="Produção" path="formaCobranca.pagSeguro.producao" value="true"/></li>
                                            <li><form:radiobutton label="SandBox" path="formaCobranca.pagSeguro.producao" value="false"/></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="span4">
                                        <see:formField type="text" label="E-mail PagSeguro" isMandatory="false" path="formaCobranca.pagSeguro.emailPagSeguroPlain"/>
                                    </div>
                                    <div class="span4">
                                        <see:formField type="text" label="Token (PRODUÇÃO)" isMandatory="false" path="formaCobranca.pagSeguro.tokenSegurancaProducao"/>
                                    </div>
                                    <div class="span4">
                                        <see:formField type="text" label="Token (SANDBOX)" isMandatory="false" path="formaCobranca.pagSeguro.tokenSegurancaSandBox"/>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                </div>
            </div>
            <!--Local-->
            <div class="pane" id="local">
                <div class="row">
                    <div class="span6">
                        <see:formField type="text" label="label.subscriptionPlace" isLabelKey="true" isMandatory="true" path="local"/>
                    </div>
                    <div class="span6">
                        <see:formField type="text" label="label.street" isLabelKey="true" isMandatory="true" path="localEndereco.logradouro"/>
                    </div>
                </div>
                <div class="row">
                    <div class="span4">
                        <see:formField label="label.number" isLabelKey="true" isMandatory="true" path="localEndereco.numero" maxlength="6"/>
                    </div>
                    <div class="span4">
                        <see:formField label="label.complement" isLabelKey="true" isMandatory="false" path="localEndereco.complemento" maxlength="60"/>
                    </div>
                    <div class="span4">
                        <see:formField label="label.district" isLabelKey="true" isMandatory="true" path="localEndereco.bairro" maxlength="60"/>
                    </div>
                </div>
                <div class="row">
                    <div class="span4">
                        <label class="label">
                            <fmt:message key="label.state"/>
                        </label>
                        <select id="estadoEdicao" class="selectfield width-100">
                            <option value="">Selecione um estado</option>
                            <c:forEach var="estado" items="${estados}">
                                <option value="${estado.id}" <c:if test="${command.localEndereco.cidade.estado.id == estado.id}">selected="selected"</c:if>>${estado.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="span4">
                        <see:formField label="label.city" isLabelKey="true" isMandatory="true" path="localEndereco.cidade" id="cidadeEdicao" type="select" itemValue="id" itemLabel="nome" selectNullItemLabel="Selecione primeiro um estado"/>
                    </div>
                    <div class="span4">
                        <see:formField label="label.zipcode" isLabelKey="true" isMandatory="true" path="localEndereco.cep" maxlength="9"/>
                    </div>
                </div>
            </div>
            <!--Ficha de inscrição-->
            <div class="pane" id="fichaInscricao">
                <div class="row">
                    <div class="span4">
                        <label class="label control">Tem ficha de inscrição?</label>
                        <ul class="no-bullet no-padding list-h">
                            <li>
                                <form:radiobutton path="configFichaInscricao.temFichaInscricao" label="Sim" value="true"/>
                            </li>
                            <li>
                                <form:radiobutton path="configFichaInscricao.temFichaInscricao" label="Não" value="false"/>
                            </li>
                        </ul>
                        <form:errors path="configFichaInscricao.temFichaInscricao" cssClass="pill error"/>
                    </div>
                    <div class="span4">
                        <label class="label control">Requer autorização da instituição?</label>
                        <ul class="no-bullet no-padding list-h">
                            <li>
                                <form:radiobutton path="configFichaInscricao.autorizacaoInstituicao" label="Sim" value="true"/>
                            </li>
                            <li>
                                <form:radiobutton path="configFichaInscricao.autorizacaoInstituicao" label="Não" value="false"/>
                            </li>
                        </ul>
                        <form:errors path="configFichaInscricao.autorizacaoInstituicao" cssClass="pill error"/>
                    </div>
                    <div class="span4">
                        <label class="label control">Requer autorização para menor de idade?</label>
                        <ul class="no-bullet no-padding list-h">
                            <li>
                                <form:radiobutton path="configFichaInscricao.autorizacaoMenor" label="Sim" value="true"/>
                            </li>
                            <li>
                                <form:radiobutton path="configFichaInscricao.autorizacaoMenor" label="Não" value="false"/>
                            </li>
                        </ul>
                        <form:errors path="configFichaInscricao.autorizacaoMenor" cssClass="pill error"/>
                    </div>
                </div>
            </div>
            <!--Crachás-->
            <div class="pane" id="crachas">
                <div class="row">
                    <div class="span2">
                        <label class="label control">Tem crachá?</label>
                        <ul class="no-bullet no-padding list-h">
                            <li>
                                <form:radiobutton path="configCracha.temCracha" label="Sim" value="true"/>
                            </li>
                            <li>
                                <form:radiobutton path="configCracha.temCracha" label="Não" value="false"/>
                            </li>
                        </ul>
                        <form:errors path="configCracha.temCracha" cssClass="pill error"/>
                    </div>
                    <div class="span2">
                        <label class="label control">Tipo de crachá</label>
                        <ul class="no-bullet no-padding">
                            <li><form:radiobutton path="configCracha.tipo" value="" /> Nenhum</li>
                            <c:forEach var="item" items="${tiposCrachas}">
                                <li><form:radiobutton path="configCracha.tipo" value="${item.name}"/> ${item.descricao}</li>
                                </c:forEach>
                        </ul>
                        <form:errors path="configCracha.tipo" cssClass="pill error"/>
                    </div>
                    <div class="span8">
                        <label class="label control">Imagem de fundo <i id="hintCrachaImagem" class="icon-info-sign"></i></label><br>
                        <input type="file" accept="image/*" id="crachaFundo" name="configCracha.imagemFundo" class="textfield">
                        <form:errors path="configCracha.imagemFundo" cssClass="pill error"/>
                    </div>
                </div>
                <c:if test="${command.configCracha.imagemFundo.data != null && not empty command.configCracha.imagemFundo.nome}">
                    <div class="row">
                        <label class="label control">Crachá atual</label><br>
                        <img src="data:image/png;base64,${fundoCracha}"/>
                    </div>
                </c:if>
            </div>
            <!--Camisetas-->
            <div class="pane" id="camisetas">
                <div class="table-wrapper scrollable bordered rounded">
                    <table class="table stroked">
                        <thead class="header">
                            <tr>
                                <th colspan="4" class="centered">Selecione os Tipos, Cores e Tamanhos de camiseta oferecidos na Edição</th>
                            </tr>
                            <tr>
                                <th class="centered">Tipos</th>
                                <th class="centered">Cores</th>
                                <th class="centered">Tamanhos</th>
                                <th class="centered">Valor</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <form:checkboxes items="${tiposCamiseta}" itemValue="id" itemLabel="descricao" path="tiposCamiseta" delimiter="<br>"/>
                                </td>
                                <td>
                                    <form:checkboxes items="${coresCamiseta}" itemValue="id" itemLabel="descricao" path="coresCamiseta" delimiter="<br>"/>
                                </td>
                                <td>
                                    <form:checkboxes items="${tamanhosCamiseta}" itemValue="id" itemLabel="descricao" path="tamanhosCamiseta" delimiter="<br>"/>
                                </td>
                                <td>
                                    <form:input type="text" cssClass="textfield width-100" path="valorCamiseta" maxlength="3"/>
                                </td>
                            </tr>
                        </tbody>
                        <tfoot class="footer">
                            <tr>
                                <td colspan="4" class="small-font-size padding">* Para desativar as camisetas para essa edição, simplesmente deixe todas as opções acima desmarcadas.</td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
        <see:formButtonGroup putSubmit="true" clearUrl="formEdicao.html?idEvento=${command.evento.id}"/>
    </div>
</form:form>

<mocca:title title="Edições cadastradas" level="2"/>
<c:choose>
    <c:when test="${empty edicoes}">
        <see:notice type="info" closeable="true">Nenhuma edição foi encontrada</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable bordered rounded shadowed">
            <table class="table striped hovered stroked small-font-size">
                <thead class="header">
                    <tr>
                        <th class="centered" style="width: 7em;"><fmt:message key="label.options"/></th>
                        <th class="centered" style="width: 5em;">#</th>
                        <th class="centered"><fmt:message key="label.theme"/></th>
                        <th class="centered" style="width: 6em;"># vagas</th>
                        <th class="centered" style="width: 8em;"><fmt:message key="label.subscriptionValue"/></th>
                        <th class="centered" style="width: 12em;"><fmt:message key="label.subscriptionPeriod"/></th>
                        <th class="centered" style="width: 8em;"><fmt:message key="label.editionPeriod"/></th>
                        <th class="centered" style="width: 6em;"><fmt:message key="label.subscriptionminage"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${edicoes}" var="edicao">
                        <c:url var="edit_url" value="formEdicao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                        <c:url var="delete_url" value="deleteEdicao.html"><c:param name="idEdicao" value=""/></c:url>
                        <c:url var="grupoIdade_url" value="menuGrupoIdade.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                        <c:url var="oficina_url" value="formOficina.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                        <c:url var="dormitorio_url" value="menuDormitorio.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                            <tr>
                                <td>
                                    <div class="btn-group small">
                                        <button  type="button" class="btn" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
                                        <c:if test="${edicao.faixaEtaria}">
                                        <button  type="button" class="btn" title="Grupos por idade" onclick="location.href = '${grupoIdade_url}';"><i class="icon-group"></i></button>
                                        </c:if>
                                        <c:if test="${edicao.oficina}">
                                        <button  type="button" class="btn" title="Oficinas" onclick="location.href = '${oficina_url}';"><i class="icon-wrench"></i></button>
                                        </c:if>
                                    <button  type="button" class="btn" title="Dormitórios" onclick="location.href = '${dormitorio_url}';"><i class="icon-building"></i></button>
                                    <button  type="button" class="btn deletaEdicao" data-id="${edicao.id}" title="Deletar"><i class="icon-trash"></i></button>
                                </div>
                            </td>
                            <td class="centered">${edicao.numero}</td>
                            <td>${edicao.tema}</td>
                            <td class="align-right">${edicao.vagas}</td>
                            <td class="align-right">R$ ${edicao.valorInscricao}</td>
                            <td class="centered"><fmt:formatDate value="${edicao.periodoInscricao.start.time}" pattern="dd/MM/yyyy"/> - <fmt:formatDate value="${edicao.periodoInscricao.end.time}" pattern="dd/MM/yyyy"/></td>
                            <td class="centered"><fmt:formatDate value="${edicao.periodoEdicao.start.time}" pattern="dd/MM/yyyy"/> - <fmt:formatDate value="${edicao.periodoEdicao.end.time}" pattern="dd/MM/yyyy"/></td>
                            <td class="align-right">${edicao.idadeMinima}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>
<see:formButtonGroup putSubmit="false" backUrl="formEvento.html"/>

<script type="text/javascript" src="<c:url value="/dwr/interface/enderecoAjaxService.js"/>"></script>
<script type="text/javascript">
function toogleMenu(idEdicao) {
    var menuId = "#menu_" + idEdicao;
    var hidden = $(menuId).children("ul").is(":hidden");
    $(menuId + ">ul").hide();
    if (hidden) {
        $(menuId).children("ul").toggle();
        $(menuId).css("top", -50);
        $(menuId).css("left", 150);
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
                    'callback': function (cidades) {
                        inputCidade.append($('<option value="">').append('Selecione uma cidade'));
                        $.each(cidades, function (index, value) {
                            inputCidade.append($('<option>').val(value.id).append(value.nome));
                        });
                        if (idCidadeAtual) {
                            inputCidade.val(idCidadeAtual);
                        }
                    },
                    'preHook': function () {
                        //lock
                        $.WidgetUtils.blockUI('Aguarde...');
                    },
                    'postHook': function () {
                        //unlock
                        $.WidgetUtils.unblockUI();
                    }
                }
        );
    }
}
$(document).ready(function () {
    document.getElementById("numero").focus();

    $("[name='vagas']").mask('9999');
    $("[name='valorInscricao']").mask('9999');
    $("[name='valorCamiseta']").mask('999');
    $("[name='idadeMinima']").mask('999');
    $('[name="localEndereco.cep"]').mask('99999-999');

    $('#estadoEdicao').change(function () {
        loadCidades($(this), $('#cidadeEdicao'));
    });

    $("#periodoInscricao_start, #periodoInscricao_end").dateTimePicker({
        'mode': 'date',
        'showExample': true,
        'picker':{
            'showClearButton': true,
            'beforeShow':function(input){
                if (input.id == 'periodoInscricao_end') {
                    //fazer parse do date para dd/MM/yyyy
                    var minDate = new Date($('#periodoInscricao_start').val());
                    minDate.setDate(minDate.getDate() + 1)
                    return {
                        minDate: minDate
                    };
                }else{
                    var maxDate = new Date($('#periodoInscricao_end').val());
                    maxDate.setDate(maxDate.getDate() - 1)
                    return {
                        maxDate: maxDate
                    };
                }
            }
        }
    });
//    $("#periodoInscricao_start").dateTimePicker({
//        'mode': 'date',
//        'showExample': true,
//        'picker': {
//            showClearButton: true,
//            'onClose': function (selectedDate) {
//                $("#periodoInscricao_end").datepicker("option", "minDate", selectedDate);
//            }
//        }
//    });
//    $("#periodoInscricao_end").dateTimePicker({
//        'mode': 'date',
//        'showExample': true,
//        picker: {
//            showClearButton: true,
//            'onSelect': function (selectedDate) {
//                $("#periodoInscricao_start").datepicker("option", "maxDate", selectedDate);
//                $("#periodoEdicao_start").datepicker("option", "minDate", selectedDate);
//            }
//        }
//    });
    $("#periodoEdicao_start").dateTimePicker({
        'mode': 'date',
        'showExample': true,
        picker: {
            showClearButton: true,
            'onClose': function (selectedDate) {
                $("#periodoEdicao_end").datepicker("option", "minDate", selectedDate);
            }
        }
    });
    //$("#periodoInscricao_start").close(); //para edições que estão sendo editadas
    $("#periodoEdicao_end").dateTimePicker({
        'mode': 'date',
        'showExample': true,
        picker: {
            showClearButton: true,
        }
    });
    //$("#periodoEdicao_start").close(); //para edições que estão sendo editadas
    $(".deletaEdicao").each(function () {
        var $this = $(this);
        var id = $this.data("id"); // cata o id
        var thisUrl = '${delete_url}' + id; // concatena na url
        $this.openUrl({
            'url': thisUrl,
            'showConfirmDialog': true,
            'confirmDialog': {
                'content': "Tem certeza que deseja deletar essa edição?"
            }
        });
    });
    $("[name=formaCobranca\\.tipoCobranca]").change(function () {
        if ($(this).val() === 'DEPOSITO_CONTA') {
            $('#deposito').show();
            $('#pagseguro').hide();
        } else if ($(this).val() === 'PAGSEGURO') {
            $('#deposito').hide();
            $('#pagseguro').show();
        } else {
            $('#deposito').hide();
            $('#pagseguro').hide();
        }
    });
    $("[name=formaCobranca\\.tipoCobranca]:checked").change();
    $("[name=tipo]").change(function () {
        $("#selTipoEdicao").hide();
        if ($(this).val() === 'CONGRESSO') {
            $('#idAUXILIAR').show();
            $('#idCOORDENADOR').show();
            $('#idCONFRATERNISTA').show();
            $('#idEVANGELIZADOR').hide();
            $('#idFACILITADOR').hide();
            $('#idOFICINEIRO').hide();
        } else if ($(this).val() === 'FAIXA_ETARIA') {
            $('#idAUXILIAR').show();
            $('#idCOORDENADOR').show();
            $('#idCONFRATERNISTA').show();
            $('#idEVANGELIZADOR').show();
            $('#idFACILITADOR').show();
            $('#idOFICINEIRO').hide();
        } else {
            $('#idAUXILIAR').show();
            $('#idCOORDENADOR').show();
            $('#idCONFRATERNISTA').show();
            $('#idEVANGELIZADOR').hide();
            $('#idFACILITADOR').hide();
            $('#idOFICINEIRO').show();
        }
    });
    $("[name=tipo]:checked").change();
    $("[name=configFichaInscricao\\.temFichaInscricao]").change(function () {
        if ($(this).val() === 'false') {
            $('[name=configFichaInscricao\\.autorizacaoInstituicao').filter('[value="false"]').attr('checked', true);
            $('[name=configFichaInscricao\\.autorizacaoMenor').filter('[value="false"]').attr('checked', true);
        }
    });
    $("[name=configFichaInscricao\\.temFichaInscricao]:checked").change();
    $("[name=configFichaInscricao\\.autorizacaoInstituicao]").change(function () {
        if ($(this).val() === 'true') {
           $('[name=configFichaInscricao\\.temFichaInscricao').filter('[value="true"]').attr('checked', true);
        }
    });
    $("[name=configFichaInscricao\\.autorizacaoMenor]").change(function () {
        if ($(this).val() === 'true') {
           $('[name=configFichaInscricao\\.temFichaInscricao').filter('[value="true"]').attr('checked', true);
        }
    });

    $("[name=configCracha\\.temCracha]").change(function () {
        if ($(this).val() === 'false') {
            $('[name=configCracha\\.tipo').filter('[value=""]').attr('checked', true);
            $('[name=configCracha\\.imageFundo').replaceWith($('[name=configCracha\\.imageFundo').clone(true));
        }
    });
    $("[name=configCracha\\.temCracha]:checked").change();

    $("[name=configCracha\\.tipo]").change(function () {
        if($(this).val() === '' || $.ObjectUtils.isBlank(${command.configCracha.tipo})){
            $('[name=configCracha\\.temCracha').filter('[value="false"]').attr('checked', true);
        }else{
            $('[name=configCracha\\.temCracha').filter('[value="true"]').attr('checked', true);
        }
    });
    $("[name=configCracha\\.tipo]:checked").change();

    $("#hintCrachaImagem").qtip({
        'content': "Os tamanhos suportados são: <strong>jpeg, gif, bmp e png</strong>."
    });
    loadCidades($('#estadoEdicao'), $('#cidadeEdicao'), '${command.localEndereco.cidade.id}');
});
</script>

