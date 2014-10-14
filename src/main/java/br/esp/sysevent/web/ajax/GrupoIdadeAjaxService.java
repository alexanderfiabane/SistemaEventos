/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.service.ConfraternistaService;
import br.esp.sysevent.core.service.GrupoIdadeService;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander
 */
@Service
public class GrupoIdadeAjaxService {

    @Autowired
    private GrupoIdadeService grupoIdadeService;
    @Autowired
    private ConfraternistaService confraternistaService;

    public Collection<GrupoIdade> findSimilares(String idGrupoIdade) {
        if (CharSequenceUtils.isBlank(idGrupoIdade)) {
            return null;
        } else {
            return grupoIdadeService.findSimilares(NumberUtils.parseLong(idGrupoIdade));
        }
    }

    public String troca(String idGrupoIdade, String idConfraternista) {
        if (idConfraternista == null || idGrupoIdade == null) {
            return "Ocorreu um erro no sistema";
        }
        Confraternista confraternista = confraternistaService.findById(NumberUtils.parseLong(idConfraternista));
        GrupoIdade grupoIdade = grupoIdadeService.findById(NumberUtils.parseLong(idGrupoIdade));
        GrupoIdade grupoIdadeConfraternista = confraternista.getGrupoIdade();
        Collection<Confraternista> facilitadores = grupoIdadeConfraternista.getFacilitadores();
        for (Confraternista facilitador : facilitadores) {
            if (facilitador.equals(confraternista)) {
                return "Este confraternista é facilitador deste grupo. Para trocá-lo vá em 'Cadastrar Grupo Idade'";
            }
        }
        if (grupoIdade.getVagas() == grupoIdade.getVagasOcupadas()) {
            return "Grupo sem vagas";
        }
        grupoIdadeConfraternista.setVagasOcupadas(grupoIdade.getVagasOcupadas() - 1);
        grupoIdadeService.saveOrUpdate(grupoIdadeConfraternista);
        confraternista.setGrupoIdade(grupoIdade);
        confraternistaService.saveOrUpdate(confraternista);
        grupoIdade.setVagasOcupadas(grupoIdade.getVagasOcupadas() + 1);
        grupoIdadeService.saveOrUpdate(grupoIdade);
        
        return "Confraternista trocado para " + grupoIdade.getNome();
    }

}
