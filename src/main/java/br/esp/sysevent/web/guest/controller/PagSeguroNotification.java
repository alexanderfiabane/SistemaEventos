/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.guest.controller;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.enums.TransactionStatus;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.service.NotificationService;
import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.dao.PagamentoInscricaoDao;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.PagSeguroConta;
import br.esp.sysevent.core.model.PagamentoInscricao;
import br.esp.sysevent.util.PagamentoInscricaoUtils;
import com.javaleks.commons.util.CalendarUtils;
import com.javaleks.commons.util.NumberUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@Controller
@RequestMapping(value = "/guest/pagSeguroNotification.html")
public class PagSeguroNotification {

    @Autowired
    private EdicaoDao edicaoDao;
    @Autowired
    private PagamentoInscricaoDao pagamentoInscricaoDao;
    @Autowired
    private InscricaoDao inscricaoDao;

    @RequestMapping(method = RequestMethod.POST)
    public void onPost(@RequestParam(value = "idEdicao", required = false) final String idEdicao, HttpServletRequest request, HttpServletResponse response) throws PagSeguroServiceException {
        //response.addHeader("Access-Control-Allow-Origin", "https://sandbox.pagseguro.uol.com.br");
        String notificationCod = (String) request.getParameter("notificationCode");
        String notificationType = (String) request.getParameter("notificationType");
        if (notificationType.equals("transaction")){
            Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
            PagSeguroConta pagSeguroAccount = edicao.getFormaCobranca().getPagSeguro();
            AccountCredentials pagSeguroCredentials = new AccountCredentials(
                    pagSeguroAccount.getEmailPagSeguro(),
                    pagSeguroAccount.getTokenSegurancaSandBox(),
                    pagSeguroAccount.getTokenSegurancaSandBox());
            Transaction transaction = NotificationService.checkTransaction(pagSeguroCredentials, notificationCod);
            PagamentoInscricao pagamentoInscricao = pagamentoInscricaoDao.findByCodPagamento(transaction.getCode());
            TransactionStatus status = transaction.getStatus();
            if(status.equals(TransactionStatus.PAID)){
                Inscricao inscricao = pagamentoInscricao.getInscricao();
                pagamentoInscricao.setDataPagamento(CalendarUtils.castToCalendar(transaction.getDate()));
                pagamentoInscricao.setDescricaoPagamento(PagamentoInscricaoUtils.montaDescricaoPagamento(transaction, false));
                pagamentoInscricao.setDescricaoPagamentoQtip(PagamentoInscricaoUtils.montaDescricaoPagamento(transaction, true));
                pagamentoInscricaoDao.saveOrUpdate(pagamentoInscricao);
                inscricao.setStatus(Inscricao.Status.PAGA);
                inscricaoDao.saveOrUpdate(inscricao);
            }
        }
    }
}
