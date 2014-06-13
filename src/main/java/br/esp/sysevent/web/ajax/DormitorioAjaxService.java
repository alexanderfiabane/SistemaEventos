/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.service.DormitorioService;
import br.msf.commons.util.NumberUtils;
import br.msf.commons.util.CharSequenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fiabane
 */
@Service
public class DormitorioAjaxService {
    
    @Autowired
    DormitorioService dormitorioService;
    
    public Dormitorio findById(String idDormitorio){
        if (CharSequenceUtils.isBlank(idDormitorio)) {            
            return null;
        }
        return dormitorioService.findById(NumberUtils.parseLong(idDormitorio));
    }
}
