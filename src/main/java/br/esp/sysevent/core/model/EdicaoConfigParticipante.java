/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.model;

import br.esp.sysevent.core.model.Confraternista.Tipo;
import com.javaleks.commons.core.model.AbstractEntity;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Entity
@Table(name = "EDICAO_CONFIG_PART")
@AttributeOverride(name = "id", column = @Column(name = "ID_ED_CONFIG_PART"))
public class EdicaoConfigParticipante extends AbstractEntity{
    private static final long serialVersionUID = -831591557949906585L;

    @ManyToOne
    @JoinColumn(name = "ID_EDICAO", nullable = false)
    private Edicao edicao;
    @Column(name = "TIPO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipoParticipante;
    @Column(name = "OCUPA_VAGA", nullable = false)
    private boolean ocupaVaga;
    @Column(name = "ISENTO", nullable = false)
    private boolean isento;

    public Edicao getEdicao() {
        return edicao;
    }

    public void setEdicao(Edicao edicao) {
        this.edicao = edicao;
    }

    public Tipo getTipoParticipante() {
        return tipoParticipante;
    }

    public void setTipoParticipante(Tipo tipoParticipante) {
        this.tipoParticipante = tipoParticipante;
    }

    public boolean isOcupaVaga() {
        return ocupaVaga;
    }

    public void setOcupaVaga(boolean ocupaVaga) {
        this.ocupaVaga = ocupaVaga;
    }

    public boolean isIsento() {
        return isento;
    }

    public void setIsento(boolean isento) {
        this.isento = isento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.edicao);
        hash = 59 * hash + Objects.hashCode(this.tipoParticipante);
        hash = 59 * hash + (this.ocupaVaga ? 1 : 0);
        hash = 59 * hash + (this.isento ? 1 : 0);
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
        final EdicaoConfigParticipante other = (EdicaoConfigParticipante) obj;
        if (!Objects.equals(this.edicao, other.edicao)) {
            return false;
        }
        if (this.tipoParticipante != other.tipoParticipante) {
            return false;
        }
        if (this.ocupaVaga != other.ocupaVaga) {
            return false;
        }
        if (this.isento != other.isento) {
            return false;
        }
        return true;
    }
}
