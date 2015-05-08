package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.TipoCamiseta;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TipoCamisetaDaoBean extends AbstractEntityDao<Long, TipoCamiseta> implements TipoCamisetaDao {

    @Autowired
    public TipoCamisetaDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(readOnly = false)
    public void insertDefaultData() {
        final Collection<TipoCamiseta> tipos = new ArrayList<TipoCamiseta>();
        TipoCamiseta tipo;

        tipo = new TipoCamiseta();
        tipo.setDescricao("Regata");
        tipos.add(tipo);

        tipo = new TipoCamiseta();
        tipo.setDescricao("Sem Manga");
        tipos.add(tipo);

        tipo = new TipoCamiseta();
        tipo.setDescricao("Manga curta (gola redonda)");
        tipos.add(tipo);

        tipo = new TipoCamiseta();
        tipo.setDescricao("Manga curta (gola V)");
        tipos.add(tipo);

        tipo = new TipoCamiseta();
        tipo.setDescricao("Manga curta (gola polo)");
        tipos.add(tipo);

        tipo = new TipoCamiseta();
        tipo.setDescricao("Manga longa");
        tipos.add(tipo);

        saveOrUpdateAll(tipos);
    }
}
