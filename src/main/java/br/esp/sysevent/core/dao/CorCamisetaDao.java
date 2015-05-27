package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.CorCamiseta;
import com.javaleks.commons.core.dao.EntityDao;

public interface CorCamisetaDao extends BaseSistemaDao<Long, CorCamiseta> {

    public void insertDefaultData();
}
