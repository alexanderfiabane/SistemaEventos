/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Estado;
import com.javaleks.commons.core.dao.annotation.PreSave;
import com.javaleks.commons.core.dao.annotation.PreSaveOrUpdate;
import com.javaleks.commons.io.exception.RuntimeIOException;
import com.javaleks.commons.util.ArrayUtils;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.IOUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
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
@Repository(value = "cidadeDao")
public class CidadeDaoBean extends BaseTaperaDaoBean<Long, Cidade> implements CidadeDao {

    @Autowired
    public CidadeDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    @Autowired
    private EstadoDao estadoDao;

    @Override
    public Collection<Cidade> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order) {
        final Criteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (caseSensitive) {
            c.add(Restrictions.like("nome", nome, matchMode));
        } else {
            c.add(Restrictions.ilike("nome", nome, matchMode));
        }
        c.addOrder(order);
        return findByCriteria(c);
    }

    @Override
    public Collection<Cidade> findByEstado(final Estado estado, final Order order) {
        final Criteria c = createCriteria().
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
                add(Restrictions.eq("estado", estado)).
                addOrder(order);
        return findByCriteria(c);
    }

    @Override
    public Collection<Cidade> findByEstado(final Long idEstado) {
        final Criteria criteria = createCriteria()
                .add(Restrictions.eq("estado.id", idEstado))
                .addOrder(Order.asc("nome"));
        return findByCriteria(criteria);
    }

    @Override
    public Cidade findCapital(final Estado estado) {
        final Criteria criteria = createCriteria()
                .add(Restrictions.eq("estado", estado))
                .add(Restrictions.eq("capital", Boolean.TRUE));
        return DataAccessUtils.uniqueResult(findByCriteria(criteria));
    }

    @Override
    @Transactional(readOnly = false)
    public void insertDefaultData() {
        saveOrUpdateAll(createCidades());
    }

    @PreSave
    @PreSaveOrUpdate
    protected void checkCapital(final Cidade entity) {
        if (entity.isCapital()) {
            final Cidade capital = findCapital(entity.getEstado());
            if (capital != null) {
                throw new IllegalArgumentException("Já existe capital para " + entity.getEstado().getNome());
            }
        }
    }
            
    private Collection<Cidade> createCidades() {
        try {
            final Collection<Cidade> cidades = new ArrayList<Cidade>();
            Collection<Estado> estados = estadoDao.findAll();
            for (Estado estado : estados) {
                Resource res = new ClassPathResource("/br/ojimarcius/commons/persistence/data/Cities_BR_" + estado.getSigla() + ".txt");
                if (!res.exists()) {
                    continue; // se nao existir arquivo para este estado, passamos para o prox
                }
                final Collection<String> lines = IOUtils.readLines(res.getInputStream(), IOUtils.UTF_8);
                for (String currLine : lines) {
                    if (CharSequenceUtils.isBlankOrNull(currLine) || CharSequenceUtils.startsWith("#", currLine)) {
                        continue; // ignore comment and invalid lines.
                    }
                    final String[] parts = currLine.split("\\|");
                    if (ArrayUtils.isNotEmpty(parts)) {
                        if (parts.length == 1) {
                            cidades.add(new Cidade(parts[0], estado));
                        } else if (parts.length == 2) {
                            cidades.add(new Cidade(parts[1], estado));
                        } else if (parts.length == 3) {
                            if ("capital".equalsIgnoreCase(parts[2])) {
                                cidades.add(new Cidade(parts[1], estado, Boolean.TRUE));
                            } else {
                                cidades.add(new Cidade(parts[1], estado));
                            }
                        }
                    }
                }
            }
            return cidades;
        } catch (IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }
}
