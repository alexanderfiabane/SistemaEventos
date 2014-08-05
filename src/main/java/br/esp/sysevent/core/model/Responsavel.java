/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.esp.sysevent.core.model;

import br.ojimarcius.commons.persistence.model.AbstractEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Alexander
 */
@Entity
@Table(name = "RESPONSAVEIS")
@AttributeOverride(name = "id", column =
                                @Column(name = "ID_RESPONSAVEL"))
public class Responsavel extends AbstractEntity<Long> {
    private static final long serialVersionUID = 2663592209120735750L;
    
    @Column(name = "NOME_RESPONSAVEL", nullable = true)
    private String nome;   
    @Column(name = "FONE", length = 80, nullable = true)
    private String telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 97 * hash + (this.telefone != null ? this.telefone.hashCode() : 0);
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
        final Responsavel other = (Responsavel) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if ((this.telefone == null) ? (other.telefone != null) : !this.telefone.equals(other.telefone)) {
            return false;
        }
        return true;
    }
    
}
