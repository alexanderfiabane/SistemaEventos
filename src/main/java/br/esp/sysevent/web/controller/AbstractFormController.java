/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.controller;

import br.esp.sysevent.persistence.springframework.validation.Validator;
import com.javaleks.commons.core.dao.EntityDao;
import com.javaleks.commons.core.model.Entity;
import java.io.Serializable;
import org.springframework.validation.BindingResult;

/**
 * Base para todos os controllers de edição de entidade (Formulários).
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @param <ID>
 * @param <T>
 */
public abstract class AbstractFormController<ID extends Serializable & Comparable<ID>, T extends Entity<ID>> extends I18nController {

    /**
     * Vamos chamar o command de "command", sempre!
     */
    protected static final String COMMAND_NAME = "command";

    /**
     * Retorna o validador do formulário, se houver.
     * @return
     */
    protected abstract Validator<T> getValidator();

    /**
     * Retorna o Entityservice do command formulário.
     * @param <S>
     * @return
     */
    protected abstract <S extends EntityDao<ID, T>> S getCommandService();

    /**
     * Executa a validação, se houver validador definido.
     * @param command
     * @param result
     * @return
     */
    protected BindingResult runValidator(final T command, final BindingResult result) {
        if (getValidator() != null) {
            getValidator().validate(command, result);
        }
        return result;
    }
}
