/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.util.Objects;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "OFICINAS")
@AttributeOverride(name = "id", column = @Column(name = "ID_OFICINA"))
public class Oficina extends AbstractEntity {
    private static final long serialVersionUID = 1333652413938643192L;

    @Column(name = "NOME", length = 160, nullable = false)
    private String nome;
    @Column(name = "VAGAS", nullable = false)
    private Integer vagas;
    @Column(name = "VAGAS_OCUPADAS", nullable = false)
    private Integer vagasOcupadas;
    @ManyToOne
    @JoinColumn(name = "ID_EDICAO", nullable = false)
    private Edicao edicaoEvento;
    @OneToMany(mappedBy = "oficina")
    @Where(clause = "tipo = 'OFICINEIRO'")
    private Set<Confraternista> oficineiros;

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

    public Set<Confraternista> getOficineiros() {
        return oficineiros;
    }

    public void setOficineiros(final Set<Confraternista> oficineiros) {
        this.oficineiros = oficineiros;
    }

    public Edicao getEdicaoEvento() {
        return edicaoEvento;
    }

    public void setEdicaoEvento(Edicao edicaoEvento) {
        this.edicaoEvento = edicaoEvento;
    }

    public int getSaldoVagas() {
        return vagas - vagasOcupadas;
    }

    public boolean temVaga() {
        return getSaldoVagas() > 0;
    }

    public void ocupaVaga() {
        vagasOcupadas++;
    }

    public void desocupaVaga() {
        vagasOcupadas--;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.getId());
        hash = 61 * hash + Objects.hashCode(this.nome);
        hash = 61 * hash + Objects.hashCode(this.vagas);
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
        final Oficina other = (Oficina) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.vagas, other.vagas)) {
            return false;
        }
        return true;
    }

}
