package com.example.copytable.config;

import com.example.copytable.listener.CustomJobListener;
import com.example.copytable.processor.DepartmentItemProcessor;
import com.example.copytable.listener.CustomItemReaderListener;
import com.example.copytable.listener.CustomStepExcutionListener;
import com.example.copytable.model.postgre.Department;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;

@Configuration
public class JobConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("postgreEntityManagerFactory")
    private EntityManagerFactory postgreEntityManagerFactory;

    @Autowired
    @Qualifier("sqlEntityManagerFactory")
    private EntityManagerFactory sqlEntityManagerFactory;

    @Autowired
    @Qualifier("jpaTransactionManager")
    private JpaTransactionManager jpaTransactionManager;

    @Autowired
    private DepartmentItemProcessor departmentItemProcessor;

    @Autowired
    private CustomJobListener customJobListener;


    @Bean(name = "migrationJob")
    public Job migrationJob() {
        return jobBuilderFactory.get("migration")
                .incrementer(new RunIdIncrementer())
                .listener(customJobListener)
                .start(migrationStep())
                .build();
    }

    @Bean
    public Step migrationStep() {

        return stepBuilderFactory.get("step")
                .listener(new CustomStepExcutionListener())
                .listener(new CustomItemReaderListener())
                .<Department, com.example.copytable.model.sql.Department>chunk(1)
                .reader(jpaCursorItemReader())
                .processor(departmentItemProcessor)
                .writer(jpaItemWriter())
                .faultTolerant()
                .skip(Throwable.class)
                .skipLimit(0)
                .retryLimit(3)
                .retry(Throwable.class)
                .transactionManager(jpaTransactionManager)
                .build();
    }




    @Bean
    public JpaCursorItemReader<Department> jpaCursorItemReader() {
        System.out.println("reader");
        JpaCursorItemReader<Department> jpaCursorItemReader = new JpaCursorItemReader<>();

        jpaCursorItemReader.setEntityManagerFactory(postgreEntityManagerFactory);
        jpaCursorItemReader.setQueryString("FROM Department");


        return jpaCursorItemReader;
    }

    @Bean
    public JpaItemWriter<com.example.copytable.model.sql.Department> jpaItemWriter() {
        System.out.println("writer");
        JpaItemWriter<com.example.copytable.model.sql.Department> jpaItemWriter = new JpaItemWriter<>();

        jpaItemWriter.setEntityManagerFactory(sqlEntityManagerFactory);
        return jpaItemWriter;
    }


}
