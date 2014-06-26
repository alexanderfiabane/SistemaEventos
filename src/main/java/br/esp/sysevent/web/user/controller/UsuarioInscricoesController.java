/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.user.controller;

import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.core.service.InscricaoService;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Alexander
 */
@Controller
public class UsuarioInscricoesController {

    protected static final String[] INIT_PROPS = {"confraternista.camisetas"};

    @Autowired
    private InscricaoService inscricaoService;

    @RequestMapping(value = "/user/listUsuarioInscricoes.html", method = RequestMethod.GET)
    public String listUsuarioInscricoes(final ModelMap model) {

        final Usuario loggedUser = ControllerUtils.getLoggedUser();
        final Collection<Inscricao> inscricoes = inscricaoService.findByUsuario(loggedUser);
        if (inscricoes == null) {
            throw new IllegalArgumentException("Não há edições cadastradas.");
        }
        model.addAttribute("inscricoes", inscricoes);
        return "user/listUsuarioInscricoes";
    }

    @RequestMapping(value = "/user/inscricaoUsuarioSuccess.html", method = RequestMethod.GET)
    public String inscricaoUsuarioSuccess(@RequestParam(value="idInscricao",required=false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao, INIT_PROPS);
        model.addAttribute("command", inscricao);
        return "user/inscricaoUsuarioSuccess";
    }

    protected Inscricao getInscricao(final String idInscricao, String... initProps) throws IllegalArgumentException {
        if (!CharSequenceUtils.isNumber(idInscricao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Inscricao inscricao = inscricaoService.findById(NumberUtils.parseLong(idInscricao), initProps);
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscrição não encontrada.");
        }
        final Usuario loggedUser = ControllerUtils.getLoggedUser();
        if(!loggedUser.getPessoa().getId().equals(inscricao.getConfraternista().getPessoa().getId())) {
            throw new IllegalArgumentException("Acesso negado a informações de outra pessoa");
        }
        return inscricao;
    }

}
