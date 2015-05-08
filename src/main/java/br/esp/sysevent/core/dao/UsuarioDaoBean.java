/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.model.Usuario;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import com.javaleks.commons.util.CalendarUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Repository
public class UsuarioDaoBean extends AbstractEntityDao<Long, Usuario> implements UsuarioDao {

    @Autowired
    public UsuarioDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Usuario findByLogin(final String login, final Boolean onlyAtivos) {
        final Criteria criteria = createCriteria()
                .add(Restrictions.eq("username", login));
        if (onlyAtivos) {
            criteria.add(Restrictions.eq("enabled", Boolean.TRUE));
        }
        return DataAccessUtils.uniqueResult(findByCriteria(criteria));
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByLogin(final String login) {
        return findByLogin(login, Boolean.TRUE);
    }

    /**
     * Este metodo vem da interface "org.springframework.security.core.userdetails.UserDetailsService".
     * Ele é usado pelo spring-security para fazer a autenticação do usuário.
     * @param username
     * @return 
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
        save(admin);
    }
}
