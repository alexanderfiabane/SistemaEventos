/*
 * Copyright (c) 2013, CPD-UFSM. All Rights Reserved.
 */
package br.ufsm.cpd.sie.core.ajax;

import java.io.Serializable;
import java.util.Map;

/**
 * Classe base para pagina��o ajax.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 * @param <T> Tipo de retorno do search().
 */
public abstract class AbstractAjaxFinder<T extends Serializable> extends AbstractAjaxService implements AjaxFinder<T>{
   /**
     * Deve retornar um mapa, contendo o nome do field como chave, e a msg de erro de sua valida��o.
     * <p/>
     * Retorno vazio ou nulo significa sem erros de valida��o.
     *
     * @param params A cole��o de parametros da pesquisa.
     * @return Um mapa, contendo o nome do field como chave, e a msg de erro de sua valida��o.
     */
    @Override
    public Map<String, String> validate(final Map<String, String> params) {
        return null;
    }
}
