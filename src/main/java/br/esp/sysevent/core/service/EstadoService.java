/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Estado;
import br.ojimarcius.commons.persistence.service.EntityService;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface EstadoService extends EntityService<Long, Estado> {

    public Estado findBySigla(final String sigla);

    public boolean exists(final String sigla);

    /**
     * Insere os 26 estados brasileiros mais o distrito federal no banco de dados.
     */
    public void insertDefaultData();
}
