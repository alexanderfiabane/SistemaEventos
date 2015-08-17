/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Embeddable
public class ImagemArquivo implements Serializable{
    private static final long serialVersionUID = -8711721395591462491L;

    @Column(name = "NOME")
    private String nome;
    @Column(name = "CONTEUDO", columnDefinition = "LONGBLOB")
    private byte[] data;

    public ImagemArquivo() {
        this.nome = "";
        this.data = null;
    }
    public ImagemArquivo(String nome, byte[] data) {
        this.nome = nome;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Arrays.hashCode(this.data);
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
        final ImagemArquivo other = (ImagemArquivo) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Arrays.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }
}
