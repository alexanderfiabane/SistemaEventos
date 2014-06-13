/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import br.msf.commons.persistence.model.AbstractEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "ESTADOS")
@AttributeOverride(name = "id", column =
                                @Column(name = "ID_ESTADO"))
public class Estado extends AbstractEntity<Long> {

    private static final long serialVersionUID = 2178711986985855434L;
    @Column(name = "NOME", length = 80, nullable = false)
    private String nome;
    @Column(name = "SIGLA", nullable = false, length = 2, unique = true)
    private String sigla;

    public Estado() {
    }

    public Estado(final String nome, final String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(final String sigla) {
        this.sigla = sigla.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + hashCodeById(this);
        hash = 83 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 83 * hash + (this.sigla != null ? this.sigla.hashCode() : 0);
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
        final Estado other = (Estado) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if ((this.sigla == null) ? (other.sigla != null) : !this.sigla.equals(other.sigla)) {
            return false;
        }
        return true;
    }    
}
