/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.guest.validation;

import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.persistence.springframework.validation.AbstractValidator;
import br.esp.sysevent.web.guest.command.RecuperaSenhaCommand;
import com.javaleks.commons.util.CharSequenceUtils;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Component
public class RecuperaSenhaValidator extends AbstractValidator<RecuperaSenhaCommand>{

    protected final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}");

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public void validateCommand(RecuperaSenhaCommand command, Errors errors) {
        if (!CharSequenceUtils.isBlankOrNull(command.getUsername())){
            validateUsername(command.getUsername(), errors);
        }else{
            validateCpf(command.getCpf(), errors);
        }
    }

    private void validateUsername(String username, Errors errors) {
        Usuario usuario = usuarioDao.findByLogin(username);
        if (usuario == null){
            errors.rejectValue("username", "errors.notExists");
        }
    }

    private void validateCpf(String cpf, Errors errors) {
        if (CharSequenceUtils.isBlankOrNull(cpf)){
            errors.rejectValue("cpf", "errors.passwordrecovery.required");
            errors.rejectValue("username", "errors.passwordrecovery.required");
        }else if(!isValidCPF(cpf)){
            errors.rejectValue("cpf", "errors.invalid");
        }
        if (usuarioDao.findByCpf(cpf) == null){
            errors.rejectValue("cpf", "errors.notExists");
        }
    }

    protected boolean isValidCPF(String cpf) {
        if (!CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }
        final String cpfNoMask = cpf.replace(".", "").replace("-", "");
        final String numero = cpfNoMask.substring(0, 9);
        int[] digitos = new int[11];
        int peso, soma, resto;

        //calculo do 1º digito
        soma = 0;
        peso = 10;
        for (int i = 0; i < 9; i++) {
            digitos[i] = Character.digit(numero.charAt(i), 10);
            soma += digitos[i] * peso--;
        }
        resto = soma % 11;
        resto = resto < 2 ? 0 : 11 - resto;
        digitos[9] = resto;

        //calculo do 2º digito
        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * peso--;
        }
        resto = soma % 11;
        resto = resto < 2 ? 0 : 11 - resto;
        digitos[10] = resto;

        return cpfNoMask.substring(9).equals(String.valueOf(digitos[9]).concat(String.valueOf(digitos[10])));
    }

}
