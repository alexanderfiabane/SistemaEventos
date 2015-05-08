/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.GrupoIdade;
import java.util.Collection;
import java.util.HashSet;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fiabane
 */
@Repository
public class GrupoIdadeDaoBean extends BaseTaperaDaoBean<Long, GrupoIdade> implements GrupoIdadeDao{

    @Autowired
    public GrupoIdadeDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Collection<GrupoIdade> findAllByEdicao() {
        return super.findAll(new Order[] {Order.asc("nome")});
    }

    @Override
    public Collection<GrupoIdade> findByIdade(int idade){
        final DetachedCriteria c = createDetachedCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.le("idadeMinima", idade))
                .add(Restrictions.ge("idadeMaxima", idade));
        return findByCriteria(c, Order.asc("nome"));
    }

    @Override
    public Collection<GrupoIdade> findByIdadeTipo(int idade, Confraternista.Tipo tipo){
        final DetachedCriteria c = createDetachedCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.le("idadeMinima", idade))
                .add(Restrictions.ge("idadeMaxima", idade))
                .add(Restrictions.eq("tipo", tipo));
        return findByCriteria(c, Order.asc("nome"));
    }

    @Override
    public Collection<GrupoIdade> findSimilares(Long idGrupoIdade) {
        Collection<GrupoIdade> gruposIdadesSimilares = new HashSet<GrupoIdade>();
        GrupoIdade grupoIdadeSimilar = findById(idGrupoIdade);
        Collection<GrupoIdade> gruposIdades = findByIdadeTipo(grupoIdadeSimilar.getIdadeMinima(), grupoIdadeSimilar.getTipo());
        for (GrupoIdade grupoIdade : gruposIdades) {
            if(!grupoIdade.equals(grupoIdadeSimilar)){
                gruposIdadesSimilares.add(grupoIdade);
            }
        }
        return gruposIdadesSimilares;
    }
}
