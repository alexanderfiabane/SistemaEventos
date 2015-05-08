/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.dao.OficinaDao;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.web.admin.validation.OficinaValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.persistence.springframework.validation.Validator;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/admin/formOficina.html")
public class FormOficinaController extends AbstractFormController<Long, Oficina> {

    @Autowired
    private EdicaoDao edicaoDao;
    @Autowired
    private OficinaDao oficinaDao;
    @Autowired
    private OficinaValidator validator;

    @Override
    protected Validator<Oficina> getValidator() {
        return validator;
    }

    @Override
    protected OficinaDao getCommandService() {
        return oficinaDao;
    }

    @ModelAttribute(COMMAND_NAME)
    public Oficina getCommand(@RequestParam(value = "idOficina", required = false) final String idOficina,
                              @RequestParam(value = "idEdicao", required = false) final String idEdicao) {
        final Oficina oficina;
        if (CharSequenceUtils.isNumber(idOficina)) {
            oficina = oficinaDao.findById(NumberUtils.parseLong(idOficina));
        } else if (CharSequenceUtils.isNumber(idEdicao)) {
            final Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
            if (edicao == null) {
                throw new IllegalArgumentException("Edição não encontrada");
            }
            oficina = new Oficina();
            oficina.setEdicaoEvento(edicao);
            oficina.setVagasOcupadas(0);
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }
        return oficina;
    }

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final Oficina command, final ModelMap model) {
        model.addAttribute("oficinas", oficinaDao.findByProperty("edicaoEvento", command.getEdicaoEvento()));
        //return form view
        return "admin/formOficina";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final Oficina command,
                         final BindingResult result,
                         final ModelMap model,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {
        // validate data
        if (runValidator(command, result).hasErrors()) {
            return onGet(command, model);
        }
        oficinaDao.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        status.setComplete();
        return "redirect:/admin/formOficina.html?idEdicao=" + command.getEdicaoEvento().getId();
    }
}
