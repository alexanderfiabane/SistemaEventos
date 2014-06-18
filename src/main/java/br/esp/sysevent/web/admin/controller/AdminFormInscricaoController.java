/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.web.guest.controller.FormInscricaoController;
import br.esp.sysevent.core.model.Inscricao;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.NumberUtils;
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

    protected static final String[] INIT_PROPS = {"confraternista.camisetas"};

    @Override
    @ModelAttribute(COMMAND_NAME)
    public Inscricao getCommand(@RequestParam(value = "idInscricao", required = false) final String idInscricao) {
        final Inscricao command;
        if (CharSequenceUtils.isNumber(idInscricao)) {
            command = inscricaoService.findById(NumberUtils.parseLong(idInscricao), INIT_PROPS);
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
