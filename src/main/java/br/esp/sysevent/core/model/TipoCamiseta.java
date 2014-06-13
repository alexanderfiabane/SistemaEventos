package br.esp.sysevent.core.model;

import br.msf.commons.persistence.model.AbstractEntity;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TIPOS_CAMISETA")
@AttributeOverride(name = "id", column = @Column(name = "ID_TIPO_CAM"))
public class TipoCamiseta extends AbstractEntity<Long> {
    private static final long serialVersionUID = 8310315145254678448L;    
    
    @Column(name = "DESCRICAO", nullable = false, unique = true)
    private String descricao;
    @Column(name = "VALOR_CAMISETA", nullable = false)
    private BigDecimal valorCamiseta;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tiposCamiseta")
    private Collection<Edicao> edicoes;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorCamiseta() {
        return valorCamiseta;
    }

    public void setValorCamiseta(BigDecimal valorCamiseta) {
        this.valorCamiseta = valorCamiseta;
    }   

    public Collection<Edicao> getEdicoes() {
        return edicoes;
    }

    public void setEdicoes(final Collection<Edicao> edicoes) {
        this.edicoes = edicoes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
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
        final TipoCamiseta other = (TipoCamiseta) obj;
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        return true;
    }
}
