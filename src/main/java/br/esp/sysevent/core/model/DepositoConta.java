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
@Table(name = "DEPOSITO_CONTA")
@AttributeOverride(name = "id", column = @Column(name = "ID_DEPOSITO_CONTA"))
public class DepositoConta extends AbstractEntity{
    private static final long serialVersionUID = 4952142593765962855L;

    @Column(name = "NOME_BANCO", nullable = true)
    private String banco;
    @Column(name = "AGENCIA", nullable = true)
    private String agencia;
    @Column(name = "NUMERO_CONTA", nullable = true)
    private String numeroConta;
    @Column(name = "OPERACAO", nullable = true)
    private String operacao;

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.banco);
        hash = 23 * hash + Objects.hashCode(this.agencia);
        hash = 23 * hash + Objects.hashCode(this.numeroConta);
        hash = 23 * hash + Objects.hashCode(this.operacao);
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
        final DepositoConta other = (DepositoConta) obj;
        if (!Objects.equals(this.banco, other.banco)) {
            return false;
        }
        if (!Objects.equals(this.agencia, other.agencia)) {
            return false;
        }
        if (!Objects.equals(this.numeroConta, other.numeroConta)) {
            return false;
        }
        if (!Objects.equals(this.operacao, other.operacao)) {
            return false;
        }
        return true;
    }
}
