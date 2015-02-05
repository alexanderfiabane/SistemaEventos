/*
 * Copyright (c) 2014, CPD-UFSM. All Rights Reserved.
 */
package br.ufsm.cpd.sie.core.ajax;

import br.ufsm.cpd.commons.search.Operator;
import br.ufsm.cpd.commons.text.EnhancedStringBuilder;
import br.ufsm.cpd.commons.util.ArgumentUtils;
import br.ufsm.cpd.commons.util.CharSequenceUtils;
import br.ufsm.cpd.commons.util.CollectionUtils;
import br.ufsm.cpd.commons.util.DateUtils;
import br.ufsm.cpd.commons.util.LocaleUtils;
import br.ufsm.cpd.commons.util.NumberUtils;
import br.ufsm.cpd.commons.web.spring.validation.ValidatorConstants;
import br.ufsm.cpd.sie.core.entidade.Entity;
import br.ufsm.cpd.sie.core.entidade.ItemTabEstruturada;
import br.ufsm.cpd.sie.core.service.EntityService;
import br.ufsm.cpd.sie.core.service.TabEstruturadaService;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

/**
 * Classe base para pagina��o ajax.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 */
public abstract class AbstractAjaxService extends ValidatorConstants {
    protected static final Logger LOGGER = Logger.getLogger(AbstractAjaxService.class.getName());
    protected static final String[] TRUE_VALUES = {"true", "on", "yes", "checked", "sim", "y", "s", "1"};
    protected MessageSource messageSource;
    protected Locale locale;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    public MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * Indica se o mapa de par�metros possui algum item.
     *
     * @param params O mapa a ser avaliado.
     * @return true se este mapa possuir algum item. false caso contr�rio.
     */
    protected static boolean hasParams(final Map<String, String> params) {
        return CollectionUtils.isNotEmpty(params);
    }
    /**
     * Indica se o par�metro de dado nome (key) existe no mapa e n�o � vazio.
     *
     * @param key    O nome do par�metro.
     * @param params O mapa a ser avaliado.
     * @return true se existe um parametro n�o-vazio com o nome dado no mapa. false caso contr�rio.
     */
    protected static boolean hasParam(final String key, final Map<String, String> params) {
        return CharSequenceUtils.isNotBlank(getParam(key, params));
    }
    /**
     * Retorna o valor que est� registrado para o nome de par�metro dado.
     * <p/>
     * Pode retornar null caso este n�o exista.
     *
     * @param key    O nome do par�metro.
     * @param params O mapa a ser avaliado.
     * @return O valor que est� registrado para o nome de par�metro dado.
     */
    protected static String getParam(final String key, final Map<String, String> params) {
        if (CollectionUtils.isNotEmpty(params)) {
            final String val = params.get(key);
            return val != null ? val.trim() : val;
        }
        return null;
    }
    /**
     * Retorna um {@link br.ufsm.cpd.sie.core.ajax.AbstractAjaxService.Order Order} que representa a ordena��o da tabela.
     *
     * @param params mapa de parametros que conter� os parametros de ordena��o.
     * @return O {@link br.ufsm.cpd.sie.core.ajax.AbstractAjaxService.Order Order} representando a ordena��o da tabela,
     *         ou null se esta n�o estiver ordenada.
     */
    protected static Order getOrder(final Map<String, String> params) {
        if (hasParam("orderBy", params) && hasParam("orderMode", params)) {
            return new Order(getParam("orderBy", params), getParam("orderMode", params));
        }
        return null;
    }
    /**
     * Retorna um {@link org.hibernate.criterion.Order Order} que representa a ordena��o da tabela.
     *
     * @param params mapa de parametros que conter� os parametros de ordena��o.
     * @return O {@link org.hibernate.criterion.Order Order} representando a ordena��o da tabela, ou null se esta n�o estiver ordenada.
     */
    protected static org.hibernate.criterion.Order getHibernateOrder(final Map<String, String> params) {
        final Order order = getOrder(params);
        return order != null ? order.toHibernateOrder() : null;
    }
    /**
     * Retorna um {@link br.ufsm.cpd.commons.search.Operator.Order Order} que representa a ordena��o da tabela.
     *
     * @param params mapa de parametros que conter� os parametros de ordena��o.
     * @return O {@link br.ufsm.cpd.commons.search.Operator.Order Order} representando a ordena��o da tabela, ou null se esta n�o estiver ordenada.
     */
    protected static Operator.Order getChainOrder(final Map<String, String> params) {
        final Order order = getOrder(params);
        return order != null ? order.toChainOrder(): null;
    }

    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S get(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return get(targetClass, key, params, null, null);
    }
    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S get(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return get(targetClass, key, params, pattern, null);
    }
    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S get(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        return parseValue(targetClass, getParam(key, params), pattern, locale, false);
    }
    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S getNullsafe(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return getNullsafe(targetClass, key, params, null, null);
    }
    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S getNullsafe(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return getNullsafe(targetClass, key, params, pattern, null);
    }
    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S getNullsafe(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        return parseValue(targetClass, getParam(key, params), pattern, locale, true);
    }

