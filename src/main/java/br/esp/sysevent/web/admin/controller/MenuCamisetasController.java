/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Alexander
 */
@Controller
@RequestMapping("/admin/menuCamisetas.html")
public class MenuCamisetasController {

    @RequestMapping(method = RequestMethod.GET)
    public String onGet(final ModelMap model) {
        return "admin/menuCamisetas";
    }
}