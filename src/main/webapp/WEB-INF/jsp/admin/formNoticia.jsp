<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<!-- este elemento <content> passa o breadcrumbs para o titlebar do layout -->
<content tag="titlebarContent">
    <javalek:pagetitle label="Cadastrar Notícia" defaultIsLabelKey="true">
        <javalek:icon><c:url value="/assets/application/img/icons/iconNoticias.png"/></javalek:icon>
        <javalek:breadcrumb label="label.page.adminArea"><javalek:url><c:url value="/admin/menu.html"/></javalek:url></javalek:breadcrumb>
        <javalek:breadcrumb label="Cadastros Básicos" isLabelKey="false"><javalek:url><c:url value="/admin/menuCadastrosBasicos.html"/></javalek:url></javalek:breadcrumb>
    </javalek:pagetitle>
</content>

<see:notice type="success" visible="${!empty message}" closeable="true">${message}</see:notice>
<javalek:message var="confirmDeleteMsg" key="message.confirm.delete"/>

<form:form commandName="command">
    <div class="row-fluid">
        <div class="span3">
            <see:formField label="label.title" isLabelKey="true" isMandatory="true" path="titulo" maxlength="100"/>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <see:formField label="label.content" isLabelKey="true" isMandatory="true" path="conteudo" type="textarea" inputClass="span12"/>
        </div>
    </div>
    <see:formButtonGroup formUrl="/admin/formNoticia.html"/>
</form:form>

<div class="row-fluid">
    <display:table id="noticia" name="noticias" pagesize="10" requestURI="/admin/formNoticia.html" class="table table-striped table-condensed">
        <c:url var="edit_url" value="/admin/formNoticia.html"><c:param name="idNoticia" value="${noticia.id}"/></c:url>
        <c:url var="delete_url" value="/admin/deleteNoticia.html"><c:param name="idNoticia" value="${noticia.id}"/></c:url>
        <display:column media="html" titleKey="label.options" class="twoOption centered" headerClass="centered">
            <button  type="button" class="btn btn-mini" title="Editar" onclick="location.href = '${edit_url}';"><i class="icon-edit"></i></button>
            <button  type="button" class="btn btn-mini" title="Deletar" onclick="confirmRedir('${delete_url}', '${confirmDeleteMsg}');"><i class="icon-remove"></i></button>
        </display:column>
        <display:column titleKey="label.title" property="titulo" class="centered" headerClass="centered"/>
        <display:column titleKey="label.date" media="html" class="centered" headerClass="centered">
            <javalek:formatDate value="${noticia.data}" pattern="i18n.dateFormat.java" isPatternKey="true"/>
        </display:column>
    </display:table>
</div>

<script type="text/javascript">
    onload = function() {
        document.getElementById("titulo").focus();
    };
</script>
