/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Sexo;
import br.msf.commons.persistence.springframework.validation.AbstractValidator;
import br.msf.commons.util.CharSequenceUtils;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Component
public class DormitorioValidator extends AbstractValidator<Dormitorio> {

    private final Pattern NOME_PATTERN = Pattern.compile("[\\p{L} ]+");

    @Override
    public void validateCommand(Dormitorio target, Errors errors) {
        validateEdicao(target.getEdicaoEvento(), errors);
        validateNome(target.getNome(), errors);
        validateVagas(target.getVagas(), errors);
        validateSexo(target.getSexo(), errors);
        validateCoordenador(target.getCoordenador(), errors);
        validateViceCoordenador(target.getViceCoordenador(), errors);
    }

    private void validateEdicao(Edicao edicao, Errors errors) {
        if (edicao == null) {
            errors.rejectValue("edicaoEvento", "errors.required");
        }
    }

    public void validateNome(final String nome, final Errors errors) {
        if (CharSequenceUtils.isBlankOrNull(nome)) {
            // nome obrigatório
            errors.rejectValue("nome", "errors.required");
        } else if (!NOME_PATTERN.matcher(nome).matches()) {
            // nome inválido
            errors.rejectValue("nome", "errors.invalid");
        }
    }

    private void validateVagas(Integer vagas, Errors errors) {
        if (vagas == null) {
            errors.rejectValue("vagas", "errors.required");
        }
    }

    private void validateSexo(Sexo sexo, Errors errors) {
        if (sexo == null) {
            errors.rejectValue("sexo", "errors.required");
        }
    }

    private void validateCoordenador(Confraternista coordenador, Errors errors) {
        if (coordenador == null) {
            errors.rejectValue("coordenador", "errors.required");
        }
    }

    private void validateViceCoordenador(Confraternista viceCoordenador, Errors errors) {
        if (viceCoordenador == null) {
            errors.rejectValue("viceCoordenador", "errors.required");
        }
    }
}
