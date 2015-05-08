/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
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
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "DORMITORIOS")
@AttributeOverride(name = "id", column =
                                @Column(name = "ID_DORMITORIO"))
public class Dormitorio extends AbstractEntity {
    private static final long serialVersionUID = 2667670358839610774L;

    @Column(name = "NOME", length = 40, nullable = false)
    private String nome;
    @Column(name = "VAGAS", nullable = false)
    private Integer vagas;
    @Column(name = "VAGAS_OCUPADAS", nullable = false)
    private Integer vagasOcupadas;
    @Column(name = "SEXO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @ManyToOne
    @JoinColumn(name = "ID_COORDENADOR", nullable = false)
    private Confraternista coordenador;
    @ManyToOne
    @JoinColumn(name = "ID_VICE_COORDENADOR", nullable = true)
    private Confraternista viceCoordenador;
    @ManyToOne
    @JoinColumn(name = "ID_EDICAO", nullable = false)
    private Edicao edicaoEvento;

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(final Integer vagas) {
        this.vagas = vagas;
    }

    public Integer getVagasOcupadas() {
        return vagasOcupadas;
    }

    public void setVagasOcupadas(Integer vagasOcupadas) {
        this.vagasOcupadas = vagasOcupadas;
    }

    public Confraternista getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(final Confraternista coordenador) {
        this.coordenador = coordenador;
    }

    public Confraternista getViceCoordenador() {
        return viceCoordenador;
    }

    public void setViceCoordenador(final Confraternista viceCoordenador) {
        this.viceCoordenador = viceCoordenador;
    }

    public Edicao getEdicaoEvento() {
        return edicaoEvento;
    }

    public void setEdicaoEvento(Edicao edicaoEvento) {
        this.edicaoEvento = edicaoEvento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 13 * hash + (this.vagas != null ? this.vagas.hashCode() : 0);
        hash = 13 * hash + (this.sexo != null ? this.sexo.hashCode() : 0);
        hash = 13 * hash + (this.edicaoEvento != null ? this.edicaoEvento.hashCode() : 0);
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
        final Dormitorio other = (Dormitorio) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.vagas != other.vagas && (this.vagas == null || !this.vagas.equals(other.vagas))) {
            return false;
        }
        if (this.sexo != other.sexo) {
            return false;
        }
        if (this.edicaoEvento != other.edicaoEvento && (this.edicaoEvento == null || !this.edicaoEvento.equals(other.edicaoEvento))) {
            return false;
        }
        return true;
    }

}
