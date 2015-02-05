<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Inscrições Abertas" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconInscricoesAbertas.png"/></msf:icon>
        <msf:breadcrumb label="label.page.mainMenu"><msf:url><c:url value="/index.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<c:choose>
    <c:when test="${empty edicoes}">
        <see:notice type="info" closeable="true">Nenhuma edição com inscrição aberta foi encontrada</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable bordered rounded shadowed">
            <table class="table striped hovered stroked">
                <thead class="header">
                    <tr>
                        <th class="centered" style="width: 10em;"><msf:message key="label.options"/></th>
                        <th class="centered"><msf:message key="label.event"/></th>
                        <th class="centered"><msf:message key="label.number"/></th>
                        <th class="centered" style="width: 20em;"><msf:message key="label.theme"/></th>
                        <th class="centered"><msf:message key="label.subscriptionPeriod"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${edicoes}" var="edicao">
                        <tr>
                            <td class="centered">
                                <c:url var="inscricao_url" value="/guest/formInscricao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                                <button type="button" class="btn small" title="Fazer inscrição" onclick="location.href = '${inscricao_url}';"><i class="icon-ok"></i> Inscrever-se</button>
                            </td>
                            <td class="centered">${edicao.evento.nome}</td>
                            <td class="centered">${edicao.numero}</td>
                            <td class="centered">${edicao.tema}</td>
                            <td class="centered">
                                de <msf:formatPeriod value="${edicao.periodoInscricao}" pattern="i18n.dateFormat.java" isPatternKey="true"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>
