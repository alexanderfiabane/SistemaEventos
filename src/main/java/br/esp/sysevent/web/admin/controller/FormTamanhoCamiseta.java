package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.TamanhoCamisetaDao;
import br.esp.sysevent.core.model.TamanhoCamiseta;
import br.esp.sysevent.web.admin.validation.TamanhoCamisetaValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.persistence.springframework.validation.Validator;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
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
    private TamanhoCamisetaDao tamanhoCamisetaDao;

    @Override
    protected Validator<TamanhoCamiseta> getValidator() {
        return validator;
    }

    @Override
    protected TamanhoCamisetaDao getCommandService() {
        return tamanhoCamisetaDao;
    }

    @ModelAttribute(COMMAND_NAME)
    public TamanhoCamiseta getCommand(@RequestParam(value = "idTamanho", required = false) final String idTamanho) {
        final TamanhoCamiseta command;
        if (CharSequenceUtils.isNumber(idTamanho)) {
            command = tamanhoCamisetaDao.findById(NumberUtils.parseLong(idTamanho));
        } else {
            command = new TamanhoCamiseta();
        }
        return command;
    }

    @ModelAttribute("tamanhosCamiseta")
    public Collection<TamanhoCamiseta> getTamanhosCamiseta() {
        return tamanhoCamisetaDao.findAll();
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
        tamanhoCamisetaDao.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        // clear the command object from the session and return form success view
        status.setComplete();
        return "redirect:/admin/formTamanhoCamiseta.html";
    }

}
