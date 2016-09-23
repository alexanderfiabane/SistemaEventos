<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="label.page.payment" isTitleKey="true"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>
<form:form commandName="command">
    <div class="row">
        <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
            <legend class="label"><h4><fmt:message key="label.paymentdetails"/></h4></legend>
            <div class="row">
                <div class="span8">
                    <c:out value="${command.descricaoPagamento}" escapeXml="false"/>
                </div>
                <div class="span4 align-right">
                    <label class="label">Data do pagamento:</label>
                    <fmt:formatDate value="${command.dataPagamento.time}" pattern="dd/MM/yyyy"/>
                </div>
            </div>
            <div class="table-wrapper scrollable">
                <table class="table stroked striped">
                    <caption>Itens adquiridos</caption>
                    <thead class="header">
                        <tr>
                            <th>Descrição</th>
                            <th style="width: 10em;">Quantidade</th>
                            <th style="width: 7em;">Valor (R$)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${itens}">
                            <tr>
                                <td>${item.description}</td>
                                <td class="align-center">${item.quantity}</td>
                                <td class="align-right">${item.amount * item.quantity}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </fieldset>
    </div>
</form:form>
<c:url value="/user/listUsuarioInscricoes.html" var="backUrl"/>
<see:formButtonGroup putSubmit="false" backUrl="${backUrl}"/>