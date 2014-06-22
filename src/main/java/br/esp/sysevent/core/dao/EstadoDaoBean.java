/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Estado;
import br.ojimarcius.commons.persistence.dao.AbstractEntityDaoBean;
import java.util.Collection;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Repository
public class EstadoDaoBean extends AbstractEntityDaoBean<Long, Estado> implements EstadoDao {

    @Override
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Estado findBySigla(final String sigla) {
        final DetachedCriteria criteria = createCriteria();
        criteria.add(Restrictions.eq("sigla", sigla.toUpperCase()));
        return DataAccessUtils.uniqueResult(findByCriteria(criteria));
    }
 
    public Collection<Estado> findAll() {
        return super.findAll(Order.asc("nome"));
    }
}
