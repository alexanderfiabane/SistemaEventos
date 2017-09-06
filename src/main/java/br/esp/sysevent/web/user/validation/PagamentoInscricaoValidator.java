/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.user.validation;

import br.esp.sysevent.core.model.PagamentoInscricao;
import br.esp.sysevent.persistence.springframework.validation.AbstractValidator;
import com.javaleks.commons.util.CharSequenceUtils;
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
        if(CharSequenceUtils.isBlankOrNull(target.getCodPagamento())) {
            errors.rejectValue("codPagamento", "errors.required");
        }
        if(target.getDataPagamento()== null) {
            errors.rejectValue("dataPagamento", "errors.required");
        }
        if(target.getValor() == null) {
            errors.rejectValue("valor", "errors.required");
        }
        if(CharSequenceUtils.isBlankOrNull(target.getDescricaoPagamento())) {
            errors.rejectValue("descricaoPagamento", "errors.required");
        }
        if (target.getComprovante() == null || target.getComprovante().getData() == null){
            errors.rejectValue("comprovante", "errors.required");
        }
    }
}
