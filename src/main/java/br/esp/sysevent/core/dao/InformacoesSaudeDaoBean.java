/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.InformacoesSaude;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Alexander
 */
public class InformacoesSaudeDaoBean extends BaseTaperaDaoBean<Long, InformacoesSaude> implements InformacoesSaudeDao{

    @Autowired
    public InformacoesSaudeDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
