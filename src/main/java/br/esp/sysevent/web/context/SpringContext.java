/*
 * Sistema de Eventos - Web - Copyright (c) 2013 TaperinhaSoft. All rights reserved.
 */
package br.esp.sysevent.web.context;

import br.ojimarcius.commons.util.LocaleUtils;
import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Configura o contexto do SpringFramework.
 * <p/>
 * Define alguns beans básicos e agrega os módulos de configuração da aplicação.
 * <p/>
 * @see SpringDataSource
 * @see SpringService
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
@Configuration
@EnableTransactionManagement
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

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public AnnotationMethodHandlerAdapter handlerAdapter() {
        final AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter = new AnnotationMethodHandlerAdapter();
        annotationMethodHandlerAdapter.setAlwaysUseFullPath(true);
        return annotationMethodHandlerAdapter;
    }

    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(
                "org.springframework.security.messages",
                "br.esp.sysevent.web.resources.Application",
                "br.esp.sysevent.web.resources.Configs",
                "br.esp.sysevent.web.resources.Labels",
                "br.esp.sysevent.web.resources.Messages",
                "br.esp.sysevent.web.resources.Errors");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(LocaleUtils.PT_BR_LOCALE);
        return localeResolver;
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
