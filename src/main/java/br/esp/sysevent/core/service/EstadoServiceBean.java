/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.EstadoDao;
import br.esp.sysevent.core.model.Estado;
import br.ojimarcius.commons.io.exception.RuntimeIOException;
import br.ojimarcius.commons.persistence.service.AbstractEntityServiceBean;
import br.ojimarcius.commons.util.ArrayUtils;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.IOUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
public class EstadoServiceBean extends AbstractEntityServiceBean<Long, Estado> implements EstadoService {

    @Override
    protected EstadoDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final EstadoDao dao) {
        super.setDao(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public Estado findBySigla(final String sigla) {
        return getDao().findBySigla(sigla);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(final String sigla) {
        return findBySigla(sigla) != null;
    }

    @Override
    @Transactional(readOnly = false)
    public void insertDefaultData() {
        getDao().saveOrUpdateAll(createEstados());
    }

    private Collection<Estado> createEstados() {
        try {
            final Collection<Estado> estados = new ArrayList<Estado>();
            Resource res = new ClassPathResource("/br/ojimarcius/commons/persistence/data/States_BR.txt");
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
