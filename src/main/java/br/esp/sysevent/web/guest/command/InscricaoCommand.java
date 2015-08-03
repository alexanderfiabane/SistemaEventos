/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.guest.command;

import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;

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
    private Inscricao inscricao;
    private String emailConfirm;
    private Usuario usuario;

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public String getEmailConfirm() {
        return emailConfirm;
    }

    public void setEmailConfirm(String emailConfirm) {
        this.emailConfirm = emailConfirm;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
