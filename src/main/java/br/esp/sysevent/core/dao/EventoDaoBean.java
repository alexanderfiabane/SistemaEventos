/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Evento;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository(value = "eventoDao")
public class EventoDaoBean extends AbstractBaseSistemaDaoBean<Long, Evento> implements EventoDao {

    @Autowired
    public EventoDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
