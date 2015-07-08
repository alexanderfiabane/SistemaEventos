/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.user.validation;

import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.persistence.springframework.validation.AbstractValidator;
import br.esp.sysevent.web.user.command.UsuarioCommand;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.EntityUtils;
import java.util.regex.Pattern;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Component
public class UsuarioValidator extends AbstractValidator<UsuarioCommand> {

    protected final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,15}$");
    protected final Pattern PASSWORD_PATTERN = Pattern.compile(".{3,10}");
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public void validateCommand(UsuarioCommand command, Errors errors) {
        validateUsername(command.getUsuario(), errors);
        validatePassword(command, errors);
    }

    protected void validateUsername(Usuario usuario, Errors errors) {
        final Usuario outroUsuario = usuarioDao.findByLogin(usuario.getUsername());
        if (outroUsuario != null) {
            if (EntityUtils.isPersistent(usuario) && usuario.getId() != outroUsuario.getId()) {
                errors.rejectValue("usuario.username", "errors.alreadyExists");
                return;
            }
        }
        if (!LOGIN_PATTERN.matcher(usuario.getUsername()).matches()) {
            errors.rejectValue("usuario.username", "errors.userInvalid");
        }
    }

    protected void validatePassword(UsuarioCommand command, Errors errors) {
        if (CharSequenceUtils.isBlankOrNull(command.getSenhaAtual())) {
            errors.rejectValue("senhaAtual", "errors.required");
            return;
        }
        if (CharSequenceUtils.isBlankOrNull(command.getNovaSenha())) {
            errors.rejectValue("novaSenha", "errors.required");
            return;
        }
        if (CharSequenceUtils.isBlankOrNull(command.getConfirmaNovaSenha())) {
            errors.rejectValue("confirmaNovaSenha", "errors.required");
            return;
        }
        String senhaAtualCodificada = DigestUtils.sha256Hex(command.getSenhaAtual());
        if(!senhaAtualCodificada.equals(command.getUsuario().getPassword())){
            errors.rejectValue("senhaAtual", "errors.passwordInvalid");
            return;
        }
        if (!command.getNovaSenha().equals(command.getConfirmaNovaSenha())){            
            errors.rejectValue("novaSenha", "errors.passwordNotMach");
            errors.rejectValue("confirmaNovaSenha", "errors.passwordNotMach");
        }else{
            if (!PASSWORD_PATTERN.matcher(command.getNovaSenha()).matches()) {
                errors.rejectValue("novaSenha", "errors.passwordInvalid");
            }        
        }
    }

}
