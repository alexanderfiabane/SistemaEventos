<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Gerenciamento de Inscrições (${edicao.tema})" isTitleKey="false"/>

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
                    <option value="${tipoInscricao.descricao}">${tipoInscricao.descricao}</option>
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
                    <option value="${status.value}">${status.value}</option>
                </c:forEach>
            </select>
        </div>
        <div class="span3">
            <label class="label">
                <fmt:message key="label.paymentnumber"/> (pagamento)
            </label>
            <input name="inscricao.pagamento.numeroDocumento" type="text" class="textfield" title="Número do documento"/>
        </div>
        <div class="span3">
            <label class="label">
                <fmt:message key="label.paymentdate"/>
            </label>
            <input name="inscricao.pagamento.data.time" type="text" class="textfield date" title="Data do pagamento da inscrição"/>
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
                <th ><fmt:message key="label.name"/></th>
                <th ><fmt:message key="label.subscriptiontype"/></th>
                <th ><fmt:message key="label.subscriptionstatus"/></th>
                <th ><fmt:message key="label.subscriptionDateReceive"/></th>
                <th ><fmt:message key="label.paymentdate"/></th>
                <th ><fmt:message key="label.paymentnumber"/></th>                
            </tr>
        </thead>
        <tbody>
            <tr data-role="tableRow">
                <td class="centered">
                    <c:url var="url_view" value="view.html">
                        <c:param name="idInscricao" value=""/>@{id}
                    </c:url>
                    <c:url var="url_edit" value="form.html">
                        <c:param name="idInscricao" value=""/>@{id}
                    </c:url>
                    <c:url var="url_aprova" value="aprova.html">
                        <c:param name="idInscricao" value=""/>@{id}
                    </c:url>
                    <c:url var="url_efetiva" value="efetiva.html">
                        <c:param name="idInscricao" value=""/>@{id}
                    </c:url>
                    <c:url var="url_indefere" value="indefere.html">
                        <c:param name="idInscricao" value=""/>@{id}
                    </c:url>
                    <c:url var="url_reabre" value="reabre.html">
                        <c:param name="idInscricao" value=""/>@{id}
                    </c:url>
                    <div class="btn-group">
                        <button type="button" class="btn mini" title="Visualizar Inscrição" onclick="location.href = '${url_view}';"><i class="icon-eye-open"></i></button>
                        <button  type="button" class="btn mini" title="Editar Inscrição" onclick="location.href = '${url_edit}';"><i class="icon-edit"></i></button>
                            <c:choose>
                                <c:when test="@{podeAprovar}">
                                <button type="button" class="btn mini" title="Confirmar Inscrição " onclick="location.href = '${url_aprova}';"><i class="icon-check"></i></button>
                                <button type="button" class="btn mini" title="Reabrir para Edição" onclick="location.href = '${url_reabre}';"><i class="icon-share"></i></button>
                                </c:when>
                                <c:when test="@{podeEfetivar}">
                                <button type="button" class="btn mini" title="Efetivar Inscrição" onclick="location.href = '${url_efetiva}';"><i class="icon-thumbs-up"></i></button>
                                </c:when>
                            </c:choose>
                            <c:if test="@{not indeferida}">
                            <button type="button" class="btn mini" title="Indeferir Inscrição" onclick="location.href = '${url_indefere}';"><i class="icon-thumbs-down"></i></button>
                            </c:if>
                    </div>
                </td>
                <td>@{confraternista.pessoa.nome}</td>
                <td>@{confraternista.tipo.descricao}</td>
                <td>@{status.value}</td>
                <td>@{dataRecebimento|format=dd/MM/yyyy|ifBlank=Não informado}</td>
                <td>@{pagamento.data|format=dd/MM/yyyy|ifBlank=Não informado}</td>
                <td>@{pagamento.numeroDocumento|ifBlank=Não informado}</td>                
            </tr>
        </tbody>
    </table>
</div>
<see:formButtonGroup putSubmit="false" backUrl="listEdicao.html?idEvento=${edicao.evento.id}"/>
<script type="text/javascript" src="<c:url value="/dwr/interface/inscricaoAjaxService.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#paginationWrapper').ajaxTable({
            ajaxTable: inscricaoAjaxService,
            refreshOnCreate: true,
            hideOnCreate: false,
            externalParams: {
                containerId: 'searchParams'
            }
        });
    });
</script>
