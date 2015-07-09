<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Formulário de recuperação de senha"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>

<form:form commandName="command">
    <div class="row">
        <div class="span5">
            <see:formField label="Login" type="text" id="username" path="username"/>
        </div>
        <div class="span2 centered">
            <br>
            <strong> OU </strong>
        </div>
        <div class="span5">
            <see:formField label="CPF" type="text" id="cpf" path="cpf"/>
        </div>
    </div>
    <see:formButtonGroup putSubmit="true" backUrl="../index.html" clearUrl="esqueciSenha.html"/>
</form:form>
<script>
    $(document).ready(function(){
       $("#cpf").mask('999.999.999-99');
    });
</script>