/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "INSCRICOES")
@AttributeOverride(name = "id", column = @Column(name = "ID_INSCRICAO"))
public class Inscricao extends AbstractEntity {

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
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
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
        hash = 71 * hash + (this.confraternista != null ? this.confraternista.hashCode() : 0);
        hash = 71 * hash + (this.edicaoEvento != null ? this.edicaoEvento.hashCode() : 0);
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
        final Inscricao other = (Inscricao) obj;
        if (this.confraternista != other.confraternista && (this.confraternista == null || !this.confraternista.equals(other.confraternista))) {
            return false;
        }
        if (this.edicaoEvento != other.edicaoEvento && (this.edicaoEvento == null || !this.edicaoEvento.equals(other.edicaoEvento))) {
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
