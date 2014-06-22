/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.web.admin.validation.DormitorioValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.service.ConfraternistaService;
import br.esp.sysevent.core.service.DormitorioService;
import br.esp.sysevent.core.service.EdicaoService;
import br.ojimarcius.commons.persistence.springframework.beans.propertyeditors.CustomEntityEditor;
import br.ojimarcius.commons.persistence.springframework.validation.Validator;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.util.Collection;
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
@RequestMapping(value = "/admin/formDormitorio.html")
public class FormDormitorioController extends AbstractFormController<Long, Dormitorio> {

    @Autowired
    private EdicaoService edicaoService;
    @Autowired
    private ConfraternistaService confraternistaService;
    @Autowired
    private DormitorioService dormitorioService;
    @Autowired
    private DormitorioValidator validator;

    @Override
    protected Validator<Dormitorio> getValidator() {
        return validator;
    }

    @Override
    protected DormitorioService getCommandService() {
        return dormitorioService;
    }

    @ModelAttribute(COMMAND_NAME)
    public Dormitorio getCommand(@RequestParam(value = "idDormitorio", required = false) final String idDormitorio,
            @RequestParam(value = "idEdicao", required = false) final String idEdicao) {
        final Dormitorio dormitorio;
        if (CharSequenceUtils.isNumber(idDormitorio)) {
            dormitorio = dormitorioService.findById(NumberUtils.parseLong(idDormitorio));
        } else if (CharSequenceUtils.isNumber(idEdicao)) {
            final Edicao edicao = edicaoService.findById(NumberUtils.parseLong(idEdicao));
            if (edicao == null) {
                throw new IllegalArgumentException("Edição não encontrada");
            }
            dormitorio = new Dormitorio();
            dormitorio.setEdicaoEvento(edicao);
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }
        return dormitorio;
    }

    /* sexos para o select do form */
    @ModelAttribute("sexos")
    public Collection<Sexo> getSexos() {
        return Sexo.getValues();
    }

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Confraternista.class, new CustomEntityEditor<Confraternista>(confraternistaService));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final Dormitorio command, final ModelMap model) {
        model.addAttribute("dormitorios", dormitorioService.findByProperty("edicaoEvento", command.getEdicaoEvento()));
        //return form view
        return "admin/formDormitorio";
    }

    /**
     * Processa a submissão do form.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final Dormitorio command,
            final BindingResult result,
            final ModelMap model,
            final RedirectAttributes attributes,
            final SessionStatus status,
            final Locale locale) {
        // validate data
        if (runValidator(command, result).hasErrors()) {
            return onGet(command, model);
        }
//        Confraternista coordenador = command.getCoordenador();
//        coordenador = confraternistaService.findById(coordenador.getId());
//        coordenador.setDormitorio(command);
//        confraternistaService.saveOrUpdate(coordenador);
//        Confraternista viceCoordenador = command.getViceCoordenador();
//        if (!coordenador.equals(viceCoordenador)) {
//            viceCoordenador = confraternistaService.findById(viceCoordenador.getId());
//            viceCoordenador.setDormitorio(command);
//            confraternistaService.saveOrUpdate(viceCoordenador);
//        }        
        dormitorioService.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        status.setComplete();
        return "redirect:/admin/formDormitorio.html?idEdicao=" + command.getEdicaoEvento().getId();
    }
}
