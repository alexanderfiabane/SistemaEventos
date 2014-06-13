/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.model.Usuario;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import br.msf.commons.util.CalendarUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Service(value = "usuarioService")
public class UsuarioServiceBean extends AbstractEntityServiceBean<Long, Usuario> implements UsuarioService {

    @Override
    protected UsuarioDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final UsuarioDao dao) {
        super.setDao(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByLogin(final String login) {
        return findByLogin(login, Boolean.TRUE);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByLogin(final String login, final Boolean onlyAtivos) {
        return getDao().findByLogin(login, onlyAtivos);
    }

    /**
     * Este metodo vem da interface "org.springframework.security.core.userdetails.UserDetailsService".
     * Ele é usado pelo spring-security para fazer a autenticação do usuário.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Usuario usuario = findByLogin(username, Boolean.TRUE);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario nao encontrado.");
        }
        return usuario;
    }

    @Override
    @Transactional(readOnly = false)
    public void insertDefaultData() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Administrador do Sistema");
        pessoa.setDataNascimento(CalendarUtils.today());
        pessoa.setSexo(Sexo.MASCULINO);
        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPassword(DigestUtils.sha256Hex("@WSXzaq1"));
        admin.setPessoa(pessoa);
        admin.setRole(Usuario.Role.ROLE_ADMIN);
        getDao().save(admin);
    }
}
