/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.guest.controller;

import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.web.guest.command.RecuperaSenhaCommand;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Controller
@RequestMapping(value = "/guest/esqueciSenha.html")
public class RecuperarSenhaController {

    private static final String COMMAND_NAME = "command";
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private RecuperaSenhaValidator validator;

    @ModelAttribute(COMMAND_NAME)
    public RecuperaSenhaCommand getCommand() {
        RecuperaSenhaCommand command = new RecuperaSenhaCommand();
        return command;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final RecuperaSenhaCommand command) {
        return "guest/esqueciSenha";
    }

    public String onPost(@ModelAttribute(COMMAND_NAME) final RecuperaSenhaCommand command,
            final ModelMap model,
            final BindingResult result,
            final Locale locale) {
        validator.validate(command, result);
        if (result.hasErrors()) {
            return onGet(command);
        }
        return "guest/esqueciSenha";
    }
}
