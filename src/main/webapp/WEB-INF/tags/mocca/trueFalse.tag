<%@ tag body-content="empty" description="Imprime valores de true ou false."%>

<%--
  - Imprime valores de true ou false.
  -
  - @author Adriano Pereira (adriano@ufsm.br)
---%>

<%@ attribute name="id" required="false" type="java.lang.String" description="Id do campo"%>
<%@ attribute name="test" required="true" type="java.lang.Boolean" description="valor de teste"%>
<%@ attribute name="trueMessage" required="false" type="java.lang.String" description="Mensagem a ser exibida se o teste for verdadeiro. Default = sim"%>
<%@ attribute name="falseMessage" required="false" type="java.lang.String" description="Mensagem a ser exibida se o teste for falso. Default = não"%>
<%@ attribute name="nullMessage" required="false" type="java.lang.String" description="Mensagem a ser exibida se o teste for nulo. Default = -"%>
<%@ attribute name="cssTrue" required="false" type="java.lang.String" description="CSS a ser exibido se o teste for verdadeiro."%>
<%@ attribute name="cssFalse" required="false" type="java.lang.String" description="CSS a ser exibido se o teste for falso."%>
<%@ attribute name="cssNull" required="false" type="java.lang.String" description="CSS a ser exibido se o teste for nulo."%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    <c:when test="${test == null}">
        <span <c:if test="${cssNull != null}"> class="<c:out value="${cssNull}" />"</c:if><c:if test="${id != null}"> id="<c:out value="${id}" />"</c:if>>
            <c:choose>
                <c:when test="${nullMessage != null}">
                    <c:out value="${nullMessage}" />
                </c:when>
                <c:otherwise>
                    -
                </c:otherwise>
            </c:choose>
        </span>
    </c:when>
    <c:when test="${test != null && test}">
        <span <c:if test="${cssTrue != null}"> class="<c:out value="${cssTrue}" />"</c:if><c:if test="${id != null}"> id="<c:out value="${id}" />"</c:if>>
            <c:choose>
                <c:when test="${trueMessage != null}">
                    <c:out value="${trueMessage}" />
                </c:when>
                <c:otherwise>
                    Sim
                </c:otherwise>
            </c:choose>
        </span>
    </c:when>
    <c:otherwise>
        <span <c:if test="${cssFalse != null}">class="<c:out value="${cssFalse}" />"</c:if><c:if test="${id != null}"> id="<c:out value="${id}" />"</c:if>>
            <c:choose>
                <c:when test="${falseMessage != null}">
                    <c:out value="${falseMessage}" />
                </c:when>
                <c:otherwise>
                    Não
                </c:otherwise>
            </c:choose>
        </span>
    </c:otherwise>
</c:choose>