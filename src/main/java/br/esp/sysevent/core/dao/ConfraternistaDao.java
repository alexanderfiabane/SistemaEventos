/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Sexo;
import br.ojimarcius.commons.persistence.dao.EntityDao;
import java.util.Collection;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
public interface ConfraternistaDao extends EntityDao<Long, Confraternista> {

    public Collection<Confraternista> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order);
    public Collection<Confraternista> findByDormitorio(final Long idDormitorio, final Order order);
    public Collection<Confraternista> findByDormitorio(Dormitorio dormitorio);
    public Collection<Confraternista> findBySemDormitorio(Order order);

    public Collection<Confraternista> findBySexoSemDormitorio(Sexo genero, Order asc);
}
