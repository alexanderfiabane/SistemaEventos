/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Estado;
import br.ojimarcius.commons.persistence.dao.EntityDao;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface EstadoDao extends EntityDao<Long, Estado> {

    public Estado findBySigla(final String sigla);
}
