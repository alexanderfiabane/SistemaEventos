/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.user.controller;

import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.core.service.InscricaoService;
import br.esp.sysevent.core.service.ReportService;
import br.ojimarcius.commons.util.NumberUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Controller
public class ReportController {

    @Autowired
    private InscricaoService inscricaoService;
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/user/fichaInscricao.html", method = RequestMethod.GET)
    public ModelAndView fichaInscricao(@RequestParam(value="idInscricao",required=false) final String idInscricao,
                                       final HttpServletRequest request,
                                       final HttpServletResponse response) throws Exception {
        final Inscricao inscricao = getInscricao(idInscricao);
        final byte[] pdf = reportService.geraRelatorio(inscricao);
        ControllerUtils.writeHttpAttached(pdf, "ficha_inscricao.pdf", "application/pdf", response);
        return null;
    }

    protected Inscricao getInscricao(final String idInscricao) throws IllegalArgumentException, IllegalAccessException {
        if (!NumberUtils.isNumber(idInscricao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Inscricao inscricao = inscricaoService.findById(NumberUtils.parseLong(idInscricao));
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscrição não encontrada.");
        }
        final Usuario loggedUser = ControllerUtils.getLoggedUser();
        if(!loggedUser.getPessoa().getId().equals(inscricao.getConfraternista().getPessoa().getId())) {
            throw new IllegalAccessException("Acesso negado a informações de outra pessoa");
        }
        return inscricao;
    }
}
