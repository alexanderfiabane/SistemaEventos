/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Documento;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Usuario;
import br.msf.commons.persistence.service.EntityService;
import java.util.Collection;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
public interface InscricaoService extends EntityService<Long, Inscricao> {

    public Long gravaInscricao(final Inscricao inscricao);

    public Inscricao findById(final Long id, final String[] initProps);
    public Collection<Inscricao> findByEdicao(final Long idEdicao);
    public Collection<Inscricao> findByEdicaoDeferidas(final Long idEdicao);
    public Collection<Inscricao> findByEdicaoCidadeEstado(Edicao edicao);
    public Collection<Inscricao> findByEdicaoOficina(final Edicao edicao);
    public Collection<Inscricao> findByEdicaoCamiseta(final Edicao edicao);
    public Collection<Inscricao> findByEdicaoTipo(Edicao edicao);
    public Collection<Inscricao> findByEdicaoSexo(Edicao edicao);
    public Inscricao findByEdicaoDocumentos(final Long idEdicao, Documento documento);
    public Collection<Inscricao> findByUsuario(Usuario usuario);
    public Collection<Inscricao> findByEdicaoDormitorio(final Edicao edicao);
    
}