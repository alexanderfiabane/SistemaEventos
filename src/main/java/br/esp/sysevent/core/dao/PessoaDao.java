/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Pessoa;
import br.msf.commons.persistence.dao.EntityDao;
import java.util.Collection;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface PessoaDao extends EntityDao<Long, Pessoa> {

    public Collection<Pessoa> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order);
}
