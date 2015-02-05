/*
 * Copyright (c) 2013, CPD-UFSM. All Rights Reserved.
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
public abstract class AbstractAjaxFinder<T extends Serializable> extends AbstractAjaxService implements AjaxFinder<T>{
   /**
     * Deve retornar um mapa, contendo o nome do field como chave, e a msg de erro de sua validação.
     * <p/>
     * Retorno vazio ou nulo significa sem erros de validação.
     *
     * @param params A coleção de parametros da pesquisa.
     * @return Um mapa, contendo o nome do field como chave, e a msg de erro de sua validação.
     */
    @Override
    public Map<String, String> validate(final Map<String, String> params) {
        return null;
    }
}
