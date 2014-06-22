/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.web.admin.validation.CidadeValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.core.service.CidadeService;
import br.esp.sysevent.core.service.EstadoService;
import br.ojimarcius.commons.persistence.springframework.beans.propertyeditors.CustomEntityEditor;
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
 * Controller do formulário de edição de Cidades.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Controller
@RequestMapping(value = "/admin/formCidade.html")
public class FormCidadeController extends AbstractFormController<Long, Cidade> {

    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private EstadoService estadoService;
    @Autowired
    private CidadeValidator validator;

    @Override
    protected CidadeValidator getValidator() {
        return validator;
    }

    @Override
    protected CidadeService getCommandService() {
        return cidadeService;
    }

    /* Disponibiliza a lista de estados para o select do form */
    @ModelAttribute("estados")
    public Collection<Estado> getEstados() {
        return estadoService.findAll();
    }

//    /* Disponibiliza a lista de cidades ja existentes em banco */
//    @ModelAttribute("cidades")
//    public Collection<Cidade> getCidades(final ModelMap model) {
//        final Estado estado = ((Cidade) model.get(COMMAND_NAME)).getEstado();
//        if (estado != null) {
//            return cidadeService.findByProperty("estado", estado);
//        }
//        return cidadeService.findAll();
//    }
    @ModelAttribute(COMMAND_NAME)
    public Cidade getCommand(@RequestParam(value = "idCidade", required = false) final String idCidade,
            @RequestParam(value = "idEstado", required = false) final String idEstado) {
        final Cidade cidade;
        if (CharSequenceUtils.isNumber(idCidade)) {
            cidade = getCommandService().findById(NumberUtils.parseLong(idCidade));
        } else {
            cidade = new Cidade();
            if (CharSequenceUtils.isNumber(idEstado)) {
                // se nao tiver idEstado, o usuario seleciona no combo...
                cidade.setEstado(estadoService.findById(NumberUtils.parseLong(idEstado)));
            }
        }
        return cidade;
    }

    /* Registra os binder do spring, para tipos de dados complexos como datas e entidades */
    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Estado.class, new CustomEntityEditor(estadoService));
    }

    /**
     * Cria um novo objeto 'command', que será populado pelo form.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final Cidade command, final ModelMap model) {
        final Estado estado = command.getEstado();
        if (estado != null) {
            model.addAttribute("cidades", cidadeService.findByProperty("estado", command.getEstado()));
        }else{
            model.addAttribute("cidades", cidadeService.findAll());
        }
        return "admin/formCidade";
    }

    /**
     * Processa a submissão do form.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final Cidade command,
            final BindingResult result,
            final RedirectAttributes attributes,
            final SessionStatus status,
            final Locale locale) {
        // validate data
        if (runValidator(command, result).hasErrors()) {
            return "admin/formCidade";
        }
        cidadeService.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        status.setComplete();
        return "redirect:/admin/formCidade.html?idCidade=" + command.getId();
    }
}
