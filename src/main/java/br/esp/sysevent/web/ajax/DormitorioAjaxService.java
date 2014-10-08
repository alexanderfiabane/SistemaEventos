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

    public String troca(String idDormitorio, String idConfraternista) {
        Confraternista confraternista;
        Dormitorio dormitorio;
        if (idConfraternista == null) {
            return "Ocorreu um erro no sistema";
        }
        Long idConf = NumberUtils.parseLong(idConfraternista);
        Long idDorm;
        if (idDormitorio.equals("null")) {
            idDorm = null;
        } else {
            idDorm = NumberUtils.parseLong(idDormitorio);
        }
        confraternista = confraternistarioService.findById(idConf);
        if (confraternista.getDormitorio() != null){
            if ((confraternista.equals(confraternista.getDormitorio().getCoordenador()))
                    || (confraternista.equals(confraternista.getDormitorio().getViceCoordenador()))) {
                return "Este confraternista é coodenador ou vice coodenador de dormitório.\n Para editá-lo vá em 'Cadastrar dormitório'";
            }        
        }
        if (idDorm == null && idConf != null) {
            //setar idDormitorio em confraternista pra null, descontar vaga e salvar
            dormitorio = confraternista.getDormitorio();
            dormitorio.setVagasOcupadas(dormitorio.getVagasOcupadas() - 1);
            dormitorioService.saveOrUpdate(dormitorio);
            confraternista.setDormitorio(null);
            confraternistarioService.saveOrUpdate(confraternista);
            return "Confraternista trocado para 'Sem domitório'";
        } else {
            //verficar número de vagas/sexo do dormitorio
            //setar idDormitorio em confraternista pra idDormitorio, somar vaga e salvar
            dormitorio = dormitorioService.findById(idDorm);            
            if (dormitorio.getSexo().equals(confraternista.getPessoa().getSexo())) {
                if (dormitorio.getVagasOcupadas() != dormitorio.getVagas()) {
                    dormitorio.setVagasOcupadas(dormitorio.getVagasOcupadas() + 1);
                    dormitorioService.saveOrUpdate(dormitorio);
                    confraternista.setDormitorio(dormitorio);
                    confraternistarioService.saveOrUpdate(confraternista);
                    return "Confraternista trocado para " + dormitorio.getNome();
                } else {
                    return "Dormitório sem vagas";
                }
            } else {
                return "Dormitório não é do mesmo sexo do(a) confraternista.";
            }
        }
    }
}
