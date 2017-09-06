/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.user.controller;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.TransactionSearchService;
import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.dao.PagamentoInscricaoDao;
import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.PagSeguroConta;
import br.esp.sysevent.core.model.PagamentoInscricao;
import br.esp.sysevent.util.PagamentoInscricaoUtils;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import com.javaleks.commons.util.CalendarUtils;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.CollectionUtils;
import com.javaleks.commons.util.NumberUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Controller
@RequestMapping(value = "/user/pagamentoSuccessPS.html")
public class pagamentoSuccessPagSeguroController extends PagamentoFormController {

    @Autowired
    private InscricaoDao inscricaoDao;
    @Autowired
    private PagamentoInscricaoDao pagamentoInscricaoDao;

    @ModelAttribute("command")
    public PagamentoInscricao getCommand(@RequestParam(value = "idInscricao", required = true) final String idInscricao, @RequestParam(value = "codTransactionPagSeguro", required = true) final String codTransactionPagSeguro) throws PagSeguroServiceException {
        if (!CharSequenceUtils.isNumber(idInscricao)) {
            throw new IllegalStateException("Inscrição inválida");
        }
        Inscricao inscricao = inscricaoDao.findById(NumberUtils.parseLong(idInscricao));
        PagamentoInscricao pagamentoInscricao = pagamentoInscricaoDao.findByInscricao(inscricao);
        if(pagamentoInscricao == null){
            pagamentoInscricao = new PagamentoInscricao();
            pagamentoInscricao.setInscricao(inscricao);
        }
        pagamentoInscricao.setCodPagamento(codTransactionPagSeguro);

        PagSeguroConta pagSeguroAccount = inscricao.getEdicaoEvento().getFormaCobranca().getPagSeguro();
        AccountCredentials pagSeguroCredentials = new AccountCredentials(
                pagSeguroAccount.getEmailPagSeguroPlain(),
                pagSeguroAccount.getTokenSegurancaProducao(),
                pagSeguroAccount.getTokenSegurancaSandBox());
        if (pagSeguroAccount.isProducao()) {
            PagSeguroConfig.setProductionEnvironment();
        } else {
            PagSeguroConfig.setSandboxEnvironment();
        }
        Transaction transaction = TransactionSearchService.searchByCode(pagSeguroCredentials, codTransactionPagSeguro);

        pagamentoInscricao.setDataPagamento(CalendarUtils.castToCalendar(transaction.getDate()));
        pagamentoInscricao.setDescricaoPagamento(PagamentoInscricaoUtils.montaDescricaoPagamento(transaction, false));
        pagamentoInscricao.setDescricaoPagamentoQtip(PagamentoInscricaoUtils.montaDescricaoPagamento(transaction, true));
        pagamentoInscricao.setValor(transaction.getGrossAmount());
        pagamentoInscricaoDao.saveOrUpdate(pagamentoInscricao);

        inscricao.setPagamento(pagamentoInscricao);
        if (PagamentoInscricaoUtils.transacaoStatusPaga(transaction)) {
            inscricao.setStatus(Inscricao.Status.PAGA);
        }
        inscricaoDao.saveOrUpdate(inscricao);
        return pagamentoInscricao;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute("command") final PagamentoInscricao command, final ModelMap model, HttpServletRequest request) {
        List<Item> produtos = new ArrayList<>();
        produtos.add(PagamentoInscricaoUtils.montaInscricaoItemPagSeguro(command.getInscricao()));
        Collection<CamisetaConfraternista> camisetas = command.getInscricao().getConfraternista().getCamisetas();
        if (!CollectionUtils.isEmptyOrNull(camisetas)) {
            for (CamisetaConfraternista camiseta : camisetas) {
                produtos.add(PagamentoInscricaoUtils.montaCamisetaItemPagSeguro(camiseta, command.getInscricao().getEdicaoEvento().getValorCamiseta()));
            }
        }
        model.addAttribute("itens", produtos);
        Locale locale = new Locale("br");
        model.addAttribute("message", getMessage("payment.success.save", locale));
        ControllerUtils.sendMail(command.getInscricao(), getMessage("mail.subscription.payment.receive", locale), "recebimentoPagamentoPS.html");

        return "user/pagamentoSuccessPS";
    }
}
