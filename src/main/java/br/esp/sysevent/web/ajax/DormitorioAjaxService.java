/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.service.ConfraternistaService;
import br.esp.sysevent.core.service.DormitorioService;
import br.esp.sysevent.core.service.EdicaoService;
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
    private DormitorioService dormitorioService;
    @Autowired
    private ConfraternistaService confraternistaService;
    @Autowired
    private EdicaoService edicaoService;

    public Dormitorio findById(String idDormitorio) {
        if (CharSequenceUtils.isBlank(idDormitorio)) {
            return null;
        }
        return dormitorioService.findById(NumberUtils.parseLong(idDormitorio));
    }

    public Collection<Dormitorio> findByGenero(String genero, String idEdicao) {
        if (CharSequenceUtils.isBlank(genero) || CharSequenceUtils.isBlank(idEdicao)) {
            return null;
        } else {            
            Edicao edicao = edicaoService.findById(NumberUtils.parseLong(idEdicao));
            if (genero.equals(Sexo.MASCULINO.getDescricao())) {
                return dormitorioService.findBySexoEdicao(Sexo.MASCULINO, edicao);
            }
            return dormitorioService.findBySexoEdicao(Sexo.FEMININO, edicao);
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
        confraternista = confraternistaService.findById(idConf);
        if (confraternista.getDormitorio() != null){
            if ((confraternista.equals(confraternista.getDormitorio().getCoordenador()))
                    || (confraternista.equals(confraternista.getDormitorio().getViceCoordenador()))) {
                return "Este confraternista é coodenador ou vice coodenador de dormitório.\n Para trocá-lo vá em 'Cadastrar Dormitório'";
            }        
        }
        if (idDorm == null && idConf != null) {
            //setar idDormitorio em confraternista pra null, descontar vaga e salvar
            dormitorio = confraternista.getDormitorio();
            dormitorio.setVagasOcupadas(dormitorio.getVagasOcupadas() - 1);
            dormitorioService.saveOrUpdate(dormitorio);
            confraternista.setDormitorio(null);
            confraternistaService.saveOrUpdate(confraternista);
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
                    confraternistaService.saveOrUpdate(confraternista);
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
