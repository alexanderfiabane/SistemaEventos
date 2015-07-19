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

    private final Pattern TEMA_PATTERN = Pattern.compile("[\\s\\p{L}]+");
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
        } else if (!TEMA_PATTERN.matcher(tema).matches()) {
            // tema inválido
            errors.rejectValue("tema", "errors.invalid");
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
                if(CharSequenceUtils.isAllBlankOrNull(formaCobranca.getDeposito().getBanco())){
                    errors.rejectValue("formaCobranca.deposito.banco", "errors.required");
                }
                if(CharSequenceUtils.isAllBlankOrNull(formaCobranca.getDeposito().getAgencia())){
                    errors.rejectValue("formaCobranca.deposito.agencia", "errors.required");
                }
                if(CharSequenceUtils.isAllBlankOrNull(formaCobranca.getDeposito().getFavorecido())){
                    errors.rejectValue("formaCobranca.deposito.favorecido", "errors.required");
                }
                if(CharSequenceUtils.isAllBlankOrNull(formaCobranca.getDeposito().getNumeroConta())){
                    errors.rejectValue("formaCobranca.deposito.numeroConta", "errors.required");
                }
                if(CharSequenceUtils.isAllBlankOrNull(formaCobranca.getDeposito().getOperacao())){
                    errors.rejectValue("formaCobranca.deposito.operacao", "errors.required");
                }
            }else{
                if(CharSequenceUtils.isAllBlankOrNull(formaCobranca.getPagSeguro().getEmailPagSeguro())){
                    errors.rejectValue("formaCobranca.pagSeguro.emailPagSeguro", "errors.required");
                }else if(!EMAIL_PATTERN.matcher(formaCobranca.getPagSeguro().getEmailPagSeguro()).matches()){
                    errors.rejectValue("formaCobranca.pagSeguro.emailPagSeguro", "errors.invalid");
                }                                
                if(CharSequenceUtils.isAllBlankOrNull(formaCobranca.getPagSeguro().getTokenSegurancaProducao())
                        || CharSequenceUtils.isAllBlankOrNull(formaCobranca.getPagSeguro().getTokenSegurancaSandBox())){
                    errors.rejectValue("formaCobranca.pagSeguro.tokenSegurancaProducao", "errors.required.at.least.one");
                    errors.rejectValue("formaCobranca.pagSeguro.tokenSegurancaSandBox", "errors.required.at.least.one");
                }
            }
        }
    }

}
