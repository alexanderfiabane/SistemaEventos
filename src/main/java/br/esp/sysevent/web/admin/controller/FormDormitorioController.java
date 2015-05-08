/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.ConfraternistaDao;
import br.esp.sysevent.core.dao.DormitorioDao;
import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.web.admin.validation.DormitorioValidator;
import br.esp.sysevent.web.controller.AbstractFormController;
import br.esp.sysevent.persistence.springframework.beans.propertyeditors.CustomEntityEditor;
import br.esp.sysevent.persistence.springframework.validation.Validator;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
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
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Controller
@RequestMapping(value = "/admin/formDormitorio.html")
public class FormDormitorioController extends AbstractFormController<Long, Dormitorio> {

    @Autowired
    private EdicaoDao edicaoDao;
    @Autowired
    private ConfraternistaDao confraternistaDao;
    @Autowired
    private DormitorioDao dormitorioDao;
    @Autowired
    private DormitorioValidator validator;

    @Override
    protected Validator<Dormitorio> getValidator() {
        return validator;
    }

    @Override
    protected DormitorioDao getCommandService() {
        return dormitorioDao;
    }

    @ModelAttribute(COMMAND_NAME)
    public Dormitorio getCommand(@RequestParam(value = "idDormitorio", required = false) final String idDormitorio,
            @RequestParam(value = "idEdicao", required = false) final String idEdicao) {
        final Dormitorio dormitorio;
        if (CharSequenceUtils.isNumber(idDormitorio)) {
            dormitorio = dormitorioDao.findById(NumberUtils.parseLong(idDormitorio));
        } else if (CharSequenceUtils.isNumber(idEdicao)) {
            final Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
            if (edicao == null) {
                throw new IllegalArgumentException("Edição não encontrada");
            }
            dormitorio = new Dormitorio();
            dormitorio.setEdicaoEvento(edicao);
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }
        return dormitorio;
    }

    /* sexos para o select do form */
    @ModelAttribute("sexos")
    public Collection<Sexo> getSexos() {
        return Sexo.getValues();
    }

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Confraternista.class, new CustomEntityEditor<Confraternista>(confraternistaDao));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@ModelAttribute(COMMAND_NAME) final Dormitorio command, final ModelMap model) {
        model.addAttribute("dormitorios", dormitorioDao.findByProperty("edicaoEvento", command.getEdicaoEvento()));
        //return form view
        return "admin/formDormitorio";
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
    public String onPost(@ModelAttribute(COMMAND_NAME) final Dormitorio command,
            final BindingResult result,
            final ModelMap model,
            final RedirectAttributes attributes,
            final SessionStatus status,
            final Locale locale) {
        // validate data
        if (runValidator(command, result).hasErrors()) {
            return onGet(command, model);
        }
        Confraternista coordenadorNovo = command.getCoordenador();
        Confraternista viceCoordenadorNovo = command.getViceCoordenador();

        if (!coordenadorNovo.equals(viceCoordenadorNovo)) {
            if (command.getId() == null) {
                command.setVagasOcupadas(2);
            } else {
                Dormitorio dormitorioAtual = dormitorioDao.findById(command.getId());
                Confraternista coordenadorAtual = dormitorioAtual.getCoordenador();
                Confraternista viceCoordenadorAtual = dormitorioAtual.getViceCoordenador();
                if (coordenadorAtual.equals(viceCoordenadorAtual)) {
                    command.setVagasOcupadas(dormitorioAtual.getVagasOcupadas() + 1);
                }
                coordenadorAtual.setDormitorio(null);
                confraternistaDao.saveOrUpdate(coordenadorAtual);
                viceCoordenadorAtual.setDormitorio(null);
                confraternistaDao.saveOrUpdate(viceCoordenadorAtual);
            }
        } else {
            if (command.getId() == null) {
                command.setVagasOcupadas(1);
            } else {
                Dormitorio dormitorioAtual = dormitorioDao.findById(command.getId());
                Confraternista coordenadorAtual = dormitorioAtual.getCoordenador();
                Confraternista viceCoordenadorAtual = dormitorioAtual.getViceCoordenador();
                if (!coordenadorAtual.equals(viceCoordenadorAtual)) {
                    command.setVagasOcupadas(dormitorioAtual.getVagasOcupadas() - 1);
                }
                coordenadorAtual.setDormitorio(null);
                confraternistaDao.saveOrUpdate(coordenadorAtual);
                viceCoordenadorAtual.setDormitorio(null);
                confraternistaDao.saveOrUpdate(viceCoordenadorAtual);
            }
        }
        dormitorioDao.saveOrUpdate(command);
        Dormitorio domitorio = dormitorioDao.findById(command.getId());
        coordenadorNovo.setDormitorio(domitorio);
        confraternistaDao.saveOrUpdate(coordenadorNovo);
        viceCoordenadorNovo.setDormitorio(domitorio);
        confraternistaDao.saveOrUpdate(viceCoordenadorNovo);
        attributes.addFlashAttribute("message", getMessage("message.success.save", locale));
        status.setComplete();
        return "redirect:/admin/formDormitorio.html?idEdicao=" + command.getEdicaoEvento().getId();
    }
}
