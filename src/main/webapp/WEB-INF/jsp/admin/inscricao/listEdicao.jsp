<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Gerenciamento de Inscrições - Lista de Edições (${evento.sigla})" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconAnaliseInscricoes.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="label.page.managesubscription.event"><msf:url><c:url value="/admin/inscricao/listEvento.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<c:choose>
    <c:when test="${empty edicoes}">
        <bs:notice type="info" closeable="true">Não há edições cadastradas para este evento</bs:notice>
    </c:when>
    <c:otherwise>
        <div class="row-fluid">
            <display:table id="edicao" name="edicoes" pagesize="10" requestURI="/admin/inscricao/listEdicao.html"  class="table table-striped table-condensed">
                <c:url var="list_url" value="/admin/inscricao/list.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>                
                <display:column media="html" titleKey="label.options" class="centered" headerClass="twoOption centered">
                    <button type="button" class="btn btn-mini" title="Ir para inscrições desta edição" onclick="location.href = '${list_url}';"><i class="icon-share-alt"></i></button>                   
                </display:column>
                <display:column titleKey="label.number" property="numero" class="centered" headerClass="centered"/>
                <display:column titleKey="label.theme" property="tema" class="centered" headerClass="centered"/>
                <display:column titleKey="label.vacancies" property="vagas" class="centered" headerClass="centered"/>
                <display:column titleKey="label.subscriptionValue" property="valorInscricao" class="centered" headerClass="centered"/>                
                <display:column titleKey="label.subscriptionPeriod" media="html" class="centered" headerClass="centered">
                    de <msf:formatPeriod value="${edicao.periodoInscricao}" pattern="i18n.dateFormat.java" isPatternKey="true"/>
                </display:column>
                <display:column titleKey="label.subscriptionDate" media="html" class="centered" headerClass="centered">
                    <msf:formatDate value="${edicao.data}" pattern="i18n.dateFormat.java" isPatternKey="true"/>
                </display:column>    
            </display:table>
        </div>
    </c:otherwise>  
</c:choose>

