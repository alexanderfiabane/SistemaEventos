/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.ConfraternistaDao;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Sexo;
import br.ojimarcius.commons.persistence.service.AbstractEntityServiceBean;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Service
public class ConfraternistaServiceBean extends AbstractEntityServiceBean<Long, Confraternista> implements ConfraternistaService {

    @Override
    protected ConfraternistaDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final ConfraternistaDao dao) {
        super.setDao(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Confraternista> findByNome(final String nome) {
        return getDao().findByNome(nome, MatchMode.START, false, Order.asc("pessoa.nome"));
    }
    
    @Override
    public Collection<Confraternista> findByDormitorio(final Dormitorio dormitorio){
        return getDao().findByDormitorio(dormitorio);
    }       
    
    @Override
    public Collection<Confraternista> findByDormitorio(final Long idDormitorio){
        return getDao().findByDormitorio(idDormitorio, Order.asc("pessoa.nome"));
    }
    
    @Override
    public Collection<Confraternista> findBySemDormitorio(){
        return getDao().findBySemDormitorio(Order.asc("pessoa.nome"));
    }   
    
    @Override
    public Collection<Confraternista> findBySexoSemDormitorio(Sexo genero){
        return getDao().findBySexoSemDormitorio(genero, Order.asc("pessoa.nome"));
    }

    @Override
    public Collection<Confraternista> findBySexoSemDormitorioEdicao(Sexo genero, Edicao edicao) {
        Map map = new HashMap();
        map.put("sexo", genero);
        map.put("edicaoEvento", edicao);
        Collection<Confraternista> confraternistas =  getDao().findByProperties(map, Order.asc("pessoa.nome"));
        Collection<Confraternista> confraternistasSemDormitorio = new HashSet<Confraternista>();
        for (Confraternista confraternista : confraternistas) {
            if (confraternista.getDormitorio()== null){
                confraternistasSemDormitorio.add(confraternista);
            }
        }
        return confraternistasSemDormitorio;
    }
    
}
