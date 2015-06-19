<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<%@ include file="/WEB-INF/includes/dwr.jspf" %>

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
                            <div class="centeredDivOuter" style="width: 400px;">
                                <div class="centeredDivInner">
                                    <!-- Lista de confraternistas do grupoA -->
                                    <div class="table-wrapper bordered shadowed rounded">
                                        <table id="confraternistasGrupoA" class="table small-font-size stroked striped hovered narrow connectedSortable">
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
                            <div class="centeredDivOuter" style="width: 400px">
                                <div class="centeredDivInner">
                                    <!-- Lista de confraternistas do grupoB -->
                                    <div class="table-wrapper bordered shadowed rounded">
                                        <table id="confraternistasGrupoB" class="table small-font-size stroked striped hovered narrow connectedSortable">
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
            jQuery(inputConfraternista).empty();
            if (grupoIdadeSelecionado === null || grupoIdadeSelecionado === '') {
                jQuery(inputConfraternista).append(jQuery('<thead class="header">')
                        .append(jQuery('<tr>')
                                .append(jQuery('<th style="text-align: center;" colspan="3">')
                                        .append('Selecione um grupo'))));
            } else {
                grupoIdadeAjaxService.findById(grupoIdadeSelecionado, function callback(grupoIdade) {
                    jQuery(inputConfraternista).append(jQuery('<thead class="header">')
                            .append(jQuery('<tr id="' + grupoIdade.id + '">')
                                    .append(jQuery('<th style="text-align: center;" colspan="3">')
                                            .append(grupoIdade.nome + '<br/>' + 'Total de vagas: ' + grupoIdade.vagas)))
                            .append(jQuery('<tr>')
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.state"/>'))
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.city"/>'))
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.name"/>'))));
                    inscricaoAjaxService.findByIdGrupoIdade(grupoIdadeSelecionado, function callback(confraternistas) {
                        jQuery(inputConfraternista).append('<tbody id="grupoA">');
                        jQuery.each(confraternistas, function(index, value) {
                            jQuery('#grupoA').append(jQuery('<tr id="' + value.id + '">')
                                    .append(jQuery('<td>')
                                            .append(value.confraternista.pessoa.endereco.cidade.estado.sigla))
                                    .append(jQuery('<td>')
                                            .append(value.confraternista.pessoa.endereco.cidade.nome))
                                    .append(jQuery('<td>')
                                            .append(value.confraternista.pessoa.nome)));
                        });
                        jQuery(inputConfraternista).append(jQuery('<tfoot class="footer">')
                                .append(jQuery('<tr>')
                                        .append(jQuery('<td colspan="3">')
                                                .append("<strong>Vagas ocupadas: " + grupoIdade.vagasOcupadas + "</strong>"))));
                    });
                });
            }
        } else {
            var grupoIdadeSelecionado = selectedGrupoIdade;
            jQuery(inputConfraternista).empty();
            //Grupo B
            if (grupoIdadeSelecionado === null || grupoIdadeSelecionado === '') {
                jQuery(inputConfraternista).append(jQuery('<thead class="header">')
                        .append(jQuery('<tr>')
                                .append(jQuery('<th style="text-align: center;" colspan="3">')
                                        .append('Selecione um grupo'))));
            } else {
                grupoIdadeAjaxService.findById(grupoIdadeSelecionado, function callback(grupoIdade) {
                    jQuery(inputConfraternista).append(jQuery('<thead class="header">')
                            .append(jQuery('<tr id="' + grupoIdade.id + '">')
                                    .append(jQuery('<th style="text-align: center;" colspan="3">')
                                            .append(grupoIdade.nome + '<br/>' + 'Total de vagas: ' + grupoIdade.vagas)))
                            .append(jQuery('<tr>')
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.state"/>'))
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.city"/>'))
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<fmt:message key="label.name"/>'))));
                    inscricaoAjaxService.findByIdGrupoIdade(grupoIdadeSelecionado, function callback(confraternistas) {
                        jQuery(inputConfraternista).append('<tbody id="grupoB">');
                        jQuery.each(confraternistas, function(index, value) {
                            jQuery('#grupoB').append(jQuery('<tr id="' + value.id + '">')
                                    .append(jQuery('<td>')
                                            .append(value.confraternista.pessoa.endereco.cidade.estado.sigla))
                                    .append(jQuery('<td>')
                                            .append(value.confraternista.pessoa.endereco.cidade.nome))
                                    .append(jQuery('<td>')
                                            .append(value.confraternista.pessoa.nome)));
                        });
                        jQuery(inputConfraternista).append(jQuery('<tfoot class="footer">')
                                .append(jQuery('<tr>')
                                        .append(jQuery('<td colspan="3">')
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
                    receive: function(ev, ui) {
                        ui.item.parent().find('> tbody').append(ui.item);
                        //método que valida e salva troca                        
                        var idConfraternista = ui.item.context.id;
                        var idGrupoIdade = ev.target.tHead.rows[0].getAttribute("id");
                        grupoIdadeAjaxService.troca(idGrupoIdade, idConfraternista, function(retorno) {
                            bootbox.alert(retorno, function() {
                                loadConfraternistas(jQuery('#selectGrupoA'), jQuery('#confraternistasGrupoA'), true);
                                loadConfraternistas(jQuery('#selectGrupoB').val(), '#confraternistasGrupoB', false);
                            });
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
            grupoB.append(jQuery('<option>').append('Selecione primeiro um grupo na coluna da esquerda'));
            loadConfraternistas(grupoA, '#confraternistasGrupoA', true);
        } else {
            //Carrega confraternistas na coluna da esquerda
            loadConfraternistas(grupoA, jQuery('#confraternistasGrupoA'), true);
            grupoIdadeAjaxService.findSimilares(grupoASelecionado, function callback(grupo) {
                if (grupo.length === 0) {
                    bootbox.alert("Não existem grupos similares ao selecionado.");
                    grupoB.append(jQuery('<option value="">').append('Para trocar selecione outro grupo'));
                } else {
                    grupoB.append(jQuery('<option value="">').append('Selecione um grupo'));
                    jQuery.each(grupo, function(index, value) {
                        grupoB.append(jQuery('<option>').val(value.id).append(value.nome));
                    });
                }
            });
        }
    }

    /**
     * Inicializa os métodos javascript
     * @returns {undefined}     */
    jQuery(function() {
        $(document).ready(function() {
            loadGrupos(jQuery('#selectGrupoA'), jQuery('#selectGrupoB'));
            trocaConfraternistaGrupo();
            //Carrega o painel com os confraternistas do dormitorio selecionado
            jQuery('#selectGrupoB').change(function() {
                loadConfraternistas(jQuery(this).val(), '#confraternistasGrupoB', false);
            });
            jQuery('#selectGrupoA').change(function() {
                loadGrupos(jQuery(this), jQuery('#selectGrupoB'));
            });
        });
    });

</script>
