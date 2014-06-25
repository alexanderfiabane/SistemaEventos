<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Cadastrar Edição de Evento" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastrar Evento" isLabelKey="false"><msf:url><c:url value="/admin/formEvento.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<bs:notice type="success" visible="${!empty message}" closeable="true">${message}</bs:notice>
<msf:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <!--Tab-->
    <ul class="nav nav-tabs" id="EdicaoTab">
        <li class="active"><a href="#cadastroBasico" data-toggle="tab"><msf:message key="label.edition"/></a></li>        
        <li><a href="#camisetas" data-toggle="tab"><msf:message key="label.shirts"/></a></li>        
    </ul>
    <!--Conteúdo Tab-->
    <div class="tab-content">
        <!--Cadastro Básico-->
        <div class="tab-pane fade active in" id="cadastroBasico">
            <fieldset>
                <legend><msf:message key="label.editiondetails"/></legend>
                <div class="row-fluid">
                    <div class="span3">
                        <bs:formField label="label.number" isLabelKey="true" isMandatory="true" path="numero" maxlength="3"/>
                    </div>
                    <div class="span3">
                        <bs:formField label="label.theme" isLabelKey="true" isMandatory="true" path="tema" maxlength="100"/>
                    </div>
                    <div class="span3">
                        <bs:formField label="label.subscriptionValue" isLabelKey="true" isMandatory="true" path="valorInscricao" maxlength="3"/>
                    </div>
                    <div class="span3">
                        <bs:formField label="label.vacancies" isLabelKey="true" isMandatory="true" path="vagas" maxlength="4"/>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span3">
                        <bs:formField label="label.subscriptionPeriodStart" isLabelKey="true" isMandatory="true" type="date" path="periodoInscricao.start" maxlength="10"/>
                    </div>
                    <div class="span3">
                        <bs:formField label="label.subscriptionPeriodEnd" isLabelKey="true" isMandatory="true" type="date" path="periodoInscricao.end" maxlength="10"/>
                    </div>
                    <div class="span3">
                        <bs:formField label="label.subscriptionDate" isLabelKey="true" isMandatory="true" type="date" path="data" maxlength="10"/>
                    </div>
                    <div class="span3">
                        <bs:formField label="label.subscriptionminage" isLabelKey="true" isMandatory="true" path="idadeMinima" maxlength="3"/>
                    </div>
                </div>            
                <div class="row-fluid">
                    <div class="span6">
                        <msf:label label="label.editiontype" isLabelKey="true" isMandatory="true" breakAfter="false" cssClass="control-label"/>
                        <c:forEach var="item" items="${tiposEdicao}">
                            <msf:label label="${item.descricao}" colonAfter="false" cssClass="radio inline" breakAfter="false">
                                <form:radiobutton path="tipo" value="${item.name}"/>
                            </msf:label>
                        </c:forEach>                    
                    </div>                
                </div>
            </fieldset>
        </div>        
        <!--Camisetas-->
        <div class="tab-pane fade" id="camisetas">
            <fieldset>
                <legend><msf:message key="label.shirts"/></legend>
                <div class="row-fluid">            
                    <table class="table table-condensed">
                        <tr>
                            <th colspan="3" class="centered">Selecione os Tipos, Cores e Tamanhos de camiseta oferecidos na edição</th>
                        </tr>
                        <tr>
                            <th>Tipos</th>
                            <th style="width: 33%">Cores</th>
                            <th style="width: 33%">Tamanhos</th>
                        </tr>
                        <tr style="vertical-align: top;">
                            <td>
                                <div class="inline-checks">
                                    <form:checkboxes items="${tiposCamiseta}" itemValue="id" itemLabel="descricao" path="tiposCamiseta" delimiter="<br>"/>
                                </div>
                            </td>
                            <td>
                                <div class="inline-checks">
                                    <form:checkboxes items="${coresCamiseta}" itemValue="id" itemLabel="descricao" path="coresCamiseta" delimiter="<br>"/>
                                </div>
                            </td>
                            <td>
                                <div class="inline-checks">
                                    <form:checkboxes items="${tamanhosCamiseta}" itemValue="id" itemLabel="descricao" path="tamanhosCamiseta" delimiter="<br>"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <bs:notice type="warning">
                        Para desativar as camisetas para essa edição, simplesmente deixe todas as opções acima desmarcadas.
                    </bs:notice>
                </div>                            
            </fieldset>
        </div>        
        <bs:formButtonGroup formUrl="/admin/formEdicao.html?idEvento=${command.evento.id}"/>
    </div>


</form:form>

<div class="row-fluid">
    <display:table id="edicao" name="edicoes" pagesize="10" requestURI="/admin/formEdicao.html" class="table table-striped table-condensed">
        <c:url var="edit_url" value="/admin/formEdicao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
        <c:url var="delete_url" value="/admin/deleteEdicao.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
        <c:url var="grupoIdade_url" value="/admin/formGrupoIdade.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
        <c:url var="oficina_url" value="/admin/formOficina.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
        <c:url var="dormitorio_url" value="/admin/menuDormitorio.html"><c:param name="idEdicao" value="${edicao.id}"/></c:url>
        <display:column media="html" titleKey="label.options" class="threeOption centered" headerClass="centered">
            <button  type="button" class="btn btn-mini" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
            <button  type="button" class="btn btn-mini" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
            <div class="btn-group" title="">
                <a class="btn btn-mini dropdown-toggle dropup" data-toggle="dropdown" href="#">
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
                </ul>
            </div>
        </display:column>
        <display:column titleKey="label.number" property="numero" class="centered" headerClass="centered"/>
        <display:column titleKey="label.theme" property="tema" class="centered" headerClass="centered"/>
        <display:column titleKey="label.vacancies" property="vagas" class="centered" headerClass="centered"/>
        <display:column titleKey="label.subscriptionValue" property="valorInscricao" class="centered" headerClass="centered"/>        
        <display:column titleKey="label.subscriptionPeriod" media="html" class="centered" headerClass="centered">
            de <msf:formatPeriod value="${edicao.periodoInscricao}" pattern="i18n.dateFormat.java" isPatternKey="true"/>
        </display:column>
        <display:column titleKey="label.subscriptionDate" media="html" class="centered" headerClass="centered">
            <msf:formatDate value="${edicao.data}" pattern="i18n.dateFormat.java" isPatternKey="true"/>
        </display:column>
        <display:column titleKey="label.subscriptionminage" property="idadeMinima" class="centered" headerClass="centered"/>
    </display:table>
</div>
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
                onload = function() {
                    document.getElementById("tema").focus();
                };
</script>