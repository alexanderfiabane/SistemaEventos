/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Usuario;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import java.util.Calendar;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alexander
 */
@Service
public class EdicaoServiceBean extends AbstractEntityServiceBean<Long, Edicao> implements EdicaoService {

    @Override
    protected EdicaoDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final EdicaoDao dao) {
        super.setDao(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Edicao> findAbertas(){
        Calendar dataAtual = Calendar.getInstance();
        return getDao().findAbertas(dataAtual);
    }   
}
