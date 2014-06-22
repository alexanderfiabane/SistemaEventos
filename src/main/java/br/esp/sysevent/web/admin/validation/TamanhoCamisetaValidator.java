package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.TamanhoCamiseta;
import br.ojimarcius.commons.persistence.springframework.validation.AbstractValidator;
import br.ojimarcius.commons.util.CharSequenceUtils;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Giuliano
 */
@Component
public class TamanhoCamisetaValidator extends AbstractValidator<TamanhoCamiseta> {
    
    private final Pattern DESCRICAO_PATTERN = Pattern.compile("[A-Z0-9]+");

    @Override
    public void validateCommand(TamanhoCamiseta target, Errors errors) {
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
