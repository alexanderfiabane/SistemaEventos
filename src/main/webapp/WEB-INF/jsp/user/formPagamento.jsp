<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="label.page.payment" isLabelKey="true" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconFormInsc.png"/></msf:icon>
        <msf:breadcrumb label="label.page.mainMenu"><msf:url><c:url value="/user/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="label.menu.usersubscriptions"><msf:url><c:url value="/user/listUsuarioInscricoes.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

<fieldset>
    <legend><msf:message key="label.eventdetails"/></legend>
    <div class="row-fluid">
        <div class="span3">
            <see:formFieldView label="label.subscriptiontype" isLabelKey="true" value="${command.inscricao.confraternista.tipo.descricao}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.subscriptionstatus" isLabelKey="true" value="${command.inscricao.status.value}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.subscriptionValue" isLabelKey="true" isCurrency="true" value="${command.inscricao.valor}"/>
        </div>
    </div>
</fieldset>

<form:form commandName="command">
    <fieldset>
        <legend><msf:message key="label.paymentdetails"/></legend>
        <div class="row-fluid">
            <div class="span3">
                <see:formField label="label.paymentnumber" isLabelKey="true" isMandatory="true" path="numeroDocumento"/>
            </div>
            <div class="span3">
                <see:formField label="label.paymentdate" isLabelKey="true" isMandatory="true" type="date" path="data"/>
            </div>
            <div class="span3">
                <see:formField label="label.paymentvalue" isLabelKey="true" isMandatory="true" path="valor"/>
            </div>
        </div>
    </fieldset>

    <see:formButtonGroup formUrl="/user/formPagamento.html"/>
</form:form>