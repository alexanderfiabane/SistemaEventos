package br.esp.sysevent.core.service;

import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Documento;
import br.esp.sysevent.core.model.Endereco;
import br.esp.sysevent.core.model.Pessoa;
import br.esp.sysevent.core.model.Sexo;
import br.ojimarcius.commons.util.CalendarUtils;
import java.util.Collection;
import java.util.logging.Level;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Classe que executa testes básicos do PessoaService, usando o banco Derby (JavaDB) em memória.
 *
 * @author Marcius da Silva da Fonseca
 */
public class PessoaServiceTest extends AbstractServiceTest {

    //@BeforeClass
    public static void preparaDados() {
        fillEstados(); // popula a tabela de estados com dados de testes
        fillCidades(); // popula a tabela de cidades com dados de testes
    }

    //@Test
    public void testSave() {
        final CidadeService cidadeService = getService(CidadeService.class);
        final PessoaService pessoaService = getService(PessoaService.class);
        LOGGER.log(Level.INFO, cidadeService.findAll().toString());
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
        pessoaService.save(p);
        LOGGER.log(Level.INFO, "Pessoa salva com id {0}.", p.getId());
    }

    //@Test
    public void testFindByNome() {
        final PessoaService pessoaService = getService(PessoaService.class);
        final Collection<Pessoa> pessoas = pessoaService.findAll();
        LOGGER.log(Level.INFO, "a pesquisa retornou {0} resultados.", pessoas.size());
    }
}
