package com.example.copytable.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "postgreDataSource")
    @ConfigurationProperties(prefix = "spring.postgre.datasource")
    public DataSource postgreDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "spring.mysql.datasource")
    public DataSource mysqlDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public EntityManagerFactory postgreEntityManagerFactory(){

        var entityManger = new LocalContainerEntityManagerFactoryBean();
        entityManger.setDataSource(postgreDataSource());
        entityManger.setPackagesToScan("com.example.copytable.model.postgre");
        entityManger.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManger.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManger.afterPropertiesSet();


        return entityManger.getObject();
    }

    @Bean
    public EntityManagerFactory sqlEntityManagerFactory(){

        var entityManger = new LocalContainerEntityManagerFactoryBean();
        entityManger.setDataSource(mysqlDataSource());
        entityManger.setPackagesToScan("com.example.copytable.model.sql");
        entityManger.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManger.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManger.afterPropertiesSet();


        return entityManger.getObject();
    }

    @Bean
    @Primary
    public JpaTransactionManager jpaTransactionManager(){
        var transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(mysqlDataSource());
        transactionManager.setEntityManagerFactory(sqlEntityManagerFactory());

        return transactionManager;
    }

}
