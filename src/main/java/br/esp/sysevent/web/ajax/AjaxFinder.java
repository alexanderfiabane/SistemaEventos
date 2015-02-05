/*
 * Copyright (c) 2014, CPD-UFSM. All Rights Reserved.
 */
package br.ufsm.cpd.sie.core.ajax;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Interface para Localizador ajax.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 * @param <T> Tipo de retorno do search().
 */
public interface AjaxFinder<T extends Serializable> {

    /**
     * Efetua a pesquisa, trazendo os itens.
     *
     * @param firstResult O índice do primeiro resultado a ser buscado.
     * @param maxResults  O máximo de resultados por página.
     * @param params      A coleção de parametros da pesquisa.
     * @return O resultado da pesquisa.
     * @throws java.lang.Exception Caso ocorra algo inesperado.
     */
    public abstract Collection<T> search(final int firstResult, final int maxResults, final Map<String, String> params) throws Exception;

    /**
     * Deve retornar um mapa, contendo o nome do field como chave, e a msg de erro de sua validação.
     * <p/>
     * Retorno vazio ou nulo significa sem erros de validação.
     *
     * @param params A coleção de parametros da pesquisa.
     * @return Um mapa, contendo o nome do field como chave, e a msg de erro de sua validação.
     */
    public Map<String, String> validate(final Map<String, String> params);
}
