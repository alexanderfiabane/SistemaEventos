<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<%-- este elemento <content> passa o breadcrumbs para o titlebar do layout --%>
<content tag="titlebarContent">
    <msf:pagetitle label="label.page.mainMenu" isLabelKey="true" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconInscricoesAbertas.png"/></msf:icon>        
    </msf:pagetitle>    
</content>   

<div class="small-font-size">
    <mocca:menu>

        <mocca:menuItem iconClass="icon-edit" label="label.menu.openedsubscriptions" isLabelKey="true"
                        url="/guest/listInscricoesAbertas.html" />            

        <mocca:menuItem iconClass="icon-check" label="label.menu.usersubscriptions" isLabelKey="true"
                        url="/user/listUsuarioInscricoes.html" />

        <mocca:menuItem iconClass="icon-cogs" label="label.menu.adminArea" isLabelKey="true"
                        url="/admin/menu.html" />

    </mocca:menu>
</div>

