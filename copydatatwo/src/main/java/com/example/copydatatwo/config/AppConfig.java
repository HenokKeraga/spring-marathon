package com.example.copydatatwo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource datasource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    @Bean
    @ConfigurationProperties("spring.mysqldbdatasource.hikari")
    public DataSource sqlDBDatasource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();

    }
    @Bean
    @Primary
    @ConfigurationProperties("spring.postgredbdatasource.hikari")
    public DataSource postgreDBDatasource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();

    }
    @Bean
    public BatchConfigurer configurer(){
        return new DefaultBatchConfigurer(datasource());
    }
    @Bean
    public BatchDataSourceScriptDatabaseInitializer batchDataSourceInitializer(BatchProperties properties){
        return new BatchDataSourceScriptDatabaseInitializer(datasource(),properties.getJdbc());
    }

}
