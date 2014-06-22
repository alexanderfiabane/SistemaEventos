/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.PagamentoInscricao;
import br.ojimarcius.commons.persistence.dao.AbstractEntityDaoBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Repository
public class PagamentoInscricaoDaoBean extends AbstractEntityDaoBean<Long, PagamentoInscricao> implements PagamentoInscricaoDao {

    @Override
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
