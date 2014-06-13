/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import br.msf.commons.persistence.model.AbstractEntity;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Where;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "OFICINAS")
@AttributeOverride(name = "id", column = @Column(name = "ID_OFICINA"))
public class Oficina extends AbstractEntity<Long> {
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
        hash = 19 * hash + hashCodeById(this);
        hash = 19 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 19 * hash + (this.vagas != null ? this.vagas.hashCode() : 0);
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
        final Oficina other = (Oficina) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.vagas != other.vagas && (this.vagas == null || !this.vagas.equals(other.vagas))) {
            return false;
        }
        return true;
    }
}
