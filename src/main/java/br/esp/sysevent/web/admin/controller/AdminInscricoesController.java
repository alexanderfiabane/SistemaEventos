package br.esp.sysevent.web.admin.controller;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.TransactionSearchResult;
import br.com.uol.pagseguro.domain.TransactionSummary;
import br.com.uol.pagseguro.enums.TransactionStatus;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.TransactionSearchService;
import br.esp.sysevent.core.dao.ConfraternistaDao;
import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.dao.EventoDao;
import br.esp.sysevent.core.dao.GrupoIdadeDao;
import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.dao.OficinaDao;
import br.esp.sysevent.core.dao.PagamentoInscricaoDao;
import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Edicao.Tipo;
import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.model.FormaCobranca.TipoCobranca;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.model.ImagemArquivo;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.core.model.PagSeguroConta;
import br.esp.sysevent.core.model.PagamentoInscricao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.util.PagamentoInscricaoUtils;
import br.esp.sysevent.web.controller.I18nController;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.web.guest.command.InscricaoCommand;
import com.javaleks.commons.io.HttpUtils;
import com.javaleks.commons.util.CalendarUtils;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Giuliano
 */
@Controller
public class AdminInscricoesController extends I18nController {

    @Autowired
    private EdicaoDao edicaoDao;
    @Autowired
    private OficinaDao oficinaDao;
    @Autowired
    private GrupoIdadeDao grupoIdadeDao;
    @Autowired
    private InscricaoDao inscricaoDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private EventoDao eventoDao;
    @Autowired
    private ConfraternistaDao confraternistaDao;
    @Autowired
    private PagamentoInscricaoDao pagamentoInscricaoDao;

