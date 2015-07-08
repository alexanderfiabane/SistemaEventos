<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Formulário de alteração de senha"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

<form:form commandName="command">
    <div class="row">
        <div class="span6">
            <see:formField label="Login" path="usuario.username" readonly="true"/>
        </div>
        <div class="span6">
            <see:formField type="password" maxlength="10" label="Digite a senha atual" path="senhaAtual" inputClass="textfield"/>
        </div>
    </div>
    <div class="row">
        <div class="span6">
            <see:formField type="password" maxlength="10" label="Digite a nova senha" path="novaSenha" inputClass="textfield"/>
        </div>    
        <div class="span6">
            <see:formField type="password" maxlength="10" label="Confirme a nova senha" path="confirmaNovaSenha" inputClass="textfield"/>
        </div>
    </div>
    <see:formButtonGroup putSubmit="true" backUrl="listUsuarioInscricoes.html" clearUrl="alterarSenha.html"/>
</form:form>