<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<%--
<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Cadastrar Edição de Evento" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Evento" isLabelKey="false"><javalek:url><c:url value="/admin/formEvento.html"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>
--%>
<mocca:title title="Cadastrar Edição de Evento - ${command.evento.sigla}"/>
<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <!--Conteúdo Tab-->
    <div class="tabbable">
        <!--Tab-->
        <ul class="nav tabs" id="EdicaoTab">
            <li class="active"><a href="#cadastroBasico"><fmt:message key="label.edition"/></a></li>
            <li><a href="#camisetas"><fmt:message key="label.shirts"/></a></li>
        </ul>
        <div class="content">
            <!--Cadastro Básico-->
            <div class="pane active" id="cadastroBasico">
                <fieldset>
                    <%--<mocca:title title="label.editiondetails" isTitleKey="true" level="2"/>--%>
                    <div class="row">
                        <div class="span6">
                            <label class="label control">
                                <fmt:message key="label.editiontype"/>
                            </label>
                            <ul class="no-bullet no-padding">
                                <c:forEach var="item" items="${tiposEdicao}">
                                    <li class="mini-padding">
                                        <label>
                                            <form:radiobutton path="tipo" value="${item.name}"/> ${item.descricao}
                                        </label>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="span3">
                            <see:formField label="label.number" isLabelKey="true" isMandatory="true" path="numero" maxlength="3" inputClass="textfield"/>
                        </div>
                        <div class="span3">
                            <see:formField label="label.theme" isLabelKey="true" isMandatory="true" path="tema" maxlength="100" inputClass="textfield"/>
                        </div>
                        <div class="span3">
                            <see:formField label="label.subscriptionValue" isLabelKey="true" isMandatory="true" path="valorInscricao" maxlength="4" inputClass="textfield"/>
                        </div>
                        <div class="span3">
                            <see:formField label="label.vacancies" isLabelKey="true" isMandatory="true" path="vagas" maxlength="4" inputClass="textfield"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="span3">
                            <see:formField id="periodoInscricao_start" label="label.subscriptionPeriodStart" isLabelKey="true" isMandatory="true" type="date" path="periodoInscricao.start" maxlength="10"/>
                        </div>
                        <div class="span3">
                            <see:formField id="periodoInscricao_end" label="label.subscriptionPeriodEnd" isLabelKey="true" isMandatory="true" type="date" path="periodoInscricao.end" maxlength="10"/>
                        </div>
                        <div class="span3">
                            <see:formField label="label.subscriptionDate" isLabelKey="true" isMandatory="true" type="date" path="data" maxlength="10"/>
                        </div>
                        <div class="span3">
                            <see:formField label="label.subscriptionminage" isLabelKey="true" isMandatory="true" path="idadeMinima" maxlength="3" inputClass="textfield"/>
                        </div>
                    </div>
                </fieldset>
            </div>
            <!--Camisetas-->
            <div class="pane" id="camisetas">
                <fieldset>
                    <%--<mocca:title title="label.shirts" isTitleKey="true" level="2"/>--%>
                    <div class="table-wrapper">
                        <table class="table bordered rounded stroked">
                            <thead class="header">
                                <tr>
                                    <th colspan="4" class="centered">Selecione os Tipos, Cores e Tamanhos de camiseta oferecidos na Edição</th>
                                </tr>
                                <tr>
                                    <th class="centered">Tipos</th>
                                    <th class="centered">Cores</th>
                                    <th class="centered">Tamanhos</th>
                                    <th class="centered">Valor</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <form:checkboxes items="${tiposCamiseta}" itemValue="id" itemLabel="descricao" path="tiposCamiseta" delimiter="<br>"/>
                                    </td>
                                    <td>
                                        <form:checkboxes items="${coresCamiseta}" itemValue="id" itemLabel="descricao" path="coresCamiseta" delimiter="<br>"/>
                                    </td>
                                    <td>
                                        <form:checkboxes items="${tamanhosCamiseta}" itemValue="id" itemLabel="descricao" path="tamanhosCamiseta" delimiter="<br>"/>
                                    </td>
                                    <td>
                                        <form:input type="text" cssClass="textfield width-100" path="valorCamiseta" maxlength="3"/>
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot class="footer">
                                <tr>
                                    <td colspan="4" class="small-font-size padding">* Para desativar as camisetas para essa edição, simplesmente deixe todas as opções acima desmarcadas.</td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </fieldset>
            </div>
        </div>
        <see:formButtonGroup formUrl="/admin/formEdicao.html?idEvento=${command.evento.id}"/>
    </div>
