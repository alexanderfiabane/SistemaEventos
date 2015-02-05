<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="label.page.managesubscription.event" isLabelKey="true" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconAnaliseInscricoes.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<c:choose>
    <c:when test="${empty eventos}">
        <see:notice type="info" closeable="true">Não há eventos cadastrados</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable">
            <table class="table bordered rounded shadowed striped hovered stroked">
                <thead class="header">
                    <tr>
                        <th class="centered"><msf:message key="label.options"/></th>
                        <th class="centered"><msf:message key="label.name"/></th>
                        <th class="centered"><msf:message key="label.acronym"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${eventos}" var="evento">
                        <tr>
                            <td class="centered">
                                <c:url var="list_edicao_url" value="/admin/inscricao/listEdicao.html"><c:param name="idEvento" value="${evento.id}"/></c:url>
                                <button type="button" class="btn small" title="Ir para edições deste evento" onclick="location.href = '${list_edicao_url}';"><i class="icon-folder-open"></i></button>
                            </td>
                            <td>${evento.nome}</td>
                            <td class="centered">${evento.sigla}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

