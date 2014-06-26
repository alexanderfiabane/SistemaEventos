/*
 * Sistema de Eventos - Core - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.InscricaoDao;
import br.esp.sysevent.core.model.CamisetaConfraternista;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Documento;
import br.esp.sysevent.core.model.Edicao;
import br.esp.sysevent.core.model.GrupoIdade;
import br.esp.sysevent.core.model.Inscricao;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Usuario;
import br.ojimarcius.commons.persistence.service.AbstractEntityServiceBean;
import br.ojimarcius.commons.util.CalendarUtils;
import br.ojimarcius.commons.util.CharSequenceUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alexander Fiabane do Rego (alexanderfiabane@yahoo.com.br)
 */
@Service
public class InscricaoServiceBean extends AbstractEntityServiceBean<Long, Inscricao> implements InscricaoService {

    @Autowired
    private OficinaService oficinaService;
    @Autowired
    private GrupoIdadeService grupoIdadeService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EdicaoService edicaoService;
    @Autowired
    private ConfraternistaService confraternistaService;

    @Override
    protected InscricaoDao getDao() {
        return super.getDao();
    }

    @Required
    @Autowired
    public void setDao(final InscricaoDao dao) {
        super.setDao(dao);
    }

    @Override
    public Inscricao findById(final Long id, final String[] initProps) {
        return getDao().findById(id, initProps);
    }

    @Override
    public Collection<Inscricao> findByUsuario(Usuario usuario) {
        return getDao().findByUsuario(usuario);
    }

    @Override
    @Transactional(readOnly = false)
    public Long gravaInscricao(final Inscricao inscricao) {
        if (inscricao.getId() == null) {
            return gravaNovaInscricao(inscricao);
        } else {
            return atualizaInscricao(inscricao);
        }
    }

    @Transactional(readOnly = false)
    protected Long gravaNovaInscricao(final Inscricao inscricao) {
        atualizaStatus(inscricao);
        calculaValorCamisetas(inscricao);
        atualizaDocumentos(inscricao);
        atualizaInfoSaude(inscricao);
        saveOrUpdate(inscricao);
        ocupaVaga(inscricao);
        criaUsuario(inscricao);
        return inscricao.getId();
    }

