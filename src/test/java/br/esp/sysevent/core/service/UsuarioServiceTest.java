/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Documento;
import br.esp.sysevent.core.model.Endereco;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.core.model.Usuario.Role;
import br.msf.commons.util.CalendarUtils;
import java.util.logging.Level;
import org.apache.commons.codec.digest.DigestUtils;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * testa o metodo loadUserByUsername() do UsuarioService, que sera usado pelo
 * SpringSecurity na app web.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class UsuarioServiceTest extends AbstractServiceTest {

    private static Long PESSOA_ID;

    @BeforeClass
    public static void preparaDados() {
        fillEstados(); // popula a tabela de estados com dados de testes
        fillCidades(); // popula a tabela de cidades com dados de testes

        final CidadeService cidadeService = getService(CidadeService.class);
        final PessoaService pessoaService = getService(PessoaService.class);
        final Cidade c = cidadeService.findByProperty("nome", "Alegrete").iterator().next();

        Endereco end = new Endereco();
        end.setCidade(c);
        end.setLogradouro("Av. Presidente Vargas");
        end.setBairro("Centro");
        end.setNumero("69");
        end.setComplemento("ap 171");
        end.setEmail("teste@teste.com");
        end.setTelefone("666");
        end.setTelefoneEvento("666");
        end.setCep("97015-000");

        Documento d = new Documento();
        d.setRg("0000000000");
        d.setCpf("1111111111");

        Pessoa p = new Pessoa();
        p.setNome("Teste da Silva");
        p.setEndereco(end);
        p.setDocumentos(d);
        p.setDataNascimento(CalendarUtils.today());
        p.setSexo(Sexo.MASCULINO);
        PESSOA_ID = pessoaService.saveOrUpdate(p);
        LOGGER.log(Level.INFO, "Pessoa salva com id {0}.", p.getId());
    }

    @Test
    public void testLoadUserByUsername() {
        final UsuarioService usuarioService = getService(UsuarioService.class);
        final PessoaService pessoaService = getService(PessoaService.class);

        try {
            usuarioService.loadUserByUsername("teste");
//            fail(); // o banco ainda está vazio. deveria ter dado exceção e mao chegado aqui!
        } catch (UsernameNotFoundException e) {
            // banco ainda nao tem nenhum usuario. logo deve levantar a exceçao.
            assertTrue(e.getMessage().startsWith("Usuario nao encontrado"));
        }
        Usuario usuario = new Usuario();
        usuario = (Usuario) usuarioService.loadUserByUsername("teste");
        if (usuario == null) {
            usuario.setUsername("teste");
            usuario.setPassword(DigestUtils.sha256Hex("teste01"));
            usuario.setRole(Role.ROLE_ADMIN);
            usuario.setPessoa(pessoaService.findById(PESSOA_ID));
            Long id = usuarioService.save(usuario);
            assertNotNull(id); // salvou com sucesso        
        }
        try {
            usuario = (Usuario) usuarioService.loadUserByUsername("teste");
            assertTrue(usuario != null && "teste".equals(usuario.getUsername()));
        } catch (UsernameNotFoundException e) {
            fail(); // desta vez, deveria encontrar o user salvo anteriormente e nao levantar exceçao!
        }

    }
}
