/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Sexo;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import java.util.Collection;
import java.util.Collections;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Fiabane
 */
@RemoteProxy(name = "inscricaoAjaxService")
public class InscricaoAjaxService {

    @Autowired
    private InscricaoDao inscricaoDao;

    public Collection<Inscricao> findByIdGrupoIdade(final String idGrupoIdade) {
        if (CharSequenceUtils.isBlank(idGrupoIdade)) {
            return Collections.emptyList();
        }
        return inscricaoDao.findByIdGrupoIdade(NumberUtils.parseLong(idGrupoIdade));
    }

    public Collection<Inscricao> findSemDormitorioBySexo(String genero, String idEdicao){
        if (CharSequenceUtils.isBlankOrNull(genero) || CharSequenceUtils.isBlankOrNull(idEdicao)) {
            return null;
        }
        if (Sexo.MASCULINO.getDescricao().equals(genero)){
            return inscricaoDao.findSemDormitorioBySexo(Sexo.MASCULINO, NumberUtils.parseLong(idEdicao));
        }else{
            return inscricaoDao.findSemDormitorioBySexo(Sexo.FEMININO, NumberUtils.parseLong(idEdicao));
        }

    }

}
