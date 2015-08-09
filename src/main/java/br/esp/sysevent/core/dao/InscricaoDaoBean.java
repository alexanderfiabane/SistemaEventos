/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.dao;

import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Confraternista.Tipo;
import br.esp.sysevent.core.model.Documento;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Inscricao.Status;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.web.guest.command.InscricaoCommand;
import com.javaleks.commons.text.EnhancedStringBuilder;
import com.javaleks.commons.util.CalendarUtils;
import com.javaleks.commons.util.CharSequenceUtils;
import com.javaleks.commons.util.NumberUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Repository(value = "inscricaoDao")
public class InscricaoDaoBean extends AbstractBaseSistemaDaoBean<Long, Inscricao> implements InscricaoDao {
    
    @Autowired
    private OficinaDao oficinaDao;
    @Autowired
    private GrupoIdadeDao grupoIdadeDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private EdicaoDao edicaoDao;
    @Autowired
    private ConfraternistaDao confraternistaDao;
    
    @Autowired
    public InscricaoDaoBean(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    @Override
    public Long countInscricoes(final Long idEdicao,
            final String nomePessoa,
            final Calendar dataRecebimento,
            final Tipo tipoConfraternista,
            final Status situacaoInscricao,
            final String codPagamento,
            final Calendar dataPagamento) {
        Map<String, Object> params = new HashMap<>();
        params.put("idEdicao", idEdicao);
        EnhancedStringBuilder builder = new EnhancedStringBuilder();
        builder.appendln("select count(this.id) from ", getPersistentClass().getName(), " this").
                appendln("inner join this.edicaoEvento ed").
                appendln("inner join this.confraternista confraternista").
                appendln("inner join confraternista.pessoa pessoa").
                appendln("left join this.pagamento pag").
                appendln("where ed.id = :idEdicao");
        if (CharSequenceUtils.isNotBlank(nomePessoa)) {
            builder.appendln("  and pessoa.nome like :nomePessoa");
            params.put("nomePessoa", nomePessoa);
        }
        if (CharSequenceUtils.isNotBlank(codPagamento)) {
            builder.appendln("  and pag.codPagamento = :codPagamento");
            params.put("codPagamento", codPagamento);
        }
        if (tipoConfraternista != null) {
            builder.appendln("  and confraternista.tipo = :tipoConfraternista");
            params.put("tipoConfraternista", tipoConfraternista);
        }
        if (situacaoInscricao != null) {
            builder.appendln("  and this.status = :situacaoInscricao");
            params.put("situacaoInscricao", situacaoInscricao);
        }
        if (dataRecebimento != null) {
            builder.appendln("  and this.dataRecebimento = :dataRecebimento");
            params.put("dataRecebimento", dataRecebimento);
        }
        if (dataPagamento != null) {
            builder.appendln("  and pag.dataPagamento = :dataPagamento");
            params.put("dataPagamento", dataPagamento);
        }
        Query q = createQuery(builder);
        for (Map.Entry<String, Object> tmp : params.entrySet()) {
            q.setParameter(tmp.getKey(), tmp.getValue());
        }
        // setar params
        return NumberUtils.toLong((Number) q.uniqueResult());
    }
    
    @Override
    public Collection<Inscricao> searchInscricoes(final Long idEdicao,
            final String nomePessoa,
            final Calendar dataRecebimento,
            final Tipo tipoConfraternista,
            final Status situacaoInscricao,
            final String codPagamento,
            final Calendar dataPagamento,
            final Order order,
            final Integer firstResult,
            final Integer maxResults) {
        Map<String, Object> params = new HashMap<>();
        params.put("idEdicao", idEdicao);
        EnhancedStringBuilder builder = new EnhancedStringBuilder();
        builder.appendln("select this from ", getPersistentClass().getName(), " this").
                appendln("inner join this.edicaoEvento ed").
                appendln("inner join this.confraternista confraternista").
                appendln("inner join confraternista.pessoa pessoa").
                appendln("left join this.pagamento pag").
                appendln("where ed.id = :idEdicao");
        if (CharSequenceUtils.isNotBlank(nomePessoa)) {
            builder.appendln("  and pessoa.nome like :nomePessoa");
            params.put("nomePessoa", nomePessoa);
        }
        if (CharSequenceUtils.isNotBlank(codPagamento)) {
            builder.appendln("  and pag.codPagamento = :codPagamento");
            params.put("codPagamento", codPagamento);
        }
        if (tipoConfraternista != null) {
            builder.appendln("  and confraternista.tipo = :tipoConfraternista");
            params.put("tipoConfraternista", tipoConfraternista);
        }
        if (situacaoInscricao != null) {
            builder.appendln("  and this.status = :situacaoInscricao");
            params.put("situacaoInscricao", situacaoInscricao);
        }
        if (dataRecebimento != null) {
            builder.appendln("  and this.dataRecebimento = :dataRecebimento");
            params.put("dataRecebimento", dataRecebimento);
        }
        if (dataPagamento != null) {
            builder.appendln("  and pag.dataPagamento = :dataPagamento");
            params.put("dataPagamento", dataPagamento);
        }
        if (order != null) {
            builder.appendln("order by ", order.toString());
        }
        Query q = createQuery(builder);
        for (Map.Entry<String, Object> tmp : params.entrySet()) {
            q.setParameter(tmp.getKey(), tmp.getValue());
        }
        // setar params
        return q.setFirstResult(firstResult).setMaxResults(maxResults).list();
    }
    
    @Override
    public Collection<Inscricao> findByEdicao(final Long idEdicao) {
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista.pessoa", "pessoa")
                .add(Restrictions.eq("edicaoEvento.id", idEdicao))
                .addOrder(Order.asc("pessoa.nome"));
        return findByCriteria(criteria);
    }
    
    @Override
    public Collection<Inscricao> findByEdicaoDeferidas(final Long idEdicao) {
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista.pessoa", "pessoa")
                .createAlias("pessoa.endereco", "endereco")
                .createAlias("endereco.cidade", "cidade")
                .createAlias("cidade.estado", "estado")
                .add(Restrictions.eq("edicaoEvento.id", idEdicao))
                .add(Restrictions.eq("status", Inscricao.Status.EFETIVADA))
                //                .add(Restrictions.or(
                //                                Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                //                                Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                //                        ))
                .addOrder(Order.asc("pessoa.nome"));
        return findByCriteria(criteria);
    }
    
    @Override
    public Collection<Inscricao> findByEdicaoCidadeEstado(final Edicao edicao) {
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista.pessoa", "pessoa")
                .createAlias("pessoa.endereco", "endereco")
                .createAlias("endereco.cidade", "cidade")
                .createAlias("cidade.estado", "estado")
                .add(Restrictions.eq("edicaoEvento", edicao))
                .add(Restrictions.or(
                                Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                                Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                        ))
                .addOrder(Order.asc("estado.nome"))
                .addOrder(Order.asc("cidade.nome"))
                .addOrder(Order.asc("pessoa.nome"));
        return findByCriteria(criteria);
    }
    
    @Override
    public Collection<Inscricao> findByEdicaoTipo(final Edicao edicao) {
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista", "confraternista")
                .createAlias("confraternista.pessoa", "pessoa")
                .add(Restrictions.eq("edicaoEvento", edicao))
                .add(Restrictions.or(
                                Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                                Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                        ))
                .addOrder(Order.asc("confraternista.tipo"))
                .addOrder(Order.asc("pessoa.nome"));
        return findByCriteria(criteria);
    }
    
    @Override
    public Collection<Inscricao> findByEdicaoDormitorio(final Edicao edicao) {
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista", "confraternista")
                .createAlias("confraternista.pessoa", "pessoa")
                .createAlias("confraternista.dormitorio", "dormitorio")
                .add(Restrictions.eq("edicaoEvento", edicao))
                .add(Restrictions.or(
                                Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                                Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                        ))
                .addOrder(Order.asc("dormitorio.sexo"))
                .addOrder(Order.asc("dormitorio.nome"))
                .addOrder(Order.asc("pessoa.nome"));
        return findByCriteria(criteria);
    }
    
    @Override
    public Collection<Inscricao> findByIdGrupoIdade(Long idGrupoIdade) {
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista", "confraternista")
                .createAlias("confraternista.pessoa", "pessoa")
                .createAlias("confraternista.grupoIdade", "grupoIdade")
                .add(Restrictions.eq("grupoIdade.id", idGrupoIdade))
                .add(Restrictions.or(
                                Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                                Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                        ))
                .add(Restrictions.ne("confraternista.tipo", Confraternista.Tipo.FACILITADOR))
                .addOrder(Order.asc("pessoa.nome"));
        return findByCriteria(criteria);
    }
    
    @Override
    public Collection<Inscricao> findSemDormitorioBySexo(final Sexo sexo, final Long idEdicao) {
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista", "confraternista")
                .createAlias("confraternista.pessoa", "pessoa")
                .add(Restrictions.eq("edicaoEvento.id", idEdicao))
                .add(Restrictions.isNull("confraternista.dormitorio.id"))
                .add(Restrictions.or(
                                Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                                Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                        ))
                .add(Restrictions.eq("pessoa.sexo", sexo))
                .addOrder(Order.asc("pessoa.nome"));
        return findByCriteria(criteria);
    }
    
    @Override
    public Collection<Inscricao> findByEdicaoSexo(final Edicao edicao) {
        final Criteria criteria = createCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("confraternista", "confraternista")
                .createAlias("confraternista.pessoa", "pessoa")
                .add(Restrictions.eq("edicaoEvento", edicao))
                .add(Restrictions.or(
                                Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                                Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                        ))
                .addOrder(Order.asc("pessoa.sexo"))
                .addOrder(Order.asc("pessoa.nome"));
        return findByCriteria(criteria);
    }
    
    @Override
    public Collection<Inscricao> findByEdicaoCamiseta(final Edicao edicao) {
        
        final StringBuilder builder = new StringBuilder(400);
        builder
                .append("select i ")
                .append("from Inscricao i ")
                .append("join fetch i.confraternista confraternista ")
                .append("join fetch confraternista.pessoa pessoa ")
                .append("join fetch confraternista.camisetas camisetas ")
                .append("where i.edicaoEvento = :edicao ")
                .append("and i.status in (:status) ")
                .append("order by pessoa.nome ");
        
        return getCurrentSession().createQuery(builder.toString())
                .setEntity("edicao", edicao)
                .setParameterList("status", new Inscricao.Status[]{Inscricao.Status.AGUARDANDO_PAGAMENTO, Inscricao.Status.EFETIVADA})
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();

//        final Criteria criteria = createCriteria()
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
//                .createAlias("confraternista", "confraternista")
//                .createAlias("confraternista.pessoa", "pessoa")
//                .add(Restrictions.eq("edicaoEvento", edicao))
//                .add(Restrictions.ne("status", Inscricao.Status.INDEFERIDA));
//        return findByCriteria(criteria
//                , Order.asc("pessoa.nome"));
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Collection<Inscricao> findByEdicaoOficina(final Edicao edicao) {
        final StringBuilder builder = new StringBuilder(400);
        builder
                .append("select i ")
                .append("from Inscricao i ")
                .append("join fetch i.confraternista confraternista ")
                .append("join fetch confraternista.pessoa pessoa ")
                .append("join fetch confraternista.oficina oficina ")
                .append("left join fetch oficina.oficineiros oficineiro ")
                .append("where i.edicaoEvento = :edicao ")
                .append("and confraternista.tipo in (:tipo) ")
                .append("and i.status in (:status) ")
                .append("order by oficina.nome, pessoa.nome ");
        
        return getCurrentSession().createQuery(builder.toString())
                .setEntity("edicao", edicao)
                .setParameterList("tipo", new Confraternista.Tipo[]{Confraternista.Tipo.CONFRATERNISTA, Confraternista.Tipo.COORDENADOR})
                .setParameterList("status", new Inscricao.Status[]{Inscricao.Status.AGUARDANDO_PAGAMENTO, Inscricao.Status.EFETIVADA})
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Collection<Inscricao> findByEdicaoGrupoIdade(final Edicao edicao) {
        final StringBuilder builder = new StringBuilder(400);
        builder
                .append("select i ")
                .append("from Inscricao i ")
                .append("join fetch i.confraternista confraternista ")
                .append("join fetch confraternista.pessoa pessoa ")
                .append("join fetch confraternista.grupoIdade grupoIdade ")
                .append("left join fetch grupoIdade.facilitadores facilitador ")
                .append("where i.edicaoEvento = :edicao ")
                .append("and confraternista.tipo in (:tipo) ")
                .append("and i.status in (:status) ")
                .append("order by grupoIdade.nome, pessoa.nome ");
        
        return getCurrentSession().createQuery(builder.toString())
                .setEntity("edicao", edicao)
                .setParameterList("tipo", new Confraternista.Tipo[]{Confraternista.Tipo.CONFRATERNISTA, Confraternista.Tipo.COORDENADOR, Confraternista.Tipo.EVANGELIZADOR})
                .setParameterList("status", new Inscricao.Status[]{Inscricao.Status.AGUARDANDO_PAGAMENTO, Inscricao.Status.EFETIVADA})
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
    
    @Override
    public Inscricao findByEdicaoDocumentos(final Long idEdicao, Documento documento) {
        final Criteria criteria = createCriteria().
                createAlias("confraternista.pessoa", "pessoa").
                createAlias("pessoa.documentos", "documentos").
                add(Restrictions.eq("edicaoEvento.id", idEdicao)).
                add(Restrictions.or(
                                Restrictions.eq("documentos.cpf", documento.getCpf()),
                                Restrictions.eq("documentos.rg", documento.getRg())
                        ));
        
        return DataAccessUtils.uniqueResult(findByCriteria(criteria));
    }
    
    @Override
    public Collection<Inscricao> findByNomeEdicao(String nome, Sexo genero, Long idEdicao) {
        final Criteria criteria = createCriteria().
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
                createAlias("confraternista", "confraternista")
                .createAlias("confraternista.pessoa", "pessoa")
                .add(Restrictions.ilike("pessoa.nome", nome, MatchMode.ANYWHERE))
                .add(Restrictions.eq("pessoa.sexo", genero))
                .add(Restrictions.eq("edicaoEvento.id", idEdicao))
                .add(Restrictions.or(
                                Restrictions.eq("status", Inscricao.Status.AGUARDANDO_PAGAMENTO),
                                Restrictions.eq("status", Inscricao.Status.EFETIVADA)
                        ));
        return findByCriteria(criteria);
    }
    
    @Override
    public Collection<Inscricao> findByUsuario(Usuario usuario) {
        final Criteria criteria = createCriteria().
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
                createCriteria("confraternista").
                add(Restrictions.eq("pessoa", usuario.getPessoa()));
        return findByCriteria(criteria);
    }
    
    @Override
    @Transactional(readOnly = false)
    public Long gravaInscricao(final InscricaoCommand inscricaoCmd) {
        if (inscricaoCmd.getInscricao().getId() == null) {
            return gravaNovaInscricao(inscricaoCmd);
        } else {
            return atualizaInscricao(inscricaoCmd);
        }
    }
    
    @Transactional(readOnly = false)
    protected Long gravaNovaInscricao(final InscricaoCommand inscricaoCmd) {
        atualizaStatus(inscricaoCmd.getInscricao());
        calculaValorCamisetas(inscricaoCmd.getInscricao());
        atualizaDocumentos(inscricaoCmd.getInscricao());
        atualizaInfoSaude(inscricaoCmd.getInscricao());
        saveOrUpdate(inscricaoCmd.getInscricao());
        ocupaVaga(inscricaoCmd.getInscricao());
        criaUsuario(inscricaoCmd);
        return inscricaoCmd.getInscricao().getId();
    }
    
    @Transactional(readOnly = false)
    protected Long atualizaInscricao(final InscricaoCommand inscricaoCmd) {
        if (inscricaoCmd.getInscricao().isPendente()) {
            atualizaStatus(inscricaoCmd.getInscricao());
        }
        calculaValorCamisetas(inscricaoCmd.getInscricao());
        atualizaDocumentos(inscricaoCmd.getInscricao());
        atualizaInfoSaude(inscricaoCmd.getInscricao());
        final Inscricao inscricaoAtual = findById(inscricaoCmd.getInscricao().getId());
        if (inscricaoCmd.getInscricao().getEdicaoEvento().getTipo().equals(Edicao.Tipo.OFICINA)) {
            atualizaVagaOficina(inscricaoCmd.getInscricao(), inscricaoAtual);
        } else if (inscricaoCmd.getInscricao().getEdicaoEvento().getTipo().equals(Edicao.Tipo.FAIXA_ETARIA)) {
            atualizaGrupoIdade(inscricaoCmd.getInscricao(), inscricaoAtual);
        }
        atualizaUsuario(inscricaoCmd);
        flushAndClear();
        saveOrUpdate(inscricaoCmd.getInscricao());
        
        return inscricaoCmd.getInscricao().getId();
    }
    
    protected void calculaValorCamisetas(final Inscricao inscricao) {
        final Edicao edicao = inscricao.getEdicaoEvento();
        BigDecimal valorInscricao = edicao.getValorInscricao();
        if (inscricao.isIsento()) {
            valorInscricao = new BigDecimal(0);
        }
        for (CamisetaConfraternista camiseta : inscricao.getConfraternista().getCamisetas()) {
            valorInscricao = valorInscricao.add(edicao.getValorCamiseta().multiply(new BigDecimal(camiseta.getQuantidadeCamiseta())));
        }
        inscricao.setValor(valorInscricao);
    }
    
    protected void atualizaStatus(final Inscricao inscricao) {
        inscricao.setDataRecebimento(CalendarUtils.now());
        inscricao.setStatus(Inscricao.Status.AGUARDANDO_AVALIACAO);
    }
    
    protected void atualizaDocumentos(final Inscricao inscricao) {
        final Documento documentos = inscricao.getConfraternista().getPessoa().getDocumentos();
        if (CharSequenceUtils.isBlank(documentos.getCpf())) {
            documentos.setCpf(null);
        }
        if (CharSequenceUtils.isBlank(documentos.getRg())) {
            documentos.setRg(null);
        }
    }
    
    protected void atualizaInfoSaude(final Inscricao inscricao) {
        // seta nulo para nao salvar lixo
        if (!inscricao.getConfraternista().getPessoa().getInformacoesSaude().temInformacao()) {
            inscricao.getConfraternista().getPessoa().setInformacoesSaude(null);
        }
    }
    
    protected void ocupaVaga(final Inscricao inscricao) {
        final Edicao edicao = edicaoDao.findById(inscricao.getEdicaoEvento().getId());
        final Edicao.Tipo tipoEdicao = edicao.getTipo();
        final Confraternista confraternista = inscricao.getConfraternista();
        if (inscricao.isOcupaVagaGrupoOficina() && !tipoEdicao.equals(Edicao.Tipo.CONGRESSO)) {
            if (tipoEdicao.equals(Edicao.Tipo.OFICINA)) {
                Oficina oficina = confraternista.getOficina();
                if (oficina != null) {
                    oficina = oficinaDao.findById(oficina.getId());
                    oficina.ocupaVaga();                    
                    oficinaDao.saveOrUpdate(oficina);
                }
            } else if (tipoEdicao.equals(Edicao.Tipo.FAIXA_ETARIA)) {
                insereGrupoIdade(inscricao, false);
            }
        }        
        if (inscricao.isOcupaVagaEvento()) {
            edicao.ocupaVaga();
            edicaoDao.saveOrUpdate(edicao);
        }        
    }
    
    protected void criaUsuario(final InscricaoCommand inscricaoCmd) {
        final Pessoa pessoa = inscricaoCmd.getInscricao().getConfraternista().getPessoa();
        final Usuario usuario = new Usuario();
        usuario.setPessoa(pessoa);
        usuario.setRole(Usuario.Role.ROLE_USER);
        usuario.setUsername(inscricaoCmd.getUsuario().getUsername());
        usuario.setPassword(DigestUtils.sha256Hex(CalendarUtils.format(pessoa.getDataNascimento(), "ddMMyyyy")));
        usuario.setEnabled(true);
        usuarioDao.save(usuario);
    }
    
    protected void atualizaVagaOficina(final Inscricao inscricao, final Inscricao inscricaoAtual) {
        final Confraternista confraternista = inscricao.getConfraternista();
        final Confraternista confraternistaAtual = inscricaoAtual.getConfraternista();
        final Oficina oficina = confraternista.getOficina();
        final Oficina oficinaAtual = confraternistaAtual.getOficina();
        if (oficina != null && (!oficina.getId().equals(oficinaAtual.getId()) || !confraternista.equals(confraternistaAtual))) {
            //trocou de oficina
            if(inscricao.isOcupaVagaGrupoOficina()){
                oficina.ocupaVaga();
                flushAndClear();
                oficinaDao.saveOrUpdate(oficina);
            }
            if(inscricaoAtual.isOcupaVagaGrupoOficina()){
                oficinaAtual.desocupaVaga();
                oficinaDao.saveOrUpdate(oficinaAtual);
            }
        }
    }
    
    protected void atualizaGrupoIdade(Inscricao inscricao, Inscricao inscricaoAtual) {
        final Calendar dataNascimento = inscricao.getConfraternista().getPessoa().getDataNascimento();
        final Calendar dataNascimentoAtual = inscricaoAtual.getConfraternista().getPessoa().getDataNascimento();
        final Confraternista confraternista = inscricao.getConfraternista();
        final Confraternista confraternistaAtual = inscricaoAtual.getConfraternista();
        final GrupoIdade grupoIdadeAtual = inscricaoAtual.getConfraternista().getGrupoIdade();
        if (grupoIdadeAtual != null && inscricaoAtual.isOcupaVagaGrupoOficina()) {
            if (!CalendarUtils.truncatedEquals(dataNascimento, dataNascimentoAtual, Calendar.DAY_OF_MONTH)
                    || !confraternista.getTipo().equals(confraternistaAtual.getTipo())
                    || !confraternista.getTipo().equals(grupoIdadeAtual.getTipo())) {
                grupoIdadeAtual.desocupaVaga();
                grupoIdadeDao.saveOrUpdate(grupoIdadeAtual);
            }
        }
        insereGrupoIdade(inscricao, true);
    }
    
    protected void atualizaUsuario(final InscricaoCommand inscricaoCmd) {
        final String username = inscricaoCmd.getUsuario().getUsername();
        Usuario usuarioAtual = usuarioDao.findByPessoaTipo(inscricaoCmd.getInscricao().getConfraternista().getPessoa(),
                Usuario.Role.ROLE_USER);
        if (!usuarioAtual.getUsername().equals(username)) {
            usuarioAtual.setUsername(username);
            usuarioDao.saveOrUpdate(usuarioAtual);
        }
    }
    
    protected void insereGrupoIdade(Inscricao inscricao, Boolean atualiza) {
        final Confraternista confraternista = inscricao.getConfraternista();
        Integer idadeConfraternista = diferencaDatas(inscricao.getEdicaoEvento().getPeriodoEdicao().getStart(), confraternista.getPessoa().getDataNascimento());
        Collection<GrupoIdade> gruposIdade = grupoIdadeDao.findByIdadeTipo(idadeConfraternista, confraternista.getTipo());
        if (gruposIdade != null && gruposIdade.size() > 0) {
            for (GrupoIdade grupoIdade : gruposIdade) {
                if (grupoIdade.getSaldoVagas() == 0) {
                    continue;
                } else {                    
                    grupoIdade.ocupaVaga();
                    grupoIdadeDao.saveOrUpdate(grupoIdade);                    
                    confraternista.setGrupoIdade(grupoIdade);
                    break;
                }
            }
        } else {
            if (!confraternista.getTipo().equals(Confraternista.Tipo.FACILITADOR)) {
                confraternista.setGrupoIdade(null);
            }
        }
        if (!atualiza) {
            confraternistaDao.saveOrUpdate(confraternista);
        }
    }//Gerar exceção "Entrar em contato com a administração do evento, cadastro de grupos idade incompleto"

    private Integer diferencaDatas(Calendar dataMaior, Calendar dataMenor) {
        int anoMenor = dataMenor.get(Calendar.YEAR);
        int anoMaior = dataMaior.get(Calendar.YEAR);
        int mesMenor = dataMenor.get(Calendar.MONTH);
        int mesMaior = dataMaior.get(Calendar.MONTH);
        int diaMenor = dataMenor.get(Calendar.DAY_OF_MONTH);
        int diaMaior = dataMaior.get(Calendar.DAY_OF_MONTH);
        int idade = anoMaior - anoMenor;
        if ((mesMaior < mesMenor)
                || ((mesMaior == mesMenor) && (diaMaior < diaMenor))) {
            idade -= 1;
        }
        return idade;
    }
}
