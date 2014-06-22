/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.web.admin.validation.EventoValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.service.EventoService;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
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
 *
 * @author mfonseca
 */
@Controller
@RequestMapping("/admin/formEvento.html")
public class FormEventoController extends AbstractFormController<Long, Evento> {

    @Autowired
    private EventoService eventoService;
    @Autowired
    private EventoValidator validator;

    @Override
    protected EventoValidator getValidator() {
        return validator;
    }

    @Override
    protected EventoService getCommandService() {
        return eventoService;
    }

    /* Eventos ja existentes em banco */
    @ModelAttribute("eventos")
    public Collection<Evento> getEventos() {
        return eventoService.findAll();
    }

    /**
     * Cria um novo objeto 'command', que será populado pelo form.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@RequestParam(value = "idEvento", required = false) final String idEvento, final ModelMap model) {
        final Evento evento;
        if (CharSequenceUtils.isNumber(idEvento)) {
            evento = eventoService.findById(NumberUtils.parseLong(idEvento));
        } else {
            evento = new Evento();
        }
        // add command object
        model.addAttribute(COMMAND_NAME, evento);
        //return form view
        return "admin/formEvento";
    }

    /**
     * Processa a submissão do form.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final Evento command,
                         final BindingResult result,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {

        // validate data
        if (runValidator(command, result).hasErrors()) {
            return "admin/formEvento";
        }
        eventoService.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        // clear the command object from the session and return form success view
        status.setComplete();
        return "redirect:/admin/formEvento.html?idEvento=" + command.getId();
    }
}
