package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.TipoCamiseta;

public interface TipoCamisetaDao extends BaseSistemaDao<Long, TipoCamiseta> {

    public void insertDefaultData();

}
