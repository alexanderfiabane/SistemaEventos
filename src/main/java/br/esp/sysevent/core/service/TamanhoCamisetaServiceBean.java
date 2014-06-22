package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.TamanhoCamisetaDao;
import br.esp.sysevent.core.model.TamanhoCamiseta;
import br.ojimarcius.commons.persistence.service.AbstractEntityServiceBean;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TamanhoCamisetaServiceBean extends AbstractEntityServiceBean<Long, TamanhoCamiseta> implements TamanhoCamisetaService {

    @Override
    protected TamanhoCamisetaDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final TamanhoCamisetaDao dao) {
        super.setDao(dao);
    }

    @Override
    @Transactional(readOnly = false)
    public void insertDefaultData() {
        final Collection<TamanhoCamiseta> tamanhos = new ArrayList<TamanhoCamiseta>();
        TamanhoCamiseta tam;

        tam = new TamanhoCamiseta();
        tam.setDescricao("[PP] Pequeno");
        tamanhos.add(tam);
        
        tam = new TamanhoCamiseta();
        tam.setDescricao("[P] Pequeno");
        tamanhos.add(tam);

        tam = new TamanhoCamiseta();
        tam.setDescricao("[M] Médio");
        tamanhos.add(tam);

        tam = new TamanhoCamiseta();
        tam.setDescricao("[G] Grande");
        tamanhos.add(tam);

        tam = new TamanhoCamiseta();
        tam.setDescricao("[GG] Grande");
        tamanhos.add(tam);

        tam = new TamanhoCamiseta();
        tam.setDescricao("[XGG] Extra Grande");
        tamanhos.add(tam);
        
        saveOrUpdateAll(tamanhos);
    }
}
