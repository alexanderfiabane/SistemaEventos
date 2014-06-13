/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.model;

import java.util.Arrays;
import java.util.Collection;

/**
 * Sexo da Pessoa.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public enum Sexo {

    MASCULINO("M", "Masculino"),
    FEMININO("F", "Feminino");
    private final String sigla;
    private final String descricao;

    private Sexo(final String sigla, final String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getName() {
        return name();
    }

    @Override
    public String toString() {
        return getDescricao();
    }

    public static Collection<Sexo> getValues() {
        return Arrays.asList(values());
    }
}
