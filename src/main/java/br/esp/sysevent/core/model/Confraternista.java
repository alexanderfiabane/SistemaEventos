/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.util.Arrays;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "CONFRATERNISTAS")
@AttributeOverride(name = "id", column = @Column(name = "ID_CONF"))
public class Confraternista extends AbstractEntity {

    private static final long serialVersionUID = -5564651400461738883L;

    @Column(name = "TIPO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Column(name = "CRACHA", length = 80, nullable = false)
    private String nomeCracha;
    @Column(name = "ATIV_CASA_ESP", length = 500, nullable = false)
    private String atividadeCasaEspirita;
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "ID_PESSOA", nullable = false)
    private Pessoa pessoa;
    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "ID_RESPONSAVEL", nullable = true)
    private Responsavel responsavelEvento;
    @ManyToOne
    @JoinColumn(name = "ID_OFICINA", nullable = true)
    private Oficina oficina;
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_IDADE", nullable = true)
    private GrupoIdade grupoIdade;
    @ManyToOne
    @JoinColumn(name = "ID_DORMITORIO", nullable = true)
    private Dormitorio dormitorio;
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "ID_CASA_ESP", nullable = true)
    private CasaEspirita casaEspirita;
    @OneToMany(mappedBy = "confraternista", orphanRemoval = true, fetch = FetchType.EAGER)
    @Cascade({CascadeType.ALL})
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<CamisetaConfraternista> camisetas;

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getNomeCracha() {
        return nomeCracha;
    }

    public void setNomeCracha(final String nomeCracha) {
        this.nomeCracha = nomeCracha;
    }

    public Dormitorio getDormitorio() {
        return dormitorio;
    }

    public void setDormitorio(final Dormitorio dormitorio) {
        this.dormitorio = dormitorio;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(final Oficina oficina) {
        this.oficina = oficina;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Responsavel getResponsavelEvento() {
        return responsavelEvento;
    }

    public void setResponsavelEvento(Responsavel responsavelEvento) {
        this.responsavelEvento = responsavelEvento;
    }

    public GrupoIdade getGrupoIdade() {
        return grupoIdade;
    }

    public void setGrupoIdade(GrupoIdade grupoIdade) {
        this.grupoIdade = grupoIdade;
    }

    public void setPessoa(final Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public CasaEspirita getCasaEspirita() {
        return casaEspirita;
    }

    public void setCasaEspirita(CasaEspirita casaEspirita) {
        this.casaEspirita = casaEspirita;
    }

    public String getAtividadeCasaEspirita() {
        return atividadeCasaEspirita;
    }

    public void setAtividadeCasaEspirita(String atividadeCasaEspirita) {
        this.atividadeCasaEspirita = atividadeCasaEspirita;
    }

    public Collection<CamisetaConfraternista> getCamisetas() {
        return camisetas;
    }

    public void setCamisetas(Collection<CamisetaConfraternista> camisetas) {
        this.camisetas = camisetas;
    }

    public boolean isOcupaVaga() {
        switch (tipo) {
            case CONFRATERNISTA:
            case EVANGELIZADOR:
            case COORDENADOR:
            case FACILITADOR:
            case OFICINEIRO:
                return true;
            default:
                return false;
        }
    }

    public enum Tipo {

        AUXILIAR("Auxiliar"),
        COORDENADOR("Coordenador"),
        CONFRATERNISTA("Confraternista"),
        EVANGELIZADOR("Evangelizador"),
        FACILITADOR("Facilitador"),
        OFICINEIRO("Oficineiro");
        private final String descricao;

        private Tipo(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getName() {
            return name();
        }

        public static Collection<Tipo> getValues() {
            return Arrays.asList(Tipo.values());
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.tipo != null ? this.tipo.hashCode() : 0);
        hash = 59 * hash + (this.pessoa != null ? this.pessoa.hashCode() : 0);
        hash = 59 * hash + (this.casaEspirita != null ? this.casaEspirita.hashCode() : 0);
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
        final Confraternista other = (Confraternista) obj;
        if (this.tipo != other.tipo) {
            return false;
        }
        if (this.pessoa != other.pessoa && (this.pessoa == null || !this.pessoa.equals(other.pessoa))) {
            return false;
        }
        if (this.casaEspirita != other.casaEspirita && (this.casaEspirita == null || !this.casaEspirita.equals(other.casaEspirita))) {
            return false;
        }
        return true;
    }


}
