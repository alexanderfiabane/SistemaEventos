/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.EnderecoDao;
import br.esp.sysevent.core.model.Endereco;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Service
public class EnderecoServiceBean extends AbstractEntityServiceBean<Long, Endereco> implements EnderecoService {

    @Override
    protected EnderecoDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final EnderecoDao dao) {
        super.setDao(dao);
    }
}
