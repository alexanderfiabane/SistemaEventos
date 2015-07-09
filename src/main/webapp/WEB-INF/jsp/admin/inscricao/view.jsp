<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Detalhes da Inscrição"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<see:notice type="error" visible="${!empty erro}" closeable="true">${erro}</see:notice>

    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h4><fmt:message key="label.eventdetails"/></h4>
    </legend>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.subscriptiontype" isLabelKey="true" value="${command.inscricao.confraternista.tipo.descricao}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.subscriptionstatus" isLabelKey="true" value="${command.inscricao.status.value}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.subscriptionValue" isLabelKey="true" isCurrency="true" value="${command.inscricao.valor}"/>
        </div>
    </div>
</fieldset>
<!--DADOS USUÁRIO-->
<fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
    <legend class="label">
        <h4><fmt:message key="label.userdetails"/></h4>
    </legend>
    <div class="row">
        <div class="span4">
            <see:formFieldView label="label.username" isLabelKey="true" value="${command.usuario.username}"/>
        </div>
    </div>
</fieldset>
<!--DADOS PESSOAIS-->
<fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
    <legend class="label">
        <h4><fmt:message key="label.personaldetails"/></h4>
    </legend>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.fullname" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.badge" isLabelKey="true" value="${command.inscricao.confraternista.nomeCracha}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.birthday" isLabelKey="true" isDate="true" value="${command.inscricao.confraternista.pessoa.dataNascimento.time}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.sex" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.sexo.descricao}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.cpf" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.documentos.cpf}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.id" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.documentos.rg}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.birthcertificate" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.documentos.certidaoNascimento}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.street" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.endereco.logradouro}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.number" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.endereco.numero}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.complement" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.endereco.complemento}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.district" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.endereco.bairro}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.city" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.endereco.cidade.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.state" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.endereco.cidade.estado.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.zipcode" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.endereco.cep}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.email" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.endereco.email}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.phone" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.endereco.telefone}"/>
        </div>
        <div class="span6">
            <see:formFieldView label="label.phoneatevent" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.endereco.telefoneEvento}"/>
        </div>
    </div>
    <div id="responsavel" class="row" style="display:none;">
        <div class="span9">
            <see:formFieldView label="label.responsible" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.responsavel.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.phone" isLabelKey="true" value="${command.inscricao.confraternista.pessoa.responsavel.telefone}"/>
        </div>
    </div>
</fieldset>

<fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
    <legend class="label">
        <h4><fmt:message key="label.healthfooddetails"/></h4>
    </legend>
    <div class="row">
        <div class="span12">
            <see:formFieldView label="Medicação" isLabelKey="false" value="${command.inscricao.confraternista.pessoa.informacoesSaude.medicacao}"/>
        </div>
    </div>
    <div class="row">
        <div class="span6">
            <see:formFieldView label="Convênio" isLabelKey="false" value="${command.inscricao.confraternista.pessoa.informacoesSaude.convenio}"/>
        </div>
        <div class="span6">
            <see:formFieldView label="Telefone" isLabelKey="false" value="${command.inscricao.confraternista.pessoa.informacoesSaude.convenioTelefone}"/>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <see:formFieldView label="Alergia" isLabelKey="false" value="${command.inscricao.confraternista.pessoa.informacoesSaude.alergia}"/>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <see:formFieldView label="Alimentação" isLabelKey="false" value="${command.inscricao.confraternista.pessoa.informacoesSaude.dieta}"/>
        </div>
    </div>
</fieldset>

<fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
    <legend class="label">
        <h4><fmt:message key="label.housedetails"/></h4>
    </legend>
    <div class="row">
        <div class="span12">
            <see:formFieldView label="label.name" isLabelKey="true" value="${command.inscricao.confraternista.casaEspirita.nome}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.street" isLabelKey="true" value="${command.inscricao.confraternista.casaEspirita.endereco.logradouro}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.number" isLabelKey="true" value="${command.inscricao.confraternista.casaEspirita.endereco.numero}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.complement" isLabelKey="true" value="${command.inscricao.confraternista.casaEspirita.endereco.complemento}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.district" isLabelKey="true" value="${command.inscricao.confraternista.casaEspirita.endereco.bairro}"/>
        </div>
    </div>
    <div class="row">
        <div class="span3">
            <see:formFieldView label="label.city" isLabelKey="true" value="${command.inscricao.confraternista.casaEspirita.endereco.cidade.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.state" isLabelKey="true" value="${command.inscricao.confraternista.casaEspirita.endereco.cidade.estado.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.zipcode" isLabelKey="true" value="${command.inscricao.confraternista.casaEspirita.endereco.cep}"/>
        </div>
    </div>
    <div id="evangelizadorResponsavel" class="row" style="display:none;">
        <div class="span9">
            <see:formFieldView label="label.responsibleevent" isLabelKey="true" value="${command.inscricao.confraternista.responsavelEvento.nome}"/>
        </div>
        <div class="span3">
            <see:formFieldView label="label.phone" isLabelKey="true" value="${command.inscricao.confraternista.responsavelEvento.telefone}"/>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <see:formFieldView label="label.activityatie" isLabelKey="true" value="${command.inscricao.confraternista.atividadeCasaEspirita}"/>
        </div>
    </div>
</fieldset>

<c:if test="${not empty command.inscricao.edicaoEvento.oficinas}">
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h4><fmt:message key="label.workshopdetails"/></h4>
        </legend>
        <div class="row">
            <see:formFieldView label="label.workshopname" isLabelKey="true" value="${command.inscricao.confraternista.oficina.nome}"/>
        </div>
    </fieldset>
</c:if>

<c:if test="${not empty command.inscricao.edicaoEvento.gruposIdade}">
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h4><fmt:message key="label.groupagedetails"/></h4>
        </legend>
        <div class="row">
            <see:formFieldView label="label.groupagename" isLabelKey="true" value="${command.inscricao.confraternista.grupoIdade.nome}"/>
        </div>
    </fieldset>
</c:if>

<c:if test="${not empty command.inscricao.confraternista.camisetas}">
    <fieldset class="control bordered rounded shadowed small-margin-bottom large-padding-bottom">
        <legend class="label">
            <h4><fmt:message key="label.shirtdetails"/></h4>
        </legend>
        <div class="row">
            <div class="table-wrapper scrollable bordered rounded">
                <table id="camisetas" class="table striped hovered stroked">
                    <thead class="header">
                        <tr><th class="centered" colspan="4"><fmt:message key="label.shirts"/></th></tr>
                        <tr>
                            <th class="centered"><fmt:message key="label.shirttype"/></th>
                            <th class="centered"><fmt:message key="label.shirtcolor"/></th>
                            <th class="centered"><fmt:message key="label.shirtsize"/></th>
                            <th class="centered"><fmt:message key="label.shirtquant"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="camiseta" items="${command.inscricao.confraternista.camisetas}">
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
<script>
    function calculaMaiorIdade(nasc) {
        var hoje = new Date(), idade;
        idade = (
                (hoje.getMonth() > nasc.getMonth())
                ||
                (hoje.getMonth() == nasc.getMonth() && hoje.getDate() >= nasc.getDate())
                ) ? hoje.getFullYear() - nasc.getFullYear() : hoje.getFullYear() - nasc.getFullYear() - 1;
        if (idade > 18) {
            return true;
        }
    }

    $(document).ready(function () {
                var tipoEvento = '${command.inscricao.edicaoEvento.tipo}';
                var tipoConfraternista = '${command.inscricao.confraternista.tipo}';
                var dataNascimento = parseDate(${command.inscricao.confraternista.pessoa.dataNascimento.time});
        if ((tipoEvento == 'FAIXA_ETARIA') && (tipoConfraternista == 'CONFRATERNISTA')) {
            $('#evangelizadorResponsavel').show();
        } else {
            $('#evangelizadorResponsavel').hide();
        }
        if (!calculaMaiorIdade(dataNascimento)) {
            $('#responsavel').show();
        } else {
            $('#responsavel').hide();
        }
    });
</script>