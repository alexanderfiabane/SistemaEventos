/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.Documento;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;
import br.ojimarcius.commons.persistence.dao.EntityDao;
import java.util.Calendar;
import java.util.Collection;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
public interface InscricaoDao extends EntityDao<Long, Inscricao> {

    @Override
    public Long saveOrUpdate(Inscricao entity);

    public Inscricao findById(final Long id, final String[] initProps);
    public Collection<Inscricao> findByEdicao(final Long idEdicao);
    public Collection<Inscricao> findByEdicaoCidadeEstado(final Edicao Edicao);
    public Collection<Inscricao> findByEdicaoOficina(final Edicao edicao);
    public Collection<Inscricao> findByEdicaoCamiseta(final Edicao edicao);
    public Collection<Inscricao> findByEdicaoTipo(final Edicao edicao);
    public Collection<Inscricao> findByEdicaoSexo(final Edicao edicao);
    public Inscricao findByEdicaoDocumentos(final Long idEdicao, Documento documento);
    public Collection<Inscricao> findByUsuario(Usuario usuario);
    public Collection<Inscricao> findByEdicaoDormitorio(final Edicao edicao);
    public Collection<Inscricao> findByEdicaoDeferidas(Long idEdicao);
}
