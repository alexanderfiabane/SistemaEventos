/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.CidadeDao;
import br.esp.sysevent.core.dao.CorCamisetaDao;
import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.dao.EstadoDao;
import br.esp.sysevent.core.dao.EventoDao;
import br.esp.sysevent.core.dao.TamanhoCamisetaDao;
import br.esp.sysevent.core.dao.TipoCamisetaDao;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.CorCamiseta;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.EdicaoConfigCracha;
import br.esp.sysevent.core.model.EdicaoConfigFichaInscricao;
import br.esp.sysevent.core.model.EdicaoConfigParticipante;
import br.esp.sysevent.core.model.Endereco;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.core.model.Evento;
import br.esp.sysevent.core.model.FormaCobranca;
import br.esp.sysevent.core.model.TamanhoCamiseta;
import br.esp.sysevent.core.model.TipoCamiseta;
import br.esp.sysevent.persistence.springframework.beans.propertyeditors.CustomCalendarEditor;
import br.esp.sysevent.persistence.springframework.beans.propertyeditors.CustomEntityEditor;
import br.esp.sysevent.web.admin.validation.EdicaoValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.CollectionUtils;
import com.javaleks.commons.util.NumberUtils;
import java.util.ArrayList;
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
    private EdicaoDao edicaoDao;
    @Autowired
    private EventoDao eventoDao;
    @Autowired
    private TipoCamisetaDao tipoCamisetaDao;
    @Autowired
    private CorCamisetaDao corCamisetaDao;
    @Autowired
    private TamanhoCamisetaDao tamanhoCamisetaDao;
    @Autowired
    private EstadoDao estadoDao;
    @Autowired
    private CidadeDao cidadeDao;
    @Autowired
    private EdicaoValidator validator;

    @Override
    protected EdicaoValidator getValidator() {
        return validator;
    }

    @Override
    protected EdicaoDao getCommandService() {
        return edicaoDao;
    }

    @ModelAttribute(COMMAND_NAME)
    public Edicao getCommand(@RequestParam(value = "idEvento", required = false) final String idEvento,
            @RequestParam(value = "idEdicao", required = false) final String idEdicao) {
        Edicao edicao = new Edicao();
        if (CharSequenceUtils.isNumber(idEdicao)) {
            // busca uma edicao ja existente
            edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));           
        } else if (CharSequenceUtils.isNumber(idEvento)) {
            // cria uma nova edicao para o evento
            final Evento evento = eventoDao.findById(NumberUtils.parseLong(idEvento));
            if (evento == null) {
                throw new IllegalArgumentException("Evento não encontrado");
            }
            edicao.setVagasOcupadas(0);
            edicao.setEvento(evento);
            Collection<EdicaoConfigParticipante> edicaoConfigParticipantes = new ArrayList<>();
            Collection<Confraternista.Tipo> tiposConfraternistas = Confraternista.Tipo.getValues();
            for (Confraternista.Tipo tiposConfraternista : tiposConfraternistas) {
                EdicaoConfigParticipante edicaoConfigParticipante = new EdicaoConfigParticipante();
                edicaoConfigParticipante.setTipoParticipante(tiposConfraternista);
                edicaoConfigParticipante.setIsento(false);
                edicaoConfigParticipante.setOcupaVaga(true);
                edicaoConfigParticipante.setOcupaVagaGp(true);
                edicaoConfigParticipante.setEdicao(edicao);
                edicaoConfigParticipantes.add(edicaoConfigParticipante);
            }
            edicao.setEdicaoConfigParticipantes(edicaoConfigParticipantes);
            EdicaoConfigFichaInscricao configFichaInscricao = new EdicaoConfigFichaInscricao();
            configFichaInscricao.setTemFichaInscricao(false);
            configFichaInscricao.setAutorizacaoInstituicao(false);
            configFichaInscricao.setAutorizacaoMenor(false);
            configFichaInscricao.setEdicao(edicao);
            edicao.setConfigFichaInscricao(configFichaInscricao);
            EdicaoConfigCracha configCracha = new EdicaoConfigCracha();
            configCracha.setEdicao(edicao);
            configCracha.setTemCracha(false);
            edicao.setConfigCracha(configCracha);
            edicao.setLocalEndereco(new Endereco());
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }
        return edicao;
    }

    @ModelAttribute("tiposEdicao")
    public Collection<Edicao.Tipo> getTiposEdicao() {
        return Edicao.Tipo.getValues();
    }
    @ModelAttribute("tiposCrachas")
    public Collection<EdicaoConfigCracha.TipoCracha> getTiposCrachas() {
        return EdicaoConfigCracha.TipoCracha.getValues();
    }

    @ModelAttribute("tiposFormaCobranca")
    public Collection<FormaCobranca.TipoCobranca> getTiposFormaCobranca() {
        return FormaCobranca.TipoCobranca.getTipos();
    }

    @ModelAttribute("tiposCamiseta")
    public Collection<TipoCamiseta> getTiposCamiseta() {
        return CollectionUtils.asList(tipoCamisetaDao.findAll());
    }

    @ModelAttribute("coresCamiseta")
    public Collection<CorCamiseta> getCoresCamiseta() {
        return CollectionUtils.asList(corCamisetaDao.findAll());
    }

    @ModelAttribute("tamanhosCamiseta")
    public Collection<TamanhoCamiseta> getTamanhosCamiseta() {
        return CollectionUtils.asList(tamanhoCamisetaDao.findAll());
    }
    
    @ModelAttribute("estados")
    public Collection<Estado> getEstados() {
        return estadoDao.findAll();
    }

    /* Registra os binder do spring, para tipos de dados complexos como datas e entidades */
    @InitBinder
    protected void initBinder(final WebDataBinder binder, final Locale locale) {
        binder.registerCustomEditor(Calendar.class, new CustomCalendarEditor(getDateFormat(locale), true));
        binder.registerCustomEditor(TipoCamiseta.class, new CustomEntityEditor<>(tipoCamisetaDao));
        binder.registerCustomEditor(CorCamiseta.class, new CustomEntityEditor<>(corCamisetaDao));
        binder.registerCustomEditor(TamanhoCamiseta.class, new CustomEntityEditor<>(tamanhoCamisetaDao));
        binder.registerCustomEditor(Cidade.class, new CustomEntityEditor<>(cidadeDao));
    }

    /**
     * Cria um novo objeto 'command', que será populado pelo form.
     *
     * @param command
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final Edicao command, final ModelMap model) {
        model.addAttribute("edicoes", edicaoDao.findByProperty("evento", command.getEvento()));
        //return form view
        return "admin/formEdicao";
    }

    /**
     * Processa a submissão do form.
     *
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
        edicaoDao.saveOrUpdate(command);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        // clear the command object from the session and return form success view
        status.setComplete();
        return "redirect:/admin/formEdicao.html?idEvento=" + command.getEvento().getId();
    }
}
