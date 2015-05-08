/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Usuario;
import com.javaleks.commons.core.dao.EntityDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface UsuarioDao extends EntityDao<Long, Usuario> {

    public Usuario findByLogin(final String login, final Boolean onlyAtivos);
    public Usuario findByLogin(final String login);
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException;
    public void insertDefaultData();
}
