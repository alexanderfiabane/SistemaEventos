/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Sexo;
import br.ojimarcius.commons.persistence.dao.AbstractEntityDaoBean;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository
public class ConfraternistaDaoBean extends AbstractEntityDaoBean<Long, Confraternista> implements ConfraternistaDao {

    @Override
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Collection<Confraternista> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order) {
        final DetachedCriteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.createAlias("pessoa", "pessoa");
        if (caseSensitive) {
            c.add(Restrictions.like("pessoa.nome", nome, matchMode));
        } else {
            c.add(Restrictions.ilike("pessoa.nome", nome, matchMode));
        }
        return findByCriteria(c, order);
    }

    @Override
    public Collection<Confraternista> findByDormitorio(final Dormitorio dormitorio) {

        final DetachedCriteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.add(Restrictions.eq("dormitorio", dormitorio));

        return findByCriteria(c);
    }

    @Override
    public Collection<Confraternista> findByDormitorio(final Long idDormitorio, final Order order) {

        final DetachedCriteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.createAlias("dormitorio", "dormitorio");
        c.createAlias("pessoa", "pessoa");
        c.createAlias("pessoa.endereco", "endereco");
        c.createAlias("endereco.cidade", "cidade");
        c.add(Restrictions.eq("dormitorio.id", idDormitorio));
        return findByCriteria(c, order);
    }

    @Override
    public Collection<Confraternista> findBySemDormitorio(Order order) {

        final DetachedCriteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.add(Restrictions.isNull("dormitorio"));
        c.createAlias("pessoa", "pessoa");

        return findByCriteria(c, order);
    }

    @Override
    public Collection<Confraternista> findBySexoSemDormitorio(Sexo genero, Order order) {
//        final StringBuilder builder = new StringBuilder(400);
//        builder
//                .append("select i.confraternista ")
//                .append("from Inscricao i ")
//                .append("join i.confraternista c ")
//                .append("join fetch c.pessoa pessoa ")                                
//                .append("where pessoa.sexo = :genero ")                
//                .append("and i.status in (:status) ")
//                .append("order by :order ");
//
//        return getCurrentSession().createQuery(builder.toString())
//                .setEntity("confraternista", Confraternista.class)
//                .setParameter("genero", genero)
//                .setParameterList("status", new Inscricao.Status[] {Inscricao.Status.AGUARDANDO_PAGAMENTO, Inscricao.Status.EFETIVADA})
//                .setParameter("order", order)
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
//                .list();

        final DetachedCriteria c = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("pessoa", "pessoa")
                .add(Restrictions.isNull("dormitorio"))
                .add(Restrictions.eq("pessoa.sexo", genero));
//                .add(Restrictions.or(
//                    Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
//                    Restrictions.eq("status", Inscricao.Status.EFETIVADA));        

        return findByCriteria(c, order);
    }

}
