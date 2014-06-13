/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.web.admin.validation.NoticiaValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.core.model.Noticia;
import br.esp.sysevent.core.service.NoticiaService;
import br.msf.commons.persistence.springframework.beans.propertyeditors.CustomCalendarEditor;
import br.msf.commons.persistence.springframework.validation.Validator;
import br.msf.commons.util.NumberUtils;
import java.util.Calendar;
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
 * @author Alexander
 */
@Controller
@RequestMapping(value = "/admin/formNoticia.html")
public class FormNoticiaController extends AbstractFormController<Long, Noticia> {

    @Autowired
    private NoticiaService noticiaService;
    @Autowired
    private NoticiaValidator validator;

    @Override
    protected Validator<Noticia> getValidator() {
        return validator;
    }

    @Override
    protected NoticiaService getCommandService() {
        return noticiaService;
    }

    @ModelAttribute(COMMAND_NAME)
    public Noticia getCommand(@RequestParam(value = "idNoticia", required = false) final String idNoticia) {
        final Noticia noticia;
        if (NumberUtils.isNumber(idNoticia)) {
            noticia = noticiaService.findById(NumberUtils.parseLong(idNoticia));
        } else {
            noticia = new Noticia();
        }
        return noticia;
    }

    /* notícias para a listagem */
    @ModelAttribute("noticias")
    public Collection<Noticia> getNoticias() {
        return noticiaService.findAll();
    }

    @InitBinder
    protected void initBinder(final WebDataBinder binder, final Locale locale) {
        binder.registerCustomEditor(Calendar.class, new CustomCalendarEditor(getDateFormat(locale), true));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(final ModelMap model) {
        //return form view
        return "admin/formNoticia";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final Noticia command,
                         final BindingResult result,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {
        // validate data
        if (runValidator(command, result).hasErrors()) {
            return "admin/formNoticia";
        }
        noticiaService.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        status.setComplete();
        return "redirect:/admin/formNoticia.html?idNoticia=" + command.getId();
    }
}
