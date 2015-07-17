/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Entity
@Table(name = "FORMA_COBRANCA")
@AttributeOverride(name = "id", column = @Column(name = "ID_FORMA_COBRANCA"))
public class FormaCobranca extends AbstractEntity{
    private static final long serialVersionUID = -7286761908204052652L;

    @ManyToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "ID_DEPOSITO", nullable = true)
    private DepositoConta deposito;
    @ManyToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "ID_PAGSEGURO", nullable = true)
    private PagSeguroConta pagSeguro;
    @Column(name = "TIPO_COBRANCA", nullable = true)
    private TipoCobranca tipoCobranca;

    public TipoCobranca getTipoCobranca() {
        return tipoCobranca;
    }

    public void setTipoCobranca(TipoCobranca tipoCobranca) {
        this.tipoCobranca = tipoCobranca;
    }

    public boolean isSemCobranca(){
        return TipoCobranca.NENHUM.equals(getTipoCobranca());
    }

    public boolean isDepositoConta(){
        return TipoCobranca.DEPOSITO_CONTA.equals(getTipoCobranca());
    }

    public boolean isPagSeguroConta(){
        return TipoCobranca.PAGSEGURO.equals(getTipoCobranca());
    }

    public DepositoConta getDeposito() {
        return deposito;
    }

    public void setDeposito(DepositoConta deposito) {
        this.deposito = deposito;
    }

    public PagSeguroConta getPagSeguro() {
        return pagSeguro;
    }

    public void setPagSeguro(PagSeguroConta pagSeguro) {
        this.pagSeguro = pagSeguro;
    }

    public enum TipoCobranca {
        NENHUM("Sem Cobrança"),
        DEPOSITO_CONTA("Depósito em Conta"),
        PAGSEGURO("PagSeguro");
        private final String descricao;

        private TipoCobranca(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getName() {
            return name();
        }

        public static Collection<FormaCobranca.TipoCobranca> getTipos() {
            return Arrays.asList(FormaCobranca.TipoCobranca.values());
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.deposito);
        hash = 79 * hash + Objects.hashCode(this.pagSeguro);
        hash = 79 * hash + Objects.hashCode(this.tipoCobranca);
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
        final FormaCobranca other = (FormaCobranca) obj;
        if (!Objects.equals(this.deposito, other.deposito)) {
            return false;
        }
        if (!Objects.equals(this.pagSeguro, other.pagSeguro)) {
            return false;
        }
        if (this.tipoCobranca != other.tipoCobranca) {
            return false;
        }
        return true;
    }
}
