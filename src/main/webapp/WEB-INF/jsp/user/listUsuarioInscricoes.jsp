<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="label.menu.usersubscriptions" isTitleKey="true"/>

<c:choose>
    <c:when test="${empty inscricoes}">
        <see:notice type="info" closeable="true">Você não possui inscrições</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable bordered rounded shadowed">
            <table class="table striped hovered stroked small-font-size">
                <thead class="header">
                    <tr>
                        <th style="width: 6em;" class="centered"><fmt:message key="label.options"/></th>
                        <th><fmt:message key="label.event"/></th>
                        <th style="width: 2em;"><fmt:message key="label.number"/></th>
                        <th><fmt:message key="label.theme"/></th>
                        <th style="width: 10em;"><fmt:message key="label.subscriptionstatus"/></th>
                        <th style="width: 8em;" class="centered"><fmt:message key="label.subscriptionDate"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${inscricoes}" var="inscricao">
                    <tr>
                        <td class="centered">
                            <c:url var="inscricao_pendente_url" value="/user/formUsuarioInscricao.html"><c:param name="idInscricao" value="${inscricao.id}"/></c:url>
                            <c:url var="inscricao_confirmacao_url" value="/user/inscricaoUsuarioSuccess.html"><c:param name="idInscricao" value="${inscricao.id}"/></c:url>
                            <c:url var="inscricao_imprimir_url" value="/user/fichaInscricao.html"><c:param name="idInscricao" value="${inscricao.id}"/></c:url>
                            <c:url var="inscricao_pagamento_url" value="/user/formPagamento.html"><c:param name="idInscricao" value="${inscricao.id}"/></c:url>
                            <div class="btn-group small">
                            <c:choose>
                                <c:when test="${inscricao.podeAnalisar}">
                                    <button type="button" class="btn" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>
                                    <button type="button" class="btn" title="Editar inscrição" onclick="location.href = '${inscricao_pendente_url}';"><i class="icon-edit"></i></button>
                                </c:when>
                                <c:when test="${inscricao.podeEfetivar && not inscricao.edicaoEvento.formaCobranca.semCobranca}">
                                    <button type="button" class="btn" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>
                                    <button type="button" class="btn" title="Imprimir inscrição" onclick="location.href = '${inscricao_imprimir_url}';"><i class="icon-print"></i></button>
                                    <button type="button" class="btn" title="Pagar inscrição" onclick="location.href = '${inscricao_pagamento_url}';"><i class="icon-dollar"></i></button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="btn" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>
                                    <button type="button" class="btn" title="Imprimir inscrição" onclick="location.href = '${inscricao_imprimir_url}';"><i class="icon-print"></i></button>
                                </c:otherwise>
                                    <%--
                                <c:when test="${inscricao.podeAprovar}">
                                    <button type="button" class="btn" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>
                                    <button type="button" class="btn" title="Imprimir inscrição" onclick="location.href = '${inscricao_imprimir_url}';"><i class="icon-print"></i></button>
                                </c:when>
                                <c:if test="${inscricao.efetivada}">
                                    <button type="button" class="btn" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>
                                    <button type="button" class="btn" title="Imprimir inscrição" onclick="location.href = '${inscricao_imprimir_url}';"><i class="icon-print"></i></button>
                                </c:if>
                                <c:if test="${inscricao.indeferida}">
                                    <button type="button" class="btn" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>
                                    <button type="button" class="btn" title="Imprimir inscrição" onclick="location.href = '${inscricao_imprimir_url}';"><i class="icon-print"></i></button>
                                </c:if>
                                    --%>
                            </c:choose>
                            </div>
                        </td>
                        <td>${inscricao.edicaoEvento.evento.nome}</td>
                        <td class="align-right">${inscricao.edicaoEvento.numero}</td>
                        <td>${inscricao.edicaoEvento.tema}</td>
                        <td>${inscricao.status.value}</td>
                        <td class="centered"><fmt:formatDate value="${inscricao.edicaoEvento.data.time}" pattern="dd/MM/yyyy"/></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

