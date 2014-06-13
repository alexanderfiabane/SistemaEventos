package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.CorCamisetaDao;
import br.esp.sysevent.core.model.CorCamiseta;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CorCamisetaServiceBean extends AbstractEntityServiceBean<Long, CorCamiseta> implements CorCamisetaService {

    @Override
    protected CorCamisetaDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final CorCamisetaDao dao) {
        super.setDao(dao);
    }

    @Override
    @Transactional(readOnly = false)
    public void insertDefaultData() {
        final Collection<CorCamiseta> cores = new ArrayList<CorCamiseta>();
        CorCamiseta c;

        c = new CorCamiseta();
        c.setDescricao("Verde");
        cores.add(c);

        c = new CorCamiseta();
        c.setDescricao("Amarelo");
        cores.add(c);

        c = new CorCamiseta();
        c.setDescricao("Azul");
        cores.add(c);

        c = new CorCamiseta();
        c.setDescricao("Branco");
        cores.add(c);

        c = new CorCamiseta();
        c.setDescricao("Vermelho");
        cores.add(c);

        c = new CorCamiseta();
        c.setDescricao("Cinza");
        cores.add(c);

        c = new CorCamiseta();
        c.setDescricao("Preto");
        cores.add(c);
        saveOrUpdateAll(cores);
    }
}
