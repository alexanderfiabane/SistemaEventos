<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Gerenciamento de Inscrições (${edicao.tema})" isTitleKey="false"/>

<%--<c:choose>
    <c:when test="${empty inscricoes}">
        <see:notice type="info" closeable="false">Nenhuma inscrição encontrada</see:notice>
    </c:when>
    <c:otherwise>--%>
        <div id="searchParams" class="box bordered rounded control">
            <div class="row">
                <div class="span9">
                    <label class="label">
                        <fmt:message key="label.name"/>
                    </label>
                    <input name="inscricao.confraternista.pessoa.nome" type="text" class="textfield" title="Nome do confraternista"/>
                </div>
                <div class="span3">
                    <label class="label">
                        Data que enviou inscrição
                    </label>
                    <input type="text" name="inscricao.dataInscricao" class="textfield date" title="Data de envio da inscrição"/>
                </div>
            </div>
            <div class="row">
                <div class="span3">
                    <label class="label">
                        <fmt:message key="label.subscriptiontype"/>
                    </label>
                    <select name="inscricao.confraternista.tipo" title="Tipo de inscrição">
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
                    <select name="inscricao.status.value" title="Situação da inscrição">
                        <option value="">Selecione a situação da inscrição</option>
                        <c:forEach items="${inscricaoStatus}" var="status">
                            <option value="${status.value}">${status.value}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="span3">
                    <label class="label">
                        <fmt:message key="label.paymentnumber"/>
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
                <button type="button" class="btn control bold table-refresh" name="search">Pesquisar</button>
            </div>
        </div>
        <div class="row">
            <fieldset class="box rounded bordered shadowed no-margin">
                <legend class="label">Resultados</legend>
                <div id="paginationWrapper" class="table-wrapper scrollable">
                    <table class="table small-font-size">
                        <thead class="header">
                            <tr>
                                <th class="centered"><fmt:message key="label.options"/></th>
                                <th data-name="${inscricao.confraternista.pessoa.nome}" class="centered"><fmt:message key="label.name"/></th>
                                <th data-name="${inscricao.confraternista.tipo.descricao}" class="centered"><fmt:message key="label.subscriptiontype"/></th>
                                <th data-name="${inscricao.status.value}" class="centered"><fmt:message key="label.subscriptionstatus"/></th>
                                <th data-name="${inscricao.valor}" class="centered"><fmt:message key="label.subscriptionvalue"/></th>
                                <th data-name="" class="centered"><fmt:message key="label.paymentdate"/></th>
                                <th data-name="${inscricao.pagamento.numeroDocumento}" class="centered"><v:message key="label.paymentnumber"/></th>
                        <th data-name="${inscricao.pagamento.valor}" class="centered"><fmt:message key="label.paymentvalue"/></th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr data-role="tableRow">
                                <td>
                                    <c:url var="url_view" value="/admin/inscricao/view.html">
                                        <c:param name="idInscricao" value="${inscricao.id}"/>
                                    </c:url>
                                    <c:url var="url_edit" value="/admin/inscricao/form.html">
                                        <c:param name="idInscricao" value="${inscricao.id}"/>
                                    </c:url>
                                    <c:url var="url_aprova" value="/admin/inscricao/aprova.html">
                                        <c:param name="idInscricao" value="${inscricao.id}"/>
                                    </c:url>
                                    <c:url var="url_efetiva" value="/admin/inscricao/efetiva.html">
                                        <c:param name="idInscricao" value="${inscricao.id}"/>
                                    </c:url>
                                    <c:url var="url_indefere" value="/admin/inscricao/indefere.html">
                                        <c:param name="idInscricao" value="${inscricao.id}"/>
                                    </c:url>
                                    <c:url var="url_reabre" value="/admin/inscricao/reabre.html">
                                        <c:param name="idInscricao" value="${inscricao.id}"/>
                                    </c:url>
                                    <div class="btn-group">
                                        <button type="button" class="btn mini" title="Visualizar Inscrição" id="detalhesInscricao" onclick="location.href = '${url_view}';"><i class="icon-eye-open"></i></button>
                                        <button  type="button" class="btn mini" title="Editar Inscrição" id="editarInscricao" onclick="location.href = '${url_edit}';"><i class="icon-edit"></i></button>
                                            <c:choose>
                                                <c:when test="${inscricao.podeAprovar}">
                                                <button type="button" class="btn mini" title="Confirmar Inscrição " id="aprovarInscricao" onclick="location.href = '${url_aprova}';"><i class="icon-check"></i></button>
                                                <button type="button" class="btn mini" title="Reabrir para Edição" id="reabreInscricao" onclick="location.href = '${url_reabre}';"><i class="icon-share"></i></button>
                                                </c:when>
                                                <c:when test="${inscricao.podeEfetivar}">
                                                <button type="button" class="btn mini" title="Efetivar Inscrição" id="efetivarInscricao" onclick="location.href = '${url_efetiva}';"><i class="icon-thumbs-up"></i></button>
                                                </c:when>
                                            </c:choose>
                                            <c:if test="${not inscricao.indeferida}">
                                            <button type="button" class="btn mini" title="Indeferir Inscrição" id="indeferirInscricao" onclick="location.href = '${url_indefere}';"><i class="icon-thumbs-down"></i></button>
                                            </c:if>
                                    </div>
                                </td>
                                <td>${inscricao.confraternista.pessoa.nome}</td>
                                <td>${inscricao.confraternista.tipo.descricao}</td>
                                <td>${inscricao.status.value}</td>
                                <td><fmt:formatNumber value="${inscricao.valor}" type="currency" currencySymbol="R$" minFractionDigits="2"/></td>
                                <td><fmt:formatDate value="${inscricao.pagamento.data}" pattern="dd/MM/yyyy" /></td>
                                <td>${inscricao.pagamento.numeroDocumento}</td>
                                <td><fmt:formatNumber value="${inscricao.pagamento.valor}" type="currency" currencySymbol="R$" minFractionDigits="2"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </fieldset>
        </div>
                            <%--
    </c:otherwise>
</c:choose>
                            --%>
<see:formButtonGroup putSubmit="false" backUrl="listEdicao.html?idEvento=${edicao.evento.id}"/>
<script type="text/javascript">
    $(document).ready(function() {
        $('#resultado').ajaxTable({
            ajaxTable: ${ajaxService},
            refreshOnCreate: true,
            hideOnCreate: false,
            externalParams: {
                containerId: 'searchParams'
            }
        });
    });
</script>
