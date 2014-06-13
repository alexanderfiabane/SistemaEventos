<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="label.menu.usersubscriptions" isLabelKey="true" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconMinhasInscricoes.png"/></msf:icon>
        <msf:breadcrumb label="label.page.mainMenu"><msf:url><c:url value="/user/menu.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<c:choose>
    <c:when test="${empty inscricoes}">
        <bs:notice type="info" closeable="true">Você não possui inscrições</bs:notice>
    </c:when>
    <c:otherwise>
        <div class="row-fluid">
            <display:table id="inscricao" name="inscricoes" pagesize="10" requestURI="/user/listUsuarioInscricoes.html" class="table table-striped table-condensed">                        
                <c:url var="inscricao_pendente_url" value="/user/formUsuarioInscricao.html"><c:param name="idInscricao" value="${inscricao.id}"/></c:url>
                <c:url var="inscricao_confirmacao_url" value="/user/inscricaoUsuarioSuccess.html"><c:param name="idInscricao" value="${inscricao.id}"/></c:url>
                <c:url var="inscricao_imprimir_url" value="/user/fichaInscricao.html"><c:param name="idInscricao" value="${inscricao.id}"/></c:url>
                <c:url var="inscricao_pagamento_url" value="/user/formPagamento.html"><c:param name="idInscricao" value="${inscricao.id}"/></c:url>
                <display:column media="html" titleKey="label.options" class="centered" headerClass="centered">
                    <c:choose>
                        <c:when test="${inscricao.podeAnalisar}">
                            <button type="button" class="btn btn-mini" title="Editar inscrição" onclick="location.href = '${inscricao_pendente_url}';"><i class="icon-edit"></i></button>
                            <button type="button" class="btn btn-mini" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>                            
                        </c:when>
                        <c:when test="${inscricao.podeAprovar}">                                
                            <button type="button" class="btn btn-mini" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>                            
                            <button type="button" class="btn btn-mini" title="Imprimir inscrição" onclick="location.href = '${inscricao_imprimir_url}';"><i class="icon-print"></i></button>                            
                        </c:when>
                        <c:when test="${inscricao.podeEfetivar}">
                            <button type="button" class="btn btn-mini" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>                            
                            <button type="button" class="btn btn-mini" title="Imprimir inscrição" onclick="location.href = '${inscricao_imprimir_url}';"><i class="icon-print"></i></button>
                            <button type="button" class="btn btn-mini" title="Pagar inscrição" onclick="location.href = '${inscricao_pagamento_url}';"><i class="icon-barcode"></i></button>                            
                        </c:when>
                        <c:when test="${inscricao.efetivada}">
                            <button type="button" class="btn btn-mini" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>
                            <button type="button" class="btn btn-mini" title="Imprimir inscrição" onclick="location.href = '${inscricao_imprimir_url}';"><i class="icon-print"></i></button>
                        </c:when>
                        <c:when test="${inscricao.indeferida}">
                            <button type="button" class="btn btn-mini" title="Visualizar inscrição" onclick="location.href = '${inscricao_confirmacao_url}';"><i class="icon-eye-open"></i></button>
                            <button type="button" class="btn btn-mini" title="Imprimir inscrição" onclick="location.href = '${inscricao_imprimir_url}';"><i class="icon-print"></i></button>
                        </c:when>
                    </c:choose>
                </display:column>
                <display:column titleKey="label.event" value="${inscricao.edicaoEvento.evento.nome}" class="centered" headerClass="centered"/>
                <display:column titleKey="label.number" value="${inscricao.edicaoEvento.numero}ª" class="centered" headerClass="centered"/>
                <display:column titleKey="label.theme" value="${inscricao.edicaoEvento.tema}" class="centered" headerClass="centered"/>                        
                <display:column titleKey="label.subscriptionstatus" value="${inscricao.status.value}" class="centered" headerClass="centered"/>                        
                <display:column titleKey="label.subscriptionPeriod" media="html" class="centered" headerClass="centered">
                    de <msf:formatPeriod value="${inscricao.edicaoEvento.periodoInscricao}" pattern="i18n.dateFormat.java" isPatternKey="true"/>
                </display:column>
            </display:table>
        </div>
    </c:otherwise>  
</c:choose>      

