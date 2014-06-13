/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import br.msf.commons.persistence.model.AbstractEntity;
import br.msf.commons.temporal.Period;
import br.msf.commons.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Alexander
 */
@Entity
@Table(name = "EDICOES")
@AttributeOverride(name = "id", column =
        @Column(name = "ID_EDICAO"))
public class Edicao extends AbstractEntity<Long> {
    private static final long serialVersionUID = -814676588800010502L;    
    
    @ManyToOne
    @JoinColumn(name = "ID_EVENTO", nullable = false)
    private Evento evento;
    @Column(name = "TEMA", length = 80, nullable = false)
    private String tema;
    @Column(name = "VAGAS", nullable = false)
    private Integer vagas;
    @Column(name = "VAGAS_OCUPADAS", nullable = false)
    private Integer vagasOcupadas;
    @Column(name = "NUMERO", nullable = false)
    private Integer numero;
    @Column(name = "VALOR_INSCR", nullable = false)
    private BigDecimal valorInscricao;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "start", column =
                @Column(name = "DATA_INI")),
        @AttributeOverride(name = "end", column =
                @Column(name = "DATA_FIM"))
    })
    private Period periodoInscricao;
    @Column(name = "DATA_EDICAO", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar data;
    @Column(name = "TIPO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @ManyToMany
    @JoinTable(name = "EDICOES_TIPOS_CAM", schema = "see2500_emuse",
            joinColumns = {
        @JoinColumn(name = "ID_EVENTO", nullable = false)},
            inverseJoinColumns = {
        @JoinColumn(name = "ID_TIPO_CAM", nullable = false)})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<TipoCamiseta> tiposCamiseta;
    @ManyToMany
    @JoinTable(name = "EDICOES_CORES_CAM", schema = "see2500_emuse",
            joinColumns = {
        @JoinColumn(name = "ID_EVENTO", nullable = false)},
            inverseJoinColumns = {
        @JoinColumn(name = "ID_COR_CAM", nullable = false)})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<CorCamiseta> coresCamiseta;
    @ManyToMany
    @JoinTable(name = "EDICOES_TAMANHOS_CAM", schema = "see2500_emuse",
            joinColumns = {
        @JoinColumn(name = "ID_EVENTO", nullable = false)},
            inverseJoinColumns = {
        @JoinColumn(name = "ID_TAMANHO_CAM", nullable = false)})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<TamanhoCamiseta> tamanhosCamiseta;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "edicaoEvento")
    private Collection<Oficina> oficinas;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "edicaoEvento")
    private Collection<GrupoIdade> gruposIdade;

    public String getTema() {
        return tema;
    }

    public void setTema(final String tema) {
        this.tema = tema;
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

    public Period getPeriodoInscricao() {
        return periodoInscricao;
    }

    public void setPeriodoInscricao(final Period periodoInscricao) {
        this.periodoInscricao = periodoInscricao;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(final Evento evento) {
        this.evento = evento;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(final Integer numero) {
        this.numero = numero;
    }

    public BigDecimal getValorInscricao() {
        return valorInscricao;
    }

    public void setValorInscricao(final BigDecimal valorInscricao) {
        this.valorInscricao = valorInscricao;
    }

    public Collection<TipoCamiseta> getTiposCamiseta() {
        return tiposCamiseta;
    }

    public void setTiposCamiseta(final Collection<TipoCamiseta> tiposCamiseta) {
        this.tiposCamiseta = tiposCamiseta;
    }

    public Collection<CorCamiseta> getCoresCamiseta() {
        return coresCamiseta;
    }

    public void setCoresCamiseta(final Collection<CorCamiseta> coresCamiseta) {
        this.coresCamiseta = coresCamiseta;
    }

    public Collection<TamanhoCamiseta> getTamanhosCamiseta() {
        return tamanhosCamiseta;
    }

    public void setTamanhosCamiseta(final Collection<TamanhoCamiseta> tamanhosCamiseta) {
        this.tamanhosCamiseta = tamanhosCamiseta;
    }

    public Collection<Oficina> getOficinas() {
        return oficinas;
    }

    public void setOficinas(Collection<Oficina> oficinas) {
        this.oficinas = oficinas;
    }

    public Collection<GrupoIdade> getGruposIdade() {
        return gruposIdade;
    }

    public void setGruposIdade(Collection<GrupoIdade> gruposIdade) {
        this.gruposIdade = gruposIdade;
    }

    public boolean isPossuiCamisetas() {
        // qquer uma das colecoes de camiseta estando vazia, consideramos que a edicao nao possui camiseta.
        return CollectionUtils.isNotEmpty(getTiposCamiseta())
                && CollectionUtils.isNotEmpty(getTamanhosCamiseta())
                && CollectionUtils.isNotEmpty(getCoresCamiseta());
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

    public boolean isCongresso() {
        return Tipo.CONGRESSO.equals(getTipo());
    }

    public boolean isFaixaEtaria() {
        return Tipo.FAIXA_ETARIA.equals(getTipo());
    }

    public boolean isOficina() {
        return Tipo.OFICINA.equals(getTipo());
    }

    public boolean isOficinaFaixaEtaria() {
        return Tipo.OFICINA_FAIXA_ETARIA.equals(getTipo());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.tema != null ? this.tema.hashCode() : 0);
        hash = 71 * hash + (this.periodoInscricao != null ? this.periodoInscricao.hashCode() : 0);
        hash = 71 * hash + (this.evento != null ? this.evento.hashCode() : 0);
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
        final Edicao other = (Edicao) obj;
        if ((this.tema == null) ? (other.tema != null) : !this.tema.equals(other.tema)) {
            return false;
        }
        if (this.periodoInscricao != other.periodoInscricao && (this.periodoInscricao == null || !this.periodoInscricao.equals(other.periodoInscricao))) {
            return false;
        }
        if (this.evento != other.evento && (this.evento == null || !this.evento.equals(other.evento))) {
            return false;
        }
        return true;
    }

    public enum Tipo {

        CONGRESSO("Congresso"),
        FAIXA_ETARIA("Faixa Etária"),
        OFICINA("Oficina"),
        OFICINA_FAIXA_ETARIA("Oficina e Faixa Etária");
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

        public static Collection<Edicao.Tipo> getValues() {
            return Arrays.asList(Edicao.Tipo.values());
        }
    }
}
