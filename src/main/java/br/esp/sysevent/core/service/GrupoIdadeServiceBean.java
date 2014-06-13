/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.dao.GrupoIdadeDao;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fiabane
 */
@Service
public class GrupoIdadeServiceBean extends AbstractEntityServiceBean<Long, GrupoIdade> implements GrupoIdadeService{
    
    @Override
    protected GrupoIdadeDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final GrupoIdadeDao dao) {
        super.setDao(dao);
    }
    
    public Collection<GrupoIdade> findByIdade(int idade){       
        return getDao().findByIdade(idade);
    }
}
