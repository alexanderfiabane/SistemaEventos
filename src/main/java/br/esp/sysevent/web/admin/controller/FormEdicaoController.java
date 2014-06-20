/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.model.CorCamiseta;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.model.TamanhoCamiseta;
import br.esp.sysevent.core.model.TipoCamiseta;
import br.esp.sysevent.core.service.CorCamisetaService;
import br.esp.sysevent.core.service.EdicaoService;
import br.esp.sysevent.core.service.EventoService;
import br.esp.sysevent.core.service.TamanhoCamisetaService;
import br.esp.sysevent.core.service.TipoCamisetaService;
import br.esp.sysevent.web.admin.validation.EdicaoValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.msf.commons.persistence.springframework.beans.propertyeditors.CustomCalendarEditor;
import br.msf.commons.persistence.springframework.beans.propertyeditors.CustomEntityEditor;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.CollectionUtils;
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
@RequestMapping(value = "/admin/formEdicao.html")
public class FormEdicaoController extends AbstractFormController<Long, Edicao> {

    @Autowired
    private EdicaoService edicaoService;
    @Autowired
    private EventoService eventoService;
    @Autowired
    private TipoCamisetaService tipoCamisetaService;
    @Autowired
    private CorCamisetaService corCamisetaService;
    @Autowired
    private TamanhoCamisetaService tamanhoCamisetaService;
    @Autowired
    private EdicaoValidator validator;

    @Override
    protected EdicaoValidator getValidator() {
        return validator;
    }

    @Override
    protected EdicaoService getCommandService() {
        return edicaoService;
    }

    @ModelAttribute(COMMAND_NAME)
    public Edicao getCommand(@RequestParam(value = "idEvento", required = false) final String idEvento,
                             @RequestParam(value = "idEdicao", required = false) final String idEdicao) {
        Edicao edicao = new Edicao();
        if (CharSequenceUtils.isNumber(idEdicao)) {
            // busca uma edicao ja existente
            edicao = edicaoService.findById(NumberUtils.parseLong(idEdicao));
        } else if (CharSequenceUtils.isNumber(idEvento)) {
            // cria uma nova edicao para o evento
            final Evento evento = eventoService.findById(NumberUtils.parseLong(idEvento));
            if (evento == null) {
                throw new IllegalArgumentException("Evento não encontrado");
            }            
            edicao.setVagasOcupadas(0);            
            edicao.setEvento(evento);
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }
        return edicao;
    }
    
    @ModelAttribute("tiposEdicao")
    public Collection<Edicao.Tipo> getTiposEdicao() {
        return Edicao.Tipo.getValues();
    }
    
    @ModelAttribute("tiposCamiseta")
    public Collection<TipoCamiseta> getTiposCamiseta() {
        return CollectionUtils.asList(tipoCamisetaService.findAll());
    }

    @ModelAttribute("coresCamiseta")
    public Collection<CorCamiseta> getCoresCamiseta() {
        return CollectionUtils.asList(corCamisetaService.findAll());
    }

    @ModelAttribute("tamanhosCamiseta")
    public Collection<TamanhoCamiseta> getTamanhosCamiseta() {
        return CollectionUtils.asList(tamanhoCamisetaService.findAll());
    }

    /* Registra os binder do spring, para tipos de dados complexos como datas e entidades */
    @InitBinder
    protected void initBinder(final WebDataBinder binder, final Locale locale) {
        // TODO : Não ta fazendo o bind do Calendar.
        //binder.registerCustomEditor(Period.class, new CustomCalendarEditor(getDateFormat(locale), true));        
        binder.registerCustomEditor(Calendar.class, new CustomCalendarEditor(getDateFormat(locale), true));
        binder.registerCustomEditor(TipoCamiseta.class, new CustomEntityEditor<TipoCamiseta>(tipoCamisetaService));
        binder.registerCustomEditor(CorCamiseta.class, new CustomEntityEditor<CorCamiseta>(corCamisetaService));
        binder.registerCustomEditor(TamanhoCamiseta.class, new CustomEntityEditor<TamanhoCamiseta>(tamanhoCamisetaService));
    }

    /**
     * Cria um novo objeto 'command', que será populado pelo form.
     * @param command
     * @param model
     * @return 
     */
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final Edicao command, final ModelMap model) {
        model.addAttribute("edicoes", edicaoService.findByProperty("evento", command.getEvento()));
        //return form view
        return "admin/formEdicao";
    }

    /**
     * Processa a submissão do form.
     * @param command
     * @param result
     * @param model
     * @param attributes
     * @param locale
     * @param status
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final Edicao command,
                         final BindingResult result,
                         final ModelMap model,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {

        // validate data
        if (runValidator(command, result).hasErrors()) {
            return onGet(command, model);
        }
        edicaoService.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        // clear the command object from the session and return form success view
        status.setComplete();
        return "redirect:/admin/formEdicao.html?idEvento=" + command.getEvento().getId();
    }
}
