/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.user.validation;

import br.esp.sysevent.core.model.PagamentoInscricao;
import br.esp.sysevent.persistence.springframework.validation.AbstractValidator;
import br.ojimarcius.commons.util.CharSequenceUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Component
public class PagamentoInscricaoValidator extends AbstractValidator<PagamentoInscricao> {

    @Override
    public void validateCommand(PagamentoInscricao target, Errors errors) {
        if(CharSequenceUtils.isBlank(target.getNumeroDocumento())) {
            errors.rejectValue("numeroDocumento", "errors.required");
        }
        if(target.getData() == null) {
            errors.rejectValue("data", "errors.required");
        }
        if(target.getValor() == null) {
            errors.rejectValue("valor", "errors.required");
        }
    }
}
