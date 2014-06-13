<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<div class="row-fluid">
    <div class="span4">
        <div class="well">
            <bs:notice type="error" visible="${not empty param.error}">
                <h4><msf:message key="message.loginFail"/>:</h4>
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </bs:notice>
            <form class="form-signin" name='f' action="<c:url value='j_spring_security_check'/>" method='POST'>
                <h2 class="form-signin-heading">Login</h2>
                <input name='j_username' type="text" class="input-block-level" placeholder="E-mail">
                <input name='j_password' type="password" class="input-block-level" placeholder="Senha">
                <div class="form-actions" style="padding-left: 0; padding-right: 0px; ">
                    <button type="submit" class="btn btn-large btn-primary">Entrar</button>
                    <button type="button" class="btn btn-link pull-right">Esqueci minha senha</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Noticias -->
    <div class="span8">
        <bs:notice type="info" visible="${empty noticias}"><msf:message key="message.noNews"/></bs:notice>
        <c:forEach var="noticia" items="${noticias}" varStatus="i">
            <div class="row-fluid">
                <div class="span12 well">
                    <h4><msf:formatDate value="${noticia.data}" pattern="i18n.dateFormat.java" isPatternKey="true"/> - ${noticia.titulo}</h4>
                    <p>${noticia.conteudo}</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
