package br.esp.sysevent.core.service;

import br.esp.sysevent.core.dao.CidadeDao;
import br.esp.sysevent.core.dao.EstadoDao;
import br.esp.sysevent.core.model.Cidade;
import br.esp.sysevent.core.model.Estado;
import br.esp.sysevent.web.context.SpringContext;
import com.javaleks.commons.core.dao.EntityDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AbstractServiceTest {

    protected static final Logger LOGGER = Logger.getLogger(AbstractServiceTest.class.getName());
    /**
     * @see SpringContext
     */
    private static final AnnotationConfigApplicationContext SPRING_CONTEXT = new AnnotationConfigApplicationContext(SpringContext.class);

    /**
     * Salva alguns estados para testes
     */
    protected static void fillEstados() {
        try {
            final EstadoDao estadoDao = getService(EstadoDao.class);
            final Collection<Estado> estados = new ArrayList<Estado>();
            estados.add(new Estado("Rio Grande do Sul", "RS"));
            estados.add(new Estado("Santa Catarina", "SC"));
            estados.add(new Estado("Paraná", "PR"));
            estados.add(new Estado("São Paulo", "SP"));
            estadoDao.saveOrUpdateAll(estados);
        } catch (Exception ex) {
            // se der org.hibernate.exception.ConstraintViolationException é pq outro teste ja havia executado a alimentacao das tabelas
            LOGGER.log(Level.WARNING, "{0} {1}", new Object[]{ex.getClass().getName(), ex.getMessage()});
        }
    }

    /**
     * Salva algumas cidades para testes.<p/>
     * Utiliza os estados salvos por {@link #fillEstados() fillEstados()}
     */
    protected static void fillCidades() {
        try {
            final EstadoDao estadoDao = getService(EstadoDao.class);
            final CidadeDao cidadeDao = getService(CidadeDao.class);
            final Collection<Cidade> cidades = new ArrayList<Cidade>();
            cidades.add(new Cidade("Alegrete", estadoDao.findBySigla("RS")));
            cidades.add(new Cidade("Porto Alegre", estadoDao.findBySigla("RS")));
            cidades.add(new Cidade("Santa Maria", estadoDao.findBySigla("RS")));
            cidades.add(new Cidade("Florianópolis", estadoDao.findBySigla("SC")));
            cidades.add(new Cidade("Curitiba", estadoDao.findBySigla("PR")));
            cidades.add(new Cidade("São Paulo", estadoDao.findBySigla("SP")));
            cidadeDao.saveOrUpdateAll(cidades);
        } catch (Exception ex) {
            // se der org.hibernate.exception.ConstraintViolationException é pq outro teste ja havia executado a alimentacao das tabelas
            LOGGER.log(Level.WARNING, "{0} {1}", new Object[]{ex.getClass().getName(), ex.getMessage()});
        }
    }

    /**
     * limpa a tabela de estados.<p/>
     */
    protected static void emptyEstados() {
        getService(EstadoDao.class).deleteAll();
    }

    /**
     * limpa a tabela de cidades.<p/>
     */
    protected static void emptyCidades() {
        getService(CidadeDao.class).deleteAll();
    }

    /**
     * Retorna um service do contexto.<p/>
     * Basta informar a interface do service desejado.
     * @param <T>
     * @param svcClass
     * @return
     */
    protected static <T extends EntityDao> T getService(final Class<T> svcClass) {
        return SPRING_CONTEXT.getBean(svcClass);
    }
}
