/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Alexander
 */
@Entity
@Table(name = "EDICAO_CONFIG_CRACHA")
@AttributeOverride(name = "id", column =
        @Column(name = "ID_ED_CONFIG_CRACHA"))
public class EdicaoConfigCracha extends AbstractEntity{
    private static final long serialVersionUID = -5996817071538853898L;

    @ManyToOne
    @JoinColumn(name = "ID_EDICAO", nullable = false)
    private Edicao edicao;
    @Column(name = "TEM_CRACHA", nullable = false)
    private boolean temCracha;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "IMAGEM_NOME", nullable = true)),
        @AttributeOverride(name = "data", column = @Column(name = "IMAGEM_CONTEUDO", nullable = true, columnDefinition = "MEDIUMBLOB"))
    })
    private ImagemArquivo imagemFundo;
    @Column(name = "TIPO_CRACHA", nullable = true)
    @Enumerated(EnumType.STRING)
    private TipoCracha tipo;

    public Edicao getEdicao() {
        return edicao;
    }

    public void setEdicao(Edicao edicao) {
        this.edicao = edicao;
    }

    public boolean isTemCracha() {
        return temCracha;
    }

    public void setTemCracha(boolean temCracha) {
        this.temCracha = temCracha;
    }

    public ImagemArquivo getImagemFundo() {
        return imagemFundo;
    }

    public void setImagemFundo(ImagemArquivo imagemFundo) {
        this.imagemFundo = imagemFundo;
    }

    public TipoCracha getTipo() {
        return tipo;
    }

    public void setTipo(TipoCracha tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.edicao);
        hash = 17 * hash + (this.temCracha ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.tipo);
        return hash;
    }

    public enum TipoCracha{
        PEQUENO_HORIZONTAL("07cm x 10cm"),
        MEDIO_HORIZONTAL("08cm x 12cm"),
        GRANDE_VERTICAL("14cm x 10cm");
        private final String descricao;

        private TipoCracha(final String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getName() {
            return name();
        }

        public static Collection<TipoCracha> getValues() {
            return Arrays.asList(TipoCracha.values());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EdicaoConfigCracha other = (EdicaoConfigCracha) obj;
        if (!Objects.equals(this.edicao, other.edicao)) {
            return false;
        }
        if (this.temCracha != other.temCracha) {
            return false;
        }
        if (this.tipo != other.tipo) {
            return false;
        }
        return true;
    }
}
