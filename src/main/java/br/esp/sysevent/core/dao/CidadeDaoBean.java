/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Estado;
import br.msf.commons.persistence.dao.AbstractEntityDaoBean;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
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
public class CidadeDaoBean extends AbstractEntityDaoBean<Long, Cidade> implements CidadeDao {

    @Override
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Collection<Cidade> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order) {
        final DetachedCriteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (caseSensitive) {
            c.add(Restrictions.like("nome", nome, matchMode));
        } else {
            c.add(Restrictions.ilike("nome", nome, matchMode));
        }
        return findByCriteria(c, order);
    }

    @Override
    public Collection<Cidade> findByEstado(final Estado estado, final Order order) {
        final DetachedCriteria c = createCriteria().
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
                add(Restrictions.eq("estado", estado));
        return findByCriteria(c, order);
    }

    @Override
    public Collection<Cidade> findByEstado(final Long idEstado) {
        final DetachedCriteria criteria = createCriteria()
                .add(Restrictions.eq("estado.id", idEstado))
                .addOrder(Order.asc("nome"));
        return findByCriteria(criteria);
    }
    
    @Override
    public Cidade findCapital(final Estado estado) {
        final DetachedCriteria criteria = createCriteria()
                .add(Restrictions.eq("estado", estado))
                .add(Restrictions.eq("capital", Boolean.TRUE));
        return DataAccessUtils.uniqueResult(findByCriteria(criteria));
    }
}
