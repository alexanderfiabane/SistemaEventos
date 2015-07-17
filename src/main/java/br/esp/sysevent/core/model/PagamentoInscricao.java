/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
     private static final long serialVersionUID = 4995003556456753846L;

    @OneToOne
    @JoinColumn(name = "ID_INSCRICAO", nullable = false)
    private Inscricao inscricao;
    @Column(name = "CODIGO", nullable = true)
    private String codPagamento;
    @Column(name = "DESCRICAO_PG", nullable = true)
    private String descricaoPagamento;
    @Column(name = "DATA_PAGAMENTO", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dataPagamento;
    @Column(name = "VALOR", nullable = true)
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
