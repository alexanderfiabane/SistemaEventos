<%@ include file="/WEB-INF/includes/jstl.jspf" %>

<div>
    <c:if test="${param.error}">
        <div id="theme_login_error">
            <div style="margin: 8px;">
                <msf:message key="message.loginFail"/>:<br/>
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </div>
        <br/>
    </c:if>

    <form name='f' action="<c:url value='j_spring_security_check'/>" method='POST'>
        <div class="labelLogin"><msf:message key="label.username"/></div>
        <div style="padding-bottom:15px;margin-left:-12px;">
            <input type='text' name='j_username' value='' class="loginField" name="usuario" size="31" maxlength="20"/>
        </div>
        <div class="labelLogin"><msf:message key="label.password"/></div>
        <div style="padding-bottom:15px;margin-left:-12px;">
            <input type='password' name='j_password' class="loginField" size="31" maxlength="20"/>
        </div>
        <div>
            <div style="float:left; vertical-align:bottom;"><a id="forgotPasswordLink" href="login.html"><msf:message key="label.forgotPasswd"/></a></div>
            <div style="float:right;">
                <button class="login" type="submit"><msf:message key="label.login"/></button>
            </div>
            <div style="clear: both;"></div>
        </div>
    </form>
</div>
