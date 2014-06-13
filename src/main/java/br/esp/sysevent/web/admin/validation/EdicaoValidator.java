/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.service.EdicaoService;
import br.msf.commons.persistence.springframework.validation.AbstractValidator;
import br.msf.commons.temporal.Period;
import br.msf.commons.util.CharSequenceUtils;
import java.math.BigDecimal;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Alexander
 */
@Component
public class EdicaoValidator extends AbstractValidator<Edicao> {

    private final Pattern TEMA_PATTERN = Pattern.compile("[\\p{L} ]+");
    @Autowired
    EdicaoService edicaoService;

    @Override
    public void validateCommand(final Edicao edicao, final Errors errors) {
        validateEvento(edicao.getEvento(), errors); // valida o evento da edição
        validateTema(edicao.getTema(), errors); // valida o tema
        validateNumero(edicao.getNumero(), errors); // valida o número de edições
        validateValorInscricao(edicao.getValorInscricao(), errors); // valida o valor da inscrição
        validateVagas(edicao.getVagas(), errors); // valida o número de vagas da edição
        validatePeriodoInscricao(edicao.getPeriodoInscricao(), errors); // valida o perído de inscrição
    }

    private void validateEvento(Evento evento, Errors errors) {
        if (evento == null) {
            // número da edição obrigatório
            errors.rejectValue("evento", "errors.required");
        }
    }

    public void validateTema(final String tema, final Errors errors) {
        if (CharSequenceUtils.isBlankOrNull(tema)) {
            // tema obrigatório
            errors.rejectValue("tema", "errors.required");
        } else if (!TEMA_PATTERN.matcher(tema).matches()) {
            // tema inválido
            errors.rejectValue("tema", "errors.invalid");
        }
    }

    public void validateNumero(final Integer numero, final Errors errors) {
        if (numero == null) {
            // número da edição obrigatório
            errors.rejectValue("numero", "errors.required");
        }
    }

    private void validateVagas(Integer vagas, Errors errors) {
        if (vagas == null) {
            // número de vagas obrigatório
            errors.rejectValue("vagas", "errors.required");
        }
    }

    private void validateValorInscricao(BigDecimal valorInscricao, Errors errors) {
        if (valorInscricao == null) {
            // valor da inscrição obrigatório
            errors.rejectValue("valorInscricao", "errors.required");
        }
    }

    private void validatePeriodoInscricao(Period periodoInscricao, Errors errors) {
        if (periodoInscricao == null) {
            // período de inscrição obrigatório
            errors.rejectValue("periodoInscricao", "errors.required");
        } else {
            if(periodoInscricao.getStart() == null) {
                errors.rejectValue("periodoInscricao.start", "errors.required");
            }
            if(periodoInscricao.getEnd() == null) {
                errors.rejectValue("periodoInscricao.end", "errors.required");
            }
            if(!errors.hasFieldErrors("periodoInscricao.start") &&
               !errors.hasFieldErrors("periodoInscricao.end") &&
                periodoInscricao.getStart().after(periodoInscricao.getEnd())) {
                    errors.rejectValue("periodoInscricao", "errors.invalid");
            }
        }
    }
}
