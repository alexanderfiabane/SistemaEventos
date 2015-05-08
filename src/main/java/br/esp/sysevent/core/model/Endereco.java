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

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "ENDERECOS")
@AttributeOverride(name = "id", column =
                                @Column(name = "ID_ENDERECO"))
public class Endereco extends AbstractEntity {

    private static final long serialVersionUID = -2155619439655011984L;
    @Column(name = "LOGRADOURO", length = 80, nullable = false)
    private String logradouro;
    @Column(name = "NUMERO", length = 80, nullable = false)
    private String numero;
    @Column(name = "COMPLEMENTO", length = 80, nullable = true)
    private String complemento;
    @Column(name = "BAIRRO", length = 80, nullable = false)
    private String bairro;
    @Column(name = "EMAIL", length = 80, nullable = true)
    private String email;
    @Column(name = "FONE", length = 80, nullable = true)
    private String telefone;
    @Column(name = "FONE_EVENTO", length = 80, nullable = true)
    private String telefoneEvento;
    @Column(name = "CEP", length = 10, nullable = false)
    private String cep;
    @ManyToOne
    @JoinColumn(name = "ID_CIDADE", nullable = false)
    private Cidade cidade;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(final String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(final String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(final String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(final String bairro) {
        this.bairro = bairro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(final Cidade cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefoneEvento() {
        return telefoneEvento;
    }

    public void setTelefoneEvento(String telefoneEvento) {
        this.telefoneEvento = telefoneEvento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(final String cep) {
        this.cep = cep;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.logradouro != null ? this.logradouro.hashCode() : 0);
        hash = 53 * hash + (this.bairro != null ? this.bairro.hashCode() : 0);
        hash = 53 * hash + (this.cep != null ? this.cep.hashCode() : 0);
        hash = 53 * hash + (this.cidade != null ? this.cidade.hashCode() : 0);
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
        final Endereco other = (Endereco) obj;
        if ((this.logradouro == null) ? (other.logradouro != null) : !this.logradouro.equals(other.logradouro)) {
            return false;
        }
        if ((this.bairro == null) ? (other.bairro != null) : !this.bairro.equals(other.bairro)) {
            return false;
        }
        if ((this.cep == null) ? (other.cep != null) : !this.cep.equals(other.cep)) {
            return false;
        }
        if (this.cidade != other.cidade && (this.cidade == null || !this.cidade.equals(other.cidade))) {
            return false;
        }
        return true;
    }
}
