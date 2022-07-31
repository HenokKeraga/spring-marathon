package com.example.copytable.config;

import com.example.copytable.batch.DepartmentItemProcessor;
import com.example.copytable.model.postgre.Department;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
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


    @Bean
    public Job migrationJob() {
        return jobBuilderFactory.get("migration")
                .incrementer(new RunIdIncrementer())
                .start(migrationStep())
                .build();
    }

    @Bean
    public Step migrationStep() {

        return stepBuilderFactory.get("step")
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
