/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.ojimarcius.commons.persistence.service.EntityService;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
public interface DormitorioService extends EntityService<Long, Dormitorio> {
    
    public void alocaConfraternistasAleatoriamente(final Edicao edicao);
}
