/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Oficina;
import br.ojimarcius.commons.persistence.springframework.validation.AbstractValidator;
import br.ojimarcius.commons.util.CharSequenceUtils;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Component
public class OficinaValidator extends AbstractValidator<Oficina> {

    private final Pattern NOME_PATTERN = Pattern.compile("[\\p{L} ]+");

    @Override
    public void validateCommand(Oficina target, Errors errors) {
        validateEdicao(target.getEdicaoEvento(), errors);
        validateNome(target.getNome(), errors);
        validateVagas(target.getVagas(), errors);
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

    private void validateEdicao(Edicao edicao, Errors errors) {
        if (edicao == null) {
            errors.rejectValue("edicaoEvento", "errors.required");
        }
    }

    private void validateVagas(Integer vagas, Errors errors) {
        if (vagas == null) {
            errors.rejectValue("vagas", "errors.required");
        }
    }
}
