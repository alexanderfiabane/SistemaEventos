/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.msf.commons.persistence.service.EntityService;
import java.util.Collection;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
public interface ConfraternistaService extends EntityService<Long, Confraternista> {

    public Collection<Confraternista> findByNome(final String nome);
    public Collection<Confraternista> findByDormitorio(final Dormitorio dormitorio);
    public Collection<Confraternista> findByDormitorio(final Long idDormitorio);
    public Collection<Confraternista> findBySemDormitorio();
}
