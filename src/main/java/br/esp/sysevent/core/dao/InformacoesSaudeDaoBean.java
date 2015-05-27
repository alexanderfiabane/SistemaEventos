/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.InformacoesSaude;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander
 */
@Repository(value = "informacoesSaudeDao")
public class InformacoesSaudeDaoBean extends AbstractBaseSistemaDaoBean<Long, InformacoesSaude> implements InformacoesSaudeDao{

    @Autowired
    public InformacoesSaudeDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
