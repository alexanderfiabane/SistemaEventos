package br.esp.sysevent.web.ajax;

import java.io.Serializable;
import java.util.Map;

/**
 * Classe base para pagina��o ajax.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 * @param <T> Tipo de retorno do search().
 */
public interface AjaxTable<T extends Serializable> extends AjaxFinder<T> {
    
    /**
     * Deve retornar a contagem total dos itens da pesquisa.
     *
     * @param params A cole��o de parametros da pesquisa.
     * @return A contagem total dos itens da pesquisa.
     * @throws java.lang.Exception Caso ocorra algo inesperado.
     */
    public abstract long count(final Map<String, String> params) throws Exception;
}
