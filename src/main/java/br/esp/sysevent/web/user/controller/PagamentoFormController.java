/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.user.controller;

import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.PagamentoInscricao;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.core.service.InscricaoService;
import br.esp.sysevent.core.service.PagamentoInscricaoService;
import br.esp.sysevent.web.user.validation.PagamentoInscricaoValidator;
import br.ojimarcius.commons.persistence.springframework.beans.propertyeditors.CustomCalendarEditor;
import br.ojimarcius.commons.persistence.springframework.validation.Validator;
import br.ojimarcius.commons.util.NumberUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
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
public class PagamentoFormController  extends AbstractFormController<Long, PagamentoInscricao> {

    @Autowired
    private InscricaoService inscricaoService;
    @Autowired
    private PagamentoInscricaoService pagamentoInscricaoService;
    @Autowired
    private PagamentoInscricaoValidator validator;

    @Override
    protected Validator<PagamentoInscricao> getValidator() {
        return validator;
    }

    @Override
    protected PagamentoInscricaoService getCommandService() {
        return pagamentoInscricaoService;
    }

    @ModelAttribute(COMMAND_NAME)
    public PagamentoInscricao getCommand(@RequestParam(value = "idInscricao", required = false) final String idInscricao) throws Exception {
        final Inscricao inscricao = getInscricao(idInscricao);
        if(inscricao.getStatus() != Inscricao.Status.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Pagamento não está liberado para esta inscrição");
        }
        final Collection<PagamentoInscricao> pagamentos = pagamentoInscricaoService.findByProperty("inscricao", inscricao);
        if(pagamentos.isEmpty()) {
            final PagamentoInscricao pagamento = new PagamentoInscricao();
            pagamento.setInscricao(inscricao);
            return pagamento;
        } else {
            return pagamentos.iterator().next();
        }
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder, final Locale locale) {
        binder.registerCustomEditor(Calendar.class, new CustomCalendarEditor(getDateFormat(locale), true));
        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, getNumberFormat(locale), true));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final PagamentoInscricao command, final ModelMap model) {
        //return form view
        return "user/formPagamento";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final PagamentoInscricao command,
                         final BindingResult result,
                         final ModelMap model,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {

        if (runValidator(command, result).hasErrors()) {
            return onGet(command, model);
        }
        pagamentoInscricaoService.save(command);
        model.addAttribute("message", getMessage("message.success.save", locale));

        // clear the command object from the session and return form success view
        status.setComplete();
        return "user/pagamentoSuccess";
    }

    protected Inscricao getInscricao(final String idInscricao) throws IllegalArgumentException, IllegalAccessException {
        if (!NumberUtils.isNumber(idInscricao)) {
            throw new IllegalArgumentException("Parâmetros inválidos.");
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
