<%@ tag body-content="scriptless" description="Bot�es de wizard."%>

<%--
  - Componente que facilita a adi��o dos bot�es usados em wizards e a sua padroniza��o ao longo dos passos do wizard.
  - Utiliza os templates pr�-definidos na tag <mocca:button />
  -
  - @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
---%>

<%@ attribute name="currentIndex" required="true" type="java.lang.Integer"
              description="�ndice do passo no wizard. o valor deve ser o �ndice desta view no atributo 'pages' do wizardController."%>

<%@ attribute name="isLastStep" required="false" type="java.lang.Boolean"
              description="Opcional. Indica se o passo corrente � u �ltimo do wizard. Default = false."%>

<%@ attribute name="isFinishable" required="false" type="java.lang.Boolean"
              description="Opcional. Indica se o usu�rio poder� enviar o formul�rio neste passo. Default = {isLastStep}."%>

<%@ attribute name="finishLabel" required="false" type="java.lang.String"
              description="Opcional. Define o r�tulo do bot�o finalizar. Default = 'Enviar'."%>

<%@ attribute name="isFinishLabelKey" required="false" type="java.lang.Boolean"
              description="Opcional. Indica se o r�tulo do bot�o finalizar � o texto pronto ou a key do properties bundle. Default = false."%>

<%@ attribute name="cancelUrl" required="false" type="java.lang.String"
              description="Opcional. Indica a URL para o qual se deve direcionar o usu�rio, ao se clicar no bot�o cancelar. Se n�o for definida uma url, o bot�o cancelar n�o ser� mostrado."%>

<%@ attribute name="position" required="false" type="java.lang.String"
              description="Opcional. Indica onde o painel de botoes est� posicionado para que este seja estilizado de acordo. Pode ser 'top' ou 'bottom'. Default = 'bottom'."%>

<%@ attribute name="cssClass" required="false" type="java.lang.String"
              description="Opcional. Classes extras para serem aplicadas na div que cont�m os botoes"%>

<%@ attribute name="btnCssClass" required="false" type="java.lang.String"
              description="Opcional. Classes extras para serem aplicadas em todos os botoes"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mocca" uri="http://portal.ufsm.br/mocca" %>

<%-- optional defaults --%>
<c:if test="${empty isLastStep}"><c:set var="isLastStep" value="false"/></c:if>
<c:if test="${empty isFinishable}"><c:set var="isFinishable" value="false"/></c:if>
<c:if test="${empty isFinishLabelKey}"><c:set var="isFinishLabelKey" value="false"/></c:if>
<c:if test="${isLastStep}"><c:set var="isFinishable" value="true"/></c:if>
<c:if test="${empty position}"><c:set var="position" value="bottom"/></c:if>

<%-- spring wizard controller target names --%>
<c:set var="prev_target" value="_target${currentIndex - 1}"/>
<c:set var="cancel_target" value="cancel"/>
<c:set var="next_target" value="_target${currentIndex + 1}"/>

<mocca:formActions position="${position}" cssClass="${cssClass}" btnCssClass="${btnCssClass}"
                   putSubmit="${isFinishable}" submitTemplate="submit" submitName="_finish" submitLabel="${finishLabel}" isSubmitLabelKey="${isFinishLabelKey}">
    <c:if test="${!empty cancelUrl}">
        <mocca:button template="cancel" name="${cancel_target}" cssClass="cancel left ${btnCssClass}"/>
    </c:if>
    <jsp:doBody />
    <c:if test="${currentIndex > 0}">
        <mocca:button template="previous" name="${prev_target}" cssClass="${btnCssClass}" isSubmit="true"/>
    </c:if>
    <c:if test="${!isLastStep}">
        <mocca:button template="next" name="${next_target}" cssClass="${btnCssClass}" isSubmit="true"/>
    </c:if>
</mocca:formActions>

<%-- Javascript que mostra um di�logo de onfirma��o quando o usu�rio clica em cancelar. --%>
<c:if test="${!empty cancelUrl}">
    <c:url var="url_cancelar" value="${cancelUrl}"/>
    <script type="text/javascript">
        $(document).ready(function() {
            var $cancel = $('.cancel');
            $cancel.each(function() {
                var $current = $(this);
                var loaded = $(this).data('loaded');
                if (!loaded) {
                    $current.openUrl({'url': '${url_cancelar}', 'showConfirmDialog': true});
                    $current.data('loaded', true);
                }
            });
        });
    </script>
</c:if>
