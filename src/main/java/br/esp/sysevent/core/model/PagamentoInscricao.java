/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.math.BigDecimal;
import java.util.Calendar;
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

    private static final long serialVersionUID = 6548964156787845687L;
    @ManyToOne
    @JoinColumn(name = "ID_INSCRICAO", nullable = false)
    private Inscricao inscricao;
    @Column(name = "NUMERO_DOCUMENTO", length = 50, nullable = false)
    private String numeroDocumento;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "DATA", nullable = false)
    private Calendar data;
    @Column(name = "VALOR", nullable = false)
    private BigDecimal valor;

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.data != null ? this.data.hashCode() : 0);
        hash = 19 * hash + (this.valor != null ? this.valor.hashCode() : 0);
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
        if (this.inscricao != other.inscricao && (this.inscricao == null || !this.inscricao.equals(other.inscricao))) {
            return false;
        }
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
            return false;
        }
        if (this.valor != other.valor && (this.valor == null || !this.valor.equals(other.valor))) {
            return false;
        }
        return true;
    }

}
