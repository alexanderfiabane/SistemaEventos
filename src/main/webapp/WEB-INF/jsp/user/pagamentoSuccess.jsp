<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="label.page.payment" isLabelKey="true" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconFormInsc.png"/></msf:icon>
        <msf:breadcrumb label="label.page.mainMenu"><msf:url><c:url value="/user/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="label.menu.usersubscriptions"><msf:url><c:url value="/user/listUsuarioInscricoes.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<bs:notice type="success" visible="${!empty message}" closeable="true">${message}</bs:notice>
<bs:notice type="error" visible="${!empty erro}" closeable="true">${erro}</bs:notice>

<fieldset>
    <legend><msf:message key="label.eventdetails"/></legend>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.subscriptiontype" isLabelKey="true" value="${command.inscricao.confraternista.tipo.descricao}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.subscriptionstatus" isLabelKey="true" value="${command.inscricao.status.value}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.subscriptionValue" isLabelKey="true" isCurrency="true" value="${command.inscricao.valor}"/>
        </div>
    </div>
</fieldset>

<fieldset>
    <legend><msf:message key="label.paymentdetails"/></legend>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.paymentnumber" isLabelKey="true" value="${command.numeroDocumento}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.paymentdate" isLabelKey="true" isDate="true" value="${command.data.time}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.paymentvalue" isLabelKey="true" isCurrency="true" value="${command.valor}"/>
        </div>
    </div>
</fieldset>
