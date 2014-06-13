/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.PessoaDao;
import br.esp.sysevent.core.model.Pessoa;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import java.util.Collection;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Service
public class PessoaServiceBean extends AbstractEntityServiceBean<Long, Pessoa> implements PessoaService {

    @Override
    protected PessoaDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final PessoaDao dao) {
        super.setDao(dao);
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

    @Override
    @Transactional(readOnly = true)
    public Collection<Pessoa> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order) {
        return getDao().findByNome(nome, matchMode, caseSensitive, order);
    }
}
