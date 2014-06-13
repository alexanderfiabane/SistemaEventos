package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.TipoCamisetaDao;
import br.esp.sysevent.core.model.TipoCamiseta;
import br.msf.commons.persistence.service.AbstractEntityServiceBean;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoCamisetaServiceBean extends AbstractEntityServiceBean<Long, TipoCamiseta> implements TipoCamisetaService {

    @Override
    protected TipoCamisetaDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final TipoCamisetaDao dao) {
        super.setDao(dao);
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
