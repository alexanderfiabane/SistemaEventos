/*
 * Copyright (c) 2014, CPD-UFSM. All Rights Reserved.
 */

package br.ufsm.cpd.sie.core.ajax;

import java.io.Serializable;
import java.util.Map;

/**
 * Classe base para paginação ajax.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 * @param <T> Tipo de retorno do search().
 */
public interface AjaxTable<T extends Serializable> extends AjaxFinder<T> {
    
    /**
     * Deve retornar a contagem total dos itens da pesquisa.
     *
     * @param params A coleção de parametros da pesquisa.
     * @return A contagem total dos itens da pesquisa.
     * @throws java.lang.Exception Caso ocorra algo inesperado.
     */
    public abstract long count(final Map<String, String> params) throws Exception;
}
