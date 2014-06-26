<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<%@ include file="/WEB-INF/includes/dwr.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <msf:pagetitle label="Troca de Dormit�rio do Confraternista">
        <msf:icon><c:url value="/assets/application/img/icons/iconCadastro.png"/></msf:icon>
        <msf:breadcrumb label="label.page.adminArea" isLabelKey="true"><msf:url><c:url value="/admin/menu.html"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Cadastrar Edi��o" isLabelKey="false"><msf:url><c:url value="/admin/formEdicao.html?idEvento=${edicao.evento.id}"/></msf:url></msf:breadcrumb>
        <msf:breadcrumb label="Dormit�rio" isLabelKey="false"><msf:url><c:url value="/admin/menuDormitorio.html?idEdicao=${edicao.id}"/></msf:url></msf:breadcrumb>
    </msf:pagetitle>
</content>

<bs:notice type="success" visible="${!empty message}" closeable="true">${message}</bs:notice>
<msf:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<div class="row-fluid">
    <div class="row-fluid">
        <msf:label label="label.dormitory" isMandatory="true" isLabelKey="true" breakAfter="false" cssClass="control-label"/>
        <select id="dormitorios">
            <option value="">Selecione um dormit�rio</option>
            <c:forEach var="dormitorio" items="${dormitorios}">
                <option value="${dormitorio.id}">${dormitorio.nome}</option>
            </c:forEach>
        </select>                
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
                                    <table id="confraternistasComDormitorio" class="table table-bordered table-striped table-condensed connectedSortable">
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
                        <td>
                            <div class="centeredDivOuter" style="width: 400px">
                                <div class="centeredDivInner">
                                    <!-- Lista de confraternistas sem dormit�rio -->
                                    <table id="confraternistasSemDormitorio" class="table table-bordered table-striped table-condensed connectedSortable">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center;" colspan="3">
                                                    Confraternistas Sem Dormit�rio
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
                                        <tbody>
                                            <c:forEach items="${confraternistasSemDormitorio}" var="confraternistaSemDormitorio">
                                                <tr>                                                     
                                                    <td>${confraternistaSemDormitorio.pessoa.endereco.cidade.estado.sigla}</td>
                                                    <td>${confraternistaSemDormitorio.pessoa.endereco.cidade.nome}</td>
                                                    <td>${confraternistaSemDormitorio.pessoa.nome}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </td>
                    </tr>
            </table>
        </div>
    </div>                    
</div>
<div class="row-fluid">
    <bs:formButtonGroup formUrl="/admin/trocaDormitorio.html?idEdicao=${edicao.id}"/>
</div>
<script type="text/javascript" src="<c:url value="/dwr/interface/confraternistaAjaxService.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/dormitorioAjaxService.js"/>"></script>
<script type="text/javascript">
    //Carrega o painel com os confraternistas do dormitorio selecionado
    jQuery('#dormitorios').change(function() {
        loadConfraternistas(jQuery(this), jQuery('#confraternistasComDormitorio'));
        trocaDormitorios();
    });

    function loadConfraternistas(inputDormitorio, inputConfraternista) {
        var dormitorioSelecionado = inputDormitorio.val();
        jQuery(inputConfraternista).empty();
        if (dormitorioSelecionado == '') {
            jQuery(inputConfraternista).append(jQuery('<thead>')
                    .append(jQuery('<tr>')
                    .append(jQuery('<th style="text-align: center;" colspan="3">')
                    .append('Selecione um dormit�rio'))));
        } else {
            dormitorioAjaxService.findById(dormitorioSelecionado, function callback(dormitorio) {
                jQuery(inputConfraternista).append(jQuery('<thead>')
                        .append(jQuery('<tr>')
                        .append(jQuery('<th style="text-align: center;" colspan="3">')
                        .append(dormitorio.nome)))
                        .append(jQuery('<tr>')
                        .append(jQuery('<th style="text-align: center;">')
                        .append('<msf:message key="label.state"/>'))
                        .append(jQuery('<th style="text-align: center;">')
                        .append('<msf:message key="label.city"/>'))
                        .append(jQuery('<th style="text-align: center;">')
                        .append('<msf:message key="label.name"/>'))));
                confraternistaAjaxService.findByIdDormitorio(dormitorioSelecionado, function callback(confraternistas) {
                    jQuery(inputConfraternista).append('<tbody id="dormitorioSelec">');
                    jQuery.each(confraternistas, function(index, value) {
                        jQuery(dormitorioSelec).append(jQuery('<tr>')
                                .append(jQuery('<td>')
                                .append(value.pessoa.endereco.cidade.estado.sigla))
                                .append(jQuery('<td>')
                                .append(value.pessoa.endereco.cidade.nome))
                                .append(jQuery('<td>')
                                .append(value.pessoa.nome)));
                    });
                });
            });
        }
    }

    jQuery.ready(function() {
        trocaDormitorios();
    });

    $("#confraternistasComDormitorio, #confraternistasSemDormitorio")

    //Fun��o de Drag and Drop entre as tabelas dos dormit�rios
    //TODO: colocar cursor no meio
    //TODO: colocar aviso de como funciona a troca de dormit�rio.
    function trocaDormitorios() {
        $("#confraternistasComDormitorio, #confraternistasSemDormitorio").sortable(
                {connectWith: ".connectedSortable",
                    cursor: "move",
                    items: '> tbody > *',
                    receive: function(ev, ui) {
                        ui.item.parent().find('> tbody').append(ui.item);
                    },                    
                    cursorAt: {left: 20},
                    revert: true
                }
        ).disableSelection();
    }
    ;

//    jQuery().ready(function() {
//        tableSort(jQuery('#confraternistasSemDormitorio'));
//    });
//
//    function tableSort(inputTable) {
//        inputTable.tablesorter({
//            theme: 'blue',
//            widthFixed: true,
//            widgets: ['zebra'],
//            headers: {
//                0: {sorter: false}
//            },
//            sortList: [[2, 0]]
//        });
//    }
</script>