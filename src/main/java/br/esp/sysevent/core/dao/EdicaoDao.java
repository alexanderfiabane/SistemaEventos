/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Usuario;
import br.ojimarcius.commons.persistence.dao.EntityDao;
import java.util.Calendar;
import java.util.Collection;

/**
 *
 * @author Alexander
 */
public interface EdicaoDao extends EntityDao<Long, Edicao> {

    public Collection<Edicao> findAbertas(Calendar dataAtual);
    
}
