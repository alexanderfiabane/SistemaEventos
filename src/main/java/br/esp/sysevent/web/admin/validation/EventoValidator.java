/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.service.EstadoService;
import br.ojimarcius.commons.persistence.springframework.validation.AbstractValidator;
import br.ojimarcius.commons.util.CharSequenceUtils;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Alexander
 */
@Component
public class EventoValidator extends AbstractValidator<Evento> {

    private final Pattern NOME_PATTERN = Pattern.compile("[\\p{L} ]+");
    private final Pattern SIGLA_PATTERN = Pattern.compile("[a-zA-Z]+");
    //private final Pattern WEBSITE_PATTERN = Pattern.compile("^(http[s]?://|ftp://)?(www\\\\.)?[a-zA-Z0-9-\\\\.]+\\\\.(com|org|net|mil|edu|ca|co.uk|com.au|gov|br)$");

    @Autowired
    EstadoService eventoService;

    @Override
    public void validateCommand(final Evento evento, final Errors errors) {
        validateNome(evento.getNome(), errors); // valida o nome
        validateSigla(evento.getId(), evento.getSigla(), errors); // valida a sigla
        validateSite(evento.getSite(), errors); // valida a sigla
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

    public void validateSigla(final Long id, final String sigla, final Errors errors) {
        if (CharSequenceUtils.isBlankOrNull(sigla)) {
            // sigla obrigatória
            errors.rejectValue("sigla", "errors.required");
        } else if (!SIGLA_PATTERN.matcher(sigla).matches()) {
            // sigla inválida
            errors.rejectValue("sigla", "errors.invalid");
        }
    }

    private void validateSite(String site, Errors errors) {
        if (CharSequenceUtils.isBlankOrNull(site)) {
            // site obrigatório
            errors.rejectValue("site", "errors.required");
        } 
//        else if (!WEBSITE_PATTERN.matcher(site).matches()) {
//            // site inválida
//            errors.rejectValue("site", "errors.invalid");
//        }
    }
}
