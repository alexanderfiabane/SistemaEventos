<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="label.page.managesubscription.event" isTitleKey="true"/>

<c:choose>
    <c:when test="${empty eventos}">
        <see:notice type="info" closeable="true">Não há eventos cadastrados</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable bordered rounded shadowed">
            <table class="table striped hovered stroked small-font-size">
                <thead class="header">
                    <tr>
                        <th class="centered" style="width: 8em;"><fmt:message key="label.options"/></th>
                        <th class="centered"><fmt:message key="label.name"/></th>
                        <th class="centered" style="width: 18em;"><fmt:message key="label.acronym"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${eventos}" var="evento">
                        <tr>
                            <td class="centered">
                                <c:url var="list_edicao_url" value="/admin/inscricao/listEdicao.html"><c:param name="idEvento" value="${evento.id}"/></c:url>
                                <button type="button" class="btn small" title="Ir para edições deste evento" onclick="location.href = '${list_edicao_url}';"><i class="icon-share-alt"></i></button>
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
<see:formButtonGroup putSubmit="false" backUrl="../menu.html"/>
