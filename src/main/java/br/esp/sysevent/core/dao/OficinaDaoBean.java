/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Oficina;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import java.util.Collection;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository
public class OficinaDaoBean extends AbstractEntityDao<Long, Oficina> implements OficinaDao {

    @Autowired
    public OficinaDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Collection<Oficina> findAll() {
        return super.findAll(Order.asc("nome"));
    }

}
