<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<%-- este elemento <content> passa o breadcrumbs para o titlebar do layout --%>
<content tag="titlebarContent">
    <mocca:title title="label.page.mainMenu" isTitleKey="true"/>    
</content>

<div class="small-font-size">
    <mocca:menu>

        <mocca:menuItem iconClass="icon-check" label="label.menu.usersubscriptions" isLabelKey="true"
                        url="/user/listUsuarioInscricoes.html" />                    

    </mocca:menu>
</div>
