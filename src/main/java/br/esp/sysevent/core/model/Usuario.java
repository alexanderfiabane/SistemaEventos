/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import br.ojimarcius.commons.persistence.model.AbstractEntity;
import java.util.Arrays;
import java.util.Collection;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "USUARIOS")
@AttributeOverride(name = "id", column = @Column(name = "ID_USUARIO"))
/**
 * NOTA!!! Mudei o nome de uns atributos para esta classe ser compatível com o UserDetails do spring-security!
 */
public class Usuario extends AbstractEntity<Long> implements UserDetails {

    private static final long serialVersionUID = 7466084620699902860L;
    @Column(name = "LOGIN", length = 80, unique = true, nullable = false)
    private String username;
    @Column(name = "SENHA", length = 512, nullable = false) // password sera encriptado
    private String password;
    @Column(name = "TIPO_USUARIO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "IND_ATIVO", nullable = false)
    private boolean enabled;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PESSOA", nullable = false)
    private Pessoa pessoa;

    public Usuario() {
        this.enabled = Boolean.TRUE;
        this.role = Role.ROLE_USER;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(final Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public boolean isAdmin() {
        return getRole() != null && Role.ROLE_ADMIN.equals(getRole());
    }

    public boolean isUser() {
        return getRole() != null && Role.ROLE_USER.equals(getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(getRole().getValue()));
    }

    @Override
    public boolean isAccountNonExpired() {
        /* a conta do usuario nunca expira... pelo menos por agora... */
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        /* a conta do usuario nunca é bloqueada... pelo menos por agora... */
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        /* o password do usuario nunca expira... pelo menos por agora... */
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + hashCodeById(this);
        hash = 29 * hash + (this.username != null ? this.username.hashCode() : 0);
        hash = 29 * hash + (this.password != null ? this.password.hashCode() : 0);
        hash = 29 * hash + (this.role != null ? this.role.hashCode() : 0);
        hash = 29 * hash + (this.enabled ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!equalsById(this, obj)) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
            return false;
        }
        if (this.role != other.role) {
            return false;
        }
        if (this.enabled != other.enabled) {
            return false;
        }
        return true;
    }

    public enum Role {

        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_USER("ROLE_USER"),
        ROLE_GUEST("ROLE_GUEST");
        private final String value;

        private Role(final String roleName) {
            this.value = roleName;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }
}
