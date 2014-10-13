<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<%@ include file="/WEB-INF/includes/dwr.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Troca de Grupo do Confraternista">
        <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea" isLabelKey="true"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastrar Edição" isLabelKey="false"><msf:url><c:url value="/admin/formEdicao.html?idEvento=${edicao.evento.id}"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Grupo por Idade" isLabelKey="false"><msf:url><c:url value="/admin/menuGrupoIdade.html?idEdicao=${edicao.id}"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<bs:notice type="success" visible="${!empty message}" closeable="true">${message}</bs:notice>
<msf:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<div class="row-fluid">
    <div class="row-fluid">
        <div class="span3">            
            <msf:label label="label.group" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
            <select id="sexo" class="span12">
                <option value="">Selecione um grupo</option>
                <c:forEach var="grupoa" items="${grupoIdade}">
                    <option value="${grupoa.id}">${grupoa.nome}</option>
                </c:forEach>
            </select>
        </div>
        <div class="span3">            
            <msf:label label="label.dormitory" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
            <select id="dormitorios" class="span12">
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
                            <!--Tabela que mostra os confraternista do dormitório selecionado-->
                            <div class="centeredDivOuter" style="width: 400px;">
                                <div class="centeredDivInner">
                                    <!-- Lista de confraternistas do dormitório -->                
                                    <table id="confraternistasComDormitorio" class="table table-bordered table-striped table-condensed connectedSortable">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center;" colspan="3">
                                                    Selecione um grupo
                                                </th>
                                            </tr>                                           
                                        </thead>                                        
                                    </table>
                                </div>
                            </div>
                        </td>
                        <td class="centered" style="padding-top: 5%;">
                            <p class="text-info"><strong>Arraste a linha</strong></p>                            
                            <img scr="/assets/application/img/icons/arrow.png"/>
                            <p class="text-info"><strong>para trocar</strong></p>
                        </td>
                        <td>
                            <div class="centeredDivOuter" style="width: 400px">
                                <div class="centeredDivInner">
                                    <!-- Lista de confraternistas sem dormitório -->
                                    <table id="confraternistasSemDormitorio" class="table table-bordered table-striped table-condensed connectedSortable">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center;" colspan="3">
                                                    Confraternistas Sem Dormitório
                                                </th>
                                            </tr>
                                            <tr>                                                
                                                <th style="text-align: center;">
                                                    <msf:message key="label.state"/>
                                                </th>
                                                <th style="text-align: center;">
                                                    <msf:message key="label.city"/>
                                                </th>
                                                <th style="text-align: center;">
                                                    <msf:message key="label.name"/>
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
</div>
<script type="text/javascript" src="<c:url value="/dwr/interface/confraternistaAjaxService.js"/>"></script>
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
            jQuery(inputConfraternista).empty();
            if (dormitorioSelecionado === null || dormitorioSelecionado === '') {
                jQuery(inputConfraternista).append(jQuery('<thead>')
                        .append(jQuery('<tr>')
                                .append(jQuery('<th style="text-align: center;" colspan="3">')
                                        .append('Selecione um grupo'))));
            } else {
                dormitorioAjaxService.findById(dormitorioSelecionado, function callback(dormitorio) {
                    jQuery(inputConfraternista).append(jQuery('<thead>')
                            .append(jQuery('<tr id="' + dormitorio.id + '">')
                                    .append(jQuery('<th style="text-align: center;" colspan="3">')
                                            .append(dormitorio.nome + '<br/>' + 'Total de vagas: '+ dormitorio.vagas)))
                            .append(jQuery('<tr>')
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<msf:message key="label.state"/>'))
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<msf:message key="label.city"/>'))
                                    .append(jQuery('<th style="text-align: center;">')
                                            .append('<msf:message key="label.name"/>'))));
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
                                                .append("<strong>Total: " + jQuery("#confraternistasComDormitorio tbody tr").length + "</strong>"))));
                    });
                });
            }
        } else {
            confraternistaAjaxService.findSemDormitorio(inputDormitorio, ${edicao.id}, function callback(confraternistas) {
                jQuery(inputConfraternista).empty();
                jQuery(inputConfraternista).append(jQuery('<thead>')
                        .append(jQuery('<tr id="null">')
                                .append(jQuery('<th style="text-align: center;" colspan="3">')
                                        .append("Confraternistas Sem Dormitório")))
                        .append(jQuery('<tr>')
                                .append(jQuery('<th style="text-align: center;">')
                                        .append('<msf:message key="label.state"/>'))
                                .append(jQuery('<th style="text-align: center;">')
                                        .append('<msf:message key="label.city"/>'))
                                .append(jQuery('<th style="text-align: center;">')
                                        .append('<msf:message key="label.name"/>'))));
                jQuery(inputConfraternista).append('<tbody id="semDormitorio">');
                if (confraternistas !== null) {
                    jQuery.each(confraternistas, function (index, value) {
                        jQuery('#semDormitorio').append(jQuery('<tr id="' + value.id + '">')
                                .append(jQuery('<td>')
                                        .append(value.pessoa.endereco.cidade.estado.sigla))
                                .append(jQuery('<td>')
                                        .append(value.pessoa.endereco.cidade.nome))
                                .append(jQuery('<td>')
                                        .append(value.pessoa.nome)));
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
                    receive: function (ev, ui) {
                        ui.item.parent().find('> tbody').append(ui.item);
                        //método que valida e salva troca                        
                        var idConfraternista = ui.item.context.id;
                        var idDormitorio = ev.target.tHead.rows[0].getAttribute("id");
                        dormitorioAjaxService.troca(idDormitorio, idConfraternista, function (retorno) {
                            bootbox.dialog(retorno, [{                                    
                                    "label": "Fechar",
                                    "class": "btn-primary"
                                }]);
                        });
                    },
                    cursorAt: {left: 20},
                    revert: true
                }
        ).disableSelection();
    }

    /**
     * Carrega os dormitórios segundo o gênero escolhido
     
     * @param {Sexo} inputSexo
     * @param {Dormitorio} inputDormitorio
     * @returns {Collection<Dormitorio>}     */
    function loadDormitorios(inputSexo, inputDormitorio) {
        var sexoSelecionado = inputSexo.val();
        inputDormitorio.empty();
        loadConfraternistas(inputDormitorio, jQuery('#confraternistasComDormitorio'), true);
        if (sexoSelecionado === '') {
            inputDormitorio.append(jQuery('<option>').append('Selecione primeiro um gênero'));
            loadConfraternistas(null, '#confraternistasSemDormitorio', false);
        } else {
            loadConfraternistas(sexoSelecionado, '#confraternistasSemDormitorio', false);
            dormitorioAjaxService.findByGenero(sexoSelecionado, ${edicao.id}, function callback(dormitorio) {
                inputDormitorio.append(jQuery('<option value="">').append('Selecione um dormitório'));
                jQuery.each(dormitorio, function (index, value) {
                    inputDormitorio.append(jQuery('<option>').val(value.id).append(value.nome));
                });
            });
        }
    }

    /**
     * Inicializa os métodos javascript
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
