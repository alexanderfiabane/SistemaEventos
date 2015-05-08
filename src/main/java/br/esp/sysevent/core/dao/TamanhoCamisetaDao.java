package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.TamanhoCamiseta;
import com.javaleks.commons.core.dao.EntityDao;

public interface TamanhoCamisetaDao extends EntityDao<Long, TamanhoCamiseta> {

    public void insertDefaultData();
}
