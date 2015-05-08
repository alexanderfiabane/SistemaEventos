/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.model.Sexo;
import com.javaleks.commons.core.dao.AbstractEntityDao;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository
public class ConfraternistaDaoBean extends AbstractEntityDao<Long, Confraternista> implements ConfraternistaDao {

    @Autowired
    public ConfraternistaDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Collection<Confraternista> findByNome(final String nome, final MatchMode matchMode, final boolean caseSensitive, final Order order) {
        final Criteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.createAlias("pessoa", "pessoa");
        if (caseSensitive) {
            c.add(Restrictions.like("pessoa.nome", nome, matchMode));
        } else {
            c.add(Restrictions.ilike("pessoa.nome", nome, matchMode));
        }
        c.addOrder(order);
        return findByCriteria(c);
    }

    @Override
    public Collection<Confraternista> findByDormitorio(final Dormitorio dormitorio) {

        final Criteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.add(Restrictions.eq("dormitorio", dormitorio));

        return findByCriteria(c);
    }

    @Override
    public Collection<Confraternista> findByDormitorio(final Long idDormitorio, final Order order) {

        final Criteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.createAlias("dormitorio", "dormitorio");
        c.createAlias("pessoa", "pessoa");
        c.createAlias("pessoa.endereco", "endereco");
        c.createAlias("endereco.cidade", "cidade");
        c.add(Restrictions.eq("dormitorio.id", idDormitorio));
        c.addOrder(order);
        return findByCriteria(c);
    }

    @Override
    public Collection<Confraternista> findBySemDormitorio(Order order) {

        final Criteria c = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.add(Restrictions.isNull("dormitorio"));
        c.createAlias("pessoa", "pessoa");
        c.addOrder(order);
        return findByCriteria(c);
    }

    @Override
    public Collection<Confraternista> findBySexoSemDormitorio(Sexo genero, Order order) {

        final Criteria c = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("pessoa", "pessoa")
                .add(Restrictions.isNull("dormitorio"))
                .add(Restrictions.eq("pessoa.sexo", genero))
                .addOrder(order);

        return findByCriteria(c);
    }

    @Override
    public Collection<Confraternista> findFacilitadoresByGrupo(GrupoIdade grupoIdade) {
        final StringBuilder builder = new StringBuilder(400);
        builder
                .append("select c ")
                .append("from Confraternista c ")
                .append("join fetch c.pessoa pessoa ")
                .append("join fetch c.grupoIdade grupoIdade ")
                .append("where grupoIdade = :grupoIdade ")
                .append("and c.tipo = 'FACILITADOR' ")
                .append("order by pessoa.nome ");

        return getCurrentSession().createQuery(builder.toString())
                .setEntity("grupoIdade", grupoIdade)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    public Collection<Confraternista> findBySexoSemDormitorioEdicao(Sexo genero, Edicao edicao) {
        Map map = new HashMap();
        map.put("sexo", genero);
        map.put("edicaoEvento", edicao);
        Collection<Confraternista> confraternistas =  findByProperties(map, Order.asc("pessoa.nome"));
        Collection<Confraternista> confraternistasSemDormitorio = new HashSet<Confraternista>();
        for (Confraternista confraternista : confraternistas) {
            if (confraternista.getDormitorio()== null){
                confraternistasSemDormitorio.add(confraternista);
            }
        }
        return confraternistasSemDormitorio;
    }

}
