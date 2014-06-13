/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Edicao;
import br.msf.commons.persistence.dao.AbstractEntityDaoBean;
import java.util.Calendar;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander
 */
@Repository
public class EdicaoDaoBean extends AbstractEntityDaoBean<Long, Edicao> implements EdicaoDao {

    @Override
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public Collection<Edicao> findAbertas(Calendar dataAtual){                
        final DetachedCriteria criteria = createCriteria().
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
                add(Restrictions.le("periodoInscricao.start", dataAtual)).
                add(Restrictions.ge("periodoInscricao.end", dataAtual));
        return findByCriteria(criteria);  
    }     
    
}
