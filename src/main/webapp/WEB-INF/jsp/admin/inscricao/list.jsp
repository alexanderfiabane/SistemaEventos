<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Gerenciamento de Inscrições (${edicao.tema})" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconAnaliseInscricoes.png"/></msf:icon>
        <msf:breadcrumb label="label.page.managesubscription.subscription"><msf:url><c:url value="/admin/inscricao/listEdicao.html?idEvento=${edicao.evento.id}"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<c:choose>
    <c:when test="${empty inscricoes}">
        <bs:notice type="info" closeable="true">Nenhuma inscrição encontrada</bs:notice>
    </c:when>
    <c:otherwise>        
        <div class="row-fluid">
            <%--
            <!--DisplayTag-->
            <div class="centeredDivOuter" style="width: 80%;">
                <div class="centeredDivInner">
                    <display:table id="inscricao" name="inscricoes" export="false" style="width: 100%;" requestURI="/admin/inscricao/list.html" class="table table-striped table-condensed">
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
                        <display:column media="html" titleKey="label.options" style="width: 240px;" class="threeOption centered" headerClass="centered">
                            <button type="button" class="find notext" title="Detalhes" id="detalhesInscricao" onclick="location.href='${url_view}';">Detalhar Inscrição</button>
                            <button  type="button" class="btn btn-mini" title="Editar" id="editarInscricao" onclick="location.href='${url_edit}';">Editar Inscrição</button>
                            <c:choose>
                                <c:when test="${inscricao.podeAprovar}">
                                    <button type="button" class="confirm notext" title="Aprovar" id="aprovarInscricao" onclick="location.href='${url_aprova}';">Aprovar Inscrição</button>
                                    <button type="button" class="open notext" title="Reabre" id="reabreInscricao" onclick="location.href='${url_reabre}';">Reabre Inscrição</button>
                                </c:when>
                                <c:when test="${inscricao.podeEfetivar}">
                                    <button type="button" class="confirm notext" title="Efetivar" id="efetivarInscricao" onclick="location.href='${url_efetiva}';">Efetivar Inscrição</button>
                                </c:when>
                            </c:choose>
                            <button type="button" class="cancel notext" title="Indeferir" id="indeferirInscricao" onclick="location.href='${url_indefere}';">Indeferir Inscrição</button>
                        </display:column>
                        <display:column titleKey="label.name" property="confraternista.pessoa.nome" sortable="true" class="centered" headerClass="centered"/>
                        <display:column titleKey="label.subscriptiontype" property="confraternista.tipo.descricao" sortable="true" class="centered" headerClass="centered"/>
                        <display:column titleKey="label.subscriptionstatus" property="status.value" sortable="true" class="centered" headerClass="centered"/>
                        <display:column titleKey="label.subscriptionvalue" media="html" sortable="true" class="centered" headerClass="centered">
                            <fmt:formatNumber value="${inscricao.valor}" type="currency" currencySymbol="R$" minFractionDigits="2"/>
                        </display:column>
                        <display:column titleKey="label.paymentvalue" media="html" sortable="true" class="centered" headerClass="centered">
                            <fmt:formatDate var="dtPgto" value="${inscricao.pagamento.data.time}" pattern="dd/MM/yyyy"/>
                            <span title="${dtPgto} - ${inscricao.pagamento.numeroDocumento}" style="cursor: default;">
                                <fmt:formatNumber value="${inscricao.pagamento.valor}" type="currency" currencySymbol="R$" minFractionDigits="2"/>
                            </span>
                        </display:column>
                    </display:table>
                </div>
            </div>
            --%>
            <!--TableSort-->
            <div class="centeredDivOuter" style="width: 100%">
                <div class="centeredDivInner">                    
                    <table id="inscricoes" class="table table-striped table-condensed tablesorter">
                        <thead>
                            <tr>
                                <th style="text-align: center;" colspan="8">
                                    <msf:message key="label.subscriptions"/>
                                </th>
                            </tr>
                            <tr>
                                <th><msf:message key="label.options"/></th>
                                <th><msf:message key="label.name"/></th>
                                <th><msf:message key="label.subscriptiontype"/></th>
                                <th><msf:message key="label.subscriptionstatus"/></th>
                                <th><msf:message key="label.subscriptionvalue"/></th>
                                <th><msf:message key="label.paymentdate"/></th>
                                <th><msf:message key="label.paymentnumber"/></th>
                                <th><msf:message key="label.paymentvalue"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="inscricao" items="${inscricoes}">
                                <tr>
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
                                        <button type="button" class="btn btn-mini" title="Visualizar Inscrição" id="detalhesInscricao" onclick="location.href = '${url_view}';"><i class="icon-eye-open"></i></button>
                                        <button  type="button" class="btn btn-mini" title="Editar Inscrição" id="editarInscricao" onclick="location.href = '${url_edit}';"><i class="icon-edit"></i></button>
                                        <c:choose>
                                            <c:when test="${inscricao.podeAprovar}">
                                                <button type="button" class="btn btn-mini" title="Confirmar Inscrição " id="aprovarInscricao" onclick="location.href = '${url_aprova}';"><i class="icon-check"></i></button>
                                                <button type="button" class="btn btn-mini" title="Reabrir para Edição" id="reabreInscricao" onclick="location.href = '${url_reabre}';"><i class="icon-share"></i></button>
                                            </c:when>
                                            <c:when test="${inscricao.podeEfetivar}">
                                                <button type="button" class="btn btn-mini" title="Efetivar Inscrição" id="efetivarInscricao" onclick="location.href = '${url_efetiva}';"><i class="icon-thumbs-up"></i></button>
                                            </c:when>
                                        </c:choose>
                                        <button type="button" class="btn btn-mini" title="Indeferir Inscrição" id="indeferirInscricao" onclick="location.href = '${url_indefere}';"><i class="icon-thumbs-down"></i></button>
                                    </td>
                                    <td>
                                        ${inscricao.confraternista.pessoa.nome}
                                    </td>
                                    <td>
                                        ${inscricao.confraternista.tipo.descricao}
                                    </td>
                                    <td>
                                        ${inscricao.status.value}
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${inscricao.valor}" type="currency" currencySymbol="R$" minFractionDigits="2"/>
                                    </td>
                                    <td>
                                        <msf:formatDate value="${inscricao.pagamento.data}" pattern="dd/MM/yyyy" />
                                    </td>
                                    <td>
                                        ${inscricao.pagamento.numeroDocumento}
                                    </td>
                                    <td>
                                        <span title="${dtPgto} - ${inscricao.pagamento.numeroDocumento}" style="cursor: default;">
                                            <fmt:formatNumber value="${inscricao.pagamento.valor}" type="currency" currencySymbol="R$" minFractionDigits="2"/>
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>                    
                </div>                
            </div>
        </div>
    </c:otherwise>
</c:choose>

<script type="text/javascript">    
    $(function(){        

        $("#inscricoes")

        // Initialize tablesorter
        // ***********************
        .tablesorter({
            theme: 'blue',
            widthFixed: true,
            widgets: ['zebra'],
            headers: {
                0: {sorter: false},
                1: {sorter: false}
            },
            sortList: [[1, 0]]
      
        });        
    });
</script>
