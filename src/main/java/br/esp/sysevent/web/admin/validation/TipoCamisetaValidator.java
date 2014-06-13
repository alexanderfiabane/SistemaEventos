package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.TipoCamiseta;
import br.msf.commons.persistence.springframework.validation.AbstractValidator;
import br.msf.commons.util.CharSequenceUtils;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Giuliano
 */
@Component
public class TipoCamisetaValidator extends AbstractValidator<TipoCamiseta> {
    
    private final Pattern DESCRICAO_PATTERN = Pattern.compile("[\\p{L} ]+");

    @Override
    public void validateCommand(TipoCamiseta target, Errors errors) {
        validadeDescricao(target.getDescricao(), errors);
    }

    private void validadeDescricao(String descricao, Errors errors) {
        if(CharSequenceUtils.isBlank(descricao)) {
            errors.rejectValue("descricao", "errors.required");
        } else if (!DESCRICAO_PATTERN.matcher(descricao).matches()) {
            errors.rejectValue("descricao", "errors.invalid");
        }
    }
}
