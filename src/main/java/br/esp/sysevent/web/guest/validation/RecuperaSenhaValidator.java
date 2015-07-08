/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.guest.validation;

import br.esp.sysevent.persistence.springframework.validation.AbstractValidator;
import br.esp.sysevent.web.guest.command.RecuperaSenhaCommand;
import org.springframework.validation.Errors;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
public class RecuperaSenhaValidator extends AbstractValidator<RecuperaSenhaCommand>{

    @Override
    public void validateCommand(RecuperaSenhaCommand command, Errors errors) {
        validateUsername(command.getUsername(), errors);
        validateCpf(command.getCpf(), errors);
    }

    private void validateUsername(String username, Errors errors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void validateCpf(String cpf, Errors errors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
