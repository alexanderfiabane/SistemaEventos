package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.CorCamiseta;
import br.msf.commons.persistence.dao.AbstractEntityDaoBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

@Repository
public class CorCamisetaDaoBean extends AbstractEntityDaoBean<Long, CorCamiseta> implements CorCamisetaDao {

    @Override
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
