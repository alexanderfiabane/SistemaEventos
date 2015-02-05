<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="label.page.adminArea" isLabelKey="true" defaultIsLabelKey="true">
        <%-- TODO : trocar icone! --%>
        <msf:icon><c:url value="/assets/application/img/icons/iconFuncoesAdmin.png"/></msf:icon>
    </msf:pagetitle>
</content>

<!-- Estas duas divs implementam um workaround para centralizar o menu -->
<div class="small-font-size">
    <mocca:menu>
        <mocca:menuItem iconClass="icon-group" label="Gerenciamento de Inscri��es" isLabelKey="false"
                        url="/admin/inscricao/listEvento.html" />
        <mocca:menuItem iconClass="icon-gear" label="Configura��o de Eventos e Edi��es" isLabelKey="false"
                        url="/admin/formEvento.html" />
        <mocca:menuItem iconClass="icon-edit" label="Cadastros B�sicos" isLabelKey="false"
                        url="/admin/menuCadastrosBasicos.html" />
        <mocca:menuItem iconClass="icon-file-text" label="Emiss�o de Relat�rios" isLabelKey="false"
                        url="/admin/relatorio/listEvento.html" />
    </mocca:menu>
</div>