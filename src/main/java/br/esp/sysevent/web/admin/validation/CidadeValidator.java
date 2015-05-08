/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.core.service.CidadeService;
import br.esp.sysevent.persistence.springframework.validation.AbstractValidator;
import br.ojimarcius.commons.util.CharSequenceUtils;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Valida uma Cidade.
 * 
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Component
public class CidadeValidator extends AbstractValidator<Cidade> {

    /**
     * Nomes de cidades aceitam apenas letras e espaços.
     */
    private final Pattern NOME_PATTERN = Pattern.compile("[\\p{L} ]+");
    
    @Autowired
    CidadeService cidadeService;

    /**
     * Valida o command inteiro.
     */
    @Override
    public void validateCommand(final Cidade cidade, final Errors errors) {
        validateNome(cidade.getNome(), errors); // valida o nome
        validateEstado(cidade.getEstado(), errors); // valida o estado
    }

    /**
     * Valida o nome da cidade.
     */
    public void validateNome(final String nome, final Errors errors) {
        if (CharSequenceUtils.isBlankOrNull(nome)) {
            // nome obrigatório
            errors.rejectValue("nome", "errors.required");
        } else if (!NOME_PATTERN.matcher(nome).matches()) {
            // nome inválido
            errors.rejectValue("nome", "errors.invalid");
        }
    }

    /**
     * Valida o estado da cidade.
     */
    public void validateEstado(final Estado estado, final Errors errors) {
        if (estado == null) {
            // sigla obrigatória
            errors.rejectValue("estado", "errors.required");
        }
    }
}
