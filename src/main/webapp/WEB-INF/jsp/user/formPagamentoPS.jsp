<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<head>
    <!--<script type="text/javascript" src="https://stc.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.lightbox.js"></script>-->
    <script type="text/javascript" src="https://stc.sandbox.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.lightbox.js"></script>
    <style>
        .pagSeguroBtn{
            padding: 0;
            opacity: .7;
        }
        .pagSeguroBtn:hover{
            opacity: 1;
        }
    </style>
</head>
<mocca:title title="label.page.payment" isTitleKey="true"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>
    <div class="row">
        <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
            <legend class="label">
                <h4><fmt:message key="label.paymentdetails"/></h4>
        </legend>
        <div class="row">
            <div class="table stroked striped">
                <div class="thead header">
                    <div class="tr">
                        <div class="th">Item</div>
                        <div class="th align-center" style="width: 10em;">Quantidade</div>
                        <div class="th align-center" style="width: 7em;">Valor (R$)</div>
                    </div>
                </div>
                <div class="tbody">
                    <c:forEach var="item" items="${produtos}">
                        <div class="tr">
                            <div class="td">${item.description}</div>
                            <div class="td align-center">${item.quantity}</div>
                            <div class="td align-right">${item.amount * item.quantity}</div>
                        </div>
                    </c:forEach>
                </div>
                <div class="tfoot">
                    <div class="tr bold">
                        <div class="td"></div>
                        <div class="td align-right">
                            <label class="label">Total à pagar</label>
                        </div>
                        <div class="td align-right">
                            ${command.inscricao.valor}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </fieldset>
    <see:formButtonGroup putSubmit="false" backUrl="listUsuarioInscricoes.html">
        <button id="pagSeguroBtn" class="btn link pagSeguroBtn" type="button" title="Pague com PagSeguro - é rápido, grátis e seguro!" data-code="${pagseguroCod}">
            <input type="image" src="https://p.simg.uol.com.br/out/pagseguro/i/botoes/pagamentos/99x61-pagar-azul-assina.gif"/>
        </button>
    </see:formButtonGroup>
</div>
<c:url var="pagamentoSuccessURL" value="/user/pagamentoSuccessPS.html">
    <c:param name="idInscricao" value="${command.inscricao.id}"/>
    <c:param name="codTransactionPagSeguro" value=""/>
</c:url>
<script type="text/javascript">
    function pagar(code) {
        var isOpened;
        isOpened = PagSeguroLightbox(code, {
            success: function (transactionCode) {
                location.href='${pagamentoSuccessURL}'+transactionCode;
            }
        });
        if (!isOpened){
           location.href='https://pagseguro.uol.com.br/v2/checkout/payment.html?code='+code;
        }
    }
    $(document).ready(function(){
       $("#pagSeguroBtn").click(function(){
           pagar($(this).data('code'));
       });
    });
</script>
