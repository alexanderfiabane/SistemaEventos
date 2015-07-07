/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.user.validation;

import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.persistence.springframework.validation.AbstractValidator;
import org.springframework.validation.Errors;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
public class UsuarioValidator extends AbstractValidator<Usuario>{

    @Override
    public void validateCommand(Usuario usuario, Errors errors) {
        validateUsername(usuario, errors);
        validatePassword(usuario.getPassword(), errors);
    }

    private void validateUsername(Usuario usuario, Errors errors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void validatePassword(String password, Errors errors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
