/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.controller;

import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.msf.commons.persistence.model.Entity;
import br.msf.commons.persistence.service.EntityService;
import br.msf.commons.persistence.springframework.validation.Validator;
import br.msf.commons.util.CharSequenceUtils;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.BindingResult;

/**
 * Base para todos os controllers de edição de entidade (Formulários).
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class AbstractFormController<ID extends Serializable & Comparable<ID>, T extends Entity<ID>> extends I18nController {

    /**
     * Vamos chamar o command de "command", sempre!
     */
    protected static final String COMMAND_NAME = "command";

    /**
     * Retorna o validador do formulário, se houver.
     */
    protected abstract Validator<T> getValidator();

    /**
     * Retorna o Entityservice do command formulário.
     */
    protected abstract <S extends EntityService<ID, T>> S getCommandService();

    /**
     * Executa a validação, se houver validador definido.
     */
    protected BindingResult runValidator(final T command, final BindingResult result) {
        if (getValidator() != null) {
            getValidator().validate(command, result);
        }
        return result;
    }
}
