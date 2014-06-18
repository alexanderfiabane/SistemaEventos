package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.web.admin.validation.TamanhoCamisetaValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.core.model.TamanhoCamiseta;
import br.esp.sysevent.core.service.TamanhoCamisetaService;
import br.msf.commons.persistence.service.EntityService;
import br.msf.commons.persistence.springframework.validation.Validator;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.NumberUtils;
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
@RequestMapping(value = "/admin/formTamanhoCamiseta.html")
public class FormTamanhoCamiseta extends AbstractFormController<Long, TamanhoCamiseta> {

    @Autowired
    private TamanhoCamisetaValidator validator;
    
    @Autowired
    private TamanhoCamisetaService tamanhoCamisetaService;
    
    @Override
    protected Validator<TamanhoCamiseta> getValidator() {
        return validator;
    }

    @Override
    protected <S extends EntityService<Long, TamanhoCamiseta>> S getCommandService() {
        return (S) tamanhoCamisetaService;
    }
    
    @ModelAttribute(COMMAND_NAME)
    public TamanhoCamiseta getCommand(@RequestParam(value = "idTamanho", required = false) final String idTamanho) {
        final TamanhoCamiseta command;
        if (CharSequenceUtils.isNumber(idTamanho)) {
            command = tamanhoCamisetaService.findById(NumberUtils.parseLong(idTamanho));
        } else {
            command = new TamanhoCamiseta();
        }
        return command;
    }
    
    @ModelAttribute("tamanhosCamiseta")
    public Collection<TamanhoCamiseta> getTamanhosCamiseta() {
        return tamanhoCamisetaService.findAll();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String onGet() {
        //return form view
        return "admin/formTamanhoCamiseta";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final TamanhoCamiseta command,
                         final BindingResult result,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {

        // validate data
        if (runValidator(command, result).hasErrors()) {
            return "admin/formTamanhoCamiseta";
        }
        tamanhoCamisetaService.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        // clear the command object from the session and return form success view
        status.setComplete();
        return "redirect:/admin/formTamanhoCamiseta.html";
    }
    
}
