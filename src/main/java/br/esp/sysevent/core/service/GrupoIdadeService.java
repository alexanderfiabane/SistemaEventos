/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.GrupoIdade;
import br.ojimarcius.commons.persistence.service.EntityService;
import java.util.Collection;

/**
 *
 * @author Fiabane
 */
public interface GrupoIdadeService extends EntityService<Long, GrupoIdade>{
    
    public Collection<GrupoIdade> findByIdade(int idade);

    public Collection<GrupoIdade> findByIdadeTipo(Integer idadeConfraternista, Confraternista.Tipo tipo);

    public Collection<GrupoIdade> findSimilares(Long idGrupoIdade);
    
}
