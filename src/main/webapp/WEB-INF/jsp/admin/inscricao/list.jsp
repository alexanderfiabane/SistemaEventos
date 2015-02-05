<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Gerenciamento de Inscrições (${edicao.tema})" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconAnaliseInscricoes.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="label.page.managesubscription.event" isLabelKey="true"><msf:url><c:url value="/admin/inscricao/listEvento.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="label.page.managesubscription.subscription" isLabelKey="true"><msf:url><c:url value="/admin/inscricao/listEdicao.html?idEvento=${edicao.evento.id}"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<c:choose>
    <c:when test="${empty inscricoes}">
        <see:notice type="info" closeable="true">Nenhuma inscrição encontrada</see:notice>
    </c:when>
    <c:otherwise>
        <div id="searchParams" class="box bordered rounded gradient control">
            <div class="row">
                <div class="span9">
                    <msf:label label="label.name" for="nome" cssClass="label" breakAfter="true" isLabelKey="true"/>
                    <input id="inscricao.confraternista.pessoa.nome" type="text" class="textfield"/>
                </div>
                <div class="span3">
                    <msf:label label="Data que enviou inscrição" for="nome" cssClass="label" breakAfter="true" isLabelKey="false"/>
                    <input id="inscricao.dataInscricao" type="date" class="textfield"/>
                </div>
            </div>
            <div class="row">
                <div class="span3">
                    <msf:label label="label.subscriptiontype" for="nome" cssClass="label" breakAfter="true" isLabelKey="true"/>
                    <select id="inscricao.confraternista.tipo.descricao" class="textfield">

                    </select>k
                </div>
                <div class="span3">
                    <msf:label label="label.subscriptionstatus" for="nome" cssClass="label" breakAfter="true" isLabelKey="true"/>
                    <select id="inscricao.status.value" class="textfield">

                    </select>
                </div>
                <div class="span3">
                    <msf:label label="label.paymentnumber" for="nome" cssClass="label" breakAfter="true" isLabelKey="true"/>
                    <input id="inscricao.pagamento.numeroDocumento" type="text" class="textfield"/>
                </div>
                <div class="span3">
                    <msf:label label="label.paymentdate" for="nome" cssClass="label" breakAfter="true" isLabelKey="true"/>
                    <input id="inscricao.pagamento.data.time" type="date" class="textfield"/>
                </div>
            </div>
            <div class="form-actions stroked-top mini-padding no-margin-bottom">
                <button type="button" class="btn primary table-refresh" name="search">Pesquisar</button>
            </div>
        </div>
        <div class="row">
            <fieldset class="box rounded bordered shadowed no-margin">
                <legend class="label">Resultados</legend>
                <div id="paginationWrapper" class="table-wrapper scrollable">
                    <table class="table hovered stroked striped nowrap">
                        <thead class="header">
                            <tr>
                                <th class="centered"><msf:message key="label.options"/></th>
                                <th data-name="${inscricao.confraternista.pessoa.nome}" class="centered"><msf:message key="label.name"/></th>
                                <th data-name="${inscricao.confraternista.tipo.descricao}" class="centered"><msf:message key="label.subscriptiontype"/></th>
                                <th data-name="${inscricao.status.value}" class="centered"><msf:message key="label.subscriptionstatus"/></th>
                                <th data-name="${inscricao.valor}" class="centered"><msf:message key="label.subscriptionvalue"/></th>
                                <th data-name="${inscricao.pagamento.data}" class="centered"><msf:message key="label.paymentdate"/></th>
                                <th data-name="${inscricao.pagamento.numeroDocumento}" class="centered"><msf:message key="label.paymentnumber"/></th>
                                <th data-name="${inscricao.pagamento.valor}" class="centered"><msf:message key="label.paymentvalue"/></th>
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
                                <td><msf:formatDate value="${inscricao.pagamento.data}" pattern="dd/MM/yyyy" /></td>
                                <td>${inscricao.pagamento.numeroDocumento}</td>
                                <td><fmt:formatNumber value="${inscricao.pagamento.valor}" type="currency" currencySymbol="R$" minFractionDigits="2"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </fieldset>
        </div>
    </c:otherwise>
</c:choose>

<script type="text/javascript">
    $(document).ready(function () {
        $('#resultado').ajaxTable({
            ajaxTable: ${ajaxService},
            refreshOnCreate: false,
            hideOnCreate: true,
            externalParams: {
                containerId: 'searchParams'
            }
        });
    });
</script>
