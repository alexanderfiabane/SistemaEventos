/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.CidadeDao;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Estado;
import br.msf.commons.io.exception.RuntimeIOException;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import br.msf.commons.util.ArrayUtils;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.IOUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Service
public class CidadeServiceBean extends AbstractEntityServiceBean<Long, Cidade> implements CidadeService {

    @Autowired
    protected EstadoService estadoService;

    @Override
    protected CidadeDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final CidadeDao dao) {
        super.setDao(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Cidade> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order) {
        return getDao().findByNome(nome, matchMode, caseSensitive, order);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Cidade> findByEstado(final Estado estado, final Order order) {
        return getDao().findByEstado(estado, order);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Cidade> findByEstado(final Long idEstado) {
        return getDao().findByEstado(idEstado);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Cidade findCapital(final Estado estado) {
        return getDao().findCapital(estado);
    }

    @Override
    @Transactional(readOnly = false)
    public Long save(Cidade entity) {
        if (entity.isCapital()) {
            final Cidade capital = findCapital(entity.getEstado());
            if (capital != null) {
                throw new IllegalArgumentException("Já existe capital para " + entity.getEstado().getNome());
            }
        }
        return super.save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public Long saveOrUpdate(Cidade entity) {
        if (entity.isCapital()) {
            final Cidade capital = findCapital(entity.getEstado());
            if (capital != null && !capital.getId().equals(entity.getId())) {
                throw new IllegalArgumentException("Já existe capital para " + entity.getEstado().getNome());
            }
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void insertDefaultData() {
        getDao().saveOrUpdateAll(createCidades());
    }

    private Collection<Cidade> createCidades() {
        try {
            final Collection<Cidade> cidades = new ArrayList<Cidade>();
            Collection<Estado> estados = estadoService.findAll();
            for (Estado estado : estados) {
                Resource res = new ClassPathResource("/br/msf/commons/persistence/data/Cities_BR_" + estado.getSigla() + ".txt");
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
