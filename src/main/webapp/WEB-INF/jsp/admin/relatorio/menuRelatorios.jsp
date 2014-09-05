<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Relat�rios  - (${edicao.tema})" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
        <msf:breadcrumb label="label.page.mainMenu"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Relat�rios - Lista de Eventos" isLabelKey="false"><msf:url><c:url value="/admin/relatorio/listEvento.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Relat�rios - Lista de Edi��es" isLabelKey="false"><msf:url><c:url value="/admin/relatorio/listEdicao.html?idEvento=${edicao.evento.id}"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <msf:menu id="menu" defaultIconSize="64" itensPerRow="4" >

            <msf:menuItem label="Relat�rio Geral por Tipo de Inscri��o">
                <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                <msf:url><c:url value="/admin/relatorio/reportConfTipo.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Relat�rio Geral por Cidades e Estado">
                <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                <msf:url><c:url value="/admin/relatorio/reportConfCidadeEstado.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Relat�rio Geral por Sexo">
                <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                <msf:url><c:url value="/admin/relatorio/reportConfSexo.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>
            <c:if test="${(edicao.tipo == 'OFICINA')}">
                <msf:menuItem label="Relat�rio de confraternistas por Oficinas">
                    <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                    <msf:url><c:url value="/admin/relatorio/reportConfOficina.html?idEdicao=${edicao.id}"/></msf:url>
                </msf:menuItem>
            </c:if>
            <c:if test="${(edicao.tipo == 'OFICINA')}">
                <msf:menuItem label="Relat�rio de confraternistas por Oficinas - Oficineiros">
                    <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                    <msf:url><c:url value="/admin/relatorio/reportConfOficinaOficineiro.html?idEdicao=${edicao.id}"/></msf:url>
                </msf:menuItem>                
            </c:if>
            <c:if test="${(edicao.tipo == 'FAIXA_ETARIA')}">
                <msf:menuItem label="Relat�rio de confraternistas por Grupos">
                    <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                    <msf:url><c:url value="/admin/relatorio/reportConfGrupo.html?idEdicao=${edicao.id}"/></msf:url>
                </msf:menuItem>
            </c:if>
            <c:if test="${(edicao.tipo == 'FAIXA_ETARIA')}">
                <msf:menuItem label="Relat�rio de confraternistas por Grupos - Facilitadores">
                    <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                    <msf:url><c:url value="/admin/relatorio/reportConfGrupoFacilitador.html?idEdicao=${edicao.id}"/></msf:url>
                </msf:menuItem>                
            </c:if>
            <msf:menuItem label="Relat�rio de Confraternistas por Dormit�rio">
                <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                <msf:url><c:url value="/admin/relatorio/reportConfDormitorio.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Relat�rio de Presen�a dos Confraternistas">
                <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                <msf:url><c:url value="/admin/relatorio/reportConfPresenca.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Relat�rio de camisetas dos confraternistas">
                <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                <msf:url><c:url value="/admin/relatorio/reportConfCamiseta.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Relat�rio de camisetas para encomenda">
                <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                <msf:url><c:url value="/admin/relatorio/reportEncomendaCamiseta.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Crach�s (Folha A3)">
                <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                <msf:url><c:url value="/admin/relatorio/reportCrachaA3.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Crach�s (Folha A4)">
                <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                <msf:url><c:url value="/admin/relatorio/reportCrachaA4.html?idEdicao=${edicao.id}"/></msf:url>
            </msf:menuItem>

        </msf:menu>
    </div>
</div>
