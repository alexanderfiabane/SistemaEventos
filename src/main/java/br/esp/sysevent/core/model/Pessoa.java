/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import br.msf.commons.persistence.model.AbstractEntity;
import java.util.Calendar;
import javax.persistence.*;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Entity
@Table(name = "pessoas")
@AttributeOverride(name = "id", column =
                                @Column(name = "ID_PESSOA"))
public class Pessoa extends AbstractEntity<Long> {

    private static final long serialVersionUID = 4498720609099052317L;
    @Column(name = "NOME", length = 80, nullable = false)
    private String nome;
    @Column(name = "DATA_NASC", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar dataNascimento;
    @Column(name = "SEXO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ENDERECO", nullable = true)
    private Endereco endereco;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_DOCUMENTOS", nullable = true)
    private Documento documentos;
    @OneToOne(cascade = CascadeType.ALL)
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

    public InformacoesSaude getInformacoesSaude() {
        return informacoesSaude;
    }

    public void setInformacoesSaude(InformacoesSaude informacoesSaude) {
        this.informacoesSaude = informacoesSaude;
    }   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + hashCodeById(this);
        hash = 79 * hash + (this.nome != null ? this.nome.hashCode() : 0);
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
        final Pessoa other = (Pessoa) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        return true;
    }
}
