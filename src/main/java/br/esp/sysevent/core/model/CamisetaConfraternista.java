/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "CAMISETAS_CONF")
@AttributeOverride(name = "id", column = @Column(name = "ID_CAMISETA_CNF"))
public class CamisetaConfraternista extends AbstractEntity {

    private static final long serialVersionUID = -2174770753829782943L;
    @ManyToOne
    @JoinColumn(name = "ID_CONF", nullable = false)
    private Confraternista confraternista;
    @ManyToOne
    @JoinColumn(name = "ID_TAMANHO", nullable = false)
    private TamanhoCamiseta tamanhoCamiseta;
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", nullable = false)
    private TipoCamiseta tipoCamiseta;
    @ManyToOne
    @JoinColumn(name = "ID_COR", nullable = false)
    private CorCamiseta corCamiseta;
    @Column(name = "QUANTIDADE", nullable = false)
    private Integer quantidadeCamiseta;

    public Confraternista getConfraternista() {
        return confraternista;
    }

    public void setConfraternista(final Confraternista confraternista) {
        this.confraternista = confraternista;
    }

    public TamanhoCamiseta getTamanhoCamiseta() {
        return tamanhoCamiseta;
    }

    public void setTamanhoCamiseta(final TamanhoCamiseta tamanhoCamiseta) {
        this.tamanhoCamiseta = tamanhoCamiseta;
    }

    public TipoCamiseta getTipoCamiseta() {
        return tipoCamiseta;
    }

    public void setTipoCamiseta(final TipoCamiseta tipoCamiseta) {
        this.tipoCamiseta = tipoCamiseta;
    }

    public CorCamiseta getCorCamiseta() {
        return corCamiseta;
    }

    public void setCorCamiseta(final CorCamiseta corCamiseta) {
        this.corCamiseta = corCamiseta;
    }

    public Integer getQuantidadeCamiseta() {
        return quantidadeCamiseta;
    }

    public void setQuantidadeCamiseta(Integer quantidadeCamiseta) {
        this.quantidadeCamiseta = quantidadeCamiseta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.confraternista != null ? this.confraternista.hashCode() : 0);
        hash = 17 * hash + (this.tamanhoCamiseta != null ? this.tamanhoCamiseta.hashCode() : 0);
        hash = 17 * hash + (this.tipoCamiseta != null ? this.tipoCamiseta.hashCode() : 0);
        hash = 17 * hash + (this.corCamiseta != null ? this.corCamiseta.hashCode() : 0);
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
        final CamisetaConfraternista other = (CamisetaConfraternista) obj;
        if (this.confraternista != other.confraternista && (this.confraternista == null || !this.confraternista.equals(other.confraternista))) {
            return false;
        }
        if (this.tamanhoCamiseta != other.tamanhoCamiseta && (this.tamanhoCamiseta == null || !this.tamanhoCamiseta.equals(other.tamanhoCamiseta))) {
            return false;
        }
        if (this.tipoCamiseta != other.tipoCamiseta && (this.tipoCamiseta == null || !this.tipoCamiseta.equals(other.tipoCamiseta))) {
            return false;
        }
        if (this.corCamiseta != other.corCamiseta && (this.corCamiseta == null || !this.corCamiseta.equals(other.corCamiseta))) {
            return false;
        }
        return true;
    }


}
