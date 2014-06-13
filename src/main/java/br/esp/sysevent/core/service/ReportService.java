/*
 * Copyright (c) 2009-2013 CPD-UFSM. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Inscricao;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Giuliano Ferreira <giuliano@ufsm.br>
 */
public interface ReportService {

    public byte[] geraRelatorio(final Inscricao inscricao) throws Exception;
    public byte[] geraRelatorio(Collection<?> colecao, String relatorio) throws Exception;
    public byte[] geraRelatorio(Collection<?> colecao, String relatorio, Map<String, Object> parametros) throws Exception;

}
