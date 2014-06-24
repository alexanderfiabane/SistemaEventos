/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import br.ojimarcius.commons.persistence.model.AbstractEntity;
import java.util.Arrays;
import java.util.Collection;
import javax.persistence.*;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "CONFRATERNISTAS")
@AttributeOverride(name = "id", column = @Column(name = "ID_CONF"))
public class Confraternista extends AbstractEntity<Long> {
    
    private static final long serialVersionUID = -5564651400461738883L;
    
    @Column(name = "TIPO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Column(name = "CRACHA", length = 80, nullable = false)
    private String nomeCracha;
    @Column(name = "ATIV_CASA_ESP", length = 500, nullable = false)
    private String atividadeCasaEspirita;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_PESSOA", nullable = false)
    private Pessoa pessoa;
    @ManyToOne
    @JoinColumn(name = "ID_OFICINA", nullable = true)
    private Oficina oficina;
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_IDADE", nullable = true)
    private GrupoIdade grupoIdade;
    @ManyToOne
    @JoinColumn(name = "ID_DORMITORIO", nullable = true)
    private Dormitorio dormitorio;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_CASA_ESP", nullable = true)
    private CasaEspirita casaEspirita;
    @OneToMany(mappedBy = "confraternista", cascade = CascadeType.ALL, orphanRemoval = true)    
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
                return true;
            default:
                return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + hashCodeById(this);
        hash = 83 * hash + (this.nomeCracha != null ? this.nomeCracha.hashCode() : 0);
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
        final Confraternista other = (Confraternista) obj;
        if ((this.nomeCracha == null) ? (other.nomeCracha != null) : !this.nomeCracha.equals(other.nomeCracha)) {
            return false;
        }
        return true;
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
}
