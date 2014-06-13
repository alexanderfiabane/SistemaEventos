/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.CamisetaConfraternistaDao;
import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Edicao;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Service
public class CamisetaConfraternistaServiceBean extends AbstractEntityServiceBean<Long, CamisetaConfraternista> implements CamisetaConfraternistaService {

    @Override
    protected CamisetaConfraternistaDao getDao() {
        return super.getDao();
    }


    @Required
    @Autowired
    public void setDao(final CamisetaConfraternistaDao dao) {
        super.setDao(dao);
    }
    
    public Collection<CamisetaConfraternista> findByConfraternista(Confraternista confraternista){
        return getDao().findByConfraternista(confraternista);
    }
    
    public Collection<CamisetaConfraternista> findByEdicao(Edicao edicao){
        return getDao().findByEdicao(edicao);
    }
}
