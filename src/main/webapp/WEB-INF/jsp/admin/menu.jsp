<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="label.page.adminArea" isTitleKey="true"/>

<mocca:menu>
    <mocca:menuItem iconClass="icon-group" label="Gerenciamento de Inscrições" isLabelKey="false"
                    url="/admin/inscricao/listEvento.html" />
    <mocca:menuItem iconClass="icon-gear" label="Configuração de Eventos e Edições" isLabelKey="false"
                    url="/admin/formEvento.html" />
    <mocca:menuItem iconClass="icon-edit" label="Cadastros Básicos" isLabelKey="false"
                    url="/admin/menuCadastrosBasicos.html" />
    <mocca:menuItem iconClass="icon-file-text" label="Relatórios" isLabelKey="false"
                    url="/admin/relatorio/listEvento.html" />
</mocca:menu>
