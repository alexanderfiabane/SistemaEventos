<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<mocca:title title="Dormitório"/>

<mocca:menu>
    <mocca:menuItem iconClass="icon-edit" label="Cadastrar Dormitório" isLabelKey="false"
                    url="/admin/formDormitorio.html?idEdicao=${edicao.id}"/>

    <mocca:menuItem iconClass="icon-exchange" label="Trocar Confraternista de Dormitório" isLabelKey="false"
                    url="/admin/trocaDormitorio.html?idEdicao=${edicao.id}"/>
</mocca:menu>

