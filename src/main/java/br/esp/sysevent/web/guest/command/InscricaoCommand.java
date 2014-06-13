/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.guest.command;

import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.CasaEspirita;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Endereco;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Usuario;
import java.util.Collection;

/**
 * Objeto que representa os dados preenchidos no formulário de inscrição.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class InscricaoCommand {

    /**
     * TODO : implementar InscricaoCommand, acrescentando os campos ou entidades
     * que vamos preencher no form.
     */
    private Usuario usuario;
    private Pessoa pessoa;
    private Confraternista confraternista;
    private Endereco endereco;
    private CasaEspirita casaEspirita;
    private Collection<CamisetaConfraternista> camisetasConfraternista;    
    private Cidade cidade;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Confraternista getConfraternista() {
        return confraternista;
    }

    public void setConfraternista(Confraternista confraternista) {
        this.confraternista = confraternista;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public CasaEspirita getCasaEspirita() {
        return casaEspirita;
    }

    public void setCasaEspirita(CasaEspirita casaEspirita) {
        this.casaEspirita = casaEspirita;
    }

    public Collection<CamisetaConfraternista> getCamisetasConfraternista() {
        return camisetasConfraternista;
    }

    public void setCamisetasConfraternista(Collection<CamisetaConfraternista> camisetasConfraternista) {
        this.camisetasConfraternista = camisetasConfraternista;
    }    

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
