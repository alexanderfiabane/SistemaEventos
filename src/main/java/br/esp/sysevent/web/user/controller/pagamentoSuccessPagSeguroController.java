/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.user.controller;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.enums.PaymentMethodType;
import br.com.uol.pagseguro.enums.TransactionStatus;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.service.TransactionSearchService;
import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.dao.PagamentoInscricaoDao;
import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.PagSeguroConta;
import br.esp.sysevent.core.model.PagamentoInscricao;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import com.javaleks.commons.text.EnhancedStringBuilder;
import com.javaleks.commons.util.CalendarUtils;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.CollectionUtils;
import com.javaleks.commons.util.NumberUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
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
public class pagamentoSuccessPagSeguroController extends PagamentoFormController{

    @Autowired
    private InscricaoDao inscricaoDao;
    @Autowired
    private PagamentoInscricaoDao pagamentoInscricaoDao;

    @ModelAttribute("command")
    public PagamentoInscricao getCommand(@RequestParam(value = "codTransactionPagSeguro", required = true) final String codTransactionPagSeguro,
            @RequestParam(value = "idInscricao", required = true) final String idInscricao) throws PagSeguroServiceException{
        if(!CharSequenceUtils.isNumber(idInscricao)){
            throw new IllegalStateException("Inscrição inválida");
        }
        Inscricao inscricao = inscricaoDao.findById(NumberUtils.parseLong(idInscricao));
        PagamentoInscricao pagamentoInscricao = new PagamentoInscricao();
        pagamentoInscricao.setInscricao(inscricao);
        pagamentoInscricao.setCodPagamento(codTransactionPagSeguro);
        PagSeguroConta pagSeguroAccount = inscricao.getEdicaoEvento().getFormaCobranca().getPagSeguro();
        AccountCredentials pagSeguroCredentials = new AccountCredentials(
                pagSeguroAccount.getEmailPagSeguro(),
                pagSeguroAccount.getTokenSegurancaSandBox(),
                pagSeguroAccount.getTokenSegurancaSandBox());
        Transaction transaction = TransactionSearchService.searchByCode(pagSeguroCredentials,codTransactionPagSeguro);
        pagamentoInscricao.setDataPagamento(CalendarUtils.castToCalendar(transaction.getDate()));
        pagamentoInscricao.setDescricaoPagamento(montaDescricaoPagamento(transaction));
        pagamentoInscricao.setValor(transaction.getGrossAmount());
        pagamentoInscricaoDao.saveOrUpdate(pagamentoInscricao);
        if (transacaoStatusPaga(transaction)){
            inscricao.setStatus(Inscricao.Status.PAGA);
            inscricaoDao.saveOrUpdate(inscricao);
        }
        return pagamentoInscricao;
    }
    @Override
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute("command") final PagamentoInscricao command, final ModelMap model){
        List<Item> produtos = new ArrayList<>();
        produtos.add(montaInscricaoItemPagSeguro(command.getInscricao()));
        Collection<CamisetaConfraternista> camisetas = command.getInscricao().getConfraternista().getCamisetas();
        if (!CollectionUtils.isEmptyOrNull(camisetas)) {
            for (CamisetaConfraternista camiseta : camisetas) {
                produtos.add(montaCamisetaItemPagSeguro(camiseta, command.getInscricao().getEdicaoEvento().getValorCamiseta()));
            }
        }
        model.addAttribute("produtos", produtos);
        Locale locale = new Locale("br");
        model.addAttribute("message", getMessage("payment.success.save", locale));
        ControllerUtils.sendMail(command.getInscricao(), getMessage("mail.subscription.payment.receive", locale), "recebimentoPagamentoPS.html");

        return "user/pagamentoSuccessPS";
    }

    protected String montaDescricaoPagamento(Transaction transaction){
        EnhancedStringBuilder descricaoPagamento = new EnhancedStringBuilder();
        descricaoPagamento.setLineBreakMode(EnhancedStringBuilder.LineBreakMode.HTML);
        descricaoPagamento.append("<label class='label'>Meio de pagmento</label>: ")
                .append(transaction.getPaymentMethod().getType().getType())
                .append("( ").append(transaction.getPaymentMethod().getCode().getDescription());
        if (transaction.getPaymentMethod().getType().equals(PaymentMethodType.CREDIT_CARD)){
            descricaoPagamento.append(" ").append(Integer.toString(transaction.getInstallmentCount())).appendln("x )");
        }else{
            descricaoPagamento.appendln(" )");
        }
        descricaoPagamento.append("<label class='label'>Valor pago em taxas</label>: ")
                .append(" R$ ").appendln(transaction.getFeeAmount().toString());
        descricaoPagamento.append("<label class='label'>Valor pago</label>: ")
                .append(" R$ ").append(transaction.getGrossAmount().toString());
        return descricaoPagamento.toString();
    }

    protected boolean transacaoStatusPaga(Transaction transaction) {
        return transaction.getStatus().equals(TransactionStatus.PAID);
    }
}
