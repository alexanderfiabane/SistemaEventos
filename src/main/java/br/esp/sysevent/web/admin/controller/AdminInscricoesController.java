package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.service.EdicaoService;
import br.esp.sysevent.core.service.EventoService;
import br.esp.sysevent.core.service.InscricaoService;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.NumberUtils;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Giuliano
 */
@Controller
public class AdminInscricoesController {

    protected static final String[] INIT_PROPS = {"confraternista.camisetas"};

    @Autowired
    protected EdicaoService edicaoService;
    @Autowired
    protected InscricaoService inscricaoService;
    @Autowired
    protected EventoService eventoService;

    @RequestMapping(value = "/admin/inscricao/list.html", method = RequestMethod.GET)
    public String list(@RequestParam(value="idEdicao",required=false) final String idEdicao, final ModelMap model) {
        if (!CharSequenceUtils.isNumber(idEdicao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Edicao edicao = edicaoService.findById(NumberUtils.parseLong(idEdicao));
        if (edicao == null) {
            throw new IllegalArgumentException("Edição não encontrada.");
        }
        final Collection<Inscricao> inscricoes = inscricaoService.findByProperty("edicaoEvento", edicao);
        model.addAttribute("edicao", edicao);
        model.addAttribute("inscricoes", inscricoes);
        return "admin/inscricao/list";
    }
    
    @RequestMapping(value = "/admin/inscricao/listEvento.html", method = RequestMethod.GET)
    public String listEvento(final ModelMap model) {
        
        final Collection<Evento> eventos = eventoService.findAll();
        if (eventos == null) {
            throw new IllegalArgumentException("Eventos não encontrados.");
        }        
        model.addAttribute("eventos", eventos);        
        return "admin/inscricao/listEvento";
    }
    
    @RequestMapping(value = "/admin/inscricao/listEdicao.html", method = RequestMethod.GET)
    public String listEdicao(@RequestParam(value="idEvento",required=false) final String idEvento, final ModelMap model) {
        if (!CharSequenceUtils.isNumber(idEvento)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Evento evento = eventoService.findById(NumberUtils.parseLong(idEvento));
        final Collection<Edicao> edicoes = edicaoService.findByProperty("evento", evento);
        if (edicoes == null) {
            throw new IllegalArgumentException("Edições não encontradas.");
        }        
        model.addAttribute("edicoes", edicoes);
        model.addAttribute("evento", evento);
        return "admin/inscricao/listEdicao";
    }

    @RequestMapping(value = "/admin/inscricao/view.html", method = RequestMethod.GET)
    public String view(@RequestParam(value="idInscricao",required=false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao, INIT_PROPS);
        model.addAttribute("command", inscricao);
        return "admin/inscricao/view";
    }

    @RequestMapping(value = "/admin/inscricao/aprova.html", method = RequestMethod.GET)
    public String aprova(@RequestParam(value="idInscricao",required=false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao);
        inscricao.setStatus(Inscricao.Status.AGUARDANDO_PAGAMENTO);
        inscricaoService.saveOrUpdate(inscricao);
        ControllerUtils.sendMail(inscricao, "Inscrição Aceita", "pagamentoInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricao.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/inscricao/efetiva.html", method = RequestMethod.GET)
    public String efetiva(@RequestParam(value="idInscricao",required=false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao);
        inscricao.setStatus(Inscricao.Status.EFETIVADA);
        inscricaoService.saveOrUpdate(inscricao);
        ControllerUtils.sendMail(inscricao, "Inscrição Efetivada", "efetivadaInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricao.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/inscricao/indefere.html", method = RequestMethod.GET)
    public String indefere(@RequestParam(value="idInscricao",required=false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao);
        inscricao.setStatus(Inscricao.Status.INDEFERIDA);        
        inscricao.getConfraternista().getOficina().setVagasOcupadas(
                inscricao.getConfraternista().getOficina().getVagasOcupadas() - 1);        
        inscricaoService.saveOrUpdate(inscricao);
        ControllerUtils.sendMail(inscricao, "Inscrição Indeferida", "indeferidaInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricao.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/inscricao/reabre.html", method = RequestMethod.GET)
    public String reabre(@RequestParam(value="idInscricao",required=false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao);
        inscricao.setStatus(Inscricao.Status.PENDENTE);
        inscricaoService.saveOrUpdate(inscricao);
        ControllerUtils.sendMail(inscricao, "Inscrição Aberta para Edição", "reabertaInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricao.getEdicaoEvento().getId();
    }

    protected Inscricao getInscricao(final String idInscricao, String... initProps) throws IllegalArgumentException {
        if (!CharSequenceUtils.isNumber(idInscricao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Inscricao inscricao = inscricaoService.findById(NumberUtils.parseLong(idInscricao), initProps);
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscrição não encontrada.");
        }
        return inscricao;
    }
}
