/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.model;

import br.ojimarcius.commons.persistence.model.AbstractEntity;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

/**
 *
 * @author Fiabane
 */
@Entity
@Table(name = "GRUPOS_IDADE")
@AttributeOverride(name = "id", column = @Column(name = "ID_GRUPO_IDADE"))
public class GrupoIdade extends AbstractEntity<Long>{
    private static final long serialVersionUID = -2450021166371519704L;    
    
    @Column(name = "NOME", length = 160, nullable = false)
    private String nome;
    @Column(name = "VAGAS", nullable = false)
    private Integer vagas;
    @Column(name = "VAGAS_OCUPADAS", nullable = false)
    private Integer vagasOcupadas;
    @ManyToOne
    @JoinColumn(name = "ID_EDICAO", nullable = false)
    private Edicao edicaoEvento;
    @Column(name = "IDADE_MIN", nullable = false)
    private Integer idadeMinima;
    @Column(name = "IDADE_MAX", nullable = false)
    private Integer idadeMaxima;
    @Column(name = "TIPO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Confraternista.Tipo tipo;
    @OneToMany(mappedBy = "grupoIdade")
    @Where(clause = "tipo = 'FACILITADOR'")
    private Set<Confraternista> facilitadores;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public Integer getVagasOcupadas() {
        return vagasOcupadas;
    }

    public void setVagasOcupadas(Integer vagasOcupadas) {
        this.vagasOcupadas = vagasOcupadas;
    }

    public Edicao getEdicaoEvento() {
        return edicaoEvento;
    }

    public void setEdicaoEvento(Edicao edicaoEvento) {
        this.edicaoEvento = edicaoEvento;
    }

    public Confraternista.Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Confraternista.Tipo tipo) {
        this.tipo = tipo;
    }   

    public Set<Confraternista> getFacilitadores() {
        return facilitadores;
    }

    public void setFacilitadores(Set<Confraternista> facilitadores) {
        this.facilitadores = facilitadores;
    }

    public Integer getIdadeMinima() {
        return idadeMinima;
    }

    public void setIdadeMinima(Integer idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public Integer getIdadeMaxima() {
        return idadeMaxima;
    }

    public void setIdadeMaxima(Integer idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
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
        hash = 89 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 89 * hash + (this.edicaoEvento != null ? this.edicaoEvento.hashCode() : 0);
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
        final GrupoIdade other = (GrupoIdade) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.edicaoEvento != other.edicaoEvento && (this.edicaoEvento == null || !this.edicaoEvento.equals(other.edicaoEvento))) {
            return false;
        }
        return true;
    }   
    
}
