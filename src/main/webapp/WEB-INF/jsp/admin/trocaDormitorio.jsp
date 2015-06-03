<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Troca de Dormit�rio do Confraternista">
        <javalek:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea" isLabelKey="true"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Evento" isLabelKey="false"><javalek:url><c:url value="/admin/formEvento.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastrar Edi��o" isLabelKey="false"><javalek:url><c:url value="/admin/formEdicao.html?idEvento=${edicao.evento.id}"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Dormit�rio" isLabelKey="false"><javalek:url><c:url value="/admin/menuDormitorio.html?idEdicao=${edicao.id}"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
    <javalek:message var="confirmDeleteMsg" key="message.confirm.delete"/>

    <div class="row">
        <div class="span6">
            <javalek:label label="label.gender" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
            <select id="sexo" class="textfield">
                <option value="">Selecione o g�nero do dormit�rio</option>
            <c:forEach var="sexo" items="${sexos}">
                <option value="${sexo.descricao}">${sexo.descricao}</option>
            </c:forEach>
        </select>
    </div>
    <div class="span6">
        <javalek:label label="label.dormitory" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
        <select id="dormitorios" class="textfield">
            <option value="">Selecione um dormit�rio</option>
            <c:forEach var="dormitorio" items="${dormitorios}">
                <option value="${dormitorio.id}">${dormitorio.nome}</option>
            </c:forEach>
        </select>
    </div>
</div>
<div id="areaTrocaDormitorio" class="centeredDivOuter" style="width: 100%">
    <div class="centeredDivInner">
        <table id="trocaDormitorio" class="table centered" style="width: 100%">
            <tbody>
                <tr>
                    <td>
                        <!--Tabela que mostra os confraternista do dormit�rio selecionado-->
                        <div class="centeredDivOuter" style="width: 400px;">
                            <div class="centeredDivInner">
                                <!-- Lista de confraternistas do dormit�rio -->
                                <table id="confraternistasComDormitorio" class="table bordered striped condensed connectedSortable">
                                    <thead>
                                        <tr>
                                            <th style="text-align: center;" colspan="3">
                                                Selecione um dormit�rio
                                            </th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </td>
                    <td class="centered" style="padding-top: 5%;">
                        <strong>Arraste a linha</strong><br>
                        <i class="icon-exchange"></i><br>
                        <strong>para trocar</strong>
                    </td>
                    <td>
                        <div class="centeredDivOuter" style="width: 400px">
                            <div class="centeredDivInner">
                                <!-- Lista de confraternistas sem dormit�rio -->
                                <table id="confraternistasSemDormitorio" class="table bordered striped condensed connectedSortable">
                                    <thead>
                                        <tr>
                                            <th style="text-align: center;" colspan="3">
                                                Confraternistas Sem Dormit�rio
                                            </th>
                                        </tr>
                                        <tr>
                                            <th style="text-align: center;">
                                    <javalek:message key="label.state"/>
                                    </th>
                                    <th style="text-align: center;">
                                    <javalek:message key="label.city"/>
                                    </th>
                                    <th style="text-align: center;">
                                    <javalek:message key="label.name"/>
                                    </th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </td>
                </tr>
        </table>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/dwr/interface/confraternistaAjaxService.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/inscricaoAjaxService.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/dormitorioAjaxService.js"/>"></script>
<script type="text/javascript">

    /**
     * Carrega os confraternisto do dormit�rio selecionado.
     *
     * @param {boolean} situacaoConfraternista se true, busca pelos confraternistas que possuem dormit�rio.
     * @param {Dormitorio} inputDormitorio
     * @param {ID Div} inputConfraternista
     * @returns {Collection<Confraternista>}
     */
    function loadConfraternistas(inputDormitorio, inputConfraternista, situacaoConfraternista) {
        if (situacaoConfraternista) {
            var dormitorioSelecionado = inputDormitorio.val();
            jQuery(inputConfraternista).empty();
            if (dormitorioSelecionado === null || dormitorioSelecionado === '') {
                jQuery(inputConfraternista).append(jQuery('<thead>')
                        .append(jQuery('<tr>')
                                .append(jQuery('<th style="text-align: center;" colspan="3">')
                                        .append('Selecione um dormit�rio'))));
            } else {
                dormitorioAjaxService.findById(dormitorioSelecionado, function callback(dormitorio) {
                    jQuery(inputConfraternista).append(jQuery('<thead>')
                            .append(jQuery('<tr id="' + dormitorio.id + '">')
                                    .append(jQuery('<th style="text-align: center;" colspan="3">')
                                            .append(dormitorio.nome + '<br/>' + 'Total de vagas: ' + dormitorio.vagas)))
                            .append(jQuery('<tr>')
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<javalek:message key="label.state"/>'))
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<javalek:message key="label.city"/>'))
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<javalek:message key="label.name"/>'))));
                    confraternistaAjaxService.findByIdDormitorio(dormitorioSelecionado, function callback(confraternistas) {
                        jQuery(inputConfraternista).append('<tbody id="dormitorioSelec">');
                        jQuery.each(confraternistas, function (index, value) {
                            jQuery('#dormitorioSelec').append(jQuery('<tr id="' + value.id + '">')
                                    .append(jQuery('<td>')
                                            .append(value.pessoa.endereco.cidade.estado.sigla))
                                    .append(jQuery('<td>')
                                            .append(value.pessoa.endereco.cidade.nome))
                                    .append(jQuery('<td>')
                                            .append(value.pessoa.nome)));
                        });
                        jQuery(inputConfraternista).append(jQuery('<tfoot>')
                                .append(jQuery('<tr>')
                                        .append(jQuery('<td colspan="3">')
                                                .append("<strong>Total: " + dormitorio.vagasOcupadas + "</strong>"))));
                    });
                });
            }
        } else {
            inscricaoAjaxService.findSemDormitorioBySexo(inputDormitorio, ${edicao.id}, function callback(confraternistas) {
                jQuery(inputConfraternista).empty();
                jQuery(inputConfraternista).append(jQuery('<thead>')
                        .append(jQuery('<tr id="null">')
                                .append(jQuery('<th style="text-align: center;" colspan="3">')
                                        .append("Confraternistas Sem Dormit�rio")))
                        .append(jQuery('<tr>')
                                .append(jQuery('<th style="text-align: center;">')
                                        .append('<javalek:message key="label.state"/>'))
                                .append(jQuery('<th style="text-align: center;">')
                                        .append('<javalek:message key="label.city"/>'))
                                .append(jQuery('<th style="text-align: center;">')
                                        .append('<javalek:message key="label.name"/>'))));
                jQuery(inputConfraternista).append('<tbody id="semDormitorio">');
                if (confraternistas !== null) {
                    jQuery.each(confraternistas, function (index, value) {
                        jQuery('#semDormitorio').append(jQuery('<tr id="' + value.id + '">')
                                .append(jQuery('<td>')
                                        .append(value.confraternista.pessoa.endereco.cidade.estado.sigla))
                                .append(jQuery('<td>')
                                        .append(value.confraternista.pessoa.endereco.cidade.nome))
                                .append(jQuery('<td>')
                                        .append(value.confraternista.pessoa.nome)));
                    });
                }
                jQuery(inputConfraternista).append(jQuery('<tfoot>')
                        .append(jQuery('<tr>')
                                .append(jQuery('<td colspan="3">')
                                        .append("<strong>Total: " + jQuery("#confraternistasSemDormitorio tbody tr").length + "</strong>"))));
            });
        }
    }

    /**
     * Plugin para trocar(arrastar) os confraternistas de um dormit�rio
     * para o grupo de 'sem dormit�rio' e vice-versa.

     * @returns {undefined}     */
    function trocaDormitorios() {
        $("#confraternistasComDormitorio, #confraternistasSemDormitorio").sortable(
                {
                    connectWith: ".connectedSortable",
                    //placeholder: "ui-state-highlight",
                    cursor: "move",
                    items: '> tbody > *',
                    receive: function (ev, ui) {
                        ui.item.parent().find('> tbody').append(ui.item);
                        //m�todo que valida e salva troca
                        var idConfraternista = ui.item.context.id;
                        var idDormitorio = ev.target.tHead.rows[0].getAttribute("id");
                        dormitorioAjaxService.troca(idDormitorio, idConfraternista, function (retorno) {
                            bootbox.alert(retorno, function () {
                                loadConfraternistas(jQuery('#dormitorios'), jQuery('#confraternistasComDormitorio'), true);
                                loadConfraternistas(jQuery('#sexo').val(), '#confraternistasSemDormitorio', false);
                            });
                        });
                    },
                    cursorAt: {left: 20},
                    revert: true
                }).disableSelection();
    }

    /**
     * Carrega os dormit�rios segundo o g�nero escolhido

     * @param {Sexo} inputSexo
     * @param {Dormitorio} inputDormitorio
     * @returns {Collection<Dormitorio>}     */
    function loadDormitorios(inputSexo, inputDormitorio) {
        var sexoSelecionado = inputSexo.val();
        inputDormitorio.empty();
        loadConfraternistas(inputDormitorio, jQuery('#confraternistasComDormitorio'), true);
        if (sexoSelecionado === '') {
            inputDormitorio.append(jQuery('<option>').append('Selecione primeiro um g�nero'));
            loadConfraternistas(null, '#confraternistasSemDormitorio', false);
        } else {
            loadConfraternistas(sexoSelecionado, '#confraternistasSemDormitorio', false);
            dormitorioAjaxService.findByGenero(sexoSelecionado, ${edicao.id}, function callback(dormitorio) {
                inputDormitorio.append(jQuery('<option value="">').append('Selecione um dormit�rio'));
                jQuery.each(dormitorio, function (index, value) {
                    inputDormitorio.append(jQuery('<option>').val(value.id).append(value.nome));
                });
            });
        }
    }

    /**
     * Inicializa os m�todos javascript
     * @returns {undefined}     */
    jQuery(function () {
        $(document).ready(function () {
            loadDormitorios(jQuery('#sexo'), jQuery('#dormitorios'));
            trocaDormitorios();
            //Carrega o painel com os confraternistas do dormitorio selecionado
            jQuery('#dormitorios').change(function () {
                loadConfraternistas(jQuery(this), jQuery('#confraternistasComDormitorio'), true);
                loadConfraternistas(jQuery('#sexo').val(), '#confraternistasSemDormitorio', false);
            });
            jQuery('#sexo').change(function () {
                loadDormitorios(jQuery(this), jQuery('#dormitorios'));
            });
        });
    });

</script>