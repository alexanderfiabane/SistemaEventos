/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;
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
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Controller
@RequestMapping(value = "/admin/inscricao/form.html")
public class AdminFormInscricaoController extends  FormInscricaoController {

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
        return command;
    }

    @Override
    protected String getFormView() {
        return "admin/inscricao/form";
    }

    @Override
    protected String getSuccessView() {
        return "admin/inscricao/view";
    }
}
