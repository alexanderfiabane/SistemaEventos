<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Cadastrar Evento" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<msf:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <form:hidden path="id"/>
    <div class="row">
        <div class="span4">
            <see:formField label="label.eventname" isLabelKey="true" isMandatory="true" path="nome" maxlength="100" inputClass="textfield width-100"/>
        </div>
        <div class="span4">
            <see:formField label="label.eventacronym" isLabelKey="true" isMandatory="true" path="sigla" maxlength="30" inputClass="textfield width-100"/>
        </div>
        <div class="span4">
            <see:formField label="label.eventsite" isLabelKey="true" isMandatory="true" path="site" maxlength="150" inputClass="textfield width-100"/>
        </div>
    </div>
    <see:formButtonGroup formUrl="/admin/formEvento.html"/>
</form:form>

<c:choose>
    <c:when test="${empty eventos}">
        <see:notice type="info" closeable="true">Nenhum evento foi encontrado</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable bordered rounded shadowed">
            <table class="table striped hovered stroked">
                <thead class="header">
                    <tr>
                        <th class="centered" style="width: 10em;"><msf:message key="label.options"/></th>
                        <th class="centered" style="width: 30em;"><msf:message key="label.name"/></th>
                        <th class="centered"><msf:message key="label.acronym"/></th>
                        <th class="centered"><msf:message key="label.site"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${eventos}" var="evento">
                        <c:url var="edit_url" value="/admin/formEvento.html"><c:param name="idEvento" value="${evento.id}"/></c:url>
                        <c:url var="delete_url" value="/admin/deleteEvento.html"><c:param name="idEvento" value="${evento.id}"/></c:url>
                        <c:url var="edicao_url" value="/admin/formEdicao.html"><c:param name="idEvento" value="${evento.id}"/></c:url>
                            <tr>
                                <td class="centered">
                                    <div class="btn-group">
                                        <button type="button" class="btn small" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
                                    <button type="button" class="btn small" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
                                    <button type="button" class="btn small" title="Criar edição deste evento" onclick="location.href = '${edicao_url}';"><i class="icon-folder-open"></i></button>
                                </div>
                            </td>
                            <td class="centered">${evento.nome}</td>
                            <td class="centered">${evento.sigla}</td>
                            <td class="centered">${evento.site}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>
<script type="text/javascript">
    onload = function () {
        document.getElementById("nome").focus();
    };
</script>
