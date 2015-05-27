package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.TamanhoCamiseta;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "tamanhoCamisetaDao")
public class TamanhoCamisetaDaoBean extends AbstractBaseSistemaDaoBean<Long, TamanhoCamiseta> implements TamanhoCamisetaDao {

    @Autowired
    public TamanhoCamisetaDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
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
