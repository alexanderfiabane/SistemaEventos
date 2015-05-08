/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.CasaEspirita;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository(value = "casaEspiritaDao")
public class CasaEspiritaDaoBean extends BaseTaperaDaoBean<Long, CasaEspirita> implements CasaEspiritaDao {

    @Autowired
    public CasaEspiritaDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Collection<CasaEspirita> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order) {
        final Criteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (caseSensitive) {
            c.add(Restrictions.like("nome", nome, matchMode));
        } else {
            c.add(Restrictions.ilike("nome", nome, matchMode));
        }
        c.addOrder(order);
        return super.findByCriteria(c);
    }
}
