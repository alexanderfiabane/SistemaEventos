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
        <bs:notice type="info" closeable="true">Nenhuma edição com inscrição aberta foi encontrada</bs:notice>
    </c:when>
    <c:otherwise>
        <div class="row-fluid">
            <display:table id="edicao" name="edicoes" pagesize="10" requestURI="/guest/listInscricoesAbertas.html" class="table table-striped table-condensed">                        
                <c:url var="inscricao_url" value="/guest/formInscricao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                <display:column media="html" titleKey="label.options" class="twoOption centered" headerClass="centered">
                    <!-- <button type="button" class="btn btn-mini" title="Fazer inscrição" onclick="location.href = '${inscricao_url}';"><i class="icon-plus"></i></button> -->
                    <button type="button" class="btn btn-mini" title="Fazer inscrição" onclick="location.href = '${inscricao_url}';"><i class="icon-ok"></i> Inscrever-se</button>                            
                </display:column>
                <display:column titleKey="label.event" value="${edicao.evento.nome}" class="centered" headerClass="centered"/>
                <display:column titleKey="label.number" value="${edicao.numero}" class="centered" headerClass="centered"/>
                <display:column titleKey="label.theme" value="${edicao.tema}" class="centered" headerClass="centered"/>                        
                <display:column titleKey="label.subscriptionPeriod" media="html" class="centered" headerClass="centered">
                    de <msf:formatPeriod value="${edicao.periodoInscricao}" pattern="i18n.dateFormat.java" isPatternKey="true"/>
                </display:column>
            </display:table>
        </div>
    </c:otherwise>  
</c:choose>      
