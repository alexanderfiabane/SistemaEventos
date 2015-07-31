/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.controller;

import br.esp.sysevent.web.controller.util.ControllerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller do /index.html. Representa o menu principal.
 * <p/>
 * Se o usuário tentando acessar, for do tipo ADMIN, este será
 * redirecionado ao menu adminnistrativo.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String onGet(final ModelMap model) {
        if (ControllerUtils.isLoggedInAsAdmin()) {
            /**
             * Se o usuário tentando acessar, for do tipo ADMIN, este será
             * redirecionado ao menu adminnistrativo.
             */
            return "redirect:/admin/menu.html";
        }
        return "mainMenu";
    }

    @RequestMapping(value = "/error/exception.html")
    public String httpException(){
        return "error/error";
    }

}