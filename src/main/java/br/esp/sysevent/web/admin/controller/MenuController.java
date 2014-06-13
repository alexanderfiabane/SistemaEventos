/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller do /index.html. Representa o menu principal.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Controller
@RequestMapping("/admin/menu.html")
public class MenuController {

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(final ModelMap model) {
        return "admin/menu";
    }
}