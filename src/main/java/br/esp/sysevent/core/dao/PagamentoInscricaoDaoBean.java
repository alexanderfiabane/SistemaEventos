/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.ImagemArquivo;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.PagamentoInscricao;
import com.javaleks.commons.util.CharSequenceUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
@Repository(value = "pagamentoInscricaoDao")
public class PagamentoInscricaoDaoBean extends AbstractBaseSistemaDaoBean<Long, PagamentoInscricao> implements PagamentoInscricaoDao {

    @Autowired
    public PagamentoInscricaoDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public PagamentoInscricao findByInscricao(Inscricao inscricao){
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("inscricao", inscricao));
        return findUniqueByCriteria(criteria);
    }

    @Override
    public PagamentoInscricao findByInscricao(Inscricao inscricao, PagamentoInscricao.PagSeguroStatus status){
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("inscricao", inscricao))
                .add(Restrictions.eq("status", status));
        return findUniqueByCriteria(criteria);
    }

    @Override
    public ImagemArquivo findComprovante(Long idPagamentoInscricao){
        PagamentoInscricao pagamentoInscricao = findById(idPagamentoInscricao);
        return pagamentoInscricao.getComprovante();
    }

    @Override
    public PagamentoInscricao findByCodPagamento(String codPagamento){
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("codPagamento", codPagamento));
        return findUniqueByCriteria(criteria);
    }

    @Override
    public PagamentoInscricao findByCodReferencia(String codReference){
        String idInscricao = CharSequenceUtils.subStringAfterFirst("SE", codReference);
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("inscricao.id", Long.parseLong(idInscricao)));
        return findUniqueByCriteria(criteria);
    }
}
