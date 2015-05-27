/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.CidadeDao;
import br.esp.sysevent.core.dao.PessoaDao;
import br.esp.sysevent.core.dao.UsuarioDao;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Documento;
import br.esp.sysevent.core.model.Endereco;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Sexo;
import br.esp.sysevent.core.model.Usuario;
import br.esp.sysevent.core.model.Usuario.Role;
import com.javaleks.commons.util.CalendarUtils;
import java.util.logging.Level;
import org.apache.commons.codec.digest.DigestUtils;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * testa o metodo loadUserByUsername() do UsuarioDao, que sera usado pelo
 * SpringSecurity na app web.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class UsuarioServiceTest extends AbstractServiceTest {

    private static Long PESSOA_ID;

    //@BeforeClass
    public static void preparaDados() {
        fillEstados(); // popula a tabela de estados com dados de testes
        fillCidades(); // popula a tabela de cidades com dados de testes

        final CidadeDao cidadeDao = getService(CidadeDao.class);
        final PessoaDao pessoaDao = getService(PessoaDao.class);
        final Cidade c = cidadeDao.findByProperty("nome", "Alegrete").iterator().next();

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
        pessoaDao.saveOrUpdate(p);
        PESSOA_ID = p.getId();
        LOGGER.log(Level.INFO, "Pessoa salva com id {0}.", p.getId());
    }

    //@Test
    public void testLoadUserByUsername() {
        final UsuarioDao usuarioDao = getService(UsuarioDao.class);
        final PessoaDao pessoaDao = getService(PessoaDao.class);

        try {
            usuarioDao.loadUserByUsername("teste");
            //fail(); // o banco ainda está vazio. deveria ter dado exceção e não chegado aqui!
        } catch (UsernameNotFoundException e) {
            // banco ainda nao tem nenhum usuario. logo deve levantar a exceçao.
            assertTrue(e.getMessage().startsWith("Usuario nao encontrado"));
        }
        Usuario usuario = new Usuario();
        usuario.setUsername("teste");
        usuario.setPassword(DigestUtils.sha256Hex("teste01"));
        usuario.setRole(Role.ROLE_ADMIN);
        usuario.setPessoa(pessoaDao.findById(PESSOA_ID));
        Long id = usuarioDao.save(usuario);
        assertNotNull(id); // salvou com sucesso
        try {
            usuario = (Usuario) usuarioDao.loadUserByUsername("teste");
            assertTrue(usuario != null && "teste".equals(usuario.getUsername()));
        } catch (UsernameNotFoundException e) {
            fail(); // desta vez, deveria encontrar o user salvo anteriormente e nao levantar exceçao!
        }

    }
}
