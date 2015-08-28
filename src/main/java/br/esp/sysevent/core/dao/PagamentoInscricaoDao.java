/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.PagamentoInscricao;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
public interface PagamentoInscricaoDao extends BaseSistemaDao<Long, PagamentoInscricao> {

    public PagamentoInscricao findByInscricao(Inscricao inscricao);
    public PagamentoInscricao findByInscricao(Inscricao inscricao, PagamentoInscricao.PagSeguroStatus status);
    public PagamentoInscricao findByCodPagamento(String codPagamento);
}
