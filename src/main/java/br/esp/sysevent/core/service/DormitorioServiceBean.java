/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.DormitorioDao;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Sexo;
import br.ojimarcius.commons.persistence.service.AbstractEntityServiceBean;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Service
public class DormitorioServiceBean extends AbstractEntityServiceBean<Long, Dormitorio> implements DormitorioService {

    @Autowired
    InscricaoService inscricaoService;
    @Autowired
    ConfraternistaService confraternistaService;

    @Override
    protected DormitorioDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final DormitorioDao dao) {
        super.setDao(dao);
    }

    public void alocaConfraternistaDormitorio(Confraternista confraternista, Dormitorio dormitorio) {
        if (!validaDormitorio(dormitorio, confraternista)) {
            throw new IllegalArgumentException("O sexo do confraternista é diferente"
                    + " do dormitório ou o dormitório está cheio.");
        }
        confraternista.setDormitorio(dormitorio);
        confraternistaService.saveOrUpdate(confraternista);
    }

    public void alocaConfraternistasDormitorio(Collection<Confraternista> confraternistas, Dormitorio dormitorio) {

        for (Confraternista confraternista : confraternistas) {
            if (!validaDormitorio(dormitorio, confraternista)) {
                throw new IllegalArgumentException("O sexo do confraternista é diferente"
                        + " do dormitório ou o dormitório está cheio.");
            }
            confraternista.setDormitorio(dormitorio);
        }
        confraternistaService.saveOrUpdateAll(confraternistas);
    }

    public void desalocaConfraternistaDormitorio(Confraternista confraternista) {
        confraternista.setDormitorio(null);
        confraternistaService.saveOrUpdate(confraternista);
    }

    public void desalocaConfraternistasDormitorio(Collection<Confraternista> confraternistas) {

        for (Confraternista confraternista : confraternistas) {
            confraternista.setDormitorio(null);
        }
        confraternistaService.saveOrUpdateAll(confraternistas);
    }

    @Override
    public void alocaConfraternistasAleatoriamente(final Edicao edicao) {

        final Collection<Dormitorio> dormitorios = getDao().findByProperty("edicaoEvento", edicao);
        if (dormitorios == null) {
            throw new IllegalArgumentException("Não há dormitórios cadastrados.");
        }
        Collection<Inscricao> inscricoes = inscricaoService.findByEdicaoDeferidas(edicao.getId());
        if(inscricoes.isEmpty()){
            throw new IllegalArgumentException("Não há confraternistas com inscrição deferida para alocar.");
        }
        //Insere o confraternista em um dormitório 'aleatoriamente'.
        for (Inscricao inscricao : inscricoes) {
            for (Dormitorio dormitorio : dormitorios) {
                if (validaDormitorio(dormitorio, inscricao.getConfraternista())) {
                    inscricao.getConfraternista().setDormitorio(dormitorio);
                    confraternistaService.saveOrUpdate(inscricao.getConfraternista());
                }
            }
        }
    }
    
    @Override
    public Collection<Dormitorio> findBySexoEdicao(Sexo sexo, Edicao edicao) {
        Map map = new HashMap();
        map.put("sexo", sexo);
        map.put("edicaoEvento", edicao);
        return getDao().findByProperties(map, Order.asc("nome"));
    }
    
    @Override
    public Collection<Dormitorio> findBySexo(Sexo sexo){
        return getDao().findByProperty("sexo", sexo, Order.asc("nome"));
    }

    private boolean validaDormitorio(Dormitorio dormitorio, Confraternista confraternista) {

        //Até 5 anos
        Integer idadeCrianca = 5;

        if (dormitorio == null) {
            return false;
        }
        //Para confraternistas acima de 5 anos
        if (validaIdade(confraternista.getPessoa().getDataNascimento()) > idadeCrianca) {
            if (dormitorio.getSexo() != confraternista.getPessoa().getSexo()) {
                return false;
            }
        }
        Collection<Confraternista> confraternistas = confraternistaService.findByDormitorio(dormitorio);
        if (dormitorio.getVagas() <= confraternistas.size()) {
            return false;
        }
        return true;
    }

    protected Integer validaIdade(Calendar dataNascimento) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dataEvento = null;
        try {
            dataEvento = df.parse("29/03/2013");
        } catch (ParseException ex) {
            Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dataEvento);

        int year1 = dataNascimento.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        int month1 = dataNascimento.get(Calendar.MONTH);
        int month2 = cal2.get(Calendar.MONTH);
        int day1 = dataNascimento.get(Calendar.DAY_OF_MONTH);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        int idade = year2 - year1;
        if ((month2 < month1)
                || ((month2 == month1) && (day2 < day1))) {
            idade -= 1;
        }
        return idade;
    }

}
