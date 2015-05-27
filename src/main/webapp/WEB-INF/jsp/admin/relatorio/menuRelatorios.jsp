<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Relat�rios  - (${edicao.tema})" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Relat�rios - Lista de Eventos" isLabelKey="false"><javalek:url><c:url value="/admin/relatorio/listEvento.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Relat�rios - Lista de Edi��es" isLabelKey="false"><javalek:url><c:url value="/admin/relatorio/listEdicao.html?idEvento=${edicao.evento.id}"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <javalek:menu id="menu" defaultIconSize="64" itensPerRow="4" >

            <javalek:menuItem label="Relat�rio Geral por Tipo de Inscri��o">
                <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/relatorio/reportConfTipo.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Relat�rio Geral por Cidades e Estado">
                <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/relatorio/reportConfCidadeEstado.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Relat�rio Geral por Sexo">
                <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/relatorio/reportConfSexo.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>
            <c:if test="${(edicao.tipo == 'OFICINA')}">
                <javalek:menuItem label="Relat�rio de confraternistas por Oficinas">
                    <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                    <javalek:url><c:url value="/admin/relatorio/reportConfOficina.html?idEdicao=${edicao.id}"/></javalek:url>
                </javalek:menuItem>
            </c:if>
            <c:if test="${(edicao.tipo == 'OFICINA')}">
                <javalek:menuItem label="Relat�rio de confraternistas por Oficinas - Oficineiros">
                    <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                    <javalek:url><c:url value="/admin/relatorio/reportConfOficinaOficineiro.html?idEdicao=${edicao.id}"/></javalek:url>
                </javalek:menuItem>                
            </c:if>
            <c:if test="${(edicao.tipo == 'FAIXA_ETARIA')}">
                <javalek:menuItem label="Relat�rio de confraternistas por Grupos">
                    <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                    <javalek:url><c:url value="/admin/relatorio/reportConfGrupo.html?idEdicao=${edicao.id}"/></javalek:url>
                </javalek:menuItem>
            </c:if>
            <c:if test="${(edicao.tipo == 'FAIXA_ETARIA')}">
                <javalek:menuItem label="Relat�rio de confraternistas por Grupos - Facilitadores">
                    <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                    <javalek:url><c:url value="/admin/relatorio/reportConfGrupoFacilitador.html?idEdicao=${edicao.id}"/></javalek:url>
                </javalek:menuItem>                
            </c:if>
            <javalek:menuItem label="Relat�rio de Confraternistas por Dormit�rio">
                <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/relatorio/reportConfDormitorio.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Relat�rio de Presen�a dos Confraternistas">
                <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/relatorio/reportConfPresenca.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Relat�rio de Sa�de e Dieta dos Confraternistas">
                <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/relatorio/reportConfSaudeAlimentacao.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Relat�rio de camisetas dos confraternistas">
                <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/relatorio/reportConfCamiseta.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Relat�rio de camisetas para encomenda">
                <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/relatorio/reportEncomendaCamiseta.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Crach�s (Folha A3)">
                <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/relatorio/reportCrachaA3.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>
            <javalek:menuItem label="Crach�s (Folha A4)">
                <javalek:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></javalek:icon>
                <javalek:url><c:url value="/admin/relatorio/reportCrachaA4.html?idEdicao=${edicao.id}"/></javalek:url>
            </javalek:menuItem>

        </javalek:menu>
    </div>
</div>
