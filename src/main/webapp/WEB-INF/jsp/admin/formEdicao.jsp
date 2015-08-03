<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Cadastrar Edição de Evento - ${command.evento.sigla}"/>
<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<mocca:title title="Formulário de cadastro" level="2"/>
<form:form commandName="command">
    <!--Conteúdo Tab-->
    <div class="tabbable">
        <!--Tab-->
        <ul class="nav tabs" id="EdicaoTab">
            <li class="active"><a href="#cadastroBasico"><fmt:message key="label.edition"/></a></li>
            <li><a href="#camisetas"><fmt:message key="label.shirts"/></a></li>
        </ul>
        <div class="content">
            <!--Cadastro Básico-->
            <div class="pane active" id="cadastroBasico">
                <fieldset>
                    <div class="row">
                        <div class="span4">
                            <div class="span6">
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
                            <div class="span6">
                                <label class="label control">
                                    <fmt:message key="label.subscriptionPaymentMethod"/>
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
                        </div>
                        <div class="span8">
                            <div class="table-wrapper">
                                <table class="table hovered stroked narrow small-font-size">
                                    <caption class="label control">Configuração de participantes da edição</caption>
                                    <thead class="header">
                                        <tr>
                                            <th style="width: 12em;">Tipo participante</th>
                                            <th style="width: 10em;" class="centered">Ocupa vaga no evento?</th>
                                            <th style="width: 10em;" class="centered">Ocupa vaga no Grupo/Oficina?</th>
                                            <th style="width: 10em;" class="centered">Isenção de taxa de inscrição</th>
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
                        </div>
                    </div>
                    <div class="row">
                        <div class="span3">
                            <see:formField label="label.number" isLabelKey="true" isMandatory="true" path="numero" maxlength="4" inputClass="textfield"/>
                        </div>
                        <div class="span3">
                            <see:formField label="label.theme" isLabelKey="true" isMandatory="true" path="tema" maxlength="100" inputClass="textfield"/>
                        </div>
                        <div class="span3">
                            <see:formField label="label.subscriptionValue" isLabelKey="true" isMandatory="true" path="valorInscricao" maxlength="4" inputClass="textfield"/>
                        </div>
                        <div class="span3">
                            <see:formField label="label.vacancies" isLabelKey="true" isMandatory="true" path="vagas" maxlength="4" inputClass="textfield"/>
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
                            <see:formField label="label.subscriptionDate" isLabelKey="true" isMandatory="true" type="text" path="data"/>
                        </div>
                        <div class="span3">
                            <see:formField label="label.subscriptionminage" isLabelKey="true" isMandatory="true" path="idadeMinima" maxlength="3" inputClass="textfield"/>
                        </div>
                    </div>
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
                </fieldset>
            </div>
            <!--Camisetas-->
            <div class="pane" id="camisetas">
                <fieldset>
                    <mocca:title title="label.shirts" isTitleKey="true" level="3"/>
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
                </fieldset>
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
                        <th class="centered" style="width: 8em;"><fmt:message key="label.subscriptionDate"/></th>
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
                                    <button  type="button" class="btn deletaEdicao" data-id="${edicao.id}" title="Deletar"><i class="icon-trash"></i></button>
                                        <c:if test="${edicao.faixaEtaria}">
                                        <button  type="button" class="btn" title="Grupos por idade" onclick="location.href = '${grupoIdade_url}';"><i class="icon-group"></i></button>
                                        </c:if>
                                        <c:if test="${edicao.oficina}">
                                        <button  type="button" class="btn" title="Oficinas" onclick="location.href = '${oficina_url}';"><i class="icon-wrench"></i></button>
                                        </c:if>
                                    <button  type="button" class="btn" title="Dormitórios" onclick="location.href = '${dormitorio_url}';"><i class="icon-building"></i></button>
                                </div>
                            </td>
                            <td class="centered">${edicao.numero}</td>
                            <td>${edicao.tema}</td>
                            <td class="align-right">${edicao.vagas}</td>
                            <td class="align-right">R$ ${edicao.valorInscricao}</td>
                            <td class="centered"><fmt:formatDate value="${edicao.periodoInscricao.start.time}" pattern="dd/MM/yyyy"/> - <fmt:formatDate value="${edicao.periodoInscricao.end.time}" pattern="dd/MM/yyyy"/></td>
                            <td class="centered"><fmt:formatDate value="${edicao.data.time}" pattern="dd/MM/yyyy"/></td>
                            <td class="align-right">${edicao.idadeMinima}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>
<see:formButtonGroup putSubmit="false" backUrl="formEvento.html"/>

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
    $(document).ready(function () {
        document.getElementById("numero").focus();

        $("[name='vagas']").mask('9999');
        $("[name='valorInscricao']").mask('9999');
        $("[name='valorCamiseta']").mask('999');
        $("[name='idadeMinima']").mask('999');

        $("#periodoInscricao_start").dateTimePicker({
            'mode': 'date',
            'showExample': true,
            'picker': {
                showClearButton: true,
                'onClose': function (selectedDate) {
                    $("#periodoInscricao_end").datepicker("option", "minDate", selectedDate);
                }
            }
        });
        $("#periodoInscricao_end").dateTimePicker({
            'mode': 'date',
            'showExample': true,
            picker: {
                showClearButton: true,
                'onSelect': function (selectedDate) {
                    $("#periodoInscricao_start").datepicker("option", "maxDate", selectedDate);
                    $("#data").datepicker("option", "minDate", selectedDate);
                }
            }
        });
        $("#data").dateTimePicker({
            'mode': 'date',
            'showExample': true,
            picker: {
                showClearButton: true
            }
        });
        $(".maisOpcoes").confirmDialog({
            'title': "Mais opções",
            'content': function ($dialogCaller) {
                var id = $dialogCaller.data("id");
                var $content = $("<div>").append($("#menuMaisOpcoes_" + id).html());
                return $content;
            },
            class: 'qtip-dialog-huge',
            'yesBtn': false,
            'noBtn': {
                label: "Fechar"
            }
        });
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
    });
</script>

