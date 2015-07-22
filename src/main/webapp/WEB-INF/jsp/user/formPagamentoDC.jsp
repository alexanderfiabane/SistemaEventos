<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="label.page.payment" isTitleKey="true"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

    <div class="row">
        <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
            <legend class="label">
                <h4><fmt:message key="label.paymentdetails"/></h4>
        </legend>
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
                    <c:forEach var="item" items="${produtos}">
                        <tr>
                            <td>${item.description}</td>
                            <td class="align-center">${item.quantity}</td>
                            <td class="align-right">${item.amount * item.quantity}</td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot class="footer">
                    <tr>
                        <td colspan="3" class="align-right bold">
                            <label class="label">Total à pagar:</label> ${command.inscricao.valor}
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </fieldset>
</div>
<form:form commandName="command">
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h4><fmt:message key="label.menu.payment"/></h4>
        </legend>
        <div class="row">
            <div class="span6">
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
                <see:formField id="pagDescricao" label="label.paymentdescription" isLabelKey="true" isMandatory="true" path="descricaoPagamento" type="textarea" maxlength="500" inputClass="textarea width-100"/>
            </div>
        </div>
    </fieldset>
    <c:url var="clearUrl" value="/user/formPagamento.html">
        <c:param name="idInscricao" value="${command.inscricao.id}"/>
    </c:url>
    <c:url var="backUrl" value="/user/listaInscricoes.html">
    </c:url>
    <see:formButtonGroup putSubmit="true" clearUrl="${clearUrl}" backUrl="${backUrl}"/>
</form:form>
<script>
    $(document).ready(function () {
        $("#pagDescricao").textCounter({
            maxChars: 500
        });
    });
</script>