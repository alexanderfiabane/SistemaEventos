<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Formulário de recuperação de senha"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

<form:form commandName="command">
    <div class="row">
        <div class="span5">
            <label class="label">
                Login
            </label>
            <input type="text" id="username" name="username"/>
        </div>
        OU
        <div class="span5">
            <label class="label">
                CPF
            </label>
            <input type="text" id="cpf" name="cpf"/>
        </div>
    </div>
    <see:formButtonGroup putSubmit="true" backUrl="../index.html" clearUrl="esqueciSenha.html"/>
</form:form>
