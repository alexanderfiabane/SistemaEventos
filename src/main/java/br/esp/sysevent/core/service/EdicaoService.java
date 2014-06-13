/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Usuario;
import br.msf.commons.persistence.service.EntityService;
import java.util.Collection;

/**
 *
 * @author Alexander
 */
public interface EdicaoService extends EntityService<Long, Edicao>{

    public Collection<Edicao> findAbertas();
    
}
