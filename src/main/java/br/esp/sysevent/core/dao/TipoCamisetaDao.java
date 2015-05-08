package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.TipoCamiseta;

public interface TipoCamisetaDao extends BaseTaperaDao<Long, TipoCamiseta> {

    public void insertDefaultData();

}