</form:form>

<c:choose>
    <c:when test="${empty edicoes}">
        <see:notice type="info" closeable="true">Nenhuma edição foi encontrada</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable">
            <table class="table bordered rounded shadowed striped hovered stroked">
                <thead class="header">
                    <tr>
                        <th class="centered" style="width: 7em;"><fmt:message key="label.options"/></th>
                <th class="centered" style="width: 5em;">#</th>
                <th class="centered"><fmt:message key="label.theme"/></th>
                <th class="centered" style="width: 6em;"># vagas</th>
                <th class="centered" style="width: 8em;"><fmt:message key="label.subscriptionValue"/></th>
                <th class="centered" style="width: 12em;"><fmt:message key="label.subscriptionPeriod"/></th>
                <th class="centered" style="width: 8em;"><fmt:message key="label.subscriptionDate"/></th>
                <th class="centered" style="width: 6em;"><fmt:message key="label.subscriptionminage"/></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${edicoes}" var="edicao">
                        <c:url var="edit_url" value="/admin/formEdicao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                        <c:url var="delete_url" value="/admin/deleteEdicao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                        <c:url var="grupoIdade_url" value="/admin/menuGrupoIdade.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                        <c:url var="oficina_url" value="/admin/formOficina.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                        <c:url var="dormitorio_url" value="/admin/menuDormitorio.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                        <c:url var="cobranca_url" value="/admin/menuFormaCobranca.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                            <tr>
                                <td class="centered ">
                                    <div class="btn-group mini">
                                        <button  type="button" class="btn small" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
                                        <button  type="button" class="btn small" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
                                        <div class="btn-group" title="Mais opções">
                                            <a class="btn small dropdown-toggle dropup" data-toggle="dropdown" href="#">
                                                <i class="icon-list"></i>
                                                <span class="caret"></span>
                                            </a>
                                            <ul class="dropdown-menu alignLeft dropup-menu">
                                                <c:if test="${edicao.faixaEtaria}">
                                                    <li><a href="${grupoIdade_url}">Grupos por Idade</a></li>
                                                </c:if>
                                                <c:if test="${edicao.oficina}">
                                                    <li><a href="${oficina_url}">Oficinas</a></li>
                                                </c:if>
                                                <li><a href="${dormitorio_url}">Dormitórios</a></li>
                                                <li><a href="${cobranca_url}">Cobrança</a></li>
                                            </ul>
                                        </div>
                                </div>
                            </td>
                            <td class="centered">${edicao.numero}</td>
                            <td>${edicao.tema}</td>
                            <td class="centered">${edicao.vagas}</td>
                            <td class="centered">R$ ${edicao.valorInscricao}</td>
                            <td class="centered">de <javalek:formatPeriod value="${edicao.periodoInscricao}" pattern="i18n.dateFormat.java" isPatternKey="true"/></td>
                    <td class="centered"><javalek:formatDate value="${edicao.data}" pattern="i18n.dateFormat.java" isPatternKey="true"/></td>
                    <td class="centered">${edicao.idadeMinima}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<script type="text/javascript">
    $(document).ready(function () {
        document.getElementById("numero").focus();

        function toogleMenu(idEdicao) {
            var menuId = "#menu_" + idEdicao;
            var hidden = $(menuId).children("ul").is(":hidden");
            $(menuId + ">ul").hide();
            if (hidden) {
                $(menuId).children("ul").toggle();
                $(menuId).css("top", -50);
                $(menuId).css("left", 150);
            }
        }

        $("[name='vagas']").mask('9999');
        $("[name='valorInscricao']").mask('9999');
        $("[name='valorCamiseta']").mask('999');
        $("[name='idadeMinima']").mask('999');

//        $("#periodoInscricao_start").dateTimePicker({
        $("[name='periodoInscricao_start']").dateTimePicker({
            'mode': 'date',
            'showExample': false,
            picker: {
                showClearButton: true,
                onClose: function (selectedDate) {
                    $("#periodoInscricao_end").datepicker("option", "minDate", selectedDate);
                }
            }
        });
        $("#periodoInscricao_end").dateTimePicker({
            'mode': 'date',
            'showExample': false,
            picker: {
                showClearButton: true,
                onClose: function (selectedDate) {
                    $("#periodoInscricao_start").datepicker("option", "maxDate", selectedDate);
                    $("#data").datepicker("option", "minDate", selectedDate);
                }
            }
        });
        $("#data").dateTimePicker({
            'mode': 'date',
            'showExample': false,
            picker: {
                showClearButton: true
            }
        });
    });
</script>