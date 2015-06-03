<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:menu>

    <mocca:menuItem iconClass="icon-edit" label="label.menu.openedsubscriptions" isLabelKey="true"
                    url="/guest/listInscricoesAbertas.html" />

    <mocca:menuItem iconClass="icon-check" label="label.menu.usersubscriptions" isLabelKey="true"
                    url="/user/listUsuarioInscricoes.html" isUrlAbsolute="false" />

    <mocca:menuItem iconClass="icon-cogs" label="label.menu.adminArea" isLabelKey="true"
                    url="/admin/menu.html" />

</mocca:menu>


