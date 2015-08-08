<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Gerenciamento de Inscrições (${edicao.tema})" isTitleKey="false"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>

    <div id="searchParams" class="box bordered rounded control">
        <input name="edicao.id" type="hidden" value="${edicao.id}"/>
    <div class="row">
        <div class="span9">
            <label class="label">
                <fmt:message key="label.name"/>
            </label>
            <input name="inscricao.confraternista.pessoa.nome" type="text" class="textfield" title="Nome do confraternista"/>
        </div>
        <div class="span3">
            <label class="label">
                <fmt:message key="label.subscriptionDateReceive"/>
            </label>
            <input type="text" name="inscricao.dataInscricao" class="textfield date" title="Data de recebimento da inscrição"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <label class="label">
                <fmt:message key="label.subscriptiontype"/>
            </label>
            <select name="inscricao.confraternista.tipo" title="Tipo de inscrição" class="selectfield">
                <option value="">Selecione um tipo de inscrição</option>
                <c:forEach items="${tipoInscricoes}" var="tipoInscricao">
                    <option value="${tipoInscricao.name}">${tipoInscricao.descricao}</option>
                </c:forEach>
            </select>
        </div>
        <div class="span3">
            <label class="label">
                <fmt:message key="label.subscriptionstatus"/>
            </label>
            <select name="inscricao.status.value" title="Situação da inscrição" class="selectfield">
                <option value="">Selecione a situação da inscrição</option>
                <c:forEach items="${inscricaoStatus}" var="status">
                    <option value="${status.name}">${status.value}</option>
                </c:forEach>
            </select>
        </div>
        <div class="span3">
            <label class="label">
                <fmt:message key="label.paymentnumber"/> (pagamento)
            </label>
            <input name="inscricao.pagamento.codPagamento" type="text" class="textfield" title="Número do documento"/>
        </div>
        <div class="span3">
            <label class="label">
                <fmt:message key="label.paymentdate"/>
            </label>
            <input name="inscricao.pagamento.dataPagamento" type="text" class="textfield date" title="Data do pagamento da inscrição"/>
        </div>
    </div>
    <div class="form-actions stroked-top mini-padding no-margin-bottom">
        <button type="button" class="btn primary table-refresh" name="search"><i class="icon-search"></i> Pesquisar</button>
    </div>
</div>


<div id="paginationWrapper" class="table-wrapper scrollable">
    <table class="table striped stroked hovered narrow small-font-size">
        <thead class="header">
            <tr>
                <th class="centered"><fmt:message key="label.options"/></th>
                <th data-name="pessoa.nome"><fmt:message key="label.name"/></th>
                <th style="min-width: 12em;" data-name="confraternista.tipo"><fmt:message key="label.subscriptiontype"/></th>
                <th data-name="this.status"><fmt:message key="label.subscriptionstatus"/></th>
                <th style="min-width: 12em;" data-name="this.dataRecebimento"><fmt:message key="label.subscriptionDateReceive"/></th>
                <th><fmt:message key="label.paymentdate"/></th>
                <th><fmt:message key="label.paymentnumber"/></th>
            </tr>
        </thead>
        <tbody>
            <tr data-role="tableRow">
                <td class="centered"></td>
                <td>@{confraternista.pessoa.nome}</td>
                <td>@{confraternista.tipo.descricao}</td>
                <td>@{status.value}</td>
                <td>@{dataRecebimento|format=dd/MM/yyyy|ifBlank=Não informado}</td>
                <td>@{pagamento.dataPagamento|format=dd/MM/yyyy|ifBlank=Não informado}</td>
                <td><i class="hint icon-info-sign"></i> @{pagamento.codPagamento|ifBlank=Não informado}</td>
            </tr>
        </tbody>
    </table>
</div>
<c:url value="/admin/inscricao/listEdicao.html" var="backUrl">
    <c:param name="idEvento" value="${edicao.evento.id}"/>
</c:url>
<see:formButtonGroup putSubmit="false" backUrl="${backUrl}"/>

<c:url var="url_view" value="view.html">
    <c:param name="idInscricao" value=""/>
</c:url>
<c:url var="url_edit" value="form.html">
    <c:param name="idInscricao" value=""/>
</c:url>
<c:url var="url_aprova" value="aprova.html">
    <c:param name="idInscricao" value=""/>
</c:url>
<c:url var="url_efetiva" value="efetiva.html">
    <c:param name="idInscricao" value=""/>
</c:url>
<c:url var="url_indefere" value="indefere.html">
    <c:param name="idInscricao" value=""/>
</c:url>
<c:url var="url_reabre" value="reabre.html">
    <c:param name="idInscricao" value=""/>
