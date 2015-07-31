package br.esp.sysevent.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Alexander Fiabane do Rego <alexander.rego@ufsm.br>
 */
@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception exception) throws Exception{
        ModelAndView model = new ModelAndView("error/error");
        model.addObject("error", exception.getMessage());
        return model;
    }

}
