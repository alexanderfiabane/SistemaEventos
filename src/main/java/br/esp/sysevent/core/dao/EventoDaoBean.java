/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Evento;
import br.msf.commons.persistence.dao.AbstractEntityDaoBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository
public class EventoDaoBean extends AbstractEntityDaoBean<Long, Evento> implements EventoDao {

    @Override
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}