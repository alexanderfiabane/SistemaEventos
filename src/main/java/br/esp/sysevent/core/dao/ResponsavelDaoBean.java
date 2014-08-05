/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Responsavel;
import br.ojimarcius.commons.persistence.dao.AbstractEntityDaoBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander
 */
@Repository
public class ResponsavelDaoBean extends AbstractEntityDaoBean<Long, Responsavel> implements ResponsavelDao{
    
    @Override
    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    } 
    
}
