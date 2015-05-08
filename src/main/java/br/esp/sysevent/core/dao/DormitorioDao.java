/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Dormitorio;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Sexo;
import java.util.Collection;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
public interface DormitorioDao extends BaseTaperaDao<Long, Dormitorio> {

    public void alocaConfraternistaDormitorio(Confraternista confraternista, Dormitorio dormitorio);
    public void alocaConfraternistasDormitorio(Collection<Confraternista> confraternistas, Dormitorio dormitorio);
    public void desalocaConfraternistaDormitorio(Confraternista confraternista);
    public void desalocaConfraternistasDormitorio(Collection<Confraternista> confraternistas);
    public void alocaConfraternistasAleatoriamente(final Edicao edicao);
    public Collection<Dormitorio> findBySexoEdicao(Sexo sexo, Edicao edicao);
    public Collection<Dormitorio> findBySexo(Sexo sexo);
    
}
