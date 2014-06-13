/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.Noticia;
import br.msf.commons.persistence.springframework.validation.AbstractValidator;
import java.util.Calendar;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Alexander
 */
@Component
public class NoticiaValidator extends AbstractValidator<Noticia> {

    @Override
    public void validateCommand(final Noticia noticia, Errors errors) {
        validateTitulo(noticia.getTitulo(), errors); //valida o título da notícia
        validateConteudo(noticia.getConteudo(), errors); //valida o texto da notícia       
    }

    private void validateTitulo(String titulo, Errors errors) {
        if (titulo == null || titulo.isEmpty()) {
            // número da edição obrigatório
            errors.rejectValue("titulo", "errors.required");
        }
    }

    private void validateConteudo(String conteudo, Errors errors) {
        if (conteudo == null || conteudo.isEmpty()) {
            errors.rejectValue("conteudo", "errors.required");
        }
    }
}
