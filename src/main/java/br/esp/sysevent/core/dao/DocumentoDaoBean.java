/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Documento;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository
public class DocumentoDaoBean extends AbstractEntityDao<Long, Documento> implements DocumentoDao {

    @Autowired
    public DocumentoDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
