/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.web.admin.validation.GrupoIdadeValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.service.EdicaoService;
import br.esp.sysevent.core.service.GrupoIdadeService;
import br.ojimarcius.commons.persistence.springframework.validation.Validator;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
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
 * @author Fiabane
 */
@Controller
@RequestMapping(value = "/admin/formGrupoIdade.html")
public class FormGrupoIdadeController extends AbstractFormController<Long, GrupoIdade> {

    @Autowired
    private EdicaoService edicaoService;
    @Autowired
    private GrupoIdadeService grupoIdadeService;
    @Autowired
    private GrupoIdadeValidator validator;
    
    @Override
    protected Validator<GrupoIdade> getValidator() {
        return validator;
    }

    @Override
    protected GrupoIdadeService getCommandService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @ModelAttribute(COMMAND_NAME)
    public GrupoIdade getCommand(@RequestParam(value = "idGrupoIdade", required = false) final String idGrupoIdade,
                              @RequestParam(value = "idEdicao", required = false) final String idEdicao) {
        final GrupoIdade grupoIdade;
        if (CharSequenceUtils.isNumber(idGrupoIdade)) {
            grupoIdade = grupoIdadeService.findById(NumberUtils.parseLong(idGrupoIdade));
        } else if (CharSequenceUtils.isNumber(idEdicao)) {
            final Edicao edicao = edicaoService.findById(NumberUtils.parseLong(idEdicao));
            if (edicao == null) {
                throw new IllegalArgumentException("Edição não encontrada");
            }
            grupoIdade = new GrupoIdade();
            grupoIdade.setEdicaoEvento(edicao);
            grupoIdade.setVagasOcupadas(0);
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }
        return grupoIdade;
    }
    
    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final GrupoIdade command, final ModelMap model) {
        model.addAttribute("gruposIdade", grupoIdadeService.findByProperty("edicaoEvento", command.getEdicaoEvento()));
        //return form view
        return "admin/formGrupoIdade";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final GrupoIdade command,
                         final BindingResult result,
                         final ModelMap model,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {
        // validate data
        if (runValidator(command, result).hasErrors()) {
            return onGet(command, model);
        }
        grupoIdadeService.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        status.setComplete();
        return "redirect:/admin/formGrupoIdade.html?idEdicao=" + command.getEdicaoEvento().getId();
    }
    
}
