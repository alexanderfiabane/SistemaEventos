/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.ConfraternistaDao;
import br.esp.sysevent.core.dao.DormitorioDao;
import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Sexo;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Alexander
 */
@Controller
@RequestMapping(value = "/admin/trocaDormitorio.html")
public class TrocaDormitorioController{

    @Autowired
    protected EdicaoDao edicaoDao;
    @Autowired
    protected DormitorioDao dormitorioDao;
    @Autowired
    protected ConfraternistaDao confraternistaDao;

    /* sexos para o select do form */
    @ModelAttribute("sexos")
    public Collection<Sexo> getSexos() {
        return Sexo.getValues();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@RequestParam(value="idEdicao",required=false) final String idEdicao, final ModelMap model) {
        if (!CharSequenceUtils.isNumber(idEdicao)) {
            throw new IllegalArgumentException("Parâmetros inválidos.");
        }
        final Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        if (edicao == null) {
            throw new IllegalArgumentException("Edição não encontrada.");
        }
        model.addAttribute("edicao", edicao);
        //model.addAttribute("dormitorios", dormitorioDao.findByProperty("edicaoEvento", edicao));
        model.addAttribute("confraternistasSemDormitorio", confraternistaDao.findBySemDormitorio());

        return "admin/trocaDormitorio";
    }
}
