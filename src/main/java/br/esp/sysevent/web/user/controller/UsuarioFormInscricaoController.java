/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.user.controller;

import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.web.guest.controller.FormInscricaoController;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;
import br.ojimarcius.commons.util.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Alexander
 */
@Controller
@RequestMapping(value = "/user/formUsuarioInscricao.html")
public class UsuarioFormInscricaoController extends FormInscricaoController{

    protected static final String[] INIT_PROPS = {"confraternista.camisetas"};

    @Override
    @ModelAttribute(COMMAND_NAME)
    public Inscricao getCommand(@RequestParam(value = "idInscricao", required = false) final String idInscricao) {
        final Inscricao command;
        if (NumberUtils.isNumber(idInscricao)) {
            command = inscricaoService.findById(NumberUtils.parseLong(idInscricao), INIT_PROPS);
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }
        final Usuario loggedUser = ControllerUtils.getLoggedUser();
        if(!loggedUser.getPessoa().getId().equals(command.getConfraternista().getPessoa().getId())) {
            throw new IllegalArgumentException("Acesso negado a informações de outra pessoa");
        }
        return command;
    }

    @Override
    protected String getFormView() {
        return "user/formUsuarioInscricao";
    }

    @Override
    protected String getSuccessView() {
        return "user/inscricaoUsuarioSuccess";
    }

}
