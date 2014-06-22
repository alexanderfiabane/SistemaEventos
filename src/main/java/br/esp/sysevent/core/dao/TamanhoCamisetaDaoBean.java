package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.TamanhoCamiseta;
import br.ojimarcius.commons.persistence.dao.AbstractEntityDaoBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

@Repository
public class TamanhoCamisetaDaoBean extends AbstractEntityDaoBean<Long, TamanhoCamiseta> implements TamanhoCamisetaDao {

    @Override
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
