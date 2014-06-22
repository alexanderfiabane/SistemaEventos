package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.web.admin.validation.CorCamisetaValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.core.model.CorCamiseta;
import br.esp.sysevent.core.service.CorCamisetaService;
import br.ojimarcius.commons.persistence.service.EntityService;
import br.ojimarcius.commons.persistence.springframework.validation.Validator;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.util.Collection;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Giuliano
 */
@Controller
@RequestMapping(value = "/admin/formCorCamiseta.html")
public class FormCorCamiseta extends AbstractFormController<Long, CorCamiseta> {

    @Autowired
    private CorCamisetaValidator validator;
    
    @Autowired
    private CorCamisetaService corCamisetaService;
    
    @Override
    protected Validator<CorCamiseta> getValidator() {
        return validator;
    }

    @Override
    protected <S extends EntityService<Long, CorCamiseta>> S getCommandService() {
        return (S) corCamisetaService;
    }
    
    @ModelAttribute(COMMAND_NAME)
    public CorCamiseta getCommand(@RequestParam(value = "idCor", required = false) final String idCor) {
        final CorCamiseta command;
        if (CharSequenceUtils.isNumber(idCor)) {
            command = corCamisetaService.findById(NumberUtils.parseLong(idCor));
        } else {
            command = new CorCamiseta();
        }
        return command;
    }
    
    @ModelAttribute("coresCamiseta")
    public Collection<CorCamiseta> getCoresCamiseta() {
        return corCamisetaService.findAll();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String onGet() {
        //return form view
        return "admin/formCorCamiseta";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final CorCamiseta command,
                         final BindingResult result,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {

        // validate data
        if (runValidator(command, result).hasErrors()) {
            return "admin/formCorCamiseta";
        }
        corCamisetaService.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        // clear the command object from the session and return form success view
        status.setComplete();
        return "redirect:/admin/formCorCamiseta.html";
    }
    
}
