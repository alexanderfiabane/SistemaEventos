/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Entity
@Table(name = "PAGAMENTOS")
@AttributeOverride(name = "id", column = @Column(name = "ID_PAGAMENTO"))
public class PagamentoInscricao extends AbstractEntity {
    private static final long serialVersionUID = -5680008393064758718L;

    @ManyToOne
    @JoinColumn(name = "ID_INSCRICAO", nullable = false)
    private Inscricao inscricao;
    private String codPagamento;
    @Column(name = "STATUS", nullable = false)
    private StatusPagamento status;
    @Column(name = "DESCRICAO_PG", nullable = false)
    private String descricaoPagamento;
    @Column(name = "DATA_PAGAMENTO", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dataPagamento;
    @Column(name = "VALOR", nullable = false)
    private BigDecimal valor;

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public String getCodPagamento() {
        return codPagamento;
    }

    public void setCodPagamento(String codPagamento) {
        this.codPagamento = codPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public String getDescricaoPagamento() {
        return descricaoPagamento;
    }

    public void setDescricaoPagamento(String descricaoPagamento) {
        this.descricaoPagamento = descricaoPagamento;
    }

    public Calendar getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Calendar dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    private enum StatusPagamento {
        AGUARDANDO("Aguardando Pagamento"),
        CANCELADO("Pagamento Cancelado"),
        RECUSADO("Pagamento Recusado");

        private final String descricao;

        private StatusPagamento(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getName() {
            return name();
        }

        public static Collection<PagamentoInscricao.StatusPagamento> getTipos() {
            return Arrays.asList(PagamentoInscricao.StatusPagamento.values());
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.inscricao);
        hash = 59 * hash + Objects.hashCode(this.codPagamento);
        hash = 59 * hash + Objects.hashCode(this.dataPagamento);
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
        final PagamentoInscricao other = (PagamentoInscricao) obj;
        if (!Objects.equals(this.inscricao, other.inscricao)) {
            return false;
        }
        if (!Objects.equals(this.codPagamento, other.codPagamento)) {
            return false;
        }
        if (!Objects.equals(this.dataPagamento, other.dataPagamento)) {
            return false;
        }
        return true;
    }
}