    /**
     * Efetua o parse de um parametro para uma entidade.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     *
     * @param <E>         Tipo de entidade do retorno.
     * @param targetClass Classe do tipo de entidade do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param service     O service a ser usado na consulta.
     * @param joins       Joins a serem efetuados na consulta.
     * @return O parametro desejado convertido para o tipo desejado.
     */
    protected static <E extends Entity<Long>> E get(final Class<E> targetClass, final String key, final Map<String, String> params, final EntityService<Long, E> service, final String... joins) {
        ArgumentUtils.rejectIfAnyNull(targetClass, service);
        final Long id = get(Long.class, key, params);
        E entity = null;
        if (id != null) {
            entity = service.findById(id, joins);
        }
        return entity;
    }
    /**
     * Efetua o parse de um parametro para um ItemTabEstruturada.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     *
     * @param <I>         Tipo de ItemTabEstruturada do retorno.
     * @param targetClass Classe do tipo de entidade do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param service     O service a ser usado na consulta.
     * @param joins       Joins a serem efetuados na consulta.
     * @return O parametro desejado convertido para o tipo desejado.
     */
    protected static <I extends ItemTabEstruturada> I get(final Class<I> targetClass, final String key, final Map<String, String> params, final TabEstruturadaService service, final String... joins) {
        ArgumentUtils.rejectIfAnyNull(targetClass, service);
        final Integer item = get(Integer.class, key, params);
        I entity = null;
        if (item != null) {
            entity = service.getItem(targetClass, item, joins);
        }
        return entity;
    }

