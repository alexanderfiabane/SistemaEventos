<%@ include file="/WEB-INF/includes/jstl.jspf" %>
<div class="band jumbotron masthead stroked-bottom shadowed">
    <div class="jumbotron-inner">
        <div class="container">
            <div class="row">
                <div class="span8 centered">
                    <div class="row no-padding-bottom no-margin-bottom">
                        <c:url var="url_logo" value="/assets/application/img/logo-vetorizado.svg"/>
                        <object class="logo-svg-login" type="image/svg+xml" data="${url_logo}">
                            <span></span>
                        </object>
                    </div>
                    <div class="row logo-txt no-margin no-padding-top padding-bottom">
                        <h1><msf:message key="application.name"/></h1>
                    </div>
                </div>
                <div class="span4 padding-top">
                    <div class="box rounded form pull-right-tablet-desktop control large-margin">
                        <see:notice type="error" visible="${not empty param.error}">
                            <h4><msf:message key="message.loginFail"/>:</h4>
                            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                        </see:notice>
                        <form class="form-signin" name='f' action="<c:url value='j_spring_security_check'/>" method='POST'>
                            <div class="row">
                                <div class="span12 padding-top align-left">
                                    <label for="login" class="label">Usuário</label>
                                    <input name='j_username' type="text" class="textfield width-100 block"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="span12 padding-top align-left">
                                    <label for="login" class="label">Senha</label>
                                    <input id="senha" name='j_password' type="password" class="textfield width-100 block"/>
                                    <span id="capsAlert_senha" class="pill danger" style="display: none;">Atenção! O CAPS LOCK está ligado!</span>
                                </div>
                            </div>
                            <div class="row">
                                <div class="span12 margin-top">
                                    <button type="submit" name="enter" class="btn primary pull-left">
                                        <i class="icon-key"></i> Entrar
                                    </button>
                                    <c:url var="changepass_url" value="/esqueciSenha.html" context="/usuario"/>
                                    <a href="${changepass_url}" class="btn link pull-left">
                                        Esqueci minha senha
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('#senha').capsAlert();
    });
</script>
