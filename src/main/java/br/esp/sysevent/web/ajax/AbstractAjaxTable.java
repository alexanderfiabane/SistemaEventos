/*
 * Copyright (c) 2013, CPD-UFSM. All Rights Reserved.
 */
package br.ufsm.cpd.sie.core.ajax;

import java.io.Serializable;

/**
 * Classe base para paginação ajax.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 * @param <T> Tipo de retorno do search().
 */
public abstract class AbstractAjaxTable<T extends Serializable> extends AbstractAjaxFinder<T> implements AjaxTable<T>{
}
