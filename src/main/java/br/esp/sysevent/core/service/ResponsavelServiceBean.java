/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.ResponsavelDao;
import br.esp.sysevent.core.model.Responsavel;
import br.ojimarcius.commons.persistence.service.AbstractEntityServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander
 */
@Service
public class ResponsavelServiceBean extends AbstractEntityServiceBean<Long, Responsavel> implements ResponsavelService{
    
    @Override
    protected ResponsavelDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final ResponsavelDao dao) {
        super.setDao(dao);
    }
    
}
