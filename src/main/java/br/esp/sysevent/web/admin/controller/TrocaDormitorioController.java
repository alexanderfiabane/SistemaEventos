/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.service.ConfraternistaService;
import br.esp.sysevent.core.service.DormitorioService;
import br.esp.sysevent.core.service.EdicaoService;
import br.msf.commons.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    protected EdicaoService edicaoService;
    @Autowired
    protected DormitorioService dormitorioService;    
    @Autowired
    protected ConfraternistaService confraternistaService;    
    
    @RequestMapping(method = RequestMethod.GET)
    public String onGet(@RequestParam(value="idEdicao",required=false) final String idEdicao, final ModelMap model) {
        final Edicao edicao = edicaoService.findById(NumberUtils.parseLong(idEdicao));
        if (edicao == null) {
            throw new IllegalArgumentException("Edição não encontrada.");
        }
        model.addAttribute("edicao", edicao);
        model.addAttribute("dormitorios", dormitorioService.findByProperty("edicaoEvento", edicao));        
        model.addAttribute("confraternistasSemDormitorio", confraternistaService.findBySemDormitorio());
        
        return "admin/trocaDormitorio";
    }    

    
}
