package br.esp.sysevent.core.model;

import br.ojimarcius.commons.persistence.model.AbstractEntity;
import java.util.Collection;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TAMANHOS_CAMISETA")
@AttributeOverride(name = "id", column = @Column(name = "ID_TAMANHO_CAM"))
public class TamanhoCamiseta extends AbstractEntity<Long> {
    private static final long serialVersionUID = -6654215482627937392L;    
    
    @Column(name = "DESCRICAO", nullable = false, unique = true)
    private String descricao;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tamanhosCamiseta")
    private Collection<Edicao> edicoes;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
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
        final TamanhoCamiseta other = (TamanhoCamiseta) obj;
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        return true;
    }
}
