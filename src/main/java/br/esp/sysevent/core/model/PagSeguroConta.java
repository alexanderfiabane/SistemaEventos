/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Entity
@Table(name = "PAGSEGURO_CONTA")
@AttributeOverride(name = "id", column = @Column(name = "ID_PAGSEGURO_CONTA"))
public class PagSeguroConta extends AbstractEntity{
     private static final long serialVersionUID = -5305530290753732847L;

    @Column(name = "EMAIL", nullable = true)
    private String emailPagSeguro;
    @Column(name = "TOKEN_SEGURANCA_PROD", nullable = true)
    private String tokenSegurancaProducao;
    @Column(name = "TOKEN_SEGURANCA_DEV", nullable = true)
    private String tokenSegurancaSandBox;
    @Column(name = "ID_APLICACAO_PROD", nullable = true)
    private String idAplicacaoProducao;
    @Column(name = "KEY_APLICACAO_PROD", nullable = true)
    private String keyAplicacaoProducao;
    @Column(name = "ID_APLICACAO_DEV", nullable = true)
    private String idAplicacaoSandBox;
    @Column(name = "KEY_APLICACAO_DEV", nullable = true)
    private String keyAplicacaoSandBox;

    public String getEmailPagSeguro() {
        return emailPagSeguro;
    }

    public void setEmailPagSeguro(String emailPagSeguro) {
        this.emailPagSeguro = emailPagSeguro;
    }

    public String getTokenSegurancaProducao() {
        return tokenSegurancaProducao;
    }

    public void setTokenSegurancaProducao(String tokenSegurancaProducao) {
        this.tokenSegurancaProducao = tokenSegurancaProducao;
    }

    public String getTokenSegurancaSandBox() {
        return tokenSegurancaSandBox;
    }

    public void setTokenSegurancaSandBox(String tokenSegurancaSandBox) {
        this.tokenSegurancaSandBox = tokenSegurancaSandBox;
    }

    public String getIdAplicacaoProducao() {
        return idAplicacaoProducao;
    }

    public void setIdAplicacaoProducao(String idAplicacaoProducao) {
        this.idAplicacaoProducao = idAplicacaoProducao;
    }

    public String getKeyAplicacaoProducao() {
        return keyAplicacaoProducao;
    }

    public void setKeyAplicacaoProducao(String keyAplicacaoProducao) {
        this.keyAplicacaoProducao = keyAplicacaoProducao;
    }

    public String getIdAplicacaoSandBox() {
        return idAplicacaoSandBox;
    }

    public void setIdAplicacaoSandBox(String idAplicacaoSandBox) {
        this.idAplicacaoSandBox = idAplicacaoSandBox;
    }

    public String getKeyAplicacaoSandBox() {
        return keyAplicacaoSandBox;
    }

    public void setKeyAplicacaoSandBox(String keyAplicacaoSandBox) {
        this.keyAplicacaoSandBox = keyAplicacaoSandBox;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.emailPagSeguro);
        hash = 47 * hash + Objects.hashCode(this.tokenSegurancaProducao);
        hash = 47 * hash + Objects.hashCode(this.idAplicacaoProducao);
        hash = 47 * hash + Objects.hashCode(this.keyAplicacaoProducao);
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
        final PagSeguroConta other = (PagSeguroConta) obj;
        if (!Objects.equals(this.emailPagSeguro, other.emailPagSeguro)) {
            return false;
        }
        if (!Objects.equals(this.tokenSegurancaProducao, other.tokenSegurancaProducao)) {
            return false;
        }
        if (!Objects.equals(this.idAplicacaoProducao, other.idAplicacaoProducao)) {
            return false;
        }
        if (!Objects.equals(this.keyAplicacaoProducao, other.keyAplicacaoProducao)) {
            return false;
        }
        return true;
    }
}
