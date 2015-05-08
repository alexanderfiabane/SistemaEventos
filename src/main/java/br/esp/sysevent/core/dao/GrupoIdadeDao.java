/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.GrupoIdade;
import com.javaleks.commons.core.dao.EntityDao;
import java.util.Collection;

/**
 *
 * @author Fiabane
 */
public interface GrupoIdadeDao extends EntityDao<Long, GrupoIdade>{

    public Collection<GrupoIdade> findByIdade(int idade);
    public Collection<GrupoIdade> findByIdadeTipo(int idade, Confraternista.Tipo tipo);
    public Collection<GrupoIdade> findSimilares(Long idGrupoIdade);
}
