/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.admin.validation;

import br.esp.sysevent.core.dao.EstadoDao;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.persistence.springframework.validation.AbstractValidator;
import com.javaleks.commons.util.CharSequenceUtils;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Valida uma Cidade.
 *
 * @author Alexander Fiabane do Rego (alexander.fiabane@gmail.com)
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Component
public class EstadoValidator extends AbstractValidator<Estado> {

    /**
     * Nomes de estados aceitam apenas letras e espaços.
     */
    private final Pattern NOME_PATTERN = Pattern.compile("[\\p{L} ]+");
    /**
     * Siglas de estados aceitam apenas 2 letras.
     */
    private final Pattern SIGLA_PATTERN = Pattern.compile("[a-zA-Z]{2}");
    @Autowired
    private EstadoDao estadoDao;

    /**
     * Valida o command inteiro.
     */
    @Override
    public void validateCommand(final Estado estado, final Errors errors) {
        validateNome(estado.getNome(), errors); // valida o nome
        validateSigla(estado.getId(), estado.getSigla(), errors); // valida a sigla
    }

    /**
     * Valida o nome do estado.
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
     * Valida a sigla do estado.
     */
    public void validateSigla(final Long id, final String sigla, final Errors errors) {
        if (CharSequenceUtils.isBlankOrNull(sigla)) {
            // sigla obrigatória
            errors.rejectValue("sigla", "errors.required");
        } else {
            final Estado estado = estadoDao.findBySigla(sigla);
            if (!SIGLA_PATTERN.matcher(sigla).matches()) {
                // sigla inválida
                errors.rejectValue("sigla", "errors.invalid");
            } else if (estado != null && !estado.getId().equals(id)) {
                // sigla ja existe. só acusa esse erro se nao estiver editando um novo estado (ou seja, estado sem id)
                errors.rejectValue("sigla", "errors.alreadyExists");
            }
        }
    }
}
