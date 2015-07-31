/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.controller;

import br.esp.sysevent.core.dao.CidadeDao;
import br.esp.sysevent.core.dao.ConfraternistaDao;
import br.esp.sysevent.core.dao.EstadoDao;
import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Confraternista;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.core.model.Oficina;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.model.Usuario;
import com.javaleks.commons.util.CalendarUtils;
import com.javaleks.commons.util.CollectionUtils;
import java.util.Collection;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    private UsuarioDao usuarioDao;    
    @Autowired
    private ConfraternistaDao confraternistaDao;
    @Autowired
    private EstadoDao estadoDao;
    @Autowired
    private CidadeDao cidadeDao;

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String onGet(final ModelMap model) {
        setupData();
        return "login";
    }

    /**
     * Insere o user admin com a senha padrão (adminadmin), caso nao exista nenhum user na tabela.
     */
    private void setupData() {
        Collection<Usuario> users = usuarioDao.findAll();
        if (CollectionUtils.isEmptyOrNull(users)) {

            // insere o usuario admin
            usuarioDao.insertDefaultData();

            // insere cidades e estados
            estadoDao.insertDefaultData();
            cidadeDao.insertDefaultData();
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
        usuarioDao.save(user);

        Confraternista confraternista = new Confraternista();
        confraternista.setNomeCracha(nome);
        confraternista.setPessoa(pessoa);
        confraternista.setTipo(Confraternista.Tipo.CONFRATERNISTA);
        confraternista.setOficina(oficina);
        confraternista.setAtividadeCasaEspirita("Teste");
        confraternistaDao.save(confraternista);
    }

    protected void addCidade(String nome, Estado estado) {
        Cidade cidade = new Cidade();
        cidade.setNome(nome);
        cidade.setEstado(estado);
        cidadeDao.save(cidade);
    }
}
