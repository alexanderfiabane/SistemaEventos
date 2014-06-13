/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.msf.commons.persistence.dao.AbstractEntityDaoBean;
import br.msf.commons.persistence.dao.EntityDao;
import br.msf.commons.persistence.model.Entity;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
public abstract class AbstractEntityDao<I extends Serializable & Comparable<I>, T extends Entity<I>> extends AbstractEntityDaoBean<I, T> implements EntityDao<I, T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractEntityDaoBean.class.getName());
    /**
     * Inicializa as propriedades de uma entidade.
     *
     * @param entity     a entidade na qual as propriedadades serão inicializadas.
     * @param properties as propriedades a serem inicializadas.
     */
    protected void initialize(final T entity, final String... properties) {
        if(properties == null) {
            return;
        }
        for (String property : properties) {
            initializeProperty(entity, property);
        }
    }
    /**
     * Inicializa as propriedades de uma coleção de entidades.
     *
     * @param entities   a coleção de entidades na qual as propriedadades serão
     *                   inicializadas.
     * @param properties as propriedades a serem inicializadas.
     */
    protected void initialize(final Collection<T> entities, final String... properties) {
        for (T entity : entities) {
            initialize(entity, properties);
        }
    }
    /**
     * Inicializa um propriedade do objeto passado. Caso a propriedade seja
     * composta (object.a.b.c), percorre cada uma delas para fazer a
     * inicialização.
     *
     * @param object   o objeto que terá a propriedade inicializada.
     * @param property a propriedade a ser inicializada.
     */
    protected void initializeProperty(final Object object, String property) {
        if (object == null) {
            return;
        }
        //retira os tipos de join
        property = property.replaceAll("\\[\\w+\\]", "");
        try {
            // Se eh colecao, nao pode mexer na propriedade, pois deve acessar
            // a propriedade dos elementos.
            if (isCollection(object)) {
                initCollection(object, property);
            } else {
                final int indexDot = property.indexOf('.');
                String propertyGet = property;

                // A propriedade eh composta.
                if (indexDot > 0) {
                    propertyGet = property.substring(0, indexDot);
                    property = property.substring(indexDot + 1, property.length());
                }
                final String clazz = object.getClass().getName();
                LOGGER.log(Level.FINE, "Inicializando {0}.{1} ", new Object[]{clazz, propertyGet});

                // Acessamos a propriedade especificada do objeto.
                final Object returned = PropertyUtils.getProperty(object, propertyGet);
                if (returned != null) {
                    initializeObject(returned);
                }
                // Se a propriedade eh composta, chamamos o metodo recursivamente.
                if (indexDot > 0) {
                    initializeProperty(returned, property);
                }
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Acesso negado a propriedade '" + property + "' em " + object.getClass(), ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException("Problema invocando o getter '" + property + "' em " + object.getClass(), ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Propriedade '" + property + "' nao encontrada em " + object.getClass(), ex);
        }
    }
    /**
     * Retorna
     * <code>true</code> se o objeto passado for uma {@link Collection coleção}.
     *
     * @param object o objeto a ser verificado.
     * @return true se for coleção, false caso contrário.
     */
    protected boolean isCollection(final Object object) {
        final boolean isCollection = (object instanceof Collection);
        LOGGER.log(Level.FINE, "{0} is collection ? {1} ", new Object[]{object.getClass(), isCollection});
        return isCollection;
    }
    /**
     * Retorna
     * <code>true</code> se o objeto passado for uma {@link Entity entidade}.
     *
     * @param object o objeto a ser verificado.
     * @return true se for entity, false caso contrário.
     */
    protected boolean isEntity(final Object object) {
        // A comparacao do pacote nao eh uma boa, mas vamos fazer o q ?
        final boolean isEntity = (object instanceof Entity);
        LOGGER.log(Level.FINE, "{0} is entity ? {1} ", new Object[]{object.getClass(), isEntity});
        return isEntity;
    }
    /**
     * Inicializa cada objeto da coleção passada.
     *
     * @param object   a propriedade dos objetos da coleção a ser inicializada.
     * @param property a propriedade (coleção) a ser inicializada.
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.SecurityException
     */
    @SuppressWarnings("unchecked")
    protected void initCollection(final Object object, final String property) throws InvocationTargetException, NoSuchMethodException, SecurityException {
        final Collection<Object> col = (Collection<Object>) object;
        LOGGER.log(Level.FINE, "Inicializando colecao {0} ", new Object[]{property});
        for (Object obj : col) {
            initializeProperty(obj, property);
        }
    }
    /**
     * Inicializa um objeto em si. Caso seja uma {@link Entity}, pegamos a
     * propriedade {@link Entity#id}. Caso seja uma
     * {@link #isCollection(java.lang.Object) coleção}, chamamos o método
     * {@link Collection#size()}. Se não for nenhum dos 2, ignoramos.
     *
     * @param object o objeto a ser inicializado.
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.SecurityException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.NoSuchMethodException
     */
    protected void initializeObject(final Object object) throws InvocationTargetException, SecurityException, IllegalAccessException, NoSuchMethodException {
        if (isEntity(object)) {
            // Se for entity, vamos chamar o id.
            PropertyUtils.getProperty(object, "id");
        } else if (isCollection(object)) {
            object.getClass().getMethod("size").invoke(object);
        }
    }
}
