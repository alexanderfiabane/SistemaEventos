/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Usuario;
import br.ojimarcius.commons.persistence.service.EntityService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface UsuarioService extends EntityService<Long, Usuario>, UserDetailsService {

    public Usuario findByLogin(final String login);

    public Usuario findByLogin(final String login, final Boolean onlyAtivos);
    
    public void insertDefaultData();
    
}