    @RequestMapping(value = "/admin/inscricao/list.html", method = RequestMethod.GET)
    public String list(@RequestParam(value = "idEdicao", required = false) final String idEdicao, final ModelMap model) {
        if (!CharSequenceUtils.isNumber(idEdicao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        if (edicao == null) {
            throw new IllegalArgumentException("Edição não encontrada.");
        }
        model.addAttribute("edicao", edicao);
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
        final InscricaoCommand inscricaoCmd = getInscricao(idInscricao);
        model.addAttribute("command", inscricaoCmd);
        return "admin/inscricao/view";
    }

    @RequestMapping(value = "/admin/inscricao/aprova.html", method = RequestMethod.GET)
    public String aprova(@RequestParam(value = "idInscricao", required = false) final String idInscricao, final ModelMap model, final Locale locale, RedirectAttributes attributes) {
        final InscricaoCommand inscricaoCmd = getInscricao(idInscricao);
        if (inscricaoCmd.getInscricao().isIsento() && inscricaoCmd.getInscricao().getConfraternista().getCamisetas().isEmpty()) {
            inscricaoCmd.getInscricao().setStatus(Inscricao.Status.PAGA);
        } else {
            inscricaoCmd.getInscricao().setStatus(Inscricao.Status.AGUARDANDO_PAGAMENTO);
        }
        inscricaoDao.saveOrUpdate(inscricaoCmd.getInscricao());
        if (inscricaoCmd.getInscricao().getEdicaoEvento().getFormaCobranca().getTipoCobranca().equals(TipoCobranca.DEPOSITO_CONTA)) {
            ControllerUtils.sendMail(inscricaoCmd.getInscricao(), "Inscrição Aceita", "pagamentoInscricaoDC.html");
        } else if (inscricaoCmd.getInscricao().getEdicaoEvento().getFormaCobranca().getTipoCobranca().equals(TipoCobranca.PAGSEGURO)) {
            ControllerUtils.sendMail(inscricaoCmd.getInscricao(), "Inscrição Aceita", "pagamentoInscricaoPS.html");
        }
        attributes.addFlashAttribute("message", getMessage("subscription.success.aproved", locale));
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricaoCmd.getInscricao().getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/inscricao/efetiva.html", method = RequestMethod.GET)
    public String efetiva(@RequestParam(value = "idInscricao", required = false) final String idInscricao, final ModelMap model, final Locale locale, RedirectAttributes attributes) {
        final InscricaoCommand inscricaoCmd = getInscricao(idInscricao);
        inscricaoCmd.getInscricao().setStatus(Inscricao.Status.EFETIVADA);
        inscricaoDao.saveOrUpdate(inscricaoCmd.getInscricao());
        attributes.addFlashAttribute("message", getMessage("subscription.success.effective", locale));
        ControllerUtils.sendMail(inscricaoCmd.getInscricao(), "Inscrição Efetivada", "efetivadaInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricaoCmd.getInscricao().getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/inscricao/indefere.html", method = RequestMethod.GET)
    public String indefere(@RequestParam(value = "idInscricao", required = false) final String idInscricao, final ModelMap model, final Locale locale, RedirectAttributes attributes) {
        final InscricaoCommand inscricaoCmd = getInscricao(idInscricao);
        inscricaoCmd.getInscricao().setStatus(Inscricao.Status.INDEFERIDA);
        liberaVaga(inscricaoCmd.getInscricao());
        inscricaoDao.saveOrUpdate(inscricaoCmd.getInscricao());
        attributes.addFlashAttribute("message", getMessage("subscription.success.rejected", locale));
        ControllerUtils.sendMail(inscricaoCmd.getInscricao(), "Inscrição Indeferida", "indeferidaInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricaoCmd.getInscricao().getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/inscricao/reabre.html", method = RequestMethod.GET)
    public String reabre(@RequestParam(value = "idInscricao", required = false) final String idInscricao, final ModelMap model, final Locale locale, RedirectAttributes attributes) {
        final InscricaoCommand inscricaoCmd = getInscricao(idInscricao);
        inscricaoCmd.getInscricao().setStatus(Inscricao.Status.PENDENTE);
        inscricaoDao.saveOrUpdate(inscricaoCmd.getInscricao());
        attributes.addFlashAttribute("message", getMessage("subscription.success.reopened", locale));
        ControllerUtils.sendMail(inscricaoCmd.getInscricao(), "Inscrição Aberta para Edição", "reabertaInscricao.html");
        return "redirect:/admin/inscricao/list.html?idEdicao=" + inscricaoCmd.getInscricao().getEdicaoEvento().getId();
    }

    @RequestMapping(value = "/admin/inscricao/pagseguroSync.html", method = RequestMethod.GET)
    public String syncPagamentos(@RequestParam(value = "idEdicao", required = false) final String idEdicao, final ModelMap model, final Locale locale, RedirectAttributes attributes) throws PagSeguroServiceException {
        Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        PagSeguroConta pagSeguroAccount = edicao.getFormaCobranca().getPagSeguro();
        AccountCredentials pagSeguroCredentials = new AccountCredentials(
                pagSeguroAccount.getEmailPagSeguroPlain(),
                pagSeguroAccount.getTokenSegurancaProducao(),
                pagSeguroAccount.getTokenSegurancaSandBox());
        if (pagSeguroAccount.isProducao()) {
            PagSeguroConfig.setProductionEnvironment();
        } else {
            PagSeguroConfig.setSandboxEnvironment();
        }
        Calendar mesAtras = Calendar.getInstance();
        mesAtras.add(Calendar.MONTH, -1);
//        TransactionSearchResult transactionSearchResult = TransactionSearchService.searchByDate(pagSeguroCredentials,
//                edicao.getPeriodoInscricao().getStart().getTime(), Calendar.getInstance().getTime(), Integer.valueOf(1), Integer.valueOf(1000));
        TransactionSearchResult transactionSearchResult = TransactionSearchService.searchByDate(pagSeguroCredentials,
                mesAtras.getTime(), Calendar.getInstance().getTime(), Integer.valueOf(1), Integer.valueOf(1000));

        if (transactionSearchResult != null) {
            List<TransactionSummary> listTransactionSummaries = transactionSearchResult.getTransactionSummaries();
            Iterator<TransactionSummary> transactionSummariesIterator = listTransactionSummaries.iterator();

            while (transactionSummariesIterator.hasNext()) {
                TransactionSummary currentTransactionSummary = (TransactionSummary) transactionSummariesIterator.next();
                PagamentoInscricao pagamentoInscricao = pagamentoInscricaoDao.findByCodReferencia(currentTransactionSummary.getReference());
                TransactionStatus statusTransacao = currentTransactionSummary.getStatus();

                if (pagamentoInscricao != null) {
                    if(!pagamentoInscricao.getInscricao().getStatus().equals(Inscricao.Status.EFETIVADA)){
                        //Atualiza as informações
                        Inscricao inscricao = pagamentoInscricao.getInscricao();
                        pagamentoInscricao.setDataPagamento(CalendarUtils.castToCalendar(currentTransactionSummary.getDate()));
                        pagamentoInscricao.setCodPagamento(currentTransactionSummary.getCode());
                        pagamentoInscricao.setValor(currentTransactionSummary.getGrossAmount().add(currentTransactionSummary.getFeeAmount().add(currentTransactionSummary.getExtraAmount())));
                        pagamentoInscricao.setDescricaoPagamento(PagamentoInscricaoUtils.montaDescricaoPagamento(currentTransactionSummary, false));
                        pagamentoInscricao.setDescricaoPagamentoQtip(PagamentoInscricaoUtils.montaDescricaoPagamento(currentTransactionSummary, true));
                        pagamentoInscricaoDao.saveOrUpdate(pagamentoInscricao);
                        if (statusTransacao.equals(TransactionStatus.PAID)) {
                            inscricao.setStatus(Inscricao.Status.PAGA);
                            inscricao.setPagamento(pagamentoInscricao);
                            inscricaoDao.saveOrUpdate(inscricao);
                        }
                    }
                } else {
                    //cria pagamentoInscricao e salva
                    InscricaoCommand inscricaoCmd = getInscricao(CharSequenceUtils.subStringAfterFirst("SE", currentTransactionSummary.getReference()));
                    if(!inscricaoCmd.getInscricao().getStatus().equals(Inscricao.Status.EFETIVADA)){
                        pagamentoInscricao = new PagamentoInscricao();
                        pagamentoInscricao.setInscricao(inscricaoCmd.getInscricao());
                        pagamentoInscricao.setDataPagamento(CalendarUtils.castToCalendar(currentTransactionSummary.getDate()));
                        pagamentoInscricao.setCodPagamento(currentTransactionSummary.getCode());
                        pagamentoInscricao.setValor(currentTransactionSummary.getGrossAmount().add(currentTransactionSummary.getFeeAmount().add(currentTransactionSummary.getExtraAmount())));
                        pagamentoInscricao.setDescricaoPagamento(PagamentoInscricaoUtils.montaDescricaoPagamento(currentTransactionSummary, false));
                        pagamentoInscricao.setDescricaoPagamentoQtip(PagamentoInscricaoUtils.montaDescricaoPagamento(currentTransactionSummary, true));
                        pagamentoInscricaoDao.saveOrUpdate(pagamentoInscricao);
                        if(statusTransacao.equals(TransactionStatus.PAID)){
                            inscricaoCmd.getInscricao().setStatus(Inscricao.Status.PAGA);
                            inscricaoCmd.getInscricao().setPagamento(pagamentoInscricao);
                            inscricaoDao.saveOrUpdate(inscricaoCmd.getInscricao());
                        }
                    }
                }
            }
            attributes.addFlashAttribute("message", "Pagamentos sincronizados com sucesso");
        } else {
            attributes.addFlashAttribute("message", "Não foram encontrados pagamentos até a data de hoje");
        }

        return "redirect:/admin/inscricao/list.html?idEdicao=" + idEdicao;
    }

    private InscricaoCommand getInscricao(final String idInscricao, String... initProps) throws IllegalArgumentException {
        InscricaoCommand inscricaoCmd = new InscricaoCommand();
        if (!CharSequenceUtils.isNumber(idInscricao)) {
            throw new IllegalArgumentException("Parametro não encontrado.");
        }
        final Inscricao inscricao = inscricaoDao.findById(NumberUtils.parseLong(idInscricao), initProps);
        final Usuario usuario = usuarioDao.findByPessoaTipo(inscricao.getConfraternista().getPessoa(), Usuario.Role.ROLE_USER);
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscrição não encontrada.");
        }
        inscricaoCmd.setInscricao(inscricao);
        inscricaoCmd.setUsuario(usuario);
        return inscricaoCmd;
    }

    @RequestMapping(value = "/admin/inscricao/downloadComprovante.html", method = RequestMethod.GET)
    public ModelAndView downloadAnexo(@RequestParam(value = "idComprovante", required = false) final Long idComprovante, final HttpServletResponse response) throws Exception {
        final ImagemArquivo comprovante = pagamentoInscricaoDao.findComprovante(idComprovante);
        if (comprovante == null) {
            throw new Exception("Compravante não encontrado");
        }
        HttpUtils.writeHttpAttached(comprovante.getNome(), comprovante.getData(), response);
        return null;
    }

    private void liberaVaga(Inscricao inscricao) {
        final Edicao edicaoEvento = inscricao.getEdicaoEvento();
        final Tipo tipoEvento = edicaoEvento.getTipo();
        final Confraternista confraternista = inscricao.getConfraternista();
        final Oficina oficina = confraternista.getOficina();
        final GrupoIdade grupoIdade = confraternista.getGrupoIdade();
        //verificar tipo da inscricao
        if (inscricao.isOcupaVagaGrupoOficina() && !tipoEvento.equals(Tipo.CONGRESSO)) {
            if (tipoEvento.equals(Tipo.OFICINA) && oficina != null) {
                oficina.setVagasOcupadas(oficina.getVagasOcupadas() - 1);
                oficinaDao.saveOrUpdate(oficina);
                confraternista.setOficina(null);
                confraternistaDao.saveOrUpdate(confraternista);
            } else if (tipoEvento.equals(Tipo.FAIXA_ETARIA) && grupoIdade != null) {
                grupoIdade.setVagasOcupadas(grupoIdade.getVagasOcupadas() - 1);
                grupoIdadeDao.saveOrUpdate(grupoIdade);
                confraternista.setGrupoIdade(null);
                confraternistaDao.saveOrUpdate(confraternista);
            }
        }
        if (inscricao.isOcupaVagaEvento()) {
            edicaoEvento.setVagasOcupadas(edicaoEvento.getVagasOcupadas() - 1);
            edicaoDao.saveOrUpdate(edicaoEvento);
        }
    }
}
