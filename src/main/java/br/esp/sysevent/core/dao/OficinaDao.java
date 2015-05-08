/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Oficina;
import com.javaleks.commons.core.dao.EntityDao;
import java.util.Collection;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
public interface OficinaDao extends EntityDao<Long, Oficina> {

    public Collection<Oficina> findAll();
}
