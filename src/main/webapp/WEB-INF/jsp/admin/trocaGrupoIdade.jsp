<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Troca de Grupo do Confraternista"/>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<fmt:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<div class="row">
    <div class="row">
        <div class="span6">
            <label class="label control">
                <fmt:message key="label.group"/>
            </label>
            <select id="selectGrupoA" class="span12">
                <option value="">Selecione um grupo</option>
                <c:forEach var="grupoa" items="${grupoIdade}">
                    <option value="${grupoa.id}">${grupoa.nome}</option>
                </c:forEach>
            </select>
        </div>
        <div class="span6">
            <label class="label control">
                <fmt:message key="label.group"/>
            </label>
            <select id="selectGrupoB" class="span12">
                <option value="">Selecione um grupo</option>
                <c:forEach var="grupob" items="${grupoIdadeb}">
                    <option value="${grupob.id}">${grupob.nome}</option>
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
                            <!--Tabela que mostra os confraternista do grupoA selecionado-->
                            <div class="centeredDivOuter">
                                <div class="centeredDivInner">
                                    <!-- Lista de confraternistas do grupoA -->
                                    <div class="table-wrapper scrollable bordered rounded">
                                        <table id="confraternistasGrupoA" class="table small-font-size stroked striped narrow connectedSortable">
                                            <thead class="header">
                                                <tr>
                                                    <th style="text-align: center;" colspan="3">
                                                        Selecione um grupo
                                                    </th>
                                                </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td class="centered" style="padding-top: 5%;">
                            <p class="text-info"><strong>Arraste a linha</strong></p>
                            <i class="icon-exchange"></i>
                            <p class="text-info"><strong>para trocar</strong></p>
                        </td>
                        <td>
                            <div class="centeredDivOuter">
                                <div class="centeredDivInner">
                                    <!-- Lista de confraternistas do grupoB -->
                                    <div class="table-wrapper scrollable bordered rounded">
                                        <table id="confraternistasGrupoB" class="table small-font-size stroked striped narrow connectedSortable">
                                            <thead class="header">
                                                <tr>
                                                    <th style="text-align: center;" colspan="3">
                                                        Selecione um grupo
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
</div>
<see:formButtonGroup putSubmit="false" backUrl="menuGrupoIdade.html?idEdicao=${edicao.id}"/>
<script type="text/javascript" src="<c:url value="/dwr/interface/inscricaoAjaxService.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/grupoIdadeAjaxService.js"/>"></script>
<script type="text/javascript">

    /**
     * Carrega os confraternisto do dormitório selecionado.
     *
     * @param {boolean} situacaoConfraternista se true, busca pelos confraternistas que possuem dormitório.
     * @param {Dormitorio} selectedGrupoIdade
     * @param {ID Div} inputConfraternista
     * @returns {Collection<Confraternista>}
     */
    function loadConfraternistas(selectedGrupoIdade, inputConfraternista, situacaoConfraternista) {
        if (situacaoConfraternista) {
            var grupoIdadeSelecionado = selectedGrupoIdade.val();
            $(inputConfraternista).empty();
            if (grupoIdadeSelecionado === null || grupoIdadeSelecionado === '') {
                $(inputConfraternista).append($('<thead class="header">')
                        .append($('<tr>')
                                .append($('<th style="text-align: center;" colspan="3">')
                                        .append('Selecione um grupo'))));
            } else {
                grupoIdadeAjaxService.findById(grupoIdadeSelecionado, function callback(grupoIdade) {
                    $(inputConfraternista).append($('<thead class="header">')
                            .append($('<tr id="' + grupoIdade.id + '">')
                                    .append($('<th style="text-align: center;" colspan="3">')
                                            .append(grupoIdade.nome + '<br/>' + 'Total de vagas: ' + grupoIdade.vagas)))
                            .append($('<tr>')
                                    .append($('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.state"/>'))
                                    .append($('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.city"/>'))
                                    .append($('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.name"/>'))));
                    inscricaoAjaxService.findByIdGrupoIdade(grupoIdadeSelecionado, function callback(confraternistas) {
                        $(inputConfraternista).append('<tbody id="grupoA">');
                        $.each(confraternistas, function (index, value) {
                            $('#grupoA').append($('<tr id="' + value.id + '">')
                                    .append($('<td>')
                                            .append(value.confraternista.pessoa.endereco.cidade.estado.sigla))
                                    .append($('<td>')
                                            .append(value.confraternista.pessoa.endereco.cidade.nome))
                                    .append($('<td>')
                                            .append(value.confraternista.pessoa.nome)));
                        });
                        $(inputConfraternista).append($('<tfoot class="footer">')
                                .append($('<tr>')
                                        .append($('<td colspan="3">')
                                                .append("<strong>Vagas ocupadas: " + grupoIdade.vagasOcupadas + "</strong>"))));
                    });
                });
            }
        } else {
            var grupoIdadeSelecionado = selectedGrupoIdade;
            $(inputConfraternista).empty();
            //Grupo B
            if (grupoIdadeSelecionado === null || grupoIdadeSelecionado === '') {
                $(inputConfraternista).append($('<thead class="header">')
                        .append($('<tr>')
                                .append($('<th style="text-align: center;" colspan="3">')
                                        .append('Selecione um grupo'))));
            } else {
                grupoIdadeAjaxService.findById(grupoIdadeSelecionado, function callback(grupoIdade) {
                    $(inputConfraternista).append($('<thead class="header">')
                            .append($('<tr id="' + grupoIdade.id + '">')
                                    .append($('<th style="text-align: center;" colspan="3">')
                                            .append(grupoIdade.nome + '<br/>' + 'Total de vagas: ' + grupoIdade.vagas)))
                            .append($('<tr>')
                                    .append($('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.state"/>'))
                                    .append($('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.city"/>'))
                                    .append($('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.name"/>'))));
                    inscricaoAjaxService.findByIdGrupoIdade(grupoIdadeSelecionado, function callback(confraternistas) {
                        $(inputConfraternista).append('<tbody id="grupoB">');
                        $.each(confraternistas, function (index, value) {
                            $('#grupoB').append($('<tr id="' + value.id + '">')
                                    .append($('<td>')
                                            .append(value.confraternista.pessoa.endereco.cidade.estado.sigla))
                                    .append($('<td>')
                                            .append(value.confraternista.pessoa.endereco.cidade.nome))
                                    .append($('<td>')
                                            .append(value.confraternista.pessoa.nome)));
                        });
                        $(inputConfraternista).append($('<tfoot class="footer">')
                                .append($('<tr>')
                                        .append($('<td colspan="3">')
                                                .append("<strong>Vagas ocupadas: " + grupoIdade.vagasOcupadas + "</strong>"))));
                    });
                });
            }
        }
    }

    /**
     * Plugin para trocar(arrastar) os confraternistas de um grupoIdade
     * para outro grupoIdade da mesma faixa etária.

     * @returns {undefined}     */
    function trocaConfraternistaGrupo() {
        $("#confraternistasGrupoA, #confraternistasGrupoB").sortable(
                {
                    connectWith: ".connectedSortable",
                    cursor: "move",
                    items: '> tbody > *',
                    receive: function (ev, ui) {
                        ui.item.parent().find('> tbody').append(ui.item);
                        //método que valida e salva troca
                        var idConfraternista = ui.item.context.id;
                        var idGrupoIdade = ev.target.tHead.rows[0].getAttribute("id");
                        grupoIdadeAjaxService.troca(idGrupoIdade, idConfraternista, function (retorno) {
                            var trocaAlert = new AlertJS({
                                'theme': "info",
                                'title': "Troca de Grupo",
                                'content': retorno,
                                'postClose': function (modal) {
                                    loadConfraternistas($('#selectGrupoA'), $('#confraternistasGrupoA'), true);
                                    loadConfraternistas($('#selectGrupoB').val(), '#confraternistasGrupoB', false);
                                }
                            });
                            trocaAlert.open();
                        });
                    },
                    cursorAt: {left: 20},
                    revert: true
                }
        ).disableSelection();
    }

    /**
     * Carrega os dormitórios segundo o gênero escolhido

     * @param {selectGrupoA} grupoA
     * @param {selectGrupoB} grupoB
     * @returns {Collection<GrupoIdade>}     */
    function loadGrupos(grupoA, grupoB) {
        var grupoASelecionado = grupoA.val();
        grupoB.empty();
        loadConfraternistas(null, '#confraternistasGrupoB', false);
        if (grupoASelecionado === '') {
            grupoB.append($('<option>').append('Selecione primeiro um grupo na coluna da esquerda'));
            loadConfraternistas(grupoA, '#confraternistasGrupoA', true);
        } else {
            //Carrega confraternistas na coluna da esquerda
            loadConfraternistas(grupoA, $('#confraternistasGrupoA'), true);
            grupoIdadeAjaxService.findSimilares(grupoASelecionado, function callback(grupo) {
                if (grupo.length === 0) {
                    var alert = new AlertJS({
                        'theme': "info",
                        'content': "Não existem grupos similares ao selecionado."
                    });
                    alert.open();
                    grupoB.append($('<option value="">').append('Para trocar selecione outro grupo'));
                } else {
                    grupoB.append($('<option value="">').append('Selecione um grupo'));
                    $.each(grupo, function (index, value) {
                        grupoB.append($('<option>').val(value.id).append(value.nome));
                    });
                }
            });
        }
    }

    /**
     * Inicializa os métodos javascript
     * @returns {undefined}     */
    $(function () {
        $(document).ready(function () {
            loadGrupos($('#selectGrupoA'), $('#selectGrupoB'));
            trocaConfraternistaGrupo();
            //Carrega o painel com os confraternistas do dormitorio selecionado
            $('#selectGrupoB').change(function () {
                loadConfraternistas($(this).val(), '#confraternistasGrupoB', false);
            });
            $('#selectGrupoA').change(function () {
                loadGrupos($(this), $('#selectGrupoB'));
            });
        });
    });

</script>
