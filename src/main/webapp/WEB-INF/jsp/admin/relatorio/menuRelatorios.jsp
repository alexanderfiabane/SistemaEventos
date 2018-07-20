<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Relatórios - (${edicao.tema})"/>

<mocca:menu>
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório Geral por Tipo de Inscrição" url="/admin/relatorio/reportConfTipo.html?idEdicao=${edicao.id}"/>
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório Geral por Cidades e Estado" url="/admin/relatorio/reportConfCidadeEstado.html?idEdicao=${edicao.id}"/>
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório Geral por Sexo" url="/admin/relatorio/reportConfSexo.html?idEdicao=${edicao.id}"/>
    <c:if test="${(edicao.tipo == 'OFICINA')}">
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório de confraternistas por Oficinas" url="/admin/relatorio/reportConfOficina.html?idEdicao=${edicao.id}"/>
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório de confraternistas por Oficinas - Oficineiros" url="/admin/relatorio/reportConfOficinaOficineiro.html?idEdicao=${edicao.id}"/>
    </c:if>
    <c:if test="${(edicao.tipo == 'FAIXA_ETARIA')}">
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório de confraternistas por Grupos" url="/admin/relatorio/reportConfGrupo.html?idEdicao=${edicao.id}"/>
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório de confraternistas por Grupos - Facilitadores" url="/admin/relatorio/reportConfGrupoFacilitador.html?idEdicao=${edicao.id}"/>
    </c:if>
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório de Confraternistas por Dormitório" url="/admin/relatorio/reportConfDormitorio.html?idEdicao=${edicao.id}"/>
    <mocca:menuItem iconClass="icon-file-text-alt" label="Lista de presença dos Confraternistas" url="/admin/relatorio/reportConfPresenca.html?idEdicao=${edicao.id}"/>
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório de Saúde e Dieta dos Confraternistas" url="/admin/relatorio/reportConfSaudeAlimentacao.html?idEdicao=${edicao.id}"/>
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório de camisetas dos Confraternistas" url="/admin/relatorio/reportConfCamiseta.html?idEdicao=${edicao.id}"/>
    <mocca:menuItem iconClass="icon-file-text-alt" label="Relatório de camisetas para encomenda" url="/admin/relatorio/reportEncomendaCamiseta.html?idEdicao=${edicao.id}"/>
    <c:if test="${edicao.configCracha.temCracha}">
        <mocca:menuItem iconClass="icon-file-text-alt" label="Crachás (Folha A3)" url="/admin/relatorio/reportCrachaA3.html?idEdicao=${edicao.id}"/>
        <mocca:menuItem iconClass="icon-file-text-alt" label="Crachás (Folha A4)" url="/admin/relatorio/reportCrachaA4.html?idEdicao=${edicao.id}"/>        
    </c:if>
</mocca:menu>
<c:url value="listEdicao.html" var="backUrl">
    <c:param name="idEvento" value="${edicao.evento.id}"/>
</c:url>
<see:formButtonGroup putSubmit="false" backUrl="${backUrl}"/>

