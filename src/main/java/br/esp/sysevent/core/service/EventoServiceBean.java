/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.EventoDao;
import br.esp.sysevent.core.model.Evento;
import br.ojimarcius.commons.persistence.service.AbstractEntityServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Service
public class EventoServiceBean extends AbstractEntityServiceBean<Long, Evento> implements EventoService {

    @Override
    protected EventoDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final EventoDao dao) {
        super.setDao(dao);
    }
}
