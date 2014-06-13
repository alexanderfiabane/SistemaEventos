<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="label.page.adminArea" isLabelKey="true" defaultIsLabelKey="true">
        <%-- TODO : trocar icone! --%>
        <msf:icon><c:url value="/assets/application/img/icons/iconFuncoesAdmin.png"/></msf:icon>
    </msf:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div id="outerMenu" class="centered" style="display: table; margin: auto;">
    <div id="innerMenu" style="display: table-cell;">
        <msf:menu id="menu" defaultIconSize="64" >

            <msf:menuItem label="Gerenciamento de Inscrições">
                <msf:icon><c:url value="/assets/application/img/icons/iconAnaliseInscricoes.png"/></msf:icon>
                <msf:url><c:url value="/admin/inscricao/listEvento.html"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Configuração de Eventos<br/> e Edições">
                <msf:icon><c:url value="/assets/application/img/icons/iconConfigEventoEdicao.png"/></msf:icon>
                <msf:url><c:url value="/admin/formEvento.html"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Cadastros Básicos">
                <msf:icon><c:url value="/assets/application/img/icons/iconCadastrosBasicos2.png"/></msf:icon>
                <msf:url><c:url value="/admin/menuCadastrosBasicos.html"/></msf:url>
            </msf:menuItem>
            <msf:menuItem label="Emissão de Relatórios">
                <msf:icon><c:url value="/assets/application/img/icons/iconRelatorios.png"/></msf:icon>
                <msf:url><c:url value="/admin/relatorio/listEvento.html"/></msf:url>
            </msf:menuItem>
        </msf:menu>
    </div>
</div>
