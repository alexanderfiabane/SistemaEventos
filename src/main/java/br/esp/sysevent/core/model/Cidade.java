/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "CIDADES", uniqueConstraints = { @UniqueConstraint( columnNames = {"NOME", "ID_ESTADO"})})
@AttributeOverride(name = "id", column = @Column(name = "ID_CIDADE"))
public class Cidade extends AbstractEntity {

    private static final long serialVersionUID = -2495362020721070436L;
    @Column(name = "NOME", length = 80, nullable = false)
    private String nome;
    @Column(name = "IND_CAPITAL", nullable = false)
    private Boolean capital;
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    private Estado estado;

    public Cidade() {
        this(null, null, Boolean.FALSE);
    }

    public Cidade(final String nome, final Estado estado) {
        this(nome, estado, Boolean.FALSE);
    }

    public Cidade(final String nome, final Estado estado, boolean capital) {
        this.nome = nome;
        this.estado = estado;
        this.capital = capital;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getNomeUp() {
        return (nome != null) ? nome.toUpperCase() : nome;
    }

    public Boolean isCapital() {
        return capital;
    }

    public Boolean getCapital() {
        return isCapital();
    }

    public void setCapital(final Boolean capital) {
        this.capital = capital;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(final Estado estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 41 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cidade other = (Cidade) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        return true;
    }
}
