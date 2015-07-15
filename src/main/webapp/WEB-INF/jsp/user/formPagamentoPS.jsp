<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<head>
    <!--<script type="text/javascript" src="https://stc.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.lightbox.js"></script>-->
    <script type="text/javascript" src="https://stc.sandbox.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.lightbox.js"></script>
</head>
<mocca:title title="label.page.payment" isTitleKey="true"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

<fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
    <legend class="label">
        <h4><fmt:message key="label.eventdetails"/></h4>
    </legend>
    <div class="row">
        <div class="span6">
            <see:formFieldView label="label.subscriptiontype" isLabelKey="true" value="${command.inscricao.confraternista.tipo.descricao}"/>
        </div>
        <div class="span6">
            <see:formFieldView label="label.subscriptionstatus" isLabelKey="true" value="${command.inscricao.status.value}"/>
        </div>
    </div>
    <div class="row">
        <div class="span6">
            <see:formFieldView label="Descrição da compra" value="${command.descricaoPagamento}" escapeXml="false"/>
        </div>
        <div class="span4">
            <see:formFieldView label="label.subscriptionValue" isLabelKey="true" isCurrency="true" value="${command.inscricao.valor}"/>
        </div>
        <div class="span2">
            <!-- INICIO FORMULARIO BOTAO PAGSEGURO -->
            <form action="https://pagseguro.uol.com.br/checkout/v2/payment.html" method="post" onsubmit="PagSeguroLightbox(this);
                    return false;">
                <!-- NÃO EDITE OS COMANDOS DAS LINHAS ABAIXO -->
                <input type="hidden" name="code" value="${pagseguroCod}" />
                <input type="image" src="https://p.simg.uol.com.br/out/pagseguro/i/botoes/pagamentos/99x61-pagar-azul-assina.gif" name="submit" alt="Pague com PagSeguro - é rápido, grátis e seguro!" />
            </form>
            <!-- FINAL FORMULARIO BOTAO PAGSEGURO -->
        </div>
    </div>
</fieldset>
