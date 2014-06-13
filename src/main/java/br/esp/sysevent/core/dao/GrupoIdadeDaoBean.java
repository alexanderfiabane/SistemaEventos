/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.GrupoIdade;
import br.msf.commons.persistence.dao.AbstractEntityDaoBean;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fiabane
 */
@Repository
public class GrupoIdadeDaoBean extends AbstractEntityDaoBean<Long, GrupoIdade> implements GrupoIdadeDao{

    @Override
    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }    
    
    public Collection<GrupoIdade> findAllByEdicao() {
        return super.findAll(Order.asc("nome"));
    }
    
    public Collection<GrupoIdade> findByIdade(int idade){
        final DetachedCriteria c = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.le("idadeMinima", idade))
                .add(Restrictions.ge("idadeMaxima", idade));
        return findByCriteria(c, Order.asc("nome"));
    }
}
