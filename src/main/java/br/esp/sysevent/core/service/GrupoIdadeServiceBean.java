/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.GrupoIdadeDao;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.GrupoIdade;
import br.ojimarcius.commons.persistence.service.AbstractEntityServiceBean;
import java.util.Collection;
import java.util.HashSet;
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
    
    @Override
    public Collection<GrupoIdade> findByIdade(int idade){       
        return getDao().findByIdade(idade);
    }
    
    @Override
    public Collection<GrupoIdade> findByIdadeTipo(Integer idade, Confraternista.Tipo tipo){       
        return getDao().findByIdadeTipo(idade, tipo);
    }   

    @Override
    public Collection<GrupoIdade> findSimilares(Long idGrupoIdade) {         
        Collection<GrupoIdade> gruposIdadesSimilares = new HashSet<GrupoIdade>();
        GrupoIdade grupoIdadeSimilar = getDao().findById(idGrupoIdade);
        Collection<GrupoIdade> gruposIdades = getDao().findByIdadeTipo(grupoIdadeSimilar.getIdadeMinima(), grupoIdadeSimilar.getTipo());
        for (GrupoIdade grupoIdade : gruposIdades) {
            if(!grupoIdade.equals(grupoIdadeSimilar)){
                gruposIdadesSimilares.add(grupoIdade);
            }
        }
        return gruposIdadesSimilares;
    }
    
    
}
