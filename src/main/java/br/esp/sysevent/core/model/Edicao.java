/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import com.javaleks.commons.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
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
public class Edicao extends AbstractEntity {
    private static final long serialVersionUID = 4133257161895949871L;

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
    private String numero;
    @Column(name = "VALOR_INSCR", nullable = false)
    private BigDecimal valorInscricao;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "start", column = @Column(name = "DATA_INSCRICAO_INI")),
        @AttributeOverride(name = "end", column = @Column(name = "DATA_INSCRICAO_FIM"))
    })
    private PersistentPeriod periodoInscricao;
    @Column(name = "DATA_EDICAO", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar data;
    @Column(name = "IDADE_MINIMA", nullable = false)
    private Integer idadeMinima;
    @Column(name = "TIPO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @ManyToMany
    @JoinTable(name = "EDICOES_TIPOS_CAM",
            joinColumns = {
        @JoinColumn(name = "ID_EVENTO", nullable = false)},
            inverseJoinColumns = {
        @JoinColumn(name = "ID_TIPO_CAM", nullable = false)})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<TipoCamiseta> tiposCamiseta;
    @ManyToMany
    @JoinTable(name = "EDICOES_CORES_CAM",
            joinColumns = {
        @JoinColumn(name = "ID_EVENTO", nullable = false)},
            inverseJoinColumns = {
        @JoinColumn(name = "ID_COR_CAM", nullable = false)})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<CorCamiseta> coresCamiseta;
    @ManyToMany
    @JoinTable(name = "EDICOES_TAMANHOS_CAM",
            joinColumns = {
        @JoinColumn(name = "ID_EVENTO", nullable = false)},
            inverseJoinColumns = {
        @JoinColumn(name = "ID_TAMANHO_CAM", nullable = false)})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<TamanhoCamiseta> tamanhosCamiseta;
    private BigDecimal valorCamiseta;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "edicaoEvento")
    private Collection<Oficina> oficinas;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "edicaoEvento")
    private Collection<GrupoIdade> gruposIdade;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_FORMA_COBRANCA", nullable = true)
    private FormaCobranca formaCobranca;

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

    public PersistentPeriod getPeriodoInscricao() {
        return periodoInscricao;
    }

    public void setPeriodoInscricao(final PersistentPeriod periodoInscricao) {
        this.periodoInscricao = periodoInscricao;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Integer getIdadeMinima() {
        return idadeMinima;
    }

    public void setIdadeMinima(Integer idadeMinima) {
        this.idadeMinima = idadeMinima;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(final String numero) {
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

    public BigDecimal getValorCamiseta() {
        return valorCamiseta;
    }

    public void setValorCamiseta(BigDecimal valorCamiseta) {
        this.valorCamiseta = valorCamiseta;
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

    public FormaCobranca getFormaCobranca() {
        return formaCobranca;
    }

    public void setFormaCobranca(FormaCobranca formaCobranca) {
        this.formaCobranca = formaCobranca;
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

    public enum Tipo {

        CONGRESSO("Congresso"),
        FAIXA_ETARIA("Faixa Etária"),
        OFICINA("Oficina");
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.evento != null ? this.evento.hashCode() : 0);
        hash = 97 * hash + (this.tema != null ? this.tema.hashCode() : 0);
        hash = 97 * hash + (this.vagas != null ? this.vagas.hashCode() : 0);
        hash = 97 * hash + (this.data != null ? this.data.hashCode() : 0);
        hash = 97 * hash + (this.tipo != null ? this.tipo.hashCode() : 0);
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
        final Edicao other = (Edicao) obj;
        if (this.evento != other.evento && (this.evento == null || !this.evento.equals(other.evento))) {
            return false;
        }
        if ((this.tema == null) ? (other.tema != null) : !this.tema.equals(other.tema)) {
            return false;
        }
        if (this.vagas != other.vagas && (this.vagas == null || !this.vagas.equals(other.vagas))) {
            return false;
        }
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
            return false;
        }
        return this.tipo == other.tipo;
    }

}
