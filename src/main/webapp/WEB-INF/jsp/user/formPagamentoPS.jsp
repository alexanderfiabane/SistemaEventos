<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<head>
    <!--<script type="text/javascript" src="https://stc.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.lightbox.js"></script>-->    
    <script type="text/javascript" src="https://stc.sandbox.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.lightbox.js"></script>    
</head>
<mocca:title title="label.page.payment" isTitleKey="true"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

<fieldset>
        <legend><fmt:message key="label.eventdetails"/></legend>
    <div class="row">
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
        <!-- INICIO FORMULARIO BOTAO PAGSEGURO -->
<form action="https://pagseguro.uol.com.br/checkout/v2/payment.html" method="post" onsubmit="PagSeguroLightbox(this); return false;">
<!-- NÃO EDITE OS COMANDOS DAS LINHAS ABAIXO -->
<input type="hidden" name="code" value="${pagseguroCod}" />
<input type="image" src="https://p.simg.uol.com.br/out/pagseguro/i/botoes/pagamentos/99x61-comprar-azul-assina.gif" name="submit" alt="Pague com PagSeguro - é rápido, grátis e seguro!" />
</form>
<!-- FINAL FORMULARIO BOTAO PAGSEGURO -->
            <%--
<form:form commandName="command">
    
    <fieldset>
        <legend><fmt:message key="label.paymentdetails"/></legend>
        <div class="row">
            <div class="span3">
                <see:formField label="label.paymentnumber" isLabelKey="true" isMandatory="true" path="codPagamento"/>
            </div>
            <div class="span3">
                <see:formField label="label.paymentdate" isLabelKey="true" isMandatory="true" type="date" path="dataPagamento"/>
            </div>
            <div class="span3">
                <see:formField label="label.paymentvalue" isLabelKey="true" isMandatory="true" path="valor"/>
            </div>
        </div>
        <div class="row">
            <div class="span12">
                <see:formFieldView label="Descrição da compra" value="${command.descricaoPagamento}"/>
            </div>
        </div>
    </fieldset>
    <see:formButtonGroup putSubmit="true" clearUrl="formPagamento.html" backUrl="listaInscricoes.html"/>
</form:form>
            --%>