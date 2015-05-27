/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Pessoa;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Repository(value = "pessoaDao")
public class PessoaDaoBean extends AbstractBaseSistemaDaoBean<Long, Pessoa> implements PessoaDao {

    @Autowired
    public PessoaDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Collection<Pessoa> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order) {
        final Criteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (caseSensitive) {
            c.add(Restrictions.like("nome", nome, matchMode));
        } else {
            c.add(Restrictions.ilike("nome", nome, matchMode));
        }
        c.addOrder(order);
        return this.findByCriteria(c);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Pessoa> findByNome(final String nome) {
        return findByNome(nome, MatchMode.START, false, null);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Pessoa> findByNome(final String nome, final MatchMode matchMode) {
        return findByNome(nome, matchMode, false, null);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Pessoa> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive) {
        return findByNome(nome, matchMode, caseSensitive, null);
    }
}
