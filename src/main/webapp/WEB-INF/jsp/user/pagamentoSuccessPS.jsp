<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="label.page.payment" isTitleKey="true"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>
<form:form commandName="command">
<div class="row">
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend><h4><fmt:message key="label.paymentdetails"/></h4></legend>
    <div class="row">
        <div class="span6">
            <c:out value="${command.descricaoPagamento}" escapeXml="false"/>
        </div>
        <div class="span6">
            <label class="label">Data do pagamento:</label>
            <fmt:formatDate value="${command.dataPagamento.time}" pattern="dd/MM/YYYY HH:mm"/>
        </div>
    </div>
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
            </div>
        </div>
    </fieldset>
</div>
</form:form>
<see:formButtonGroup putSubmit="false" backUrl="/user/listUsuarioInscricoes.html"/>