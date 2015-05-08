/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "DOCUMENTOS")
@AttributeOverride(name = "id", column =
                                @Column(name = "ID_DOCUMENTO"))
public class Documento extends AbstractEntity {

    private static final long serialVersionUID = 5953359082579266700L;
    @Column(name = "RG", length = 16, nullable = true)
    private String rg;
    @Column(name = "CERTIDAO_NASCIMENTO", length = 20, nullable = true)
    private String certidaoNascimento;
    @Column(name = "CPF", length = 16, nullable = true)
    private String cpf;

    public String getRg() {
        return rg;
    }

    public void setRg(final String rg) {
        this.rg = rg;
    }

    public String getCertidaoNascimento() {
        return certidaoNascimento;
    }

    public void setCertidaoNascimento(String certidaoNascimento) {
        this.certidaoNascimento = certidaoNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.rg != null ? this.rg.hashCode() : 0);
        hash = 79 * hash + (this.certidaoNascimento != null ? this.certidaoNascimento.hashCode() : 0);
        hash = 79 * hash + (this.cpf != null ? this.cpf.hashCode() : 0);
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
        final Documento other = (Documento) obj;
        if ((this.rg == null) ? (other.rg != null) : !this.rg.equals(other.rg)) {
            return false;
        }
        if ((this.certidaoNascimento == null) ? (other.certidaoNascimento != null) : !this.certidaoNascimento.equals(other.certidaoNascimento)) {
            return false;
        }
        if ((this.cpf == null) ? (other.cpf != null) : !this.cpf.equals(other.cpf)) {
            return false;
        }
        return true;
    }
}
