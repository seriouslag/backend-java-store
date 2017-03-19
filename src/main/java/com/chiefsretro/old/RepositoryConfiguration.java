package com.chiefsretro.old;

/**
 * Created by Landon on 3/18/2017.
 */
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableAutoConfiguration
@EntityScan("com.chiefsretro")
@EnableJpaRepositories("com.chiefsretro")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class RepositoryConfiguration {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public SessionFactory getSessionFactory() {
        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

}