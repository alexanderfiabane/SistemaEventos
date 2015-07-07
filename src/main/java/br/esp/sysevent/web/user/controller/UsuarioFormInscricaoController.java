/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.user.controller;

import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.web.guest.command.InscricaoCommand;
import br.esp.sysevent.web.guest.controller.FormInscricaoController;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private InscricaoDao inscricaoDao;
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    @ModelAttribute(COMMAND_NAME)
    public InscricaoCommand getCommand(@RequestParam(value = "idInscricao", required = false) final String idInscricao) {
        final InscricaoCommand command = new InscricaoCommand();
        if (CharSequenceUtils.isNumber(idInscricao)) {
            Inscricao inscricao = inscricaoDao.findById(NumberUtils.parseLong(idInscricao));
            command.setInscricao(inscricao);
            command.setUsuario(usuarioDao.findByPessoaTipo(inscricao.getConfraternista().getPessoa(), Usuario.Role.ROLE_USER));
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }
        final Usuario loggedUser = ControllerUtils.getLoggedUser();
        if(!loggedUser.getPessoa().getId().equals(command.getInscricao().getConfraternista().getPessoa().getId())) {
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
