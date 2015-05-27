package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.TamanhoCamiseta;

public interface TamanhoCamisetaDao extends BaseSistemaDao<Long, TamanhoCamiseta> {

    public void insertDefaultData();
}
