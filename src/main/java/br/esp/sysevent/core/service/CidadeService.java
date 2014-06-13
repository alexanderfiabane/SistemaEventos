/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Estado;
import br.msf.commons.persistence.service.EntityService;
import java.util.Collection;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface CidadeService extends EntityService<Long, Cidade> {

    public Collection<Cidade> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order);

    public Collection<Cidade> findByEstado(final Estado estado, final Order order);

    public Collection<Cidade> findByEstado(final Long idEstado);

    public Cidade findCapital(final Estado estado);

    /**
     * Insere todas as cidades de vários estados brasileiros no banco de dados.
     */
    public void insertDefaultData();
}