    /**
     * Efetua o parse de um parametro para uma cole��o.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da cole��o.
     * @param targetClass Classe do tipo de Item da cole��o.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado em formato de cole��o.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getCollection(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return getCollection(targetClass, key, params, null, null);
    }
    /**
     * Efetua o parse de um parametro para uma cole��o.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da cole��o.
     * @param targetClass Classe do tipo de Item da cole��o.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de cole��o.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getCollection(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return getCollection(targetClass, key, params, pattern, null);
    }
    /**
     * Efetua o parse de um parametro para uma cole��o.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da cole��o.
     * @param targetClass Classe do tipo de Item da cole��o.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de cole��o.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getCollection(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        final String sval = getParam(key, params);
        if (sval == null) {
            return null;
        }
        final Collection<String> tokens = new EnhancedStringBuilder(sval).split("\\|", true);
        Collection<S> collection = CollectionUtils.EMPTY_LIST;
        if (CollectionUtils.isNotEmpty(tokens)) {
            collection = new ArrayList<S>(tokens.size());
            for (String token : tokens) {
                S val = parseValue(targetClass, token, pattern, locale, false);
                if (val != null) {
                    collection.add(val);
                }
            }
        }
        return collection;
    }
    /**
     * Efetua o parse de um parametro para uma cole��o.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da cole��o.
     * @param targetClass Classe do tipo de Item da cole��o.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado em formato de cole��o.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getNullsafeCollection(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return getNullsafeCollection(targetClass, key, params, null, null);
    }
    /**
     * Efetua o parse de um parametro para uma cole��o.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da cole��o.
     * @param targetClass Classe do tipo de Item da cole��o.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de cole��o.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getNullsafeCollection(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return getNullsafeCollection(targetClass, key, params, pattern, null);
    }
    /**
     * Efetua o parse de um parametro para uma cole��o.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da cole��o.
     * @param targetClass Classe do tipo de Item da cole��o.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de cole��o.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getNullsafeCollection(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        final Collection<S> col = getCollection(targetClass, key, params, pattern, locale);
        return col != null ? col : CollectionUtils.EMPTY_LIST;
    }

    /**
     * Efetua o parse de um parametro para uma cole��o de entidades.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     *
     * @param <E>         Tipo de entidade da cole��o.
     * @param targetClass Classe do tipo de entidade do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param service     O service a ser usado na consulta.
     * @param joins       Joins a serem efetuados na consulta.
     * @return O parametro desejado em formato de cole��o.
     */
    protected static <E extends Entity<Long>> Collection<E> getNullsafeCollection(final Class<E> targetClass, final String key, final Map<String, String> params, final EntityService<Long, E> service, final String... joins) {
        final Collection<Long> ids = getNullsafeCollection(Long.class, key, params);
        if (CollectionUtils.isNotEmpty(ids)) {
            final Collection<E> entities = new ArrayList<E>(ids.size());
            for (Long id : ids) {
                final E entity = service.findById(id, joins);
                if (entity != null) {
                    entities.add(entity);
                }
            }
            return entities;
        }
        return CollectionUtils.EMPTY_LIST;
    }
    /**
     * Efetua o parse de um parametro para uma cole��o de ItemTabEstruturadas.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     *
     * @param <I>         Tipo de ItemTabEstruturada da cole��o.
     * @param targetClass Classe do tipo de entidade do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param service     O service a ser usado na consulta.
     * @param joins       Joins a serem efetuados na consulta.
     * @return O parametro desejado em formato de cole��o.
     */
    protected static <I extends ItemTabEstruturada> Collection<I> getNullsafeCollection(final Class<I> targetClass, final String key, final Map<String, String> params, final TabEstruturadaService service, final String... joins) {
        final Collection<Integer> items = getNullsafeCollection(Integer.class, key, params);
        if (CollectionUtils.isNotEmpty(items)) {
            final Collection<I> entities = new ArrayList<I>(items.size());
            for (Integer item : items) {
                final I entity = service.getItem(targetClass, item, joins);
                if (entity != null) {
                    entities.add(entity);
                }
            }
            return entities;
        }
        return CollectionUtils.EMPTY_LIST;
    }

    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getArray(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return getArray(targetClass, key, params, null, null);
    }
    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getArray(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return getArray(targetClass, key, params, pattern, null);
    }
    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE M�TODO N�O � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getArray(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        final Collection<S> col = getCollection(targetClass, key, params, pattern, locale);
        if (col == null) {
            return null;
        }
        final S[] array = (S[]) Array.newInstance(targetClass, col.size());
        return col.toArray(array);
    }
    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getNullsafeArray(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return getNullsafeArray(targetClass, key, params, null, null);
    }
    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getNullsafeArray(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return getNullsafeArray(targetClass, key, params, pattern, null);
    }
    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este m�todo suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de convers�o (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getNullsafeArray(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        final Collection<S> col = getNullsafeCollection(targetClass, key, params, pattern, locale);
        final S[] array = (S[]) Array.newInstance(targetClass, col.size());
        return col.toArray(array);
    }

    /**
     * Efetua o parse de um parametro para um array de entidades.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     *
     * @param <E>         Tipo de entidade do array.
     * @param targetClass Classe do tipo de entidade do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param service     O service a ser usado na consulta.
     * @param joins       Joins a serem efetuados na consulta.
     * @return O parametro desejado em formato de array.
     */
    protected static <E extends Entity<Long>> E[] getNullsafeArray(final Class<E> targetClass, final String key, final Map<String, String> params, final EntityService<Long, E> service, final String... joins) {
        final Collection<E> col = getNullsafeCollection(targetClass, key, params, service, joins);
        final E[] array = (E[]) Array.newInstance(targetClass, col.size());
        return col.toArray(array);
    }
    /**
     * Efetua o parse de um parametro para um array de ItemTabEstruturadas.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE M�TODO � NULL SAFE.</span>
     *
     * @param <I>         Tipo de ItemTabEstruturada do array.
     * @param targetClass Classe do tipo de entidade do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param service     O service a ser usado na consulta.
     * @param joins       Joins a serem efetuados na consulta.
     * @return O parametro desejado em formato de array.
     */
    protected static <I extends ItemTabEstruturada> I[] getNullsafeArray(final Class<I> targetClass, final String key, final Map<String, String> params, final TabEstruturadaService service, final String... joins) {
        final Collection<I> col = getNullsafeCollection(targetClass, key, params, service, joins);
        final I[] array = (I[]) Array.newInstance(targetClass, col.size());
        return col.toArray(array);
    }

    /**
     * M�todo interno que faz o parse de um valor em formato String para um tipo desejado.
     * <p/>
     * Para adicionar suporte para um novo tipo de targetClass, apenas adicione o novo parsing no corpo desse m�todo,
     * e todos os demais m�todos que recebem targetClass passar�o a reconhec�-lo.
     * <p/>
     * Este m�todo suporta:
     * <ul>
     * <li>String</li>
     * <li>StringBuilder</li>
     * <li>StringBuffer</li>
     * <li>Boolean</li>
     * <li>Integer</li>
     * <li>Long</li>
     * <li>Double</li>
     * <li>Float</li>
     * <li>BigDecimal</li>
     * <li>Short</li>
     * <li>Byte (representado em decimal)</li>
     * <li>Date</li>
     * <li>Calendar</li>
     * </ul>
     *
     * @param <S>         Tipo de item da cole��o.
     * @param targetClass Classe do tipo de Item da cole��o.
     * @param sval        Valor a ser efetuado o parsing.
     * @param pattern     O pattern de convers�o (se targetClass == Number, Date ou Calendar).
     * @param locale      O locale de convers�o (se targetClass == Number, Date ou Calendar).
     * @param nullsafe    Indica se o retorno dever� ser nullsafe
     * @return O valor da string dada convertido para o tipo desejado.
     */
    protected static <S extends Serializable> S parseValue(final Class<S> targetClass, final String sval, final CharSequence pattern, final Locale locale, final boolean nullsafe) {
        /* throw exception se targetClass invalido */
        validateTargetClass(targetClass);

        /* usa defaults se null */
        final Locale l = LocaleUtils.getNullSafeLocale(locale);
        final CharSequence np = NumberUtils.getNullSafePattern(pattern);
        final CharSequence dp = DateUtils.getNullSafePattern(pattern);

        Object val = null;
        if (String.class.isAssignableFrom(targetClass)) {
            if (sval != null) {
                val = sval;
            } else if (nullsafe) {
                // retorna vazio se nulo
                val = "";
            }
        } else if (StringBuilder.class.isAssignableFrom(targetClass)) {
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = new StringBuilder(sval);
            } else if (nullsafe) {
                // retorna StringBuilder vazia se nulo
                val = new StringBuilder();
            }
        } else if (StringBuffer.class.isAssignableFrom(targetClass)) {
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = new StringBuffer(sval);
            } else if (nullsafe) {
                // retorna StringBuilder vazia se nulo
                val = new StringBuffer();
            }
        } else if (Number.class.isAssignableFrom(targetClass)) {
            final Class<Number> ic = (Class<Number>) targetClass;
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = NumberUtils.parseTo(ic, sval, np, l);
            } else if (nullsafe) {
                // retorna zero se nulo
                val = NumberUtils.parseTo(ic, "0", np, l);
            }
        } else if (Date.class.isAssignableFrom(targetClass)) {
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = DateUtils.parseDate(sval, dp, l);
            } else if (nullsafe) {
                // n�o sei o que assumir em caso de Date nula e nullsafe = true
                throw new IllegalArgumentException("Could not get a NullSafe Date.");
            }
        } else if (Calendar.class.isAssignableFrom(targetClass)) {
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = DateUtils.parseCalendar(sval, dp, l);
            } else if (nullsafe) {
                // n�o sei o que assumir em caso de Calendar nula e nullsafe = true
                throw new IllegalArgumentException("Could not get a NullSafe Calendar.");
            }
        } else if (Boolean.class.isAssignableFrom(targetClass)) {
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = Boolean.FALSE;
                for (String v : TRUE_VALUES) {
                    if (v.equalsIgnoreCase(sval)) {
                        val = Boolean.TRUE;
                        break;
                    }
                }
            } else if (nullsafe) {
                // retorna false se nulo
                val = Boolean.FALSE;
            }
        } else {
            throw new IllegalArgumentException("Parsing for " + targetClass + " not supported.");
        }
        return (S) val;
    }
    private static void validateTargetClass(final Class<?> targetClass) {
        ArgumentUtils.rejectIfNull(targetClass);
        if (targetClass.isInterface()) {
            throw new IllegalArgumentException("targetClass cannot be an interface.");
        } else if (targetClass.isArray()) {
            throw new IllegalArgumentException("targetClass cannot be an array.");
        } else if (Collection.class.isAssignableFrom(targetClass) || Map.class.isAssignableFrom(targetClass)) {
            throw new IllegalArgumentException("targetClass cannot be a collection.");
        }
    }

    /**
     * Adiciona o field ao mapa de erros, resolvendo a message code para seu valor (de acordo com o message-source configurado).
     * <p/>
     * Se o code dado n�o for resolvido para uma msg, o pr�prio code � usado como msg de erro.
     *
     * @param field       O nome do campo a ser rejeitado.
     * @param messageCode O code para a mensagem de erro, a ser resolvido pelo message-source.
     * @param errors      O mapa de [field : msg de erro] a ser retornado para a view.
     */
    protected void reject(final String field, final String messageCode, final Map<String, String> errors) {
        reject(field, messageCode, null, errors);
    }
    /**
     * Adiciona o field ao mapa de erros, resolvendo a message code para seu valor (de acordo com o message-source configurado).
     * <p/>
     * Se o code dado n�o for resolvido para uma msg, o pr�prio code � usado como msg de erro.
     *
     * @param field       O nome do campo a ser rejeitado.
     * @param messageCode O code para a mensagem de erro, a ser resolvido pelo message-source.
     * @param params      Um array de parametros para a mensagem resolvida, caso necess�rio. Formato {0}, {1}, etc....
     * @param errors      O mapa de [field : msg de erro] a ser retornado para a view.
     */
    protected void reject(final String field, final String messageCode, final Object[] params, final Map<String, String> errors) {
        String message = null;
        if (CharSequenceUtils.isNotBlank(messageCode) && getMessageSource() != null) {
            try {
                message = getMessageSource().getMessage(messageCode, params, LocaleUtils.getNullSafeLocale(locale));
            } catch (NoSuchMessageException ex) {
                message = messageCode;
            }
        }
        if (CharSequenceUtils.isBlankOrNull(message)) {
            message = CharSequenceUtils.isNotBlank(messageCode) ? messageCode : "Invalid Field.";
        }
        errors.put(field, message);
    }

    /**
     * Representa os parametros de ordena��o de uma AjaxTable.
     */
    public static class Order {
        private final String column;
        private final String mode;
        public Order(final String column, final String mode) {
            ArgumentUtils.rejectIfAnyBlankOrNull(column, mode);
            this.column = column;
            this.mode = mode;
        }
        public String getColumn() {
            return column;
        }
        public String getMode() {
            return mode;
        }
        /**
         * Converte este objeto para o tipo Order do hibernate, que pode ser usado em consultas.
         *
         * @return O order do hibernate.
         */
        public org.hibernate.criterion.Order toHibernateOrder() {
            return "asc".equalsIgnoreCase(mode) ? org.hibernate.criterion.Order.asc(column) : org.hibernate.criterion.Order.desc(column);
        }
        public Operator.Order toChainOrder() {
            return new Operator.Order(column, ("asc".equalsIgnoreCase(mode)? Operator.Order.Type.Asc : Operator.Order.Type.Desc));
        }
    }
}
