<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="label.page.payment" isLabelKey="true" defaultIsLabelKey="true">
        <i class="icon-file-text large"></i>
        <javalek:breadcrumb label="label.page.mainMenu"><javalek:url><c:url value="/user/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="label.menu.usersubscriptions"><javalek:url><c:url value="/user/listUsuarioInscricoes.html"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

<fieldset>
    <legend><javalek:message key="label.eventdetails"/></legend>
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

<fieldset>
    <legend><javalek:message key="label.paymentdetails"/></legend>
    <div class="row-fluid">
        <div class="span3">
            <see:formFieldView label="label.paymentnumber" isLabelKey="true" value="${command.numeroDocumento}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.paymentdate" isLabelKey="true" isDate="true" value="${command.data.time}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.paymentvalue" isLabelKey="true" isCurrency="true" value="${command.valor}"/>
        </div>
    </div>
</fieldset>
