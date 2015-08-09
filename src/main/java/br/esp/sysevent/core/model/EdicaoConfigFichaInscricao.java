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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Alexander
 */
@Entity
@Table(name = "EDICAO_CONFIG_FICHA")
@AttributeOverride(name = "id", column =
        @Column(name = "ID_ED_CONFIG_FICHA"))
public class EdicaoConfigFichaInscricao extends AbstractEntity{
    private static final long serialVersionUID = 8958053423523615327L;
    
    @ManyToOne
    @JoinColumn(name = "ID_EDICAO", nullable = false)
    private Edicao edicao;
    @Column(name = "TEM_FICHA_INSCRICAO", nullable = false)
    private boolean temFichaInscricao;
    @Column(name = "AUTORIZACAO_INST", nullable = true)
    private boolean autorizacaoInstituicao;
    @Column(name = "AUTORIZACAO_MENOR", nullable = true)
    private boolean autorizacaoMenor;

    public Edicao getEdicao() {
        return edicao;
    }

    public void setEdicao(Edicao edicao) {
        this.edicao = edicao;
    }
    
    public boolean isTemFichaInscricao() {
        return temFichaInscricao;
    }

    public void setTemFichaInscricao(boolean temFichaInscicao) {
        this.temFichaInscricao = temFichaInscicao;
    }

    public boolean isAutorizacaoInstituicao() {
        return autorizacaoInstituicao;
    }

    public void setAutorizacaoInstituicao(boolean autorizacaoInstituicao) {
        this.autorizacaoInstituicao = autorizacaoInstituicao;
    }

    public boolean isAutorizacaoMenor() {
        return autorizacaoMenor;
    }

    public void setAutorizacaoMenor(boolean autorizacaoMenor) {
        this.autorizacaoMenor = autorizacaoMenor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.edicao);
        hash = 37 * hash + (this.temFichaInscricao ? 1 : 0);
        hash = 37 * hash + (this.autorizacaoInstituicao ? 1 : 0);
        hash = 37 * hash + (this.autorizacaoMenor ? 1 : 0);
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
        final EdicaoConfigFichaInscricao other = (EdicaoConfigFichaInscricao) obj;
        if (!Objects.equals(this.edicao, other.edicao)) {
            return false;
        }
        if (this.temFichaInscricao != other.temFichaInscricao) {
            return false;
        }
        if (this.autorizacaoInstituicao != other.autorizacaoInstituicao) {
            return false;
        }
        if (this.autorizacaoMenor != other.autorizacaoMenor) {
            return false;
        }
        return true;
    }
    
}
