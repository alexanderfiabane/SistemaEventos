/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.GrupoIdade;
import br.ojimarcius.commons.persistence.springframework.validation.AbstractValidator;
import br.ojimarcius.commons.util.CharSequenceUtils;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Fiabane
 */
@Component
public class GrupoIdadeValidator extends AbstractValidator<GrupoIdade>{

    private final Pattern NOME_PATTERN = Pattern.compile("[\\p{L} ]+");
    
    @Override
    public void validateCommand(GrupoIdade grupoIdade, Errors errors) {
        validateEdicao(grupoIdade.getEdicaoEvento(), errors);
        validateNome(grupoIdade.getNome(), errors);
        validateTipo(grupoIdade.getTipo(), errors);
        validateVagas(grupoIdade.getVagas(), errors);
        validateFaixaEtaria(grupoIdade.getIdadeMinima(), grupoIdade.getIdadeMaxima(), errors);
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

    private void validateFaixaEtaria(Integer idadeMinima, Integer idadeMaxima, Errors errors) {
        if(idadeMinima > idadeMaxima){
            errors.rejectValue("idadeMinima", "errors.age.minmax");
        }
    }

    private void validateTipo(Confraternista.Tipo tipo, Errors errors) {
        if (tipo == null){
            errors.rejectValue("tipo", "errors.required");
        }
    }
    
}
