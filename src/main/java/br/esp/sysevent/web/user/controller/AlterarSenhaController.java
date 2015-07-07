/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.user.controller;

import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.web.user.validation.UsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Controller
@RequestMapping(value = "/user/alterarSenha.html")
public class AlterarSenhaController {

    private static final String COMMAND_NAME = "command";

    @Autowired
    protected UsuarioValidator validator;

    public Usuario getCommand(){
        Usuario usuario = ControllerUtils.getLoggedUser();
        return usuario;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final Usuario command){
        return "user/alterarSenha";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final Usuario command,
                         final BindingResult result,
                         final ModelMap model,
                         final RedirectAttributes attributes){



        return "user/alterarSenha";
    }

}
