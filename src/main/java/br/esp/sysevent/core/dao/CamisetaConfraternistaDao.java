/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Edicao;
import com.javaleks.commons.core.dao.EntityDao;
import java.util.Collection;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
*/
public interface CamisetaConfraternistaDao extends EntityDao<Long, CamisetaConfraternista> {

    public Collection<CamisetaConfraternista> findByConfraternista(final Confraternista confraternista);
    public Collection<CamisetaConfraternista> findByEdicao(final Edicao edicao);
}
