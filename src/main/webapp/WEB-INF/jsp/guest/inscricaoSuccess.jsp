<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="label.page.subscription" isLabelKey="true" defaultIsLabelKey="true">
        <msf:icon><c:url value="/assets/application/img/icons/iconFormInsc.png"/></msf:icon>
        <msf:breadcrumb label="label.page.mainMenu"><msf:url><c:url value="/index.html"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<bs:notice type="success" visible="${!empty message}" closeable="true">${message}</bs:notice>
<bs:notice type="error" visible="${!empty erro}" closeable="true">${erro}</bs:notice>

    <fieldset>
        <legend><msf:message key="label.eventdetails"/></legend>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.subscriptiontype" isLabelKey="true" value="${command.confraternista.tipo.descricao}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.subscriptionstatus" isLabelKey="true" value="${command.status.value}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.subscriptionValue" isLabelKey="true" isCurrency="true" value="${command.valor}"/>
        </div>
    </div>
</fieldset>

<!--DADOS PESSOAIS-->
<fieldset>
    <legend><msf:message key="label.personaldetails"/></legend>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.fullname" isLabelKey="true" value="${command.confraternista.pessoa.nome}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.badge" isLabelKey="true" value="${command.confraternista.nomeCracha}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.birthday" isLabelKey="true" isDate="true" value="${command.confraternista.pessoa.dataNascimento.time}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.sex" isLabelKey="true" value="${command.confraternista.pessoa.sexo.descricao}"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.cpf" isLabelKey="true" value="${command.confraternista.pessoa.documentos.cpf}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.id" isLabelKey="true" value="${command.confraternista.pessoa.documentos.rg}"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.street" isLabelKey="true" value="${command.confraternista.pessoa.endereco.logradouro}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.number" isLabelKey="true" value="${command.confraternista.pessoa.endereco.numero}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.complement" isLabelKey="true" value="${command.confraternista.pessoa.endereco.complemento}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.district" isLabelKey="true" value="${command.confraternista.pessoa.endereco.bairro}"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.city" isLabelKey="true" value="${command.confraternista.pessoa.endereco.cidade.nome}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.state" isLabelKey="true" value="${command.confraternista.pessoa.endereco.cidade.estado.nome}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.zipcode" isLabelKey="true" value="${command.confraternista.pessoa.endereco.cep}"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.email" isLabelKey="true" value="${command.confraternista.pessoa.endereco.email}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.phone" isLabelKey="true" value="${command.confraternista.pessoa.endereco.telefone}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.phoneatevent" isLabelKey="true" value="${command.confraternista.pessoa.endereco.telefoneEvento}"/>
        </div>
    </div>
</fieldset>
<fieldset>
    <legend><msf:message key="label.healthdetails"/></legend>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="Medicação" isLabelKey="false" value="${command.confraternista.pessoa.informacoesSaude.medicacao}"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="Convênio" isLabelKey="false" value="${command.confraternista.pessoa.informacoesSaude.convenio}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="Telefone" isLabelKey="false" value="${command.confraternista.pessoa.informacoesSaude.convenioTelefone}"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="Alergia" isLabelKey="false" value="${command.confraternista.pessoa.informacoesSaude.alergia}"/>
        </div>
    </div>
</fieldset>

<fieldset>
    <legend><msf:message key="label.housedetails"/></legend>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.name" isLabelKey="true" value="${command.confraternista.casaEspirita.nome}"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.street" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.logradouro}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.number" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.numero}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.complement" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.complemento}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.district" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.bairro}"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span3">
            <bs:formFieldView label="label.city" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.cidade.nome}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.state" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.cidade.estado.nome}"/>
        </div>
        <div class="span3">
            <bs:formFieldView label="label.zipcode" isLabelKey="true" value="${command.confraternista.casaEspirita.endereco.cep}"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span5">
            <bs:formFieldView label="label.activityatie" isLabelKey="true" value="${command.confraternista.atividadeCasaEspirita}"/>
        </div>
    </div>
</fieldset>

<c:if test="${not empty command.edicaoEvento.oficinas}">        
    <fieldset>
        <legend><msf:message key="label.workshopdetails"/></legend>
        <div class="row-fluid">
            <bs:formFieldView label="label.workshopname" isLabelKey="true" value="${command.confraternista.oficina.nome}"/>
        </div>
    </fieldset>
</c:if>

<c:if test="${not empty command.edicaoEvento.gruposIdade}">        
    <fieldset>
        <legend><msf:message key="label.groupagedetails"/></legend>
        <div class="row-fluid">
            <bs:formFieldView label="label.groupagename" isLabelKey="true" value="${command.confraternista.grupoIdade.nome}"/>
        </div>
    </fieldset>
</c:if>

<c:if test="${not empty command.confraternista.camisetas}">
    <fieldset>
        <legend><msf:message key="label.shirtdetails"/></legend>
        <div class="row-fluid">
            <table id="camisetas" class="table table-striped table-condensed">
                <thead>
                    <tr><th class="center" colspan="4"><msf:message key="label.shirts"/></th></tr>
                    <tr>
                        <th><msf:message key="label.shirttype"/></th>
                        <th><msf:message key="label.shirtcolor"/></th>
                        <th><msf:message key="label.shirtsize"/></th>
                        <th><msf:message key="label.shirtquant"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="camiseta" items="${command.confraternista.camisetas}">
                        <tr>
                            <td>${camiseta.tipoCamiseta.descricao}</td>
                            <td>${camiseta.corCamiseta.descricao}</td>
                            <td>${camiseta.tamanhoCamiseta.descricao}</td>
                            <td>${camiseta.quantidadeCamiseta}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </fieldset>
</c:if>

<c:url var="url_print" value="/user/fichaInscricao.html">
    <c:param name="idInscricao" value="${command.id}"/>
</c:url>
<button type="button" class="btn btn-primary" title="Imprimir" id="imprimirInscricao" onclick="imprimiInscricao('${url_print}');"><i class="icon-print"></i> Imprimir Inscrição</button>
<script type="text/javascript">
    function imprimiInscricao(url) {
        alert("Por favor, acesse o e-mail cadastrado no formulário de inscrição para obter os dados de login no sistema e imprimir sua ficha de inscrição.");
        location.href = url;
    }
</script>