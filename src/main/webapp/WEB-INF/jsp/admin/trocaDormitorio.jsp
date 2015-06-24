<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Troca de Dormitório do Confraternista"/>
<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<div class="row">
    <div class="span6">
        <label class="control label">
            <fmt:message key="label.gender"/>
        </label>
        <select id="sexo" class="textfield">
            <option value="">Selecione o gênero do dormitório</option>
            <c:forEach var="sexo" items="${sexos}">
                <option value="${sexo.descricao}">${sexo.descricao}</option>
            </c:forEach>
        </select>
    </div>
    <div class="span6">
        <label class="control label">
            <fmt:message key="label.dormitory"/>
        </label>
        <select id="dormitorios" class="textfield">
            <option value="">Selecione um dormitório</option>
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
                        <!--Tabela que mostra os confraternista do dormitório selecionado-->
                        <div class="centeredDivOuter" style="width: 400px;">
                            <div class="centeredDivInner">
                                <!-- Lista de confraternistas do dormitório -->
                                <div class="table-wrapper">
                                    <table id="confraternistasComDormitorio" class="table bordered small-font-size stroked striped narrow connectedSortable">
                                        <thead class="header">
                                            <tr>
                                                <th style="text-align: center;" colspan="3">
                                                    Selecione um dormitório
                                                </th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
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
                                <!-- Lista de confraternistas sem dormitório -->
                                <div class="table-wrappe">
                                    <table id="confraternistasSemDormitorio" class="table bordered small-font-size stroked striped narrow connectedSortable">
                                        <thead class="header">
                                            <tr>
                                                <th style="text-align: center;" colspan="3">
                                                    Confraternistas Sem Dormitório
                                                </th>
                                            </tr>
                                            <tr>
                                                <th style="text-align: center;">
                                                    <fmt:message key="label.state"/>
                                                </th>
                                                <th style="text-align: center;">
                                                    <fmt:message key="label.city"/>
                                                </th>
                                                <th style="text-align: center;">
                                                    <fmt:message key="label.name"/>
                                                </th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
        </table>
    </div>
</div>
<see:formButtonGroup putSubmit="false" backUrl="menuDormitorio.html?idEdicao=${edicao.id}"/>
<script type="text/javascript" src="<c:url value="/dwr/interface/confraternistaAjaxService.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/inscricaoAjaxService.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/dormitorioAjaxService.js"/>"></script>
<script type="text/javascript">

    /**
     * Carrega os confraternisto do dormitório selecionado.
     *
     * @param {boolean} situacaoConfraternista se true, busca pelos confraternistas que possuem dormitório.
     * @param {Dormitorio} inputDormitorio
     * @param {ID Div} inputConfraternista
     * @returns {Collection<Confraternista>}
     */
    function loadConfraternistas(inputDormitorio, inputConfraternista, situacaoConfraternista) {
        if (situacaoConfraternista) {
            var dormitorioSelecionado = inputDormitorio.val();
            $(inputConfraternista).empty();
            if (dormitorioSelecionado === null || dormitorioSelecionado === '') {
                $(inputConfraternista).append($('<thead class="header">')
                        .append($('<tr>')
                                .append($('<th style="text-align: center;" colspan="3">')
                                        .append('Selecione um dormitório'))));
            } else {
                dormitorioAjaxService.findById(dormitorioSelecionado, function callback(dormitorio) {
                    $(inputConfraternista).append($('<thead class="header">')
                            .append($('<tr id="' + dormitorio.id + '">')
                                    .append($('<th style="text-align: center;" colspan="3">')
                                            .append(dormitorio.nome + '<br/>' + 'Total de vagas: ' + dormitorio.vagas)))
                            .append($('<tr>')
                                    .append($('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.state"/>'))
                                    .append($('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.city"/>'))
                                    .append($('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.name"/>'))));
                    confraternistaAjaxService.findByIdDormitorio(dormitorioSelecionado, function callback(confraternistas) {
                        $(inputConfraternista).append('<tbody id="dormitorioSelec">');
                        $.each(confraternistas, function(index, value) {
                            $('#dormitorioSelec').append($('<tr id="' + value.id + '">')
                                    .append($('<td>')
                                            .append(value.pessoa.endereco.cidade.estado.sigla))
                                    .append($('<td>')
                                            .append(value.pessoa.endereco.cidade.nome))
                                    .append($('<td>')
                                            .append(value.pessoa.nome)));
                        });
                        $(inputConfraternista).append($('<tfoot class="footer">')
                                .append($('<tr>')
                                        .append($('<td colspan="3">')
                                                .append("<strong>Total: " + dormitorio.vagasOcupadas + "</strong>"))));
                    });
                });
            }
        } else {
            inscricaoAjaxService.findSemDormitorioBySexo(inputDormitorio, ${edicao.id}, function callback(confraternistas) {
                $(inputConfraternista).empty();
                $(inputConfraternista).append($('<thead class="header">')
                        .append($('<tr id="null">')
                                .append($('<th style="text-align: center;" colspan="3">')
                                        .append("Confraternistas Sem Dormitório")))
                        .append($('<tr>')
                                .append($('<th style="text-align: center;">')
                                        .append('<fmt:message key="label.state"/>'))
                                .append($('<th style="text-align: center;">')
                                        .append('<fmt:message key="label.city"/>'))
                                .append($('<th style="text-align: center;">')
                                        .append('<fmt:message key="label.name"/>'))));
                $(inputConfraternista).append('<tbody id="semDormitorio">');
                if (confraternistas !== null) {
                    $.each(confraternistas, function(index, value) {
                        $('#semDormitorio').append($('<tr id="' + value.id + '">')
                                .append($('<td>')
                                        .append(value.confraternista.pessoa.endereco.cidade.estado.sigla))
                                .append($('<td>')
                                        .append(value.confraternista.pessoa.endereco.cidade.nome))
                                .append($('<td>')
                                        .append(value.confraternista.pessoa.nome)));
                    });
                }
                $(inputConfraternista).append($('<tfoot class="footer">')
                        .append($('<tr>')
                                .append($('<td colspan="3">')
                                        .append("<strong>Total: " + $("#confraternistasSemDormitorio tbody tr").length + "</strong>"))));
            });
        }
    }

    /**
     * Plugin para trocar(arrastar) os confraternistas de um dormitório
     * para o grupo de 'sem dormitório' e vice-versa.

     * @returns {undefined}     */
    function trocaDormitorios() {
        $("#confraternistasComDormitorio, #confraternistasSemDormitorio").sortable(
                {
                    connectWith: ".connectedSortable",
                    //placeholder: "ui-state-highlight",
                    cursor: "move",
                    items: '> tbody > *',
                    receive: function(ev, ui) {
                        ui.item.parent().find('> tbody').append(ui.item);
                        //método que valida e salva troca
                        var idConfraternista = ui.item.context.id;
                        var idDormitorio = ev.target.tHead.rows[0].getAttribute("id");
                        dormitorioAjaxService.troca(idDormitorio, idConfraternista, function(retorno) {
                            bootbox.alert(retorno, function() {
                                loadConfraternistas($('#dormitorios'), $('#confraternistasComDormitorio'), true);
                                loadConfraternistas($('#sexo').val(), '#confraternistasSemDormitorio', false);
                            });
                        });
                    },
                    cursorAt: {left: 20},
                    revert: true
                }).disableSelection();
    }

    /**
     * Carrega os dormitórios segundo o gênero escolhido

     * @param {Sexo} inputSexo
     * @param {Dormitorio} inputDormitorio
     * @returns {Collection<Dormitorio>}     */
    function loadDormitorios(inputSexo, inputDormitorio) {
        var sexoSelecionado = inputSexo.val();
        inputDormitorio.empty();
        loadConfraternistas(inputDormitorio, $('#confraternistasComDormitorio'), true);
        if (sexoSelecionado === '') {
            inputDormitorio.append($('<option>').append('Selecione primeiro um gênero'));
            loadConfraternistas(null, '#confraternistasSemDormitorio', false);
        } else {
            loadConfraternistas(sexoSelecionado, '#confraternistasSemDormitorio', false);
            dormitorioAjaxService.findByGenero(sexoSelecionado, ${edicao.id}, function callback(dormitorio) {
                inputDormitorio.append($('<option value="">').append('Selecione um dormitório'));
                $.each(dormitorio, function(index, value) {
                    inputDormitorio.append($('<option>').val(value.id).append(value.nome));
                });
            });
        }
    }

    /**
     * Inicializa os métodos javascript
     * @returns {undefined}     */

    $(document).ready(function() {
        loadDormitorios($('#sexo'), $('#dormitorios'));
        trocaDormitorios();
        //Carrega o painel com os confraternistas do dormitorio selecionado
        $('#dormitorios').change(function() {
            loadConfraternistas($(this), $('#confraternistasComDormitorio'), true);
            loadConfraternistas($('#sexo').val(), '#confraternistasSemDormitorio', false);
        });
        $('#sexo').change(function() {
            loadDormitorios($(this), $('#dormitorios'));
        });
    });

</script>