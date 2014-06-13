/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.DocumentoDao;
import br.esp.sysevent.core.model.Documento;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Service
public class DocumentoServiceBean extends AbstractEntityServiceBean<Long, Documento> implements DocumentoService {

    @Override
    protected DocumentoDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final DocumentoDao dao) {
        super.setDao(dao);
    }
}
