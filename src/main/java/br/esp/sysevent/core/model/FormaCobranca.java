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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Entity
@Table(name = "FORMA_COBRANCA")
@AttributeOverride(name = "id", column = @Column(name = "ID_FORMA_COBRANCA"))
public class FormaCobranca extends AbstractEntity{
    private static final long serialVersionUID = -7286761908204052652L;

    @ManyToOne(cascade = CascadeType.ALL)
    private DepositoConta deposito;
    @ManyToOne(cascade = CascadeType.ALL)
    private PagSeguro pagSeguro;
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

    public boolean isPagSeguro(){
        return TipoCobranca.PAGSEGURO.equals(getTipoCobranca());
    }

    public enum TipoCobranca {
        NENHUM("SEM COBRANCA"),
        DEPOSITO_CONTA("DEPOSITO EM CONTA"),
        PAGSEGURO("PAGSEGURO");
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
