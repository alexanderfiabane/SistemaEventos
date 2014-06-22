package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.CorCamiseta;
import br.ojimarcius.commons.persistence.service.EntityService;

public interface CorCamisetaService extends EntityService<Long, CorCamiseta> {

    public void insertDefaultData();
}
