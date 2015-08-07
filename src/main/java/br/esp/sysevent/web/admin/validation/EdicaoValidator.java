/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.model.FormaCobranca;
import br.esp.sysevent.persistence.springframework.validation.AbstractValidator;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import com.javaleks.core.model.embeddable.Period;
import java.math.BigDecimal;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Alexander
 */
@Component
public class EdicaoValidator extends AbstractValidator<Edicao> {

    protected final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Override
    public void validateCommand(final Edicao edicao, final Errors errors) {
        validateTipo(edicao.getTipo(), errors);
        validateEvento(edicao.getEvento(), errors); // valida o evento da edição
        validateTema(edicao.getTema(), errors); // valida o tema
        validateNumero(edicao.getNumero(), errors); // valida o número de edições
        validateValorInscricao(edicao.getValorInscricao(), errors); // valida o valor da inscrição
        validateVagas(edicao.getVagas(), errors); // valida o número de vagas da edição
        validatePeriodoInscricao(edicao.getPeriodoInscricao(), errors); // valida o perído de inscrição
        validatePeriodoEdicao(edicao.getPeriodoEdicao(), errors); // valida o perído da edição
        validateIdadeMinima(edicao.getIdadeMinima(), errors);
        validateValorCamiseta(edicao.getValorCamiseta(), errors);
        validateFormaCobranca(edicao.getFormaCobranca(), errors);
    }

    private void validateTipo(Edicao.Tipo tipo, Errors errors) {
        if (tipo == null){
            errors.rejectValue("tipo", "errors.required");
        }
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
        }
    }

    public void validateNumero(final String numero, final Errors errors) {
        if (CharSequenceUtils.isBlankOrNull(numero)) {
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
            if (periodoInscricao.getStart() == null) {
                errors.rejectValue("periodoInscricao.start", "errors.required");
            }
            if (periodoInscricao.getEnd() == null) {
                errors.rejectValue("periodoInscricao.end", "errors.required");
            }
            if (!errors.hasFieldErrors("periodoInscricao.start")
                && !errors.hasFieldErrors("periodoInscricao.end")
                && periodoInscricao.getStart().after(periodoInscricao.getEnd())) {
                errors.rejectValue("periodoInscricao", "errors.invalid");
            }
        }
    }
    
    private void validatePeriodoEdicao(Period periodoEdicao, Errors errors) {
        if (periodoEdicao == null) {
            // período de inscrição obrigatório
            errors.rejectValue("periodoEdicao", "errors.required");
        } else {
            if (periodoEdicao.getStart() == null) {
                errors.rejectValue("periodoEdicao.start", "errors.required");
            }
            if (periodoEdicao.getEnd() == null) {
                errors.rejectValue("periodoEdicao.end", "errors.required");
            }
            if (!errors.hasFieldErrors("periodoEdicao.start")
                && !errors.hasFieldErrors("periodoEdicao.end")
                && periodoEdicao.getStart().after(periodoEdicao.getEnd())) {
                errors.rejectValue("periodoEdicao", "errors.invalid");
            }
        }
    }

    private void validateIdadeMinima(Integer idadeMinima, Errors errors) {
        if (idadeMinima == null) {
            errors.rejectValue("idadeMinima", "errors.invalid");
        }
    }

    private void validateValorCamiseta(BigDecimal valorCamiseta, Errors errors) {
        if (valorCamiseta != null) {
            if (NumberUtils.isBigDecimal(valorCamiseta)) {
            } else {
                errors.rejectValue("valorCamiseta", "errors.invalid");
            }
        }
    }

    private void validateFormaCobranca(FormaCobranca formaCobranca, Errors errors) {
        if(formaCobranca.getTipoCobranca() == null){
            errors.rejectValue("formaCobranca.tipoCobranca", "errors.required");
        }
        if(!formaCobranca.isSemCobranca()){
            if(formaCobranca.isDepositoConta()){
                if(CharSequenceUtils.isBlankOrNull(formaCobranca.getDeposito().getBancoPlain())){
                    errors.rejectValue("formaCobranca.deposito.bancoPlain", "errors.required");
                }
                if(CharSequenceUtils.isBlankOrNull(formaCobranca.getDeposito().getAgenciaPlain())){
                    errors.rejectValue("formaCobranca.deposito.agenciaPlain", "errors.required");
                }
                if(CharSequenceUtils.isBlankOrNull(formaCobranca.getDeposito().getFavorecidoPlain())){
                    errors.rejectValue("formaCobranca.deposito.favorecidoPlain", "errors.required");
                }
                if(CharSequenceUtils.isBlankOrNull(formaCobranca.getDeposito().getNumeroContaPlain())){
                    errors.rejectValue("formaCobranca.deposito.numeroContaPlain", "errors.required");
                }
                if(CharSequenceUtils.isBlankOrNull(formaCobranca.getDeposito().getOperacao())){
                    errors.rejectValue("formaCobranca.deposito.operacao", "errors.required");
                }
            }else{
                if(CharSequenceUtils.isBlankOrNull(formaCobranca.getPagSeguro().getEmailPagSeguroPlain())){
                    errors.rejectValue("formaCobranca.pagSeguro.emailPagSeguroPlain", "errors.required");
                }else if(!EMAIL_PATTERN.matcher(formaCobranca.getPagSeguro().getEmailPagSeguroPlain()).matches()){
                    errors.rejectValue("formaCobranca.pagSeguro.emailPagSeguroPlain", "errors.invalid");
                }
                if(CharSequenceUtils.isBlankOrNull(formaCobranca.getPagSeguro().getTokenSegurancaProducao())
                        && CharSequenceUtils.isBlankOrNull(formaCobranca.getPagSeguro().getTokenSegurancaSandBox())){
                    errors.rejectValue("formaCobranca.pagSeguro.tokenSegurancaProducao", "errors.required.at.least.one");
                    errors.rejectValue("formaCobranca.pagSeguro.tokenSegurancaSandBox", "errors.required.at.least.one");
                }
            }
        }
    }

}
