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
@Table(name = "EVENTOS")
@AttributeOverride(name = "id", column = @Column(name = "ID_EVENTO"))
public class Evento extends AbstractEntity<Long> {

    private static final long serialVersionUID = -6396591197859469478L;
    @Column(name = "NOME", length = 80, nullable = false)
    private String nome;    
    @Column(name = "SIGLA", length = 80, nullable = true)
    private String sigla;    

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
        this.sigla = sigla;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 37 * hash + (this.sigla != null ? this.sigla.hashCode() : 0);
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
        final Evento other = (Evento) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if ((this.sigla == null) ? (other.sigla != null) : !this.sigla.equals(other.sigla)) {
            return false;
        }
        return true;
    }
            
}
