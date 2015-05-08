/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.EstadoDao;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.web.admin.validation.EstadoValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import java.util.Collection;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller do formulário de edição de Cidades.
 *
 * @author Alexander Fiabane do Rego (alexander.fiabane@gmail.com)
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Controller
@RequestMapping(value = "/admin/formEstado.html")
public class FormEstadoController extends AbstractFormController<Long, Estado> {

    @Autowired
    private EstadoDao estadoDao;
    @Autowired
    private EstadoValidator validator;

    @Override
    protected EstadoValidator getValidator() {
        return validator;
    }

    @Override
    protected EstadoDao getCommandService() {
        return estadoDao;
    }

    /**
     * Adiciona a lista de Estados ao referenceData, para popular a tabela.
     */
    @ModelAttribute("listEstados")
    public Collection<Estado> listEstados() {
        return estadoDao.findAll();
    }

    @ModelAttribute(COMMAND_NAME)
    public Estado getCommand(@RequestParam(value = "idEstado", required = false) final String idEstado) {
        final Estado estado;
        if (CharSequenceUtils.isNumber(idEstado)) {
            estado = getCommandService().findById(NumberUtils.parseLong(idEstado));
        } else {
            estado = new Estado();
        }
        return estado;
    }

    /**
     * Cria um novo objeto 'command', que será populado pelo form.
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(final ModelMap model) {
        //return form view
        return "admin/formEstado";
    }

    /**
     * Processa a submissão do form.
     * @param command
     * @param result
     * @param attributes
     * @param status
     * @param locale
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final Estado command,
                         final BindingResult result,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {

        // validate data
        if (runValidator(command, result).hasErrors()) {
            return "admin/formEstado";
        }
        estadoDao.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        // clear the command object from the session and return form success view
        status.setComplete();
        return "redirect:/admin/formEstado.html?idEstado=" + command.getId();
    }
}
