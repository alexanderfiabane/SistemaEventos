/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.service.ConfraternistaService;
import br.esp.sysevent.core.service.DormitorioService;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.util.Collection;
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
    @Autowired
    ConfraternistaService confraternistarioService;

    public Dormitorio findById(String idDormitorio) {
        if (CharSequenceUtils.isBlank(idDormitorio)) {
            return null;
        }
        return dormitorioService.findById(NumberUtils.parseLong(idDormitorio));
    }

    public Collection<Dormitorio> findByGenero(String genero) {
        if (CharSequenceUtils.isBlank(genero)) {
            return null;
        } else {
            if (genero.equals(Sexo.MASCULINO.getDescricao())) {
                return dormitorioService.findBySexo(Sexo.MASCULINO);
            }
            return dormitorioService.findBySexo(Sexo.FEMININO);
        }
    }

    public String troca(Long idDormitorio, Long idConfraternista) {
        Confraternista confraternista;
        if (idDormitorio == null && idConfraternista == null) {
            return null;
        } else if (idDormitorio == null && idConfraternista != null) {
            //setar idDormitorio em confraternista pra null, descontar vaga e salvar
            //confraternista = confraternistarioService.findById(idDormitorio);
            return "Confraternista trocado para 'Sem domitório'";
        } else {
            //verficar número de vagas/sexo do dormitorio
            //setar idDormitorio em confraternista pra idDormitorio, somar vaga e salvar
            return "Confraternista trocado para 'Nome dormitório'";
        }
    }
}
