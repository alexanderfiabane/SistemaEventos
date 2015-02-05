<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Gerenciamento de Inscrições - Edições (${evento.sigla})" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconAnaliseInscricoes.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="label.page.managesubscription.event"><msf:url><c:url value="/admin/inscricao/listEvento.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<c:choose>
    <c:when test="${empty edicoes}">
        <see:notice type="info" closeable="true">Não há edições cadastradas para este evento</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable">
            <table class="table bordered rounded shadowed striped hovered stroked">
                <thead class="header">
                    <tr>
                        <th class="centered" style="width: 6em;"><msf:message key="label.options"/></th>
                        <th class="centered" style="width: 3em;">#</th>
                        <th class="centered"><msf:message key="label.theme"/></th>
                        <th class="centered" style="width: 6em;"><msf:message key="label.vacancies"/></th>
                        <th class="centered" style="width: 8em;"><msf:message key="label.subscriptionValue"/></th>
                        <th class="centered" style="width: 12em;"><msf:message key="label.subscriptionPeriod"/></th>
                        <th class="centered" style="width: 8em;"><msf:message key="label.subscriptionDate"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${edicoes}" var="edicao">
                        <tr>
                            <td class="centered">
                                <c:url var="list_url" value="/admin/inscricao/list.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                                <button type="button" class="btn small" title="Ir para inscrições desta edição" onclick="location.href = '${list_url}';"><i class="icon-folder-open"></i></button>
                            </td>
                            <td class="align-right">${edicao.numero}</td>
                            <td>${edicao.tema}</td>
                            <td class="align-right">${edicao.vagas}</td>
                            <td class="align-right">${edicao.valorInscricao}</td>
                            <td class="centered">de <msf:formatPeriod value="${edicao.periodoInscricao}" pattern="i18n.dateFormat.java" isPatternKey="true"/></td>
                            <td class="centered"><msf:formatDate value="${edicao.data}" pattern="i18n.dateFormat.java" isPatternKey="true"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

