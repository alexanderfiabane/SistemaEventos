/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.web.ajax;

import br.esp.sysevent.core.dao.ConfraternistaDao;
import br.esp.sysevent.core.dao.DormitorioDao;
import br.esp.sysevent.core.dao.EdicaoDao;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Sexo;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
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
    private DormitorioDao dormitorioDao;
    @Autowired
    private ConfraternistaDao confraternistaDao;
    @Autowired
    private EdicaoDao edicaoDao;

    public Dormitorio findById(String idDormitorio) {
        if (CharSequenceUtils.isBlank(idDormitorio)) {
            return null;
        }
        return dormitorioDao.findById(NumberUtils.parseLong(idDormitorio));
    }

    public Collection<Dormitorio> findByGenero(String genero, String idEdicao) {
        if (CharSequenceUtils.isBlank(genero) || CharSequenceUtils.isBlank(idEdicao)) {
            return null;
        } else {
            Edicao edicao = edicaoDao.findById(NumberUtils.parseLong(idEdicao));
            if (genero.equals(Sexo.MASCULINO.getDescricao())) {
                return dormitorioDao.findBySexoEdicao(Sexo.MASCULINO, edicao);
            }
            return dormitorioDao.findBySexoEdicao(Sexo.FEMININO, edicao);
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
        confraternista = confraternistaDao.findById(idConf);
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
            dormitorioDao.saveOrUpdate(dormitorio);
            confraternista.setDormitorio(null);
            confraternistaDao.saveOrUpdate(confraternista);
            return "Confraternista trocado para 'Sem domitório'";
        } else {
            //verficar número de vagas/sexo do dormitorio
            //setar idDormitorio em confraternista pra idDormitorio, somar vaga e salvar
            dormitorio = dormitorioDao.findById(idDorm);
            if (dormitorio.getSexo().equals(confraternista.getPessoa().getSexo())) {
                if (dormitorio.getVagasOcupadas() != dormitorio.getVagas()) {
                    dormitorio.setVagasOcupadas(dormitorio.getVagasOcupadas() + 1);
                    dormitorioDao.saveOrUpdate(dormitorio);
                    confraternista.setDormitorio(dormitorio);
                    confraternistaDao.saveOrUpdate(confraternista);
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
