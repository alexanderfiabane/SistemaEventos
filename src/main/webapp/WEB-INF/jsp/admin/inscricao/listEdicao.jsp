<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Gerenciamento de Inscrições - Edições (${evento.sigla})" isTitleKey="false"/>

<c:choose>
    <c:when test="${empty edicoes}">
        <see:notice type="info" closeable="true">Não há edições cadastradas para este evento</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable bordered rounded shadowed">
            <table class="table striped hovered stroked">
                <thead class="header">
                    <tr>
                        <th class="align-center" style="width: 6em;"><fmt:message key="label.options"/></th>
                        <th class="align-center" style="width: 3em;">#</th>
                        <th class="align-center"><fmt:message key="label.theme"/></th>
                        <th class="align-center" style="width: 6em;"><fmt:message key="label.vacancies"/></th>
                        <th class="align-center" style="width: 8em;"><fmt:message key="label.subscriptionValue"/></th>
                        <th class="align-center" style="width: 12em;"><fmt:message key="label.subscriptionPeriod"/></th>
                        <th class="align-center" style="width: 8em;"><fmt:message key="label.subscriptionDate"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${edicoes}" var="edicao">
                        <tr>
                            <td class="centered">
                                <c:url var="list_url" value="/admin/inscricao/list.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                                <button type="button" class="btn small" title="Ir para inscrições desta edição - ${edicao.tema}" onclick="location.href = '${list_url}';"><i class="icon-share-alt"></i></button>
                            </td>
                            <td class="align-right">${edicao.numero}</td>
                            <td>${edicao.tema}</td>
                            <td class="align-right">${edicao.vagas}</td>
                            <td class="align-right">${edicao.valorInscricao}</td>
                            <td class="centered">de <javalek:formatPeriod value="${edicao.periodoInscricao}" pattern="i18n.dateFormat.java" isPatternKey="true"/></td>
                            <td class="centered"><javalek:formatDate value="${edicao.data}" pattern="i18n.dateFormat.java" isPatternKey="true"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

