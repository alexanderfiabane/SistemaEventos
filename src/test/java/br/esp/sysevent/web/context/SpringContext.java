package br.esp.sysevent.web.context;

import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configura um contexto simplista do SpringFramework, usando o banco de dados Derby (JavaDB),
 * para efetuar testes de unidade.
 *
 * @author Marcius da Silva da Fonseca
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"br.esp.sysevent.core.model",
                "br.esp.sysevent.core.dao",
                "br.esp.sysevent.core.service",
                "br.esp.sysevent.web.controller"})
public class SpringContext {

    @Bean
    public DataSource dataSource() {
        try {
            final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            final Properties properties = hibernateConfigs();
            dataSource.setDriverClass((Class<Driver>) Class.forName(properties.getProperty("hibernate.connection.driver_class")));
            dataSource.setUrl(properties.getProperty("hibernate.connection.url"));
            dataSource.setUsername(properties.getProperty("hibernate.connection.username"));
            dataSource.setPassword(properties.getProperty("hibernate.connection.password"));
            return dataSource;
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Driver class not found.", ex);
        }
    }

    @Bean
    public SessionFactory sessionFactory() {
        final LocalSessionFactoryBuilder sfb = new LocalSessionFactoryBuilder(dataSource()).scanPackages("br.esp.sysevent.core.model");
        sfb.setProperties(hibernateConfigs());
        return sfb.buildSessionFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory());
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    public Properties hibernateConfigs() {
        try {
            ClassPathResource hibernate = new ClassPathResource("br/esp/sysevent/web/context/hibernate.properties");
            final Properties properties = new Properties();
            properties.load(hibernate.getInputStream());
            return properties;
        } catch (IOException ex) {
            throw new IllegalStateException("Could not read <hibernate.properties>.", ex);
        }
    }
}
