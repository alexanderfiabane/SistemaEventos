package br.esp.sysevent.web.ajax;

import java.io.Serializable;

/**
 * Classe base para pagina��o ajax.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 * @param <T> Tipo de retorno do search().
 */
public abstract class AbstractAjaxTable<T extends Serializable> extends AbstractAjaxFinder<T> implements AjaxTable<T>{
}
