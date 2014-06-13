/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import br.msf.commons.persistence.model.AbstractEntity;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.*;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "INSCRICOES")
@AttributeOverride(name = "id", column = @Column(name = "ID_INSCRICAO"))
public class Inscricao extends AbstractEntity<Long> {

    private static final long serialVersionUID = -6397566035964502563L;
    @Column(name = "VALOR", nullable = false)
    private BigDecimal valor;
    @Column(name = "STATUS", length = 80, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "DT_INSC_REC", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dataRecebimento;
    @Column(name = "DT_INSC_CONFIRM", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dataConfirmacao;
    @Column(name = "DT_INSC_PG", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dataPagamento;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_CONFRATERNISTA", nullable = false)
    private Confraternista confraternista;
    @ManyToOne
    @JoinColumn(name = "ID_EDICAO", nullable = false)
    private Edicao edicaoEvento;
    @OneToMany(mappedBy = "inscricao", fetch = FetchType.EAGER)
    private Collection<PagamentoInscricao> pagamentos;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Calendar getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Calendar dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Calendar getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(Calendar dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public Calendar getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Calendar dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Confraternista getConfraternista() {
        return confraternista;
    }

    public void setConfraternista(final Confraternista confraternista) {
        this.confraternista = confraternista;
    }

    public Edicao getEdicaoEvento() {
        return edicaoEvento;
    }

    public void setEdicaoEvento(Edicao edicaoEvento) {
        this.edicaoEvento = edicaoEvento;
    }

    public Collection<PagamentoInscricao> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(Collection<PagamentoInscricao> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public PagamentoInscricao getPagamento() {
        if(pagamentos == null || pagamentos.isEmpty()) {
            return null;
        }
        return pagamentos.iterator().next();
    }

    public boolean isPodeAnalisar() {
        return status == Status.PENDENTE;
    }
    
    public boolean isPodeAprovar() {
        return status == Status.AGUARDANDO_AVALIACAO;
    }

    public boolean isPodeEfetivar() {
        return status == Status.AGUARDANDO_PAGAMENTO;
    }
    
    public boolean isEfetivada() {
        return status == Status.EFETIVADA;
    }
    
    public boolean isIndeferida() {
        return status == Status.INDEFERIDA;
    }

    public boolean isPendente() {
        return status == Status.PENDENTE;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + hashCodeById(this);
        hash = 23 * hash + (this.status != null ? this.status.hashCode() : 0);
        hash = 23 * hash + (this.valor != null ? this.valor.hashCode() : 0);
        hash = 23 * hash + (this.dataRecebimento != null ? this.dataRecebimento.hashCode() : 0);
        hash = 23 * hash + (this.dataConfirmacao != null ? this.dataConfirmacao.hashCode() : 0);
        hash = 23 * hash + (this.dataPagamento != null ? this.dataPagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!equalsById(this, obj)) {
            return false;
        }
        final Inscricao other = (Inscricao) obj;
        if (this.status != other.status) {
            return false;
        }
        if (this.valor != other.valor && (this.valor == null || !this.valor.equals(other.valor))) {
            return false;
        }
        if (this.dataRecebimento != other.dataRecebimento && (this.dataRecebimento == null || !this.dataRecebimento.equals(other.dataRecebimento))) {
            return false;
        }
        if (this.dataConfirmacao != other.dataConfirmacao && (this.dataConfirmacao == null || !this.dataConfirmacao.equals(other.dataConfirmacao))) {
            return false;
        }
        if (this.dataPagamento != other.dataPagamento && (this.dataPagamento == null || !this.dataPagamento.equals(other.dataPagamento))) {
            return false;
        }
        return true;
    }

    public enum Status {

        PENDENTE("Pendente"),
        AGUARDANDO_AVALIACAO("Aguardando Avaliação"),
        AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
        EFETIVADA("Efetivada"),
        INDEFERIDA("Indeferida");
        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }
}
