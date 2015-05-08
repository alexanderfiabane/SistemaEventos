/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Responsavel;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander
 */
@Repository
public class ResponsavelDaoBean extends BaseTaperaDaoBean<Long, Responsavel> implements ResponsavelDao{

    @Autowired
    public ResponsavelDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
