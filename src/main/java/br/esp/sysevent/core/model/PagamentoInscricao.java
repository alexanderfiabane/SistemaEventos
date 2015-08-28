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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    private static final long serialVersionUID = 5368918725264648235L;

    @ManyToOne
    @JoinColumn(name = "ID_INSCRICAO", nullable = false)
    private Inscricao inscricao;
    @Column(name = "CODIGO", nullable = true)
    private String codPagamento;
    @Column(name = "DESCRICAO_PG", columnDefinition = "TEXT", nullable = true)
    private String descricaoPagamento;
    @Column(name = "DESCRICAO_PG_QTIP", columnDefinition = "TEXT", nullable = true)
    private String descricaoPagamentoQtip;
    @Column(name = "DATA_PAGAMENTO", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dataPagamento;
    @Column(name = "STATUS", nullable = true)
    @Enumerated(EnumType.STRING)
    private PagSeguroStatus status;
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

    public String getDescricaoPagamentoQtip() {
        return descricaoPagamentoQtip;
    }

    public void setDescricaoPagamentoQtip(String descricaoPagamentoQtip) {
        this.descricaoPagamentoQtip = descricaoPagamentoQtip;
    }

    public Calendar getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Calendar dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public PagSeguroStatus getStatus() {
        return status;
    }

    public void setStatus(PagSeguroStatus status) {
        this.status = status;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public enum PagSeguroStatus {

        INITIATED("A transação não foi completada", 0),
        WAITING_PAYMENT("O pagamento ainda não foi processado", 1),
        IN_ANALYSIS("O pagamento está sob análise", 2),
        PAID("A o pagamento foi confirmado", 3),
        AVAILABLE("O montante da transação está disponível para saque", 4),
        IN_DISPUTE("A transação está em disputa", 5),
        REFUNDED("O montante da transação foi devolvido para o comprador", 6),
        CANCELLED("O pagamento foi cancelado", 7),
        SELLER_CHARGEBACK(
                "O pagamento foi contestado pelo comprador e o montante previamente bloqueado foi debitado do seu balanço",
                8),
        CONTESTATION(
                "O pagamento foi contestado pelo comprador e o montante foi bloqueado. Entre em contato com o serviço ao cliente",
                9),
        UNKNOWN_STATUS("Status desconhecido. Veja documentação online", -1);

        private String descricao;
        private Integer valor;

        private PagSeguroStatus(String descricao, Integer valor) {
            this.descricao = descricao;
            this.valor = valor;
        }

        public String getDescricao() {
            return this.descricao;
        }

        public Integer getValor() {
            return this.valor;
        }

        public static Collection<PagSeguroStatus> getValores() {
            return Arrays.asList(values());
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
