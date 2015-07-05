<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>


<mocca:title title="Inscrições Abertas"/>

<c:choose>
    <c:when test="${empty edicoes}">
        <see:notice type="danger" closeable="false">Nenhuma edição com inscrição aberta foi encontrada</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable bordered rounded shadowed">
            <table class="table striped hovered stroked small-font-size">
                <thead class="header">
                    <tr>
                        <th class="centered" style="width: 10em;"><fmt:message key="label.options"/></th>
                        <th><fmt:message key="label.event"/></th>
                        <th class="centered"><fmt:message key="label.number"/></th>
                        <th style="width: 20em;"><fmt:message key="label.theme"/></th>
                        <th class="centered"><fmt:message key="label.subscriptionPeriod"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${edicoes}" var="edicao">
                        <tr>
                            <td class="centered">
                                <c:url var="inscricao_url" value="/guest/formInscricao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                                <button type="button" class="btn small" title="Fazer inscrição" onclick="location.href = '${inscricao_url}';"><i class="icon-ok"></i> Inscrever-se</button>
                            </td>
                            <td>${edicao.evento.nome}</td>
                            <td class="centered">${edicao.numero}</td>
                            <td>${edicao.tema}</td>
                            <td class="centered">
                                <fmt:formatDate value="${edicao.periodoInscricao.start.time}" pattern="dd/MM/yyyy"/> - <fmt:formatDate value="${edicao.periodoInscricao.end.time}" pattern="dd/MM/yyyy"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <see:formButtonGroup putSubmit="false" backUrl="../index.html"/>
    </c:otherwise>
</c:choose>
