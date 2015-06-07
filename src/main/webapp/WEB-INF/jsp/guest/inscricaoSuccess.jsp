<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<%--
<content tag="titlebarContent">
    <javalek:pagetitle label="label.page.subscription" isLabelKey="true" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconFormInsc.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.mainMenu"><javalek:url><c:url value="/index.html"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>
--%>
<mocca:title title="label.page.subscription" isTitleKey="true"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

<fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
    <legend class="label">
        <h3><fmt:message key="label.eventdetails"/></h3>
    </legend>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.subscriptiontype" isLabelKey="true" value="${command.confraternista.tipo.descricao}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.subscriptionstatus" isLabelKey="true" value="${command.status.value}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.subscriptionValue" isLabelKey="true" isCurrency="true" value="${command.valor}"/>
        </div>
    </div>
</fieldset>

<!--DADOS PESSOAIS-->
<fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
    <legend class="label">
        <h3><fmt:message key="label.personaldetails"/></h3>
    </legend>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.fullname" isLabelKey="true" value="${command.confraternista.pessoa.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.badge" isLabelKey="true" value="${command.confraternista.nomeCracha}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.birthday" isLabelKey="true" isDate="true" value="${command.confraternista.pessoa.dataNascimento.time}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.sex" isLabelKey="true" value="${command.confraternista.pessoa.sexo.descricao}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.cpf" isLabelKey="true" value="${command.confraternista.pessoa.documentos.cpf}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.id" isLabelKey="true" value="${command.confraternista.pessoa.documentos.rg}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.birthcertificate" isLabelKey="true" value="${command.confraternista.pessoa.documentos.certidaoNascimento}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.street" isLabelKey="true" value="${command.confraternista.pessoa.endereco.logradouro}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.number" isLabelKey="true" value="${command.confraternista.pessoa.endereco.numero}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.complement" isLabelKey="true" value="${command.confraternista.pessoa.endereco.complemento}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.district" isLabelKey="true" value="${command.confraternista.pessoa.endereco.bairro}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.city" isLabelKey="true" value="${command.confraternista.pessoa.endereco.cidade.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.state" isLabelKey="true" value="${command.confraternista.pessoa.endereco.cidade.estado.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.zipcode" isLabelKey="true" value="${command.confraternista.pessoa.endereco.cep}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.email" isLabelKey="true" value="${command.confraternista.pessoa.endereco.email}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.phone" isLabelKey="true" value="${command.confraternista.pessoa.endereco.telefone}"/>
        </div>
        <div class="span6">
            <see:formFieldView label="label.phoneatevent" isLabelKey="true" value="${command.confraternista.pessoa.endereco.telefoneEvento}"/>
        </div>
    </div>
    <div class="row">
        <div class="span9">
            <see:formFieldView label="label.responsible" isLabelKey="true" value="${command.confraternista.pessoa.responsavel.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.phone" isLabelKey="true" value="${command.confraternista.pessoa.responsavel.telefone}"/>
        </div>
    </div>
</fieldset>

<fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
    <legend class="label">
        <h3><fmt:message key="label.healthfooddetails"/></h3>
    </legend>
    <div class="row">
        <div class="span12">
            <see:formFieldView label="Medicação" isLabelKey="false" value="${command.confraternista.pessoa.informacoesSaude.medicacao}"/>
        </div>
    </div>
    <div class="row">
        <div class="span6">
            <see:formFieldView label="Convênio" isLabelKey="false" value="${command.confraternista.pessoa.informacoesSaude.convenio}"/>
        </div>
        <div class="span6">
            <see:formFieldView label="Telefone" isLabelKey="false" value="${command.confraternista.pessoa.informacoesSaude.convenioTelefone}"/>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <see:formFieldView label="Alergia" isLabelKey="false" value="${command.confraternista.pessoa.informacoesSaude.alergia}"/>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <see:formFieldView label="Alimentação" isLabelKey="false" value="${command.confraternista.pessoa.informacoesSaude.dieta}"/>
        </div>
    </div>
</fieldset>

<fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
    <legend class="label">
        <h3><fmt:message key="label.housedetails"/></h3>
    </legend>
    <div class="row">
        <div class="span12">
            <see:formFieldView label="label.name" isLabelKey="true" value="${command.confraternista.casaEspirita.nome}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.street" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.logradouro}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.number" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.numero}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.complement" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.complemento}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.district" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.bairro}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.city" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.cidade.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.state" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.cidade.estado.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.zipcode" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.cep}"/>
        </div>
    </div>
    <div class="row">
        <div class="span9">
            <see:formFieldView label="label.responsibleevent" isLabelKey="true" value="${command.confraternista.responsavelEvento.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.phone" isLabelKey="true" value="${command.confraternista.responsavelEvento.telefone}"/>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <see:formFieldView label="label.activityatie" isLabelKey="true" value="${command.confraternista.atividadeCasaEspirita}"/>
        </div>
    </div>
</fieldset>

<c:if test="${not empty command.edicaoEvento.oficinas}">
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h3><fmt:message key="label.workshopdetails"/></h3>
        </legend>
        <div class="row">
            <see:formFieldView label="label.workshopname" isLabelKey="true" value="${command.confraternista.oficina.nome}"/>
        </div>
    </fieldset>
</c:if>

<c:if test="${not empty command.edicaoEvento.gruposIdade}">
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h3><fmt:message key="label.groupagedetails"/></h3>
        </legend>
        <div class="row">
            <see:formFieldView label="label.groupagename" isLabelKey="true" value="${command.confraternista.grupoIdade.nome}"/>
        </div>
    </fieldset>
</c:if>

<c:if test="${not empty command.confraternista.camisetas}">
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h3><fmt:message key="label.shirtdetails"/></h3>
        </legend>
        <div class="row">
            <div class="table-wrapper scrollable bordered rounded">
                <table id="camisetas" class="table striped hovered stroked">
                    <thead class="header">
                        <tr><th class="centered" colspan="4"><javalek:message key="label.shirts"/></th></tr>
                    <tr>
                        <th class="centered"><javalek:message key="label.shirttype"/></th>
                    <th class="centered"><javalek:message key="label.shirtcolor"/></th>
                    <th class="centered"><javalek:message key="label.shirtsize"/></th>
                    <th class="centered"><javalek:message key="label.shirtquant"/></th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="camiseta" items="${command.confraternista.camisetas}">
                            <tr>
                                <td class="centered">${camiseta.tipoCamiseta.descricao}</td>
                                <td class="centered">${camiseta.corCamiseta.descricao}</td>
                                <td class="centered">${camiseta.tamanhoCamiseta.descricao}</td>
                                <td class="align-right">${camiseta.quantidadeCamiseta}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </fieldset>
</c:if>

<c:url var="url_print" value="/user/fichaInscricao.html">
    <c:param name="idInscricao" value="${command.id}"/>
</c:url>
<div class="align-right">
    <button type="button" class="btn primary" title="Imprimir" id="imprimirInscricao" onclick="imprimiInscricao('${url_print}');"><i class="icon-print"></i> Imprimir Inscrição</button>
</div>
<script type="text/javascript">
    function imprimiInscricao(url) {
        alert("Por favor, acesse o e-mail cadastrado no formulário de inscrição para obter os dados de login no sistema e imprimir sua ficha de inscrição.");
        location.href = url;
    }
    $(document).ready({
        
    });
</script>