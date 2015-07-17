/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.user.controller;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Address;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.checkout.Checkout;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.ShippingType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.dao.PagamentoInscricaoDao;
import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Endereco;
import br.esp.sysevent.core.model.FormaCobranca;
import br.esp.sysevent.core.model.FormaCobranca.TipoCobranca;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.PagSeguroConta;
import br.esp.sysevent.core.model.PagamentoInscricao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.persistence.springframework.beans.propertyeditors.CustomCalendarEditor;
import br.esp.sysevent.persistence.springframework.validation.Validator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.web.user.validation.PagamentoInscricaoValidator;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.CollectionUtils;
import com.javaleks.commons.util.NumberUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Controller
@RequestMapping(value = "/user/formPagamento.html")
public class PagamentoFormController extends AbstractFormController<Long, PagamentoInscricao> {

    @Autowired
    private InscricaoDao inscricaoDao;
    @Autowired
    private PagamentoInscricaoDao pagamentoInscricaoDao;
    @Autowired
    private PagamentoInscricaoValidator validator;

    @Override
    protected Validator<PagamentoInscricao> getValidator() {
        return validator;
    }

    @Override
    protected PagamentoInscricaoDao getCommandService() {
        return pagamentoInscricaoDao;
    }

    @ModelAttribute(COMMAND_NAME)
    public PagamentoInscricao getCommand(@RequestParam(value = "idInscricao", required = false) final String idInscricao) throws Exception {
        //Refazer pegando o pagamento...deve ser salvo no ato da inscricao..modificar metodo de atualização da inscricao.
        final Inscricao inscricao = getInscricao(idInscricao);
        if (inscricao.getStatus() != Inscricao.Status.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Pagamento não está liberado para esta inscrição");
        }
        final PagamentoInscricao novoPagamento = new PagamentoInscricao();
        novoPagamento.setInscricao(inscricao);
        return novoPagamento;
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder, final Locale locale) {
        binder.registerCustomEditor(Calendar.class, new CustomCalendarEditor(getDateFormat(locale), true));
        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, getNumberFormat(locale), true));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final PagamentoInscricao command, final ModelMap model) throws PagSeguroServiceException {
        FormaCobranca.TipoCobranca tipoCobranca = command.getInscricao().getEdicaoEvento().getFormaCobranca().getTipoCobranca();
        List<Item> produtos = new ArrayList<>();
        produtos.add(montaInscricaoItemPagSeguro(command.getInscricao()));
        Collection<CamisetaConfraternista> camisetas = command.getInscricao().getConfraternista().getCamisetas();
        if (!CollectionUtils.isEmptyOrNull(camisetas)) {
            for (CamisetaConfraternista camiseta : camisetas) {
                produtos.add(montaCamisetaItemPagSeguro(camiseta, command.getInscricao().getEdicaoEvento().getValorCamiseta()));
            }
        }
        model.addAttribute("produtos", produtos);
        if (tipoCobranca.equals(TipoCobranca.PAGSEGURO)) {
            //Criar objeto pagseguro com os dados de 'compra' dessa inscrição
            Checkout pagseguro = new Checkout();
            pagseguro.setCurrency(Currency.BRL);
            pagseguro.setItems(produtos);
            pagseguro.setShippingAddress(montaAddressPagSeguro(command.getInscricao()));
            pagseguro.setShippingType(ShippingType.NOT_SPECIFIED);
            pagseguro.setReference("SE" + command.getInscricao().getId());
            final Confraternista confraternista = command.getInscricao().getConfraternista();
            pagseguro.setSender(confraternista.getPessoa().getNome(), confraternista.getPessoa().getEndereco().getEmail());

            //Gerar número para o botão lightbox e colocar no model
            PagSeguroConta pagSeguroAccount = command.getInscricao().getEdicaoEvento().getFormaCobranca().getPagSeguro();
            AccountCredentials pagSeguroCredentials = new AccountCredentials(
                    pagSeguroAccount.getEmailPagSeguro(),
                    pagSeguroAccount.getTokenSegurancaSandBox(),
                    pagSeguroAccount.getTokenSegurancaSandBox());

            String lightBoxCode = pagseguro.register(pagSeguroCredentials, true);
            model.addAttribute("pagseguroCod", lightBoxCode);

            return "user/formPagamentoPS";
        } else {
            //retorna view de pagamento por depósito em conta
            return "user/formPagamentoDC";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final PagamentoInscricao command,
            final BindingResult result,
            final ModelMap model,
            final RedirectAttributes attributes,
            final SessionStatus status,
            final Locale locale) throws PagSeguroServiceException {

        if (runValidator(command, result).hasErrors()) {
            return onGet(command, model);
        }
        pagamentoInscricaoDao.save(command);
        model.addAttribute("message", getMessage("message.success.save", locale));

        // clear the command object from the session and return form success view
        status.setComplete();
        return "user/pagamentoSuccess";
    }

    protected Inscricao getInscricao(final String idInscricao) throws IllegalArgumentException, IllegalAccessException {
        if (!CharSequenceUtils.isNumber(idInscricao)) {
            throw new IllegalArgumentException("Parâmetros inválidos.");
        }
        final Inscricao inscricao = inscricaoDao.findById(NumberUtils.parseLong(idInscricao));
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscrição não encontrada.");
        }
        final Usuario loggedUser = ControllerUtils.getLoggedUser();
        if (!loggedUser.getPessoa().getId().equals(inscricao.getConfraternista().getPessoa().getId())) {
            throw new IllegalAccessException("Acesso negado a informações de outra pessoa");
        }
        return inscricao;
    }

    protected Item montaInscricaoItemPagSeguro(Inscricao inscricao) {
        Item item = new Item(
                Long.toString(inscricao.getId()),
                "Inscrição" + " - " + inscricao.getEdicaoEvento().getEvento().getNome(),
                1,
                inscricao.getEdicaoEvento().getValorInscricao(),
                new Long(1),
                new BigDecimal("0.00"));
        return item;
    }

    protected Item montaCamisetaItemPagSeguro(CamisetaConfraternista camiseta, BigDecimal valorCamiseta) {
        Item item = new Item(
                Long.toString(camiseta.getId()),
                camiseta.getTipoCamiseta().getDescricao() + " - " + camiseta.getCorCamiseta().getDescricao() + " - " + camiseta.getTamanhoCamiseta().getDescricao(),
                camiseta.getQuantidadeCamiseta(),
                valorCamiseta,
                new Long(150),
                new BigDecimal("0.00")
        );
        return item;
    }

    protected Address montaAddressPagSeguro(Inscricao inscricao) {
        Endereco endereco = inscricao.getConfraternista().getPessoa().getEndereco();
        Cidade cidade = endereco.getCidade();
        Address address = new Address("BRA", // País
                cidade.getEstado().getSigla(), // UF
                cidade.getNome(), // Cidade
                endereco.getBairro(), // Bairro
                endereco.getCep(), // CEP
                endereco.getLogradouro(), // Logradouro
                endereco.getNumero(), // Número
                endereco.getComplemento() // Complemento
        );
        return address;
    }
}
