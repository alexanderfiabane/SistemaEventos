/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import com.javaleks.commons.core.model.AbstractEntity;
import java.util.Calendar;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "PESSOAS")
@AttributeOverride(name = "id", column =
                                @Column(name = "ID_PESSOA"))
public class Pessoa extends AbstractEntity {

    private static final long serialVersionUID = 4498720609099052317L;
    @Column(name = "NOME", length = 80, nullable = false)
    private String nome;
    @Column(name = "DATA_NASC", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar dataNascimento;
    @Column(name = "SEXO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @OneToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "ID_RESPONSAVEL", nullable = true)
    private Responsavel responsavel;
    @ManyToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "ID_ENDERECO", nullable = true)
    private Endereco endereco;
    @OneToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "ID_DOCUMENTOS", nullable = true)
    private Documento documentos;
    @OneToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "ID_INFO_SAUDE", nullable = true)
    private InformacoesSaude informacoesSaude;

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(final Endereco endereco) {
        this.endereco = endereco;
    }

    public Documento getDocumentos() {
        return documentos;
    }

    public void setDocumentos(final Documento documentos) {
        this.documentos = documentos;
    }

    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public InformacoesSaude getInformacoesSaude() {
        return informacoesSaude;
    }

    public void setInformacoesSaude(InformacoesSaude informacoesSaude) {
        this.informacoesSaude = informacoesSaude;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 89 * hash + (this.dataNascimento != null ? this.dataNascimento.hashCode() : 0);
        hash = 89 * hash + (this.sexo != null ? this.sexo.hashCode() : 0);
        hash = 89 * hash + (this.documentos != null ? this.documentos.hashCode() : 0);
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
        final Pessoa other = (Pessoa) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.dataNascimento != other.dataNascimento && (this.dataNascimento == null || !this.dataNascimento.equals(other.dataNascimento))) {
            return false;
        }
        if (this.sexo != other.sexo) {
            return false;
        }
        if (this.documentos != other.documentos && (this.documentos == null || !this.documentos.equals(other.documentos))) {
            return false;
        }
        return true;
    }

}
