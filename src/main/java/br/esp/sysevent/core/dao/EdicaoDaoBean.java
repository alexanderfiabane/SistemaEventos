/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Edicao;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import java.util.Calendar;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alexander
 */
@Repository
public class EdicaoDaoBean extends BaseTaperaDaoBean<Long, Edicao> implements EdicaoDao {

    @Autowired
    public EdicaoDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Collection<Edicao> findAbertas(Calendar dataAtual){
        final Criteria criteria = createCriteria().
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
                add(Restrictions.le("periodoInscricao.start", dataAtual)).
                add(Restrictions.ge("periodoInscricao.end", dataAtual));
        return findByCriteria(criteria);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Edicao> findAbertas(){
        Calendar dataAtual = Calendar.getInstance();
        return findAbertas(dataAtual);
    }

}
