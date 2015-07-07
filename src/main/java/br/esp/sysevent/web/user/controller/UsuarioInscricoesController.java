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
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
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

    @Autowired
    private InscricaoDao inscricaoDao;
    @Autowired
    private UsuarioDao usuarioDao;

    @RequestMapping(value = "/user/listUsuarioInscricoes.html", method = RequestMethod.GET)
    public String listUsuarioInscricoes(final ModelMap model) {

        final Usuario loggedUser = ControllerUtils.getLoggedUser();
        final Collection<Inscricao> inscricoes = inscricaoDao.findByUsuario(loggedUser);
        if (inscricoes == null) {
            throw new IllegalArgumentException("Não há edições cadastradas.");
        }
        model.addAttribute("inscricoes", inscricoes);
        return "user/listUsuarioInscricoes";
    }

    @RequestMapping(value = "/user/inscricaoUsuarioSuccess.html", method = RequestMethod.GET)
    public String inscricaoUsuarioSuccess(@RequestParam(value="idInscricao",required=false) final String idInscricao, final ModelMap model) {
        final InscricaoCommand inscricaoCmd = getInscricao(idInscricao);
        model.addAttribute("command", inscricaoCmd);
        return "user/inscricaoUsuarioSuccess";
    }

    protected InscricaoCommand getInscricao(final String idInscricao, String... initProps) throws IllegalArgumentException {
        InscricaoCommand inscricaoCmd = new InscricaoCommand();
        if (!CharSequenceUtils.isNumber(idInscricao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Inscricao inscricao = inscricaoDao.findById(NumberUtils.parseLong(idInscricao), initProps);
        final Usuario usuario = usuarioDao.findByPessoaTipo(inscricao.getConfraternista().getPessoa(), Usuario.Role.ROLE_USER);
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscrição não encontrada.");
        }
        final Usuario loggedUser = ControllerUtils.getLoggedUser();
        if(!loggedUser.getPessoa().getId().equals(inscricao.getConfraternista().getPessoa().getId())) {
            throw new IllegalArgumentException("Acesso negado a informações de outra pessoa");
        }
        inscricaoCmd.setInscricao(inscricao);
        inscricaoCmd.setUsuario(usuario);
        return inscricaoCmd;
    }

}
