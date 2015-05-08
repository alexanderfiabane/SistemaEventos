/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.model.Edicao;
import com.javaleks.commons.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Fiabane
 */
@Controller
@RequestMapping("/admin/menuGrupoIdade.html")
public class MenuGrupoIdadeController {

    @Autowired
    protected EdicaoDao edicaoDao;

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@RequestParam(value="idEdicao",required=false) final String idEdicao, final ModelMap model) {
        final Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
        if (edicao == null) {
            throw new IllegalArgumentException("Edição não encontrada.");
        }
        model.addAttribute("edicao", edicao);
        return "admin/menuGrupoIdade";
    }

}
