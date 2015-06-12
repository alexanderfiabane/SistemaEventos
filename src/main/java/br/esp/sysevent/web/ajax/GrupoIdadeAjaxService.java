/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.dao.ConfraternistaDao;
import br.esp.sysevent.core.dao.GrupoIdadeDao;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.GrupoIdade;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import java.util.Collection;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Alexander
 */
@RemoteProxy
public class GrupoIdadeAjaxService {

    @Autowired
    private GrupoIdadeDao grupoIdadeDao;
    @Autowired
    private ConfraternistaDao confraternistaDao;

    @RemoteMethod
    public GrupoIdade findById(String idGrupoIdade) {
        if (CharSequenceUtils.isBlank(idGrupoIdade)) {
            return null;
        }
        return grupoIdadeDao.findById(NumberUtils.parseLong(idGrupoIdade));
    }
    @RemoteMethod
    public Collection<GrupoIdade> findSimilares(String idGrupoIdade) {
        if (CharSequenceUtils.isBlank(idGrupoIdade)) {
            return null;
        } else {
            return grupoIdadeDao.findSimilares(NumberUtils.parseLong(idGrupoIdade));
        }
    }
    @RemoteMethod
    public String troca(String idGrupoIdade, String idConfraternista) {
        if (idConfraternista == null || idGrupoIdade == null) {
            return "Ocorreu um erro no sistema";
        }
        Confraternista confraternista = confraternistaDao.findById(NumberUtils.parseLong(idConfraternista));
        GrupoIdade grupoIdade = grupoIdadeDao.findById(NumberUtils.parseLong(idGrupoIdade));
        GrupoIdade grupoIdadeConfraternista = confraternista.getGrupoIdade();
        Collection<Confraternista> facilitadores = confraternistaDao.findFacilitadoresByGrupo(grupoIdadeConfraternista);
        for (Confraternista facilitador : facilitadores) {
            if (facilitador.equals(confraternista)) {
                return "Este confraternista é facilitador deste grupo. Para trocá-lo vá em 'Cadastrar Grupo Idade'";
            }
        }
        if (grupoIdade.getVagas().equals(grupoIdade.getVagasOcupadas())) {
            return "Grupo sem vagas";
        } else {
            grupoIdadeConfraternista.setVagasOcupadas(grupoIdadeConfraternista.getVagasOcupadas() - 1);
            grupoIdadeDao.saveOrUpdate(grupoIdadeConfraternista);
            confraternista.setGrupoIdade(grupoIdade);
            confraternistaDao.saveOrUpdate(confraternista);
            grupoIdade.setVagasOcupadas(grupoIdade.getVagasOcupadas() + 1);
            grupoIdadeDao.saveOrUpdate(grupoIdade);

            return "Confraternista trocado para " + grupoIdade.getNome();
        }

    }

}
