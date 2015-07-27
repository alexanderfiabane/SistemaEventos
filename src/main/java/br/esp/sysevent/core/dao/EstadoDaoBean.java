/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Estado;
import com.javaleks.commons.io.exception.RuntimeIOException;
import com.javaleks.commons.util.ArrayUtils;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.IOUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Repository(value = "estadoDao")
public class EstadoDaoBean extends AbstractBaseSistemaDaoBean<Long, Estado> implements EstadoDao {

    @Autowired
    public EstadoDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Estado findBySigla(final String sigla) {
        final Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("sigla", sigla.toUpperCase()));
        return DataAccessUtils.uniqueResult(findByCriteria(criteria));
    }

    public Collection<Estado> findAll() {
        return super.findAll(new Order[] {Order.asc("nome")});
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(final String sigla) {
        return findBySigla(sigla) != null;
    }

    @Override
    @Transactional(readOnly = false)
    public void insertDefaultData() {
        saveOrUpdateAll(createEstados());
    }

    private Collection<Estado> createEstados() {
        try {
            final Collection<Estado> estados = new ArrayList<Estado>();
            Resource res = new ClassPathResource("/com/javaleks/core/data/States_BR.txt");
            final Collection<String> lines = IOUtils.readLines(res.getInputStream(), IOUtils.UTF_8);
            for (String currLine : lines) {
                if (CharSequenceUtils.isBlankOrNull(currLine) || CharSequenceUtils.startsWith("#", currLine) || !CharSequenceUtils.contains("|", currLine)) {
                    continue; // ignore comment and invalid lines.
                }
                final String[] parts = currLine.split("\\|");
                if (ArrayUtils.isEmptyOrNull(parts) || parts.length != 2) {
                    continue; // ignore invalid lines.
                }
                estados.add(new Estado(parts[1], parts[0]));
            }
            return estados;
        } catch (IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }
}
