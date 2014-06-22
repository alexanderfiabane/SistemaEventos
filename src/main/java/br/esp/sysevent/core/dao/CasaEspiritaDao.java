/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.CasaEspirita;
import br.ojimarcius.commons.persistence.dao.EntityDao;
import java.util.Collection;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
public interface CasaEspiritaDao extends EntityDao<Long, CasaEspirita> {

    public Collection<CasaEspirita> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order);
}
