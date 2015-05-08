/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Noticia;
import com.javaleks.commons.core.dao.EntityDao;

/**
 *
 * @author Alexander
 */
public interface NoticiaDao extends EntityDao<Long, Noticia>{

     @Override
     public Long save(final Noticia entity);
     @Override
     public Long saveOrUpdate(final Noticia entity);

}
