package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.util.Collection;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CORES_CAMISETA")
@AttributeOverride(name = "id", column = @Column(name = "ID_COR_CAM"))
public class CorCamiseta extends AbstractEntity {
    private static final long serialVersionUID = 3940156635694449150L;    
    
    @Column(name = "DESCRICAO", nullable = false, unique = true)
    private String descricao;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "coresCamiseta")
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
        final CorCamiseta other = (CorCamiseta) obj;
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        return true;
    }
}
