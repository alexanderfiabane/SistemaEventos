/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.guest.controller;

import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.web.controller.I18nController;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.web.guest.command.RecuperaSenhaCommand;
import br.esp.sysevent.web.guest.validation.RecuperaSenhaValidator;
import com.javaleks.commons.util.CharSequenceUtils;
import java.util.Locale;
import org.apache.commons.codec.digest.DigestUtils;
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
public class RecuperarSenhaController extends I18nController{

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

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final RecuperaSenhaCommand command,
            final ModelMap model,
            final BindingResult result,
            final Locale locale) {
        validator.validate(command, result);
        if (result.hasErrors()) {
            return onGet(command);
        }
        Usuario usuario;
        if(!CharSequenceUtils.isBlankOrNull(command.getUsername())){
            usuario = usuarioDao.findByLogin(command.getUsername());
        }else{
            usuario = usuarioDao.findByCpf(command.getCpf());
        }
        String novaSenha = gerarSenhaAleatoria(8);
        usuario.setPassword(novaSenha);
        ControllerUtils.sendMailUsuario(usuario, getMessage("mail.password.recovery", locale), "recuperaSenha.html");
        usuario.setPassword(DigestUtils.sha256Hex(novaSenha));
        usuarioDao.saveOrUpdate(usuario);
        model.addAttribute("message", getMessage("message.success.password.recovery", locale));
        return "guest/esqueciSenha";
    }

    private static String gerarSenhaAleatoria(Integer maxDigits) {
        int qtdeMaximaCaracteres = maxDigits;
        String[] caracteres = { "a", "1", "b", "2", "4", "5", "6", "7", "8",
                "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z" };

        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < qtdeMaximaCaracteres; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            senha.append(caracteres[posicao]);
        }
        return senha.toString();
    }
}
