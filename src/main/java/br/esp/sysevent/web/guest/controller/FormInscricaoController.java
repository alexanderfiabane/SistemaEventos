/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.guest.controller;

import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.CorCamiseta;
import br.esp.sysevent.core.model.Documento;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Endereco;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.model.InformacoesSaude;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Responsavel;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.model.TamanhoCamiseta;
import br.esp.sysevent.core.model.TipoCamiseta;
import br.esp.sysevent.core.service.CidadeService;
import br.esp.sysevent.core.service.CorCamisetaService;
import br.esp.sysevent.core.service.EdicaoService;
import br.esp.sysevent.core.service.EstadoService;
import br.esp.sysevent.core.service.GrupoIdadeService;
import br.esp.sysevent.core.service.InscricaoService;
import br.esp.sysevent.core.service.OficinaService;
import br.esp.sysevent.core.service.ResponsavelService;
import br.esp.sysevent.core.service.TamanhoCamisetaService;
import br.esp.sysevent.core.service.TipoCamisetaService;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.web.guest.validation.InscricaoValidator;
import br.esp.sysevent.persistence.springframework.beans.propertyeditors.CustomCalendarEditor;
import br.esp.sysevent.persistence.springframework.beans.propertyeditors.CustomEntityEditor;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
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
 * Mostra o form de inscricao.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Controller
@RequestMapping(value = "/guest/formInscricao.html")
public class FormInscricaoController extends AbstractFormController<Long, Inscricao> {

    @Autowired
    protected ResponsavelService responsavelService;
    @Autowired
    protected EstadoService estadoService;
    @Autowired
    protected CidadeService cidadeService;
    @Autowired
    protected EdicaoService edicaoService;
    @Autowired
    protected InscricaoService inscricaoService;
    @Autowired
    protected OficinaService oficinaService;
    @Autowired
    protected GrupoIdadeService grupoIdadeService;
    @Autowired
    protected CorCamisetaService corCamisetaService;
    @Autowired
    protected TamanhoCamisetaService tamanhoCamisetaService;
    @Autowired
    protected TipoCamisetaService tipoCamisetaService;
    @Autowired
    protected InscricaoValidator validator;

    @Override
    protected InscricaoValidator getValidator() {
        return validator;
    }

    @Override
    protected InscricaoService getCommandService() {
        return inscricaoService;
    }

    @ModelAttribute(COMMAND_NAME)
    public Inscricao getCommand(@RequestParam(value = "idEdicao", required = false) final String idEdicao) {
        final Inscricao command;
        if (CharSequenceUtils.isNumber(idEdicao)) {
            final Edicao edicao = edicaoService.findById(NumberUtils.parseLong(idEdicao));
            if (edicao == null) {
                throw new IllegalArgumentException("Edição não encontrada");
            }
            final Pessoa pessoa = new Pessoa();
            pessoa.setDocumentos(new Documento());
            pessoa.setEndereco(new Endereco());
            pessoa.setInformacoesSaude(new InformacoesSaude());
            final Confraternista confraternista = new Confraternista();
            confraternista.setPessoa(pessoa);
            confraternista.setCamisetas(new ArrayList<CamisetaConfraternista>());
            command = new Inscricao();
            command.setEdicaoEvento(edicao);
            command.setConfraternista(confraternista);
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }
        return command;
    }

    /**
     * Adiciona a lista de tipos de confraternista ao referenceData.
     * @return 
     */
    @ModelAttribute("tiposConfraternista")
    public Collection<Confraternista.Tipo> getTiposConfraternista() {
        return Confraternista.Tipo.getValues();
    }

    /**
     * Adiciona a lista de sexos ao referenceData.
     * @return 
     */
    @ModelAttribute("sexos")
    public Collection<Sexo> getSexos() {
        return Sexo.getValues();
    }

    /**
     * Adiciona a lista de Estados ao referenceData.
     * @return 
     */
    @ModelAttribute("estados")
    public Collection<Estado> getEstados() {
        return estadoService.findAll();
    }

    /**
     * Inicializa os binders de tipos.
     * @param binder
     * @param locale
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder, final Locale locale) {
        binder.registerCustomEditor(Calendar.class, new CustomCalendarEditor(getDateFormat(locale), true));
        binder.registerCustomEditor(Responsavel.class, new CustomEntityEditor<Responsavel>(responsavelService));        
        binder.registerCustomEditor(Cidade.class, new CustomEntityEditor<Cidade>(cidadeService));
        binder.registerCustomEditor(Oficina.class, new CustomEntityEditor<Oficina>(oficinaService));
        binder.registerCustomEditor(GrupoIdade.class, new CustomEntityEditor<GrupoIdade>(grupoIdadeService));
        binder.registerCustomEditor(CorCamiseta.class, new CustomEntityEditor<CorCamiseta>(corCamisetaService));
        binder.registerCustomEditor(TipoCamiseta.class, new CustomEntityEditor<TipoCamiseta>(tipoCamisetaService));
        binder.registerCustomEditor(TamanhoCamiseta.class, new CustomEntityEditor<TamanhoCamiseta>(tamanhoCamisetaService));
    }

    /**
     * Cria um novo objeto 'command', que será populado pelo form.
     * @param command
     * @param model
     * @return 
     */
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final Inscricao command, final ModelMap model) {
        //return form view
        return getFormView();
    }

    /**
     * Processa a submissão do form.
     * @param command
     * @param locale
     * @param result
     * @param status
     * @param model
     * @param attributes
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST)
    public String onPost(@ModelAttribute(COMMAND_NAME) final Inscricao command,
                         final BindingResult result,
                         final ModelMap model,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {

        // remove da lista caso o usuario remova no form
        final Iterator<CamisetaConfraternista> iterator = command.getConfraternista().getCamisetas().iterator();
        while(iterator.hasNext()) {
            final CamisetaConfraternista camiseta = iterator.next();
            if(camiseta.getTipoCamiseta() == null) {
                iterator.remove();
            } else {
                camiseta.setConfraternista(command.getConfraternista());
            }
        }

        // validate data
        if (runValidator(command, result).hasErrors()) {
            if(result.hasFieldErrors("id")) {
                model.addAttribute("erro", getMessage("errors.subscription.duplicated", locale));
            }
            return onGet(command, model);
        }

        boolean isNova = command.getId() == null;
        inscricaoService.gravaInscricao(command);
        model.addAttribute("message", getMessage("message.success.save", locale));

        if(isNova) {
            ControllerUtils.sendMail(command, getMessage("mail.subscription.receive", locale), "recebimentoInscricao.html");
        }else{
            ControllerUtils.sendMail(command, getMessage("mail.subscription.update", locale), "alteracaoInscricao.html");
        }

        // clear the command object from the session and return form success view
        status.setComplete();
        return getSuccessView();
    }

    protected String getFormView() {
        return "guest/formInscricao";
    }

    protected String getSuccessView() {
        return "guest/inscricaoSuccess";
    }
}
