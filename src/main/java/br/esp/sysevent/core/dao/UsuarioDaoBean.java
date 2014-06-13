/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Usuario;
import br.msf.commons.persistence.dao.AbstractEntityDaoBean;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
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
public class UsuarioDaoBean extends AbstractEntityDaoBean<Long, Usuario> implements UsuarioDao {

    @Override
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario findByLogin(final String login, final Boolean onlyAtivos) {
        final DetachedCriteria criteria = createCriteria()
                .add(Restrictions.eq("username", login));
        if (onlyAtivos) {
            criteria.add(Restrictions.eq("enabled", Boolean.TRUE));
        }
        return DataAccessUtils.uniqueResult(findByCriteria(criteria));
    }
}
