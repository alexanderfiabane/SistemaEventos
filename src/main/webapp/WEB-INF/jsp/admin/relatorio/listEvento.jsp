<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Relatórios - Lista de Eventos"/>
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
                        <th><fmt:message key="label.name"/></th>
                        <th style="width: 18em;"><fmt:message key="label.acronym"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${eventos}" var="evento">                        
                        <tr>
                            <td>
                                <c:url var="list_edicao_url" value="/admin/relatorio/listEdicao.html"><c:param name="idEvento" value="${evento.id}"/></c:url>   
                                <button type="button" class="btn small" title="Ir para edições deste evento" onclick="location.href = '${list_edicao_url}';"><i class="icon-share-alt"></i></button>                                                     
                            </td>
                            <td>${evento.nome}</td>
                            <td>${evento.sigla}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>           
        </div>
    </c:otherwise>  
</c:choose>

