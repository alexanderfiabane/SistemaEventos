/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Sexo;
import br.ojimarcius.commons.persistence.service.EntityService;
import java.util.Collection;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
public interface DormitorioService extends EntityService<Long, Dormitorio> {
    
    public void alocaConfraternistasAleatoriamente(final Edicao edicao);

    public Collection<Dormitorio> findBySexo(Sexo sexo);
}
