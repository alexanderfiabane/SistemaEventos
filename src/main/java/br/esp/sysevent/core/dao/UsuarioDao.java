/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Usuario;
import br.ojimarcius.commons.persistence.dao.EntityDao;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface UsuarioDao extends EntityDao<Long, Usuario> {

    public Usuario findByLogin(final String login, final Boolean onlyAtivos);
}
