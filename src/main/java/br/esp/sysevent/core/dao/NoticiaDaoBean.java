/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Noticia;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import com.javaleks.commons.core.dao.annotation.PreSave;
import com.javaleks.commons.core.dao.annotation.PreSaveOrUpdate;
import com.javaleks.commons.util.CalendarUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alexander
 */
@Repository
public class NoticiaDaoBean extends BaseTaperaDaoBean<Long, Noticia> implements NoticiaDao {

    @Autowired
    public NoticiaDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @PreSave
    @PreSaveOrUpdate
    protected void checkData(final Noticia entity) {
        // set the date if necessary
        if (entity.getData() == null) {
            entity.setData(CalendarUtils.today());
        }
    }
}
