/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Documento;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;
import java.util.Calendar;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository
public class InscricaoDaoBean extends AbstractEntityDao<Long, Inscricao> implements InscricaoDao {

    @Override
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long saveOrUpdate(Inscricao entity) {
        if(entity.getId() == null) {
            getCurrentSession().persist(entity);
        } else {
            getCurrentSession().merge(entity);
        }
        return entity.getId();
    }

    @Override
    public Inscricao findById(final Long id, final String[] initProps) {
        final Inscricao inscricao = findById(id);
        initialize(inscricao, initProps);
        return inscricao;
    }

    @Override
    public Collection<Inscricao> findByEdicao(final Long idEdicao){
        final DetachedCriteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista.pessoa", "pessoa")
                .add(Restrictions.eq("edicaoEvento.id", idEdicao));
        return findByCriteria(criteria
                , Order.asc("pessoa.nome"));
    }
    
    @Override
    public Collection<Inscricao> findByEdicaoDeferidas(final Long idEdicao){
        final DetachedCriteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista.pessoa", "pessoa")
                .createAlias("pessoa.endereco", "endereco")
                .createAlias("endereco.cidade", "cidade")
                .createAlias("cidade.estado", "estado")
                .add(Restrictions.eq("edicaoEvento.id", idEdicao))
                .add(Restrictions.or(
                    Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                    Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                ));
        return findByCriteria(criteria
                , Order.asc("cidade.nome"));
    }

    @Override
    public Collection<Inscricao> findByEdicaoCidadeEstado(final Edicao edicao){
        final DetachedCriteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista.pessoa", "pessoa")
                .createAlias("pessoa.endereco", "endereco")
                .createAlias("endereco.cidade", "cidade")
                .createAlias("cidade.estado", "estado")
                .add(Restrictions.eq("edicaoEvento", edicao))
                .add(Restrictions.or(
                    Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                    Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                ));
        return findByCriteria(criteria
                , Order.asc("estado.nome")
                , Order.asc("cidade.nome")
                , Order.asc("pessoa.nome"));
    }

    @Override
    public Collection<Inscricao> findByEdicaoTipo(final Edicao edicao){
        final DetachedCriteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista", "confraternista")
                .createAlias("confraternista.pessoa", "pessoa")
                .add(Restrictions.eq("edicaoEvento", edicao))
                .add(Restrictions.or(
                    Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                    Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                ));
        return findByCriteria(criteria
                , Order.asc("confraternista.tipo")
                , Order.asc("pessoa.nome"));
    }
    
    @Override
    public Collection<Inscricao> findByEdicaoDormitorio(final Edicao edicao){
        final DetachedCriteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista", "confraternista")
                .createAlias("confraternista.pessoa", "pessoa")
                .createAlias("confraternista.dormitorio", "dormitorio")
                .add(Restrictions.eq("edicaoEvento", edicao))
                .add(Restrictions.or(
                    Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                    Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                ));
        return findByCriteria(criteria
                , Order.asc("dormitorio.sexo")
                , Order.asc("dormitorio.nome")
                , Order.asc("pessoa.nome"));
    }

    @Override
    public Collection<Inscricao> findByEdicaoSexo(final Edicao edicao){
        final DetachedCriteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista", "confraternista")
                .createAlias("confraternista.pessoa", "pessoa")
                .add(Restrictions.eq("edicaoEvento", edicao))                
                .add(Restrictions.or(
                    Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                    Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                ));
        return findByCriteria(criteria
                , Order.asc("pessoa.sexo")
                , Order.asc("pessoa.nome"));
    }

    @Override
    public Collection<Inscricao> findByEdicaoCamiseta(final Edicao edicao){
        
        final StringBuilder builder = new StringBuilder(400);
        builder
                .append("select i ")
                .append("from Inscricao i ")
                .append("join fetch i.confraternista confraternista ")
                .append("join fetch confraternista.pessoa pessoa ")
                .append("join fetch confraternista.oficina oficina ")
                .append("join fetch confraternista.camisetas camisetas ")
                .append("where i.edicaoEvento = :edicao ")                
                .append("and i.status in (:status) ")
                .append("order by pessoa.nome ");

        return getCurrentSession().createQuery(builder.toString())
                .setEntity("edicao", edicao)                
                .setParameterList("status", new Inscricao.Status[] {Inscricao.Status.AGUARDANDO_PAGAMENTO, Inscricao.Status.EFETIVADA})
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        
//        final DetachedCriteria criteria = createCriteria()
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
//                .createAlias("confraternista", "confraternista")
//                .createAlias("confraternista.pessoa", "pessoa")
//                .add(Restrictions.eq("edicaoEvento", edicao))
//                .add(Restrictions.ne("status", Inscricao.Status.INDEFERIDA));
//        return findByCriteria(criteria
//                , Order.asc("pessoa.nome"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Inscricao> findByEdicaoOficina(final Edicao edicao){
        final StringBuilder builder = new StringBuilder(400);
        builder
                .append("select i ")
                .append("from Inscricao i ")
                .append("join fetch i.confraternista confraternista ")
                .append("join fetch confraternista.pessoa pessoa ")
                .append("join fetch confraternista.oficina oficina ")
                .append("join fetch oficina.oficineiros oficineiro ")
                .append("where i.edicaoEvento = :edicao ")
                .append("and confraternista.tipo in (:tipo) ")
                .append("and i.status in (:status) ")
                .append("order by oficina.nome, pessoa.nome ");

        return getCurrentSession().createQuery(builder.toString())
                .setEntity("edicao", edicao)
                .setParameterList("tipo", new Confraternista.Tipo[]{Confraternista.Tipo.CONFRATERNISTA, Confraternista.Tipo.COORDENADOR})
                .setParameterList("status", new Inscricao.Status[] {Inscricao.Status.AGUARDANDO_PAGAMENTO, Inscricao.Status.EFETIVADA})
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();

//        final DetachedCriteria criteria = createCriteria()
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
//                .createAlias("confraternista", "confraternista")
//                .createAlias("confraternista.pessoa", "pessoa")
//                .createAlias("confraternista.oficina", "oficina")
//                .add(Restrictions.eq("edicaoEvento", edicao))
//                .add(Restrictions.eq("confraternista.tipo", Confraternista.Tipo.CONFRATERNISTA))
//                .add(Restrictions.or(
//                    Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
//                    Restrictions.eq("status", Inscricao.Status.EFETIVADA)
//                ));
//        return findByCriteria(criteria
//                , Order.asc("oficina.nome")
//                , Order.asc("pessoa.nome"));
    }

    @Override
    public Inscricao findByEdicaoDocumentos(final Long idEdicao, Documento documento){
        final DetachedCriteria criteria = createCriteria().
                createAlias("confraternista.pessoa", "pessoa").
                createAlias("pessoa.documentos", "documentos").
                add(Restrictions.eq("edicaoEvento.id", idEdicao)).
//                add(Restrictions.isNotNull("documentos.cpf")).
//                add(Restrictions.isNotNull("documentos.rg")).
                add(Restrictions.or(
                    Restrictions.eq("documentos.cpf", documento.getCpf()),
                    Restrictions.eq("documentos.rg", documento.getRg())
                ));

        return DataAccessUtils.uniqueResult(findByCriteria(criteria));
    }

    @Override
    public Collection<Inscricao> findByUsuario(Usuario usuario){
        final DetachedCriteria criteria = createCriteria().
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
                createCriteria("confraternista").
                add(Restrictions.eq("pessoa", usuario.getPessoa()));
        return findByCriteria(criteria);
    }
}