</c:url>
<script type="text/javascript" src="<c:url value="/dwr/interface/inscricaoAjaxService.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#paginationWrapper').ajaxTable({
            ajaxTable: inscricaoAjaxService,
            refreshOnCreate: true,
            hideOnCreate: false,
            externalParams: {
                containerId: 'searchParams'
            },
            'table': {
                'postAddLine': function ($tr, item) {
                    var $td = $tr.find("td").eq(0);
                    var $icoDoc = $tr.find("td").eq(6).find('.hint');
                    if ($.ObjectUtils.isUnvalued(item.pagamento)) {
                        $icoDoc.remove();
                    } else {
                        $icoDoc.qtip({
                            'content': {
                                text: item.pagamento == null ? "Não há informações" : item.pagamento.descricaoPagamentoQtip,
                                title: "Detalhes do Pagamento"
                            },
                            position: {
                                my: 'bottom right',
                                at: 'left top'
                            },
                            style: {
                                classes: 'qtip-bootstrap'
                            }
                        });
                    }
                    var $btnGroup = $('<div>', {'class': 'btn-group mini'});
                    var $visualizar = $.WidgetUtils.createButton({
                        'title': 'Visualizar Inscrição',
                        'icon': 'icon-eye-open',
                        'class': 'btn'
                    });
                    $visualizar.openUrl({
                        'url': '${url_view}' + item.id,
                        'target': 'visualizar' + item.confraternista.pessoa.nome
                    });
                    var $editar = $.WidgetUtils.createButton({
                        'title': 'Editar Inscrição',
                        'icon': 'icon-edit',
                        'class': 'btn'
                    });
                    $editar.openUrl({
                        'url': '${url_edit}' + item.id,
                        'target': 'editar' + item.confraternista.pessoa.nome
                    });
                    var $podeAprovar = $.WidgetUtils.createButton({
                        'title': 'Confirmar Inscrição',
                        'icon': 'icon-check',
                        'class': 'btn'
                    });
                    var $reabreEdicao = $.WidgetUtils.createButton({
                        'title': 'Reabrir para Edição',
                        'icon': 'icon-share',
                        'class': 'btn'
                    });
                    var $podeEfetivar = $.WidgetUtils.createButton({
                        'title': 'Efetivar Inscrição',
                        'icon': 'icon-thumbs-up',
                        'class': 'btn'
                    });
                    var $indefere = $.WidgetUtils.createButton({
                        'title': 'Indeferir Inscrição',
                        'icon': 'icon-thumbs-down',
                        'class': 'btn'
                    });
                    if (item.podeAprovar) {
                        $podeAprovar.click(function () {
                            var confirmAproove = new ConfirmJS({
                                'theme': "primary",
                                'content': "Tem certeza que deseja confirmar esta inscrição?",
                                'buttons': {
                                    'yes': {
                                        'clickFunction': function (event, modal) {
                                            location.href = '${url_aprova}' + item.id;
                                        }
                                    },
                                    'cancel': false
                                },
                                'postAppendContent': function (modal) {
                                    modal.find('.main-action').lockOnClick();
                                }
                            });
                            confirmAproove.open();
                        });
                        $reabreEdicao.click(function () {
                            var confirmReOpen = new ConfirmJS({
                                'theme': "primary",
                                'content': "Tem certeza que deseja reabrir esta inscrição?",
                                'buttons': {
                                    'yes': {
                                        'clickFunction': function (event, modal) {
                                            location.href = '${url_reabre}' + item.id;
                                        }
                                    },
                                    'cancel': false
                                },
                                'postAppendContent': function (modal) {
                                    modal.find('.main-action').lockOnClick();
                                }
                            });
                            confirmReOpen.open();
                        });
                    } else {
                        $podeAprovar.disable();
                        $reabreEdicao.disable();
                    }
                    if (item.podeEfetivar) {
                        $podeEfetivar.click(function () {
                            var confirmAllow = new ConfirmJS({
                                'theme': "primary",
                                'content': "Tem certeza que deseja efetivar esta inscrição?",
                                'buttons': {
                                    'yes': {
                                        'clickFunction': function (event, modal) {
                                            location.href = '${url_efetiva}' + item.id;
                                        }
                                    },
                                    'cancel': false
                                },
                                'postAppendContent': function (modal) {
                                    modal.find('.main-action').lockOnClick();
                                }
                            });
                            confirmAllow.open();
                        });
                    } else {
                        $podeEfetivar.disable();
                    }
                    if (!item.indeferida && !item.pendente) {
                        $indefere.click(function () {
                            var confirmNotAllow = new ConfirmJS({
                                'theme': "warning",
                                'content': "Tem certeza que deseja indeferir esta inscrição?",
                                'buttons': {
                                    'yes': {
                                        'clickFunction': function (event, modal) {
                                            location.href = '${url_indefere}' + item.id;
                                        }
                                    },
                                    'cancel': false
                                },
                                'postAppendContent': function (modal) {
                                    modal.find('.main-action').lockOnClick();
                                }
                            });
                            confirmNotAllow.open();
                        });
                    } else {
                        $indefere.disable();
                    }
                    $btnGroup.
                            append($visualizar).
                            append($editar).
                            append($podeAprovar).
                            append($reabreEdicao).
                            append($podeEfetivar).
                            append($indefere);

                    $td.append($btnGroup);
                }
            },
            'ordering': {
                enabled: true,
                orderBy: 'pessoa.nome',
                orderMode: 'asc'
            }
        });
    });
</script>
