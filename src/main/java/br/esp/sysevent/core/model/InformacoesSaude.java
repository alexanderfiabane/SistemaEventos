/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import com.javaleks.commons.util.CharSequenceUtils;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Alexander
 */
@Entity
@Table(name = "INFO_SAUDE")
@AttributeOverride(name = "id", column =
@Column(name = "ID_INFO_SAUDE"))
public class InformacoesSaude extends AbstractEntity{

    private static final long serialVersionUID = 6264718982994575893L;

    @Column(name = "MEDICACAO", nullable = true)
    private String medicacao;
    @Column(name = "CONVENIO", nullable = true)
    private String convenio;
    @Column(name = "CONV_TELEFONE", nullable = true)
    private String convenioTelefone;
    @Column(name = "ALERGIA", nullable = true)
    private String alergia;
    @Column(name = "DIETA", nullable = true)
    private String dieta;

    public String getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getConvenioTelefone() {
        return convenioTelefone;
    }

    public void setConvenioTelefone(String convenioTelefone) {
        this.convenioTelefone = convenioTelefone;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public boolean temInformacao() {
        return !CharSequenceUtils.isBlank(medicacao) ||
               !CharSequenceUtils.isBlank(convenio) ||
               !CharSequenceUtils.isBlank(alergia) ||
               !CharSequenceUtils.isBlank(dieta);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.medicacao != null ? this.medicacao.hashCode() : 0);
        hash = 83 * hash + (this.convenio != null ? this.convenio.hashCode() : 0);
        hash = 83 * hash + (this.alergia != null ? this.alergia.hashCode() : 0);
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
        final InformacoesSaude other = (InformacoesSaude) obj;
        if ((this.medicacao == null) ? (other.medicacao != null) : !this.medicacao.equals(other.medicacao)) {
            return false;
        }
        if ((this.convenio == null) ? (other.convenio != null) : !this.convenio.equals(other.convenio)) {
            return false;
        }
        if ((this.alergia == null) ? (other.alergia != null) : !this.alergia.equals(other.alergia)) {
            return false;
        }
        return true;
    }

}