    @Transactional(readOnly = false)
    protected Long atualizaInscricao(final Inscricao inscricao) {
        if (inscricao.isPendente()) {
            atualizaStatus(inscricao);
        }
        calculaValorCamisetas(inscricao);
        atualizaDocumentos(inscricao);
        atualizaInfoSaude(inscricao);
        final Inscricao inscricaoAtual = findById(inscricao.getId());
        atualizaVagaOficina(inscricao, inscricaoAtual);
        atualizaGrupoIdade(inscricao, inscricaoAtual);
        atualizaUsuario(inscricao, inscricaoAtual);
        saveOrUpdate(inscricao);

        return inscricao.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Inscricao> findByEdicao(final Long idEdicao) {
        return getDao().findByEdicao(idEdicao);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Inscricao> findByEdicaoDeferidas(final Long idEdicao) {
        return getDao().findByEdicaoDeferidas(idEdicao);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Inscricao> findByEdicaoCidadeEstado(Edicao edicao) {
        return getDao().findByEdicaoCidadeEstado(edicao);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Inscricao> findByEdicaoTipo(Edicao edicao) {
        return getDao().findByEdicaoTipo(edicao);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Inscricao> findByEdicaoSexo(Edicao edicao) {
        return getDao().findByEdicaoSexo(edicao);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Inscricao> findByEdicaoOficina(Edicao edicao) {
        return getDao().findByEdicaoOficina(edicao);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Inscricao> findByEdicaoDormitorio(Edicao edicao) {
        return getDao().findByEdicaoDormitorio(edicao);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Inscricao> findByEdicaoCamiseta(Edicao edicao) {
        return getDao().findByEdicaoCamiseta(edicao);
    }

    @Override
    @Transactional(readOnly = true)
    public Inscricao findByEdicaoDocumentos(final Long idEdicao, Documento documento) {
        return getDao().findByEdicaoDocumentos(idEdicao, documento);
    }

    protected void calculaValorCamisetas(final Inscricao inscricao) {
        final Edicao edicao = inscricao.getEdicaoEvento();
        BigDecimal valorInscricao = edicao.getValorInscricao();
        for (CamisetaConfraternista camiseta : inscricao.getConfraternista().getCamisetas()) {
            valorInscricao = valorInscricao.add(camiseta.getTipoCamiseta().getValorCamiseta().multiply(new BigDecimal(camiseta.getQuantidadeCamiseta())));
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
        final Edicao edicao = edicaoService.findById(inscricao.getEdicaoEvento().getId());
        final Edicao.Tipo tipoEdicao = edicao.getTipo();
        final Confraternista confraternista = inscricao.getConfraternista();
        if (confraternista.isOcupaVaga()) {
            if (tipoEdicao.equals(Edicao.Tipo.OFICINA)) {
                final Oficina oficina = confraternista.getOficina();
                if (oficina != null) {
                    oficina.ocupaVaga();
                    oficinaService.saveOrUpdate(oficina);
                }
            } else if (tipoEdicao.equals(Edicao.Tipo.FAIXA_ETARIA)) {
                insereGrupoIdade(inscricao);
            } 
            edicao.ocupaVaga();
            edicaoService.saveOrUpdate(edicao);
        }
    }

    protected void criaUsuario(final Inscricao inscricao) {
        final Pessoa pessoa = inscricao.getConfraternista().getPessoa();
        final Usuario usuario = new Usuario();
        usuario.setPessoa(pessoa);
        usuario.setRole(Usuario.Role.ROLE_USER);
        usuario.setUsername(pessoa.getEndereco().getEmail());
        usuario.setPassword(DigestUtils.sha256Hex(CalendarUtils.format(pessoa.getDataNascimento(), "ddMMyyyy")));
        usuario.setEnabled(true);
        usuarioService.save(usuario);
    }

    protected void atualizaVagaOficina(final Inscricao inscricao, final Inscricao inscricaoAtual) {
        final Oficina oficina = inscricao.getConfraternista().getOficina();
        final Oficina oficinaAtual = inscricaoAtual.getConfraternista().getOficina();
        if (oficina != null && !oficina.getId().equals(oficinaAtual.getId())) {
            //trocou de oficina
            oficina.ocupaVaga();
            oficinaAtual.desocupaVaga();
            oficinaService.saveOrUpdate(oficina);
            oficinaService.saveOrUpdate(oficinaAtual);
        }
    }

    protected void atualizaGrupoIdade(Inscricao inscricao, Inscricao inscricaoAtual) {
        final Calendar dataNascimento = inscricao.getConfraternista().getPessoa().getDataNascimento();
        final Calendar dataNascimentoAtual = inscricaoAtual.getConfraternista().getPessoa().getDataNascimento();
        final GrupoIdade grupoIdadeAtual = inscricaoAtual.getConfraternista().getGrupoIdade();
        final Edicao.Tipo tipoEdicao = inscricao.getEdicaoEvento().getTipo();
        if ((grupoIdadeAtual != null) && (tipoEdicao.equals(Edicao.Tipo.FAIXA_ETARIA))) {
            if (!CalendarUtils.truncatedEquals(dataNascimento, dataNascimentoAtual, Calendar.DAY_OF_MONTH)) {
                grupoIdadeAtual.desocupaVaga();
                grupoIdadeService.saveOrUpdate(grupoIdadeAtual);
                insereGrupoIdade(inscricao);
            }
        }
    }

    protected void atualizaUsuario(final Inscricao inscricao, final Inscricao inscricaoAtual) {
        final String email = inscricao.getConfraternista().getPessoa().getEndereco().getEmail();
        final String emailAtual = inscricaoAtual.getConfraternista().getPessoa().getEndereco().getEmail();
        final Calendar dataNascimento = inscricao.getConfraternista().getPessoa().getDataNascimento();
        final Calendar dataNascimentoAtual = inscricaoAtual.getConfraternista().getPessoa().getDataNascimento();
        if (!email.equals(emailAtual)) {
            removeUsuario(emailAtual);
            flush();
            criaUsuario(inscricao);
        } else if (!CalendarUtils.truncatedEquals(dataNascimento, dataNascimentoAtual, Calendar.DAY_OF_MONTH)) {
            //alterou dt nascimento
            atualizaSenha(inscricao);
        }
    }

    protected void atualizaSenha(Inscricao inscricao) {
        final Pessoa pessoa = inscricao.getConfraternista().getPessoa();
        final Usuario usuario = usuarioService.findByLogin(pessoa.getEndereco().getEmail());
        usuario.setPassword(DigestUtils.sha256Hex(CalendarUtils.format(pessoa.getDataNascimento(), "ddMMyyyy")));
        usuarioService.saveOrUpdate(usuario);
    }

    protected void removeUsuario(String emailAtual) {
        final Usuario usuario = usuarioService.findByLogin(emailAtual);
        usuarioService.delete(usuario);
    }
    
    protected void insereGrupoIdade(Inscricao inscricao) {
        final Confraternista confraternista = inscricao.getConfraternista();
        Integer idadeConfraternista = diferencaDatas(inscricao.getEdicaoEvento().getData(), confraternista.getPessoa().getDataNascimento());
        Collection<GrupoIdade> gruposIdade = grupoIdadeService.findByIdadeTipo(idadeConfraternista, confraternista.getTipo());
        if (gruposIdade != null) {
            for (GrupoIdade grupoIdade : gruposIdade) {
                if (confraternista.getGrupoIdade() != null){
                    break;
                } else if (grupoIdade.getSaldoVagas() == 0) {
                    break;
                }else if (grupoIdade.getTipo().equals(confraternista.getTipo())){
                    grupoIdade.ocupaVaga();
                    grupoIdadeService.saveOrUpdate(grupoIdade);
                    confraternista.setGrupoIdade(grupoIdade);
                    confraternistaService.saveOrUpdate(confraternista);
                }                
            }
        }//Gerar exceção "Entrar em contato com a administração do evento, cadastro de grupos idade incompleto"
    }

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