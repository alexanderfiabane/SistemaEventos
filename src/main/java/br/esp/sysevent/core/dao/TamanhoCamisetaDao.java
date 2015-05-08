package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.TamanhoCamiseta;

public interface TamanhoCamisetaDao extends BaseTaperaDao<Long, TamanhoCamiseta> {

    public void insertDefaultData();
}
