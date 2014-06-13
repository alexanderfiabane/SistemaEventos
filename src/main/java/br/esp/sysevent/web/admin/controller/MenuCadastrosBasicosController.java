/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Alexander Fiabane do Rego (alexander.fiabane@gmail.com)
 */
@Controller
@RequestMapping("/admin/menuCadastrosBasicos.html")
public class MenuCadastrosBasicosController {

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(final ModelMap model) {
        return "admin/menuCadastrosBasicos";
    }
}
