/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.NoticiaDao;
import br.esp.sysevent.core.model.Noticia;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import br.msf.commons.util.CalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alexander
 */
@Service
public class NoticiaServiceBean extends AbstractEntityServiceBean<Long, Noticia> implements NoticiaService {

    @Override
    protected NoticiaDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final NoticiaDao dao) {
        super.setDao(dao);
    }

    @Override
    @Transactional(readOnly = false)
    public Long save(final Noticia entity) {
        // set the date if necessary
        if (entity.getData() == null) {
            entity.setData(CalendarUtils.today());
        }
        return super.save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public Long saveOrUpdate(final Noticia entity) {
        // set the date if necessary
        if (entity.getData() == null) {
            entity.setData(CalendarUtils.today());
        }
        return super.saveOrUpdate(entity);
    }
}
