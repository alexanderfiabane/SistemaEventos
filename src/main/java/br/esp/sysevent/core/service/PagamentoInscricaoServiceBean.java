/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.PagamentoInscricaoDao;
import br.esp.sysevent.core.model.PagamentoInscricao;
import br.ojimarcius.commons.persistence.service.AbstractEntityServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Service
public class PagamentoInscricaoServiceBean extends AbstractEntityServiceBean<Long,PagamentoInscricao> implements PagamentoInscricaoService {

    @Override
    protected PagamentoInscricaoDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    protected void setDao(PagamentoInscricaoDao dao) {
        super.setDao(dao);
    }

}
