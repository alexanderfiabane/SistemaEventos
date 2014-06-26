/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.GrupoIdade;
import br.ojimarcius.commons.persistence.dao.EntityDao;
import java.util.Collection;

/**
 *
 * @author Fiabane
 */
public interface GrupoIdadeDao extends EntityDao<Long, GrupoIdade>{
    
    public Collection<GrupoIdade> findByIdade(int idade);

    public Collection<GrupoIdade> findByIdadeTipo(int idade, Confraternista.Tipo tipo);
}
