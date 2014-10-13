/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.service.EdicaoService;
import br.esp.sysevent.core.service.GrupoIdadeService;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.util.Collection;
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
@RequestMapping(value = "/admin/trocaGrupoIdade.html")
public class TrocaGrupoIdadeController {
    
    @Autowired
    protected EdicaoService edicaoService;
    @Autowired
    protected GrupoIdadeService grupoIdadeService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@RequestParam(value="idEdicao",required=false) final String idEdicao, final ModelMap model) {
        if (!CharSequenceUtils.isNumber(idEdicao)) {
            throw new IllegalArgumentException("Parâmetros inválidos.");
        }
        final Edicao edicao = edicaoService.findById(NumberUtils.parseLong(idEdicao));
        if (edicao == null) {
            throw new IllegalArgumentException("Edição não encontrada.");
        }
        Collection<GrupoIdade> grupos = grupoIdadeService.findByProperty("edicaoEvento", edicao);
        model.addAttribute("edicao", edicao);
        model.addAttribute("grupoIdade", grupos);        
        
        return "admin/trocaGrupoIdade";
    }
    
}
