/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.PagamentoInscricao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Repository(value = "pagamentoInscricaoDao")
public class PagamentoInscricaoDaoBean extends AbstractBaseSistemaDaoBean<Long, PagamentoInscricao> implements PagamentoInscricaoDao {

    @Autowired
    public PagamentoInscricaoDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
