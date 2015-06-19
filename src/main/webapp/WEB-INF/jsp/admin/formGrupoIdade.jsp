<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Cadastro de Grupos por Idade"/>    

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<mocca:title title="Formulário de cadastro" level="2"/>
<form:form commandName="command">
    <div class="row">
        <div class="span4">
            <see:formField label="label.grouptype" isLabelKey="true" isMandatory="true" path="tipo" type="select" itens="${tiposGrupoIdade}" itemLabel="descricao" selectNullItemLabel="Selecione o tipo do grupo"/>            
        </div>
        <div class="span8">
            <see:formField label="label.groupagename" isLabelKey="true" isMandatory="true" path="nome" maxlength="80"/>
        </div>
    </div>
    <div class="row">
        <div class="span4">
            <see:formField label="label.vacancies" isLabelKey="true" isMandatory="true" path="vagas" maxlength="3"/>
        </div>        
        <div class="span4">
            <see:formField label="label.minage" isLabelKey="true" isMandatory="true" path="idadeMinima" maxlength="3"/>
        </div>
        <div class="span4">
            <see:formField label="label.maxage" isLabelKey="true" isMandatory="true" path="idadeMaxima" maxlength="3"/>
        </div>
    </div>    
    <see:formButtonGroup putSubmit="true" clearUrl="formGrupoIdade.html?idEdicao=${command.edicaoEvento.id}"/>
</form:form>

<mocca:title title="Grupos por Idade cadastrados" level="2"/>
<c:choose>
    <c:when test="${empty gruposIdade}">
        <see:notice closeable="false" type="info">Nenhum Grupo Idade cadastrado</see:notice>
    </c:when>
    <c:otherwise>        
        <div class="table-wrapper scrollable bordered rounded shadowed">
            <table class="table striped hovered stroked small-font-size">
                <thead>
                    <tr>
                        <th style="width: 2em;" class="centered"><fmt:message key="label.options"/></th>
                        <th><fmt:message key="label.name"/></th>
                        <th style="width: 2em;"><fmt:message key="label.type"/></th>
                        <th style="width: 2em;"><fmt:message key="label.vacancies"/></th>
                        <th style="width: 2em;"><fmt:message key="label.minage"/></th>
                        <th style="width: 2em;"><fmt:message key="label.maxage"/></th>                
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="grupoIdade" items="${gruposIdade}">                
                        <tr>
                            <td>
                                <c:url var="edit_url" value="/admin/formGrupoIdade.html"><c:param name="idGrupoIdade" value="${grupoIdade.id}"/></c:url>
                                <c:url var="delete_url" value="/admin/deleteGrupoIdade.html"><c:param name="idGrupoIdade" value="${grupoIdade.id}"/></c:url>
                                <div class="btn-group small">
                                    <button  type="button" class="btn btn-mini" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
                                    <button  type="button" class="btn btn-mini" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-trash"></i></button>                        
                                </div>
                            </td>
                            <td>${grupoIdade.nome}</td>
                            <td>${grupoIdade.tipo}</td>
                            <td class="align-right">${grupoIdade.vagas}</td>
                            <td class="align-right">${grupoIdade.idadeMinima}</td>
                            <td class="align-right">${grupoIdade.idadeMaxima}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>                  
        </div>
    </c:otherwise>
</c:choose>
<see:formButtonGroup putSubmit="false" backUrl="menuGrupoIdade.html?idEdicao=${command.edicaoEvento.id}"/>
<script>
    $(document).ready(function(){
       $("#idadeMinima").mask('999'); 
       $("#idadeMaxima").mask('999'); 
       $("#vagas").mask('99999'); 
    });
</script>
