package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.dao.EventoDao;
import br.esp.sysevent.core.dao.GrupoIdadeDao;
import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.dao.OficinaDao;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Edicao.Tipo;
import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.web.controller.util.ControllerUtils;
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
 * @author Giuliano
 */
@Controller
public class AdminInscricoesController {

    protected static final String[] INIT_PROPS = {"confraternista.camisetas"};

    @Autowired
    protected EdicaoDao edicaoDao;
    @Autowired
    protected OficinaDao oficinaDao;
    @Autowired
    protected GrupoIdadeDao grupoIdadeDao;
    @Autowired
    protected InscricaoDao inscricaoDao;
    @Autowired
    protected EventoDao eventoDao;

    @RequestMapping(value = "/admin/inscricao/list.html", method = RequestMethod.GET)
    public String list(@RequestParam(value = "idEdicao", required = false) final String idEdicao, final ModelMap model) {
        if (!CharSequenceUtils.isNumber(idEdicao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        if (edicao == null) {
            throw new IllegalArgumentException("Edição não encontrada.");
        }
        final Collection<Inscricao> inscricoes = inscricaoDao.findByProperty("edicaoEvento", edicao);
        model.addAttribute("edicao", edicao);
        model.addAttribute("inscricoes", inscricoes);
        model.addAttribute("tipoInscricoes", Confraternista.Tipo.getValues());
        model.addAttribute("inscricaoStatus", Inscricao.Status.values());
        return "admin/inscricao/list";
    }

    @RequestMapping(value = "/admin/inscricao/listEvento.html", method = RequestMethod.GET)
    public String listEvento(final ModelMap model) {

        final Collection<Evento> eventos = eventoDao.findAll();
        if (eventos == null) {
            throw new IllegalArgumentException("Eventos não encontrados.");
        }
        model.addAttribute("eventos", eventos);
        return "admin/inscricao/listEvento";
    }

    @RequestMapping(value = "/admin/inscricao/listEdicao.html", method = RequestMethod.GET)
    public String listEdicao(@RequestParam(value = "idEvento", required = false) final String idEvento, final ModelMap model) {
        if (!CharSequenceUtils.isNumber(idEvento)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Evento evento = eventoDao.findById(NumberUtils.parseLong(idEvento));
        final Collection<Edicao> edicoes = edicaoDao.findByProperty("evento", evento);
        if (edicoes == null) {
            throw new IllegalArgumentException("Edições não encontradas.");
        }
        model.addAttribute("edicoes", edicoes);
        model.addAttribute("evento", evento);
        return "admin/inscricao/listEdicao";
    }

    @RequestMapping(value = "/admin/inscricao/view.html", method = RequestMethod.GET)
    public String view(@RequestParam(value = "idInscricao", required = false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao, INIT_PROPS);
        model.addAttribute("command", inscricao);
        return "admin/inscricao/view";
    }

    @RequestMapping(value = "/admin/inscricao/aprova.html", method = RequestMethod.GET)
    public String aprova(@RequestParam(value = "idInscricao", required = false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao);
        inscricao.setStatus(Inscricao.Status.AGUARDANDO_PAGAMENTO);
        inscricaoDao.saveOrUpdate(inscricao);
        ControllerUtils.sendMail(inscricao, "Inscrição Aceita", "pagamentoInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricao.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/inscricao/efetiva.html", method = RequestMethod.GET)
    public String efetiva(@RequestParam(value = "idInscricao", required = false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao);
        inscricao.setStatus(Inscricao.Status.EFETIVADA);
        inscricaoDao.saveOrUpdate(inscricao);
        ControllerUtils.sendMail(inscricao, "Inscrição Efetivada", "efetivadaInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricao.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/inscricao/indefere.html", method = RequestMethod.GET)
    public String indefere(@RequestParam(value = "idInscricao", required = false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao);
        inscricao.setStatus(Inscricao.Status.INDEFERIDA);
        liberaVaga(inscricao);
        inscricaoDao.saveOrUpdate(inscricao);
        ControllerUtils.sendMail(inscricao, "Inscrição Indeferida", "indeferidaInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricao.getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/inscricao/reabre.html", method = RequestMethod.GET)
    public String reabre(@RequestParam(value = "idInscricao", required = false) final String idInscricao, final ModelMap model) {
        final Inscricao inscricao = getInscricao(idInscricao);
        inscricao.setStatus(Inscricao.Status.PENDENTE);
        inscricaoDao.saveOrUpdate(inscricao);
        ControllerUtils.sendMail(inscricao, "Inscrição Aberta para Edição", "reabertaInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricao.getEdicaoEvento().getId();
    }

    protected Inscricao getInscricao(final String idInscricao, String... initProps) throws IllegalArgumentException {
        if (!CharSequenceUtils.isNumber(idInscricao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Inscricao inscricao = inscricaoDao.findById(NumberUtils.parseLong(idInscricao), initProps);
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscrição não encontrada.");
        }
        return inscricao;
    }

    private void liberaVaga(Inscricao inscricao) {
        final Edicao edicaoEvento = inscricao.getEdicaoEvento();
        final Tipo tipoEvento = edicaoEvento.getTipo();
        final Confraternista confraternista = inscricao.getConfraternista();
        final Oficina oficina = confraternista.getOficina();
        final GrupoIdade grupoIdade = confraternista.getGrupoIdade();
        //verificar tipo da inscricao
        if (tipoEvento.equals(Tipo.OFICINA) && oficina != null) {
            oficina.setVagasOcupadas(
                    oficina.getVagasOcupadas() - 1);
            oficinaDao.saveOrUpdate(oficina);
        } else if (tipoEvento.equals(Tipo.FAIXA_ETARIA) && grupoIdade != null) {
            grupoIdade.setVagasOcupadas(
                    grupoIdade.getVagasOcupadas() - 1);
            grupoIdadeDao.saveOrUpdate(grupoIdade);
        }
        if (confraternista.isOcupaVaga()) {
            edicaoEvento.setVagasOcupadas(
                    edicaoEvento.getVagasOcupadas() - 1);
            edicaoDao.saveOrUpdate(edicaoEvento);
        }
    }
}
