/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import br.ojimarcius.commons.persistence.model.AbstractEntity;
import java.util.Calendar;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Alexander
 */
@Entity
@Table(name = "NOTICIAS")
@AttributeOverride(name = "id", column = @Column(name = "ID_NOTICIA"))
public class Noticia extends AbstractEntity<Long>{
    private static final long serialVersionUID = 8507774596007646153L;    
    
    @Column(name = "TITULO", length = 80, nullable = false)
    private String titulo;
    @Column(name = "CONTEUDO", length = 500, nullable = false)
    private String conteudo;
    @Column(name = "DT_NOTICIA", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar data;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.titulo != null ? this.titulo.hashCode() : 0);
        hash = 89 * hash + (this.conteudo != null ? this.conteudo.hashCode() : 0);
        hash = 89 * hash + (this.data != null ? this.data.hashCode() : 0);
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
        final Noticia other = (Noticia) obj;
        if ((this.titulo == null) ? (other.titulo != null) : !this.titulo.equals(other.titulo)) {
            return false;
        }
        if ((this.conteudo == null) ? (other.conteudo != null) : !this.conteudo.equals(other.conteudo)) {
            return false;
        }
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }   
    
}
