/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Edicao;
import br.ojimarcius.commons.persistence.service.EntityService;
import java.util.Collection;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
public interface CamisetaConfraternistaService extends EntityService<Long, CamisetaConfraternista> {
    
    public Collection<CamisetaConfraternista> findByConfraternista(Confraternista confraternista);
    public Collection<CamisetaConfraternista> findByEdicao(final Edicao edicao);
}
