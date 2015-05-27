/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import com.javaleks.commons.core.dao.AbstractEntityDao;
import com.javaleks.commons.core.model.Entity;
import com.javaleks.commons.util.ArgumentUtils;
import com.javaleks.commons.util.ArrayUtils;
import com.javaleks.commons.util.ObjectUtils;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 */
public abstract class AbstractBaseSistemaDaoBean<ID extends Serializable & Comparable<ID>, T extends Entity<ID>>
        extends AbstractEntityDao<ID, T> implements BaseSistemaDao<ID, T> {

    @Autowired
    public AbstractBaseSistemaDaoBean(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Collection<T> findAll(final Order[] orders, final String... joins) {
        return findByCriteria(createCriteria(joins), orders);
    }

    @Override
    public Collection<T> findAll(final Collection<Order> orders, final String... joins) {
        Order[] tmp = new Order[]{};
        if (orders != null) {
            tmp = orders.toArray(tmp);
        }
        return findAll(tmp, joins);
    }

    @Override
    public Collection<T> findByProperty(final String propertyName, final Object propertyValue, final Order... orders) {
        ArgumentUtils.rejectIfNull(propertyName);
        final DetachedCriteria dc = createDetachedCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.add((propertyValue == null) ? Restrictions.isNull(propertyName) : Restrictions.eq(propertyName, propertyValue));
        return findByCriteria(dc, orders);
    }

    @Override
    public Collection<T> findByProperties(final Map<String, Object> properties, final Order... orders) {
        ArgumentUtils.rejectIfEmptyOrNull(properties);
        final DetachedCriteria dc = createDetachedCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        final Set<Map.Entry<String, Object>> props = properties.entrySet();
        for (Map.Entry<String, Object> entry : props) {
            final String propName = entry.getKey();
            final Object propValue = entry.getValue();
            dc.add((propValue == null) ? Restrictions.isNull(propName) : Restrictions.eq(propName, propValue));
        }
        return findByCriteria(dc, orders);
    }

    protected Collection<T> findByCriteria(final CriteriaSpecification criteria, final Order... orders) {
        return findByCriteria(criteria, 0, MAX_RESULTS, orders);
    }

    protected Collection<T> findByCriteria(final CriteriaSpecification criteria, final int firstResult, final int maxResults, final Order... orders) {
        if (ObjectUtils.isType(DetachedCriteria.class, criteria)) {
            final DetachedCriteria c = (DetachedCriteria) criteria;
            if (ArrayUtils.isNotEmpty(orders)) {
                for (Order order : orders) {
                    c.addOrder(order);
                }
            }
            return (Collection<T>) getHibernateTemplate().findByCriteria(c, firstResult, maxResults);
        } else if (ObjectUtils.isType(Criteria.class, criteria)) {
            final Criteria c = (Criteria) criteria;
            if (ArrayUtils.isNotEmpty(orders)) {
                for (Order order : orders) {
                    c.addOrder(order);
                }
            }
            return ((Criteria) criteria).setFirstResult(firstResult).setMaxResults(maxResults).list();
        } else {
            throw new IllegalArgumentException(criteria.getClass().getSimpleName() + " is not supported.");
        }
    }

    protected Collection<T> findByCriteria(final DetachedCriteria criteria, final Order... orders) {
        if (ArrayUtils.isNotEmpty(orders)) {
            for (Order order : orders) {
                criteria.addOrder(order);
            }
        }
        return criteria.getExecutableCriteria(getCurrentSession()).list();
    }

}
