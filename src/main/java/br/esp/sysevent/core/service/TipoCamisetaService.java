package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.TipoCamiseta;
import br.ojimarcius.commons.persistence.service.EntityService;

public interface TipoCamisetaService extends EntityService<Long, TipoCamiseta> {

    public void insertDefaultData();
}
