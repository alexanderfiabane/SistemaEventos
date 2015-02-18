package br.esp.sysevent.web.ajax;

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
     * @param firstResult O �ndice do primeiro resultado a ser buscado.
     * @param maxResults  O m�ximo de resultados por p�gina.
     * @param params      A cole��o de parametros da pesquisa.
     * @return O resultado da pesquisa.
     * @throws java.lang.Exception Caso ocorra algo inesperado.
     */
    public abstract Collection<T> search(final int firstResult, final int maxResults, final Map<String, String> params) throws Exception;

    /**
     * Deve retornar um mapa, contendo o nome do field como chave, e a msg de erro de sua valida��o.
     * <p/>
     * Retorno vazio ou nulo significa sem erros de valida��o.
     *
     * @param params A cole��o de parametros da pesquisa.
     * @return Um mapa, contendo o nome do field como chave, e a msg de erro de sua valida��o.
     */
    public Map<String, String> validate(final Map<String, String> params);
}
