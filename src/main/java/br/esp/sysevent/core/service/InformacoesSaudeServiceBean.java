/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.InformacoesSaudeDao;
import br.esp.sysevent.core.model.InformacoesSaude;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

/**
 *
 * @author Alexander
 */
public class InformacoesSaudeServiceBean extends AbstractEntityServiceBean<Long, InformacoesSaude> implements InformacoesSaudeService{
    
    @Override
    protected InformacoesSaudeDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final InformacoesSaudeDao dao) {
        super.setDao(dao);
    }
    
}
