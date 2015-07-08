/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.user.controller;

import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.web.controller.I18nController;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.web.user.command.UsuarioCommand;
import br.esp.sysevent.web.user.validation.UsuarioValidator;
import java.util.Locale;
import org.apache.commons.codec.digest.DigestUtils;
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
public class AlterarSenhaController extends I18nController{

    private static final String COMMAND_NAME = "command";

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    protected UsuarioValidator validator;

    @ModelAttribute(COMMAND_NAME)
    public UsuarioCommand getCommand(){
        UsuarioCommand command = new UsuarioCommand();
        Usuario usuario = ControllerUtils.getLoggedUser();        
        command.setUsuario(usuario);
        command.setSenhaAtual(new String());
        command.setNovaSenha(new String());
        command.setConfirmaNovaSenha(new String());
        return command;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final UsuarioCommand command){
        return "user/alterarSenha";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final UsuarioCommand command,
                         final BindingResult result,
                         final ModelMap model,
                         final RedirectAttributes attributes,
                         final Locale locale){

        validator.validate(command, result);
        if (result.hasErrors()) {            
            return onGet(command);
        }
        command.getUsuario().setPassword(DigestUtils.sha256Hex(command.getNovaSenha()));
        usuarioDao.saveOrUpdate(command.getUsuario());
        model.addAttribute("message", getMessage("message.success.save", locale));
        ControllerUtils.sendMailUsuario(command.getUsuario(), getMessage("mail.password.update", locale), "alterouSenha.html");
        return "user/alterarSenha";
    }

}
