<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Cadastrar Edição de Evento - ${command.evento.sigla}"/>
<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<mocca:title title="Formulário de cadastro" level="2"/>
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
                            <see:formField id="periodoInscricao_start" label="label.subscriptionPeriodStart" isLabelKey="true" isMandatory="true" type="text" path="periodoInscricao.start" inputClass="manual-init"/>
                        </div>
                        <div class="span3">
                            <see:formField id="periodoInscricao_end" label="label.subscriptionPeriodEnd" isLabelKey="true" isMandatory="true" type="text" path="periodoInscricao.end"/>
                        </div>
                        <div class="span3">
                            <see:formField label="label.subscriptionDate" isLabelKey="true" isMandatory="true" type="text" path="data"/>
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
                    <mocca:title title="label.shirts" isTitleKey="true" level="3"/>
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
        <see:formButtonGroup putSubmit="true" clearUrl="formEdicao.html?idEvento=${command.evento.id}"/>
    </div>
</form:form>

<mocca:title title="Edições cadastradas" level="2"/>
<c:choose>
    <c:when test="${empty edicoes}">
        <see:notice type="info" closeable="true">Nenhuma edição foi encontrada</see:notice>
    </c:when>
    <c:otherwise>
        <div class="table-wrapper scrollable bordered rounded shadowed">
            <table class="table striped hovered stroked small-font-size">
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
                    <c:url var="edit_url" value="formEdicao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                    <c:url var="delete_url" value="deleteEdicao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                    <c:url var="grupoIdade_url" value="menuGrupoIdade.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                    <c:url var="oficina_url" value="formOficina.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                    <c:url var="dormitorio_url" value="menuDormitorio.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                    <c:url var="cobranca_url" value="menuFormaCobranca.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
                    <tr>
                        <td class="centered ">
                            <div class="btn-group small">
                                <button  type="button" class="btn small" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
                                <!--<button  type="button" class="btn small" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>-->
                                <button  type="button" class="btn small deletaEdicao" title="Deletar"><i class="icon-trash"></i></button>
                                <button  type="button" class="btn small maisOpcoes" data-id="${edicao.id}" title="Mais opções"><i class="icon-list"></i></button>
                            </div>
                            <div id="menuMaisOpcoes_${edicao.id}" class="hidden">
                                <div class="mini-font-size">                                    
                                    <mocca:menu>
                                        <c:if test="${edicao.faixaEtaria}">
                                        <mocca:menuItem iconClass="icon-group" label="Grupos por Idade" url="${grupoIdade_url}"/>
                                        </c:if>
                                        <c:if test="${edicao.oficina}">
                                        <mocca:menuItem iconClass="icon-group" label="Oficinas" url="${oficina_url}"/>
                                        </c:if>
                                        <mocca:menuItem iconClass="icon-building" label="Dormitórios" url="${dormitorio_url}"/>
                                        <mocca:menuItem iconClass="icon-money" label="Cobrança" url="${cobranca_url}"/>
                                    </mocca:menu>                                    
                                </div>
                            </div>
<%--                                <div class="btn-group" title="Mais opções">
                                    <a class="btn small dropdown-toggle" data-toggle="dropdown" href="#">
                                        <i class="icon-list"></i>                                        
                                    </a>
                                    <ul class="dropdown-menu alignLeft" role="menu">
                                        <c:if test="${edicao.faixaEtaria}">
                                            <li><a href="${grupoIdade_url}">Grupos por Idade</a></li>
                                            </c:if>
                                            <c:if test="${edicao.oficina}">
                                            <li><a href="${oficina_url}">Oficinas</a></li>
                                            </c:if>
                                        <li><a href="${dormitorio_url}">Dormitórios</a></li>
                                        <li><a href="${cobranca_url}">Cobrança</a></li>
                                    </ul>
                                </div>--%>
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
<see:formButtonGroup putSubmit="false" backUrl="formEvento.html"/>

<script type="text/javascript">
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
    $(document).ready(function() {
        document.getElementById("numero").focus();

        $("[name='vagas']").mask('9999');
        $("[name='valorInscricao']").mask('9999');
        $("[name='valorCamiseta']").mask('999');
        $("[name='idadeMinima']").mask('999');

        $("#periodoInscricao_start").dateTimePicker({
            'mode': 'date',
            'showExample': true,
            'picker': {
                showClearButton: true,
                'onClose': function (selectedDate) {
                    $("#periodoInscricao_end").datepicker("option", "minDate", selectedDate);
                }
            }
        });
        $("#periodoInscricao_end").dateTimePicker({
            'mode': 'date',
            'showExample': true,
            picker: {
                showClearButton: true,
                'onSelect': function (selectedDate) {
                    $("#periodoInscricao_start").datepicker("option", "maxDate", selectedDate);
                    $("#data").datepicker("option", "minDate", selectedDate);
                }
            }
        });
        $("#data").dateTimePicker({
            'mode': 'date',
            'showExample': true,
            picker: {
                showClearButton: true
            }
        });
        $(".maisOpcoes").confirmDialog({
            'title': "Mais opções",
            'content': function($dialogCaller){
                var id = $dialogCaller.data("id");
                var $content = $("<div>").append($("#menuMaisOpcoes_"+id).html());
                return $content;
            },
            'class': "qtip-dialog-large",
            'yesBtn': false,
            'noBtn':{
                label:"Fechar"
            }
        });
        $(".deletaEdicao").confirmDialog({           
            'title':"Deletar Edição",
            'content': "Tem ceteza que deseja deletar essa edição?"
        });        
    });
</script>
