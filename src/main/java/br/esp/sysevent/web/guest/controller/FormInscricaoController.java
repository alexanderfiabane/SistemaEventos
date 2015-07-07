/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.guest.controller;

import br.esp.sysevent.core.dao.CidadeDao;
import br.esp.sysevent.core.dao.CorCamisetaDao;
import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.dao.EstadoDao;
import br.esp.sysevent.core.dao.GrupoIdadeDao;
import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.dao.OficinaDao;
import br.esp.sysevent.core.dao.ResponsavelDao;
import br.esp.sysevent.core.dao.TamanhoCamisetaDao;
import br.esp.sysevent.core.dao.TipoCamisetaDao;
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
import br.esp.sysevent.persistence.springframework.beans.propertyeditors.CustomCalendarEditor;
import br.esp.sysevent.persistence.springframework.beans.propertyeditors.CustomEntityEditor;
import br.esp.sysevent.web.controller.I18nController;
import br.esp.sysevent.web.controller.util.ControllerUtils;
import br.esp.sysevent.web.guest.command.InscricaoCommand;
import br.esp.sysevent.web.guest.validation.InscricaoValidator;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
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
public class FormInscricaoController extends I18nController {

    protected static final String COMMAND_NAME = "command";

    @Autowired
    protected ResponsavelDao responsavelDao;
    @Autowired
    protected EstadoDao estadoDao;
    @Autowired
    protected CidadeDao cidadeDao;
    @Autowired
    protected EdicaoDao edicaoDao;
    @Autowired
    protected InscricaoDao inscricaoDao;
    @Autowired
    protected OficinaDao oficinaDao;
    @Autowired
    protected GrupoIdadeDao grupoIdadeDao;
    @Autowired
    protected CorCamisetaDao corCamisetaDao;
    @Autowired
    protected TamanhoCamisetaDao tamanhoCamisetaDao;
    @Autowired
    protected TipoCamisetaDao tipoCamisetaDao;
    @Autowired
    protected InscricaoValidator validator;

    @ModelAttribute(COMMAND_NAME)
    public InscricaoCommand getCommand(@RequestParam(value = "idEdicao", required = false) final String idEdicao) {
        final InscricaoCommand command;
        if (CharSequenceUtils.isNumber(idEdicao)) {
            final Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
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
            command = new InscricaoCommand();
            Inscricao inscricao = new Inscricao();
            inscricao.setEdicaoEvento(edicao);
            inscricao.setConfraternista(confraternista);
            command.setInscricao(inscricao);
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
        return estadoDao.findAll();
    }

    /**
     * Inicializa os binders de tipos.
     * @param binder
     * @param locale
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder, final Locale locale) {
        binder.registerCustomEditor(Calendar.class, new CustomCalendarEditor(getDateFormat(locale), true));
        binder.registerCustomEditor(Responsavel.class, new CustomEntityEditor<>(responsavelDao));
        binder.registerCustomEditor(Cidade.class, new CustomEntityEditor<>(cidadeDao));
        binder.registerCustomEditor(Oficina.class, new CustomEntityEditor<>(oficinaDao));
        binder.registerCustomEditor(GrupoIdade.class, new CustomEntityEditor<>(grupoIdadeDao));
        binder.registerCustomEditor(CorCamiseta.class, new CustomEntityEditor<>(corCamisetaDao));
        binder.registerCustomEditor(TipoCamiseta.class, new CustomEntityEditor<>(tipoCamisetaDao));
        binder.registerCustomEditor(TamanhoCamiseta.class, new CustomEntityEditor<>(tamanhoCamisetaDao));
    }

    /**
     * Cria um novo objeto 'command', que será populado pelo form.
     * @param command
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final InscricaoCommand command, final ModelMap model) {
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
    public String onPost(@ModelAttribute(COMMAND_NAME) final InscricaoCommand command,
                         final BindingResult result,
                         final ModelMap model,
                         final RedirectAttributes attributes,
                         final SessionStatus status,
                         final Locale locale) {

        // remove da lista caso o usuario remova no form
        final Iterator<CamisetaConfraternista> iterator = command.getInscricao().getConfraternista().getCamisetas().iterator();
        while(iterator.hasNext()) {
            final CamisetaConfraternista camiseta = iterator.next();
            if(camiseta.getTipoCamiseta() == null) {
                iterator.remove();
            } else {
                camiseta.setConfraternista(command.getInscricao().getConfraternista());
            }
        }

        validator.validate(command, result);
        if (result.hasErrors()) {
            if(result.hasFieldErrors("id")) {
                model.addAttribute("erro", getMessage("errors.subscription.duplicated", locale));
            }
            return onGet(command, model);
        }
        boolean isNova = command.getInscricao().getId() == null;
        inscricaoDao.gravaInscricao(command);
        model.addAttribute("message", getMessage("message.success.save", locale));

        if(isNova) {
            ControllerUtils.sendMail(command.getInscricao(), getMessage("mail.subscription.receive", locale), "recebimentoInscricao.html");
        }else{
            ControllerUtils.sendMail(command.getInscricao(), getMessage("mail.subscription.update", locale), "alteracaoInscricao.html");
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
