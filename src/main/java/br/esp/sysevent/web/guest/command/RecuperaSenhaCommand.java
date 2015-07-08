/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.guest.command;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
public class RecuperaSenhaCommand {

    private String username;
    private String cpf;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
