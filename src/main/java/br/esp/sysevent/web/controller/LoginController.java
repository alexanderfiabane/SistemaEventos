/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.controller;

import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.core.model.Noticia;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.core.service.CidadeService;
import br.esp.sysevent.core.service.ConfraternistaService;
import br.esp.sysevent.core.service.CorCamisetaService;
import br.esp.sysevent.core.service.EdicaoService;
import br.esp.sysevent.core.service.EstadoService;
import br.esp.sysevent.core.service.EventoService;
import br.esp.sysevent.core.service.NoticiaService;
import br.esp.sysevent.core.service.OficinaService;
import br.esp.sysevent.core.service.TamanhoCamisetaService;
import br.esp.sysevent.core.service.TipoCamisetaService;
import br.esp.sysevent.core.service.UsuarioService;
import br.msf.commons.util.CalendarUtils;
import br.msf.commons.util.CollectionUtils;
import java.util.Collection;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Mostra o form personalisado de onGet.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private NoticiaService noticiaService;
    @Autowired
    private TipoCamisetaService tipoCamisetaService;
    @Autowired
    private CorCamisetaService corCamisetaService;
    @Autowired
    private TamanhoCamisetaService tamanhoCamisetaService;
    @Autowired
    private EventoService eventoService;
    @Autowired
    private EdicaoService edicaoService;
    @Autowired
    private OficinaService oficinaService;
    @Autowired
    private ConfraternistaService confraternistaService;
    @Autowired
    private EstadoService estadoService;
    @Autowired
    private CidadeService cidadeService;


    /**
     * Adiciona a lista de Estados ao referenceData, para popular a tabela.
     */
    @ModelAttribute("noticias")
    public Collection<Noticia> getNoticias() {
        return noticiaService.findAll();
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String onGet(final ModelMap model) {
        setupData();
        return "login";
    }

    /**
     * Insere o user admin com a senha padrão (adminadmin), caso nao exista nenhum user na tabela.
     */
    private void setupData() {
        Collection<Usuario> users = usuarioService.findAll();
        if (CollectionUtils.isEmptyOrNull(users)) {

            // insere o usuario admin
            usuarioService.insertDefaultData();

            // insere cidades e estados
            estadoService.insertDefaultData();
            cidadeService.insertDefaultData();

            // insere dados das camisetas
//            tipoCamisetaService.insertDefaultData();
//            corCamisetaService.insertDefaultData();
//            tamanhoCamisetaService.insertDefaultData();
//
//            // Dados para testes
//            Evento evento = new Evento();
//            evento.setNome("Encontro de Música Espírita");
//            evento.setSigla("EMUSE");
//            eventoService.save(evento);
//
//            Edicao edicao = new Edicao();
//            edicao.setEvento(evento);
//            edicao.setNumero(1);
//            edicao.setTema("Vivenciando na Arte a Plenitude do Amor");
//            edicao.setVagas(20);
//            edicao.setVagasOcupadas(0);
//            edicao.setValorInscricao(BigDecimal.TEN);
//            edicao.setValorCamiseta(BigDecimal.ONE);
//            edicao.setPeriodoInscricao(new Period(CalendarUtils.today(), CalendarUtils.tomorrow()));
//            edicao.setTiposCamiseta(tipoCamisetaService.findAll());
//            edicao.setCoresCamiseta(corCamisetaService.findAll());
//            edicao.setTamanhosCamiseta(tamanhoCamisetaService.findAll());
//            edicaoService.save(edicao);
//
//            Oficina oficina = new Oficina();
//            oficina.setEdicaoEvento(edicao);
//            oficina.setNome("Canto Coral");
//            oficina.setVagas(20);
//            oficina.setVagasOcupadas(0);
//            oficinaService.save(oficina);
//
//            oficina = new Oficina();
//            oficina.setEdicaoEvento(edicao);
//            oficina.setNome("Bolemuse");
//            oficina.setVagas(30);
//            oficina.setVagasOcupadas(25);
//            oficinaService.save(oficina);
//
//            oficina = new Oficina();
//            oficina.setEdicaoEvento(edicao);
//            oficina.setNome("Dramatização");
//            oficina.setVagas(20);
//            oficina.setVagasOcupadas(10);
//            oficinaService.save(oficina);
//
//            addConfraternista("Giuliano Ferreira", oficina);
//
//            addConfraternista("Giuliano Pereira", oficina);
        }
    }

    protected void addConfraternista(String nome, Oficina oficina) {
        Pessoa pessoa;
        pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setDataNascimento(CalendarUtils.today());
        pessoa.setSexo(Sexo.MASCULINO);

        Usuario user = new Usuario();
        user.setUsername(nome);
        user.setPassword(DigestUtils.sha256Hex("teste001"));
        user.setPessoa(pessoa);
        user.setRole(Usuario.Role.ROLE_USER);
        usuarioService.save(user);

        Confraternista confraternista = new Confraternista();
        confraternista.setNomeCracha(nome);
        confraternista.setPessoa(pessoa);
        confraternista.setTipo(Confraternista.Tipo.CONFRATERNISTA);
        confraternista.setOficina(oficina);
        confraternista.setAtividadeCasaEspirita("Teste");
        confraternistaService.save(confraternista);
    }

    protected void addCidade(String nome, Estado estado) {
        Cidade cidade = new Cidade();
        cidade.setNome(nome);
        cidade.setEstado(estado);
        cidadeService.save(cidade);
    }
}
