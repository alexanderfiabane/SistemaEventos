/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.NoticiaDao;
import br.esp.sysevent.core.model.Noticia;
import br.esp.sysevent.web.admin.validation.NoticiaValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.persistence.springframework.beans.propertyeditors.CustomCalendarEditor;
import br.esp.sysevent.persistence.springframework.validation.Validator;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
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
    private NoticiaDao noticiaDao;
    @Autowired
    private NoticiaValidator validator;

    @Override
    protected Validator<Noticia> getValidator() {
        return validator;
    }

    @Override
    protected NoticiaDao getCommandService() {
        return noticiaDao;
    }

    @ModelAttribute(COMMAND_NAME)
    public Noticia getCommand(@RequestParam(value = "idNoticia", required = false) final String idNoticia) {
        final Noticia noticia;
        if (CharSequenceUtils.isNumber(idNoticia)) {
            noticia = noticiaDao.findById(NumberUtils.parseLong(idNoticia));
        } else {
            noticia = new Noticia();
        }
        return noticia;
    }

    /* notícias para a listagem */
    @ModelAttribute("noticias")
    public Collection<Noticia> getNoticias() {
        return noticiaDao.findAll();
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
        noticiaDao.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        status.setComplete();
        return "redirect:/admin/formNoticia.html?idNoticia=" + command.getId();
    }
}
