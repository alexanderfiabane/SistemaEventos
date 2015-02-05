<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Relatórios - Lista de Edições (${evento.nome})" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
        <msf:breadcrumb label="label.page.mainMenu"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Relatórios - Lista de Eventos" isLabelKey="false"><msf:url><c:url value="/admin/relatorio/listEvento.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<c:choose>
    <c:when test="${empty edicoes}">
        <see:notice type="info" closeable="true">Não há edições cadastradas para este evento</see:notice>
    </c:when>
    <c:otherwise>
        <div class="row-fluid">
            <display:table id="edicao" name="edicoes" pagesize="10" requestURI="/admin/relatorio/listEdicao.html"  class="table table-striped table-condensed">
                <c:url var="list_url" value="/admin/relatorio/menuRelatorios.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>                
                <display:column media="html" titleKey="label.options" class="centered" headerClass="twoOption centered">
                    <button type="button" class="btn btn-mini" title="Ir para inscrições desta edição" onclick="location.href = '${list_url}';"><i class="icon-plus"></i></button>                   
                </display:column>
                <display:column titleKey="label.number" property="numero" class="centered" headerClass="centered"/>
                <display:column titleKey="label.theme" property="tema" class="centered" headerClass="centered"/>
                <display:column titleKey="label.vacancies" property="vagas" class="centered" headerClass="centered"/>
                <display:column titleKey="label.subscriptionValue" property="valorInscricao" class="centered" headerClass="centered"/>                
                <display:column titleKey="label.subscriptionPeriod" media="html" class="centered" headerClass="centered">
                    de <msf:formatPeriod value="${edicao.periodoInscricao}" pattern="i18n.dateFormat.java" isPatternKey="true"/>
                </display:column>
            </display:table>
        </div>
    </c:otherwise>  
</c:choose>

