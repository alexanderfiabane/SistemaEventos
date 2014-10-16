/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.service.GrupoIdadeService;
import br.esp.sysevent.core.service.InscricaoService;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fiabane
 */
@Service
public class InscricaoAjaxService {
    
    @Autowired
    private InscricaoService inscricaoService;
    @Autowired
    private GrupoIdadeService grupoIdadeService;
    
    public Collection<Inscricao> findByIdGrupoIdade(final String idGrupoIdade) {
        if (CharSequenceUtils.isBlank(idGrupoIdade)) {
            return Collections.emptyList();
        } 
        return inscricaoService.findByIdGrupoIdade(NumberUtils.parseLong(idGrupoIdade));
    }
    
    public Collection<Inscricao> findSemDormitorioBySexo(String genero, String idEdicao){
        if (CharSequenceUtils.isBlankOrNull(genero) || CharSequenceUtils.isBlankOrNull(idEdicao)) {
            return null;
        }        
        if (Sexo.MASCULINO.getDescricao().equals(genero)){
            return inscricaoService.findSemDormitorioBySexo(Sexo.MASCULINO, NumberUtils.parseLong(idEdicao));
        }else{
            return inscricaoService.findSemDormitorioBySexo(Sexo.FEMININO, NumberUtils.parseLong(idEdicao));
        }
        
    }
    
}
