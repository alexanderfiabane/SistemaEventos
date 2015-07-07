<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Formulário de alteração de senha"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

<form:form commandName="command">
    <div class="row">
        <div class="span4">
            <see:formField label="Login" path="username" readonly="true"/>
        </div>
    </div>
    <div class="row">
        <div class="span4">
            <see:formField type="password" label="Digite a senha atual" path="username" readonly="true"/>
        </div>
    </div>
    <div class="row">
        <div class="span4">
            <see:formField type="password" label="Digite a nova senha" path="password" readonly="true"/>
        </div>
    </div>
    <div class="row">
        <div class="span4">
            <see:formField type="password" label="Confirme a nova senha" path="username" readonly="true"/>
        </div>
    </div>
</form:form>