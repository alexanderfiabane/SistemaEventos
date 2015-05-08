/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Endereco;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository
public class EnderecoDaoBean extends BaseTaperaDaoBean<Long, Endereco> implements EnderecoDao {

    @Autowired
    public EnderecoDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
