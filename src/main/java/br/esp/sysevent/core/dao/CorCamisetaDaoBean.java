package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.CorCamiseta;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CorCamisetaDaoBean extends AbstractEntityDao<Long, CorCamiseta> implements CorCamisetaDao {

    @Autowired
    public CorCamisetaDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
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
