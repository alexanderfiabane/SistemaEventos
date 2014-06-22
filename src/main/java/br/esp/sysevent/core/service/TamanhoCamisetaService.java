package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.TamanhoCamiseta;
import br.ojimarcius.commons.persistence.service.EntityService;

public interface TamanhoCamisetaService extends EntityService<Long, TamanhoCamiseta> {

    public void insertDefaultData();
}
