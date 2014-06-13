/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.controller;

import br.esp.sysevent.web.controller.util.ControllerUtils;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * Base para todos os controllers.
 * <p/>
 * Oferece acesso ao MessageSource, para facilitar a internacionalização.
 * <p/>
 * Também oferece acesso ao formatador de datas, de acordo com o locale.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class I18nController {

    @Autowired
    private MessageSource messageSource;

    protected DateFormat getDateFormat(final Locale locale) {
        return ControllerUtils.getDateFormat(messageSource, locale);
    }

    protected NumberFormat getNumberFormat(final Locale locale) {
        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(locale));
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
        return decimalFormat;
    }

    protected String getMessage(final String key, final Locale locale) {
        return ControllerUtils.getMessage(key, messageSource, locale);
    }
}
