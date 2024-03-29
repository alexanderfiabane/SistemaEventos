/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.guest.controller;

import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.service.EdicaoService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Alexander
 */
@Controller
public class GuestInscricoesController{

    @Autowired
    private EdicaoService edicaoService;
    
    @RequestMapping(value = "/guest/listInscricoesAbertas.html", method = RequestMethod.GET)
    public String listInscricoesAbertas(final ModelMap model) {
        
        final Collection<Edicao> edicoes = edicaoService.findAbertas();
        if (edicoes == null) {
            throw new IllegalArgumentException("Não há edições cadastradas.");
        }
        model.addAttribute("edicoes", edicoes);       
        return "guest/listInscricoesAbertas";
    }
    
}
