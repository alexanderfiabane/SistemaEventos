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
                        <th class="align-right"><fmt:message key="label.vacancies"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${edicoes}" var="edicao">
                        <tr>
                            <td class="centered">
                                <c:url var="inscricao_url" value="/guest/formInscricao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                                <!--Colocar botão com título vagas encerradas-->
                                <c:choose>
                                    <c:when test="${edicao.vagasOcupadas < edicao.vagas}">
                                        <button type="button" class="btn small" title="Fazer inscrição" onclick="location.href = '${inscricao_url}';"><i class="icon-ok"></i> Inscrever-se</button>                                                                        
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" class="btn small" title="Fazer inscrição" onclick="location.href = '${inscricao_url}';" disabled><i class="icon-ok"></i> Vagas Encerradas</button>                                                                        
                                    </c:otherwise>                                    
                                </c:choose>
                            </td>
                            <td>${edicao.evento.nome}</td>
                            <td class="centered">${edicao.numero}</td>
                            <td>${edicao.tema}</td>
                            <td class="centered">
                                <fmt:formatDate value="${edicao.periodoInscricao.start.time}" pattern="dd/MM/yyyy"/> - <fmt:formatDate value="${edicao.periodoInscricao.end.time}" pattern="dd/MM/yyyy"/>
                            </td>
                            <td class="align-right">${edicao.saldoVagas}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <c:url var="backUrl" value="../index.html"></c:url>
        <see:formButtonGroup putSubmit="false" backUrl="${backUrl}"/>
    </c:otherwise>
</c:choose>
