package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.CorCamiseta;
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
public class CorCamisetaValidator extends AbstractValidator<CorCamiseta> {
    
    private final Pattern DESCRICAO_PATTERN = Pattern.compile("[\\p{L} ]+");

    @Override
    public void validateCommand(CorCamiseta target, Errors errors) {
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
