package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.web.admin.validation.TipoCamisetaValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.core.model.TipoCamiseta;
import br.esp.sysevent.core.service.TipoCamisetaService;
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
@RequestMapping(value = "/admin/formTipoCamiseta.html")
public class FormTipoCamiseta extends AbstractFormController<Long, TipoCamiseta> {

    @Autowired
    private TipoCamisetaValidator validator;
    
    @Autowired
    private TipoCamisetaService tipoCamisetaService;
    
    @Override
    protected Validator<TipoCamiseta> getValidator() {
        return validator;
    }

    @Override
    protected <S extends EntityService<Long, TipoCamiseta>> S getCommandService() {
        return (S) tipoCamisetaService;
    }
    
    @ModelAttribute(COMMAND_NAME)
    public TipoCamiseta getCommand(@RequestParam(value = "idTipo", required = false) final String idTipo) {
        final TipoCamiseta command;
        if (CharSequenceUtils.isNumber(idTipo)) {
            command = tipoCamisetaService.findById(NumberUtils.parseLong(idTipo));
        } else {
            command = new TipoCamiseta();
        }
        return command;
    }
    
    @ModelAttribute("tiposCamiseta")
    public Collection<TipoCamiseta> getTiposCamiseta() {
        return tipoCamisetaService.findAll();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String onGet() {
        //return form view
        return "admin/formTipoCamiseta";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final TipoCamiseta command,
                         final BindingResult result,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {

        // validate data
        if (runValidator(command, result).hasErrors()) {
            return "admin/formTipoCamiseta";
        }
        tipoCamisetaService.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        // clear the command object from the session and return form success view
        status.setComplete();
        return "redirect:/admin/formTipoCamiseta.html";
    }
    
}
