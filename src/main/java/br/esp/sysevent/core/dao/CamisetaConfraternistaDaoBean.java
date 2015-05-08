/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Inscricao;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository(value = "camisetaConfraternistaDao")
public class CamisetaConfraternistaDaoBean extends AbstractEntityDao<Long, CamisetaConfraternista> implements CamisetaConfraternistaDao {

    @Autowired
    public CamisetaConfraternistaDaoBean(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Collection<CamisetaConfraternista> findByConfraternista(final Confraternista confraternista) {
        final Criteria c = createCriteria().
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
                add(Restrictions.eq("confraternista", confraternista));
        return findByCriteria(c);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<CamisetaConfraternista> findByEdicao(final Edicao edicao) {
        final StringBuilder builder = new StringBuilder(400);
        builder
                .append("select camisetas ")
                .append("from Inscricao i ")
                .append("join i.confraternista confraternista ")
                .append("join confraternista.camisetas camisetas ")
                .append("where i.edicaoEvento = :edicao ")
                .append("and i.status in (:status) ")
                .append("order by camisetas.corCamiseta, camisetas.tamanhoCamiseta ");

        return getCurrentSession().createQuery(builder.toString())
                .setEntity("edicao", edicao)
                .setParameterList("status", new Inscricao.Status[]{Inscricao.Status.AGUARDANDO_PAGAMENTO, Inscricao.Status.EFETIVADA})
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();

    }
}
