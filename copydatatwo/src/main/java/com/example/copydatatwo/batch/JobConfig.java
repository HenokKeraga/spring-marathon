package com.example.copydatatwo.batch;

import com.example.copydatatwo.model.postgre.Department;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class JobConfig {
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("postgreDBDatasource")
    DataSource postgreDBDatasource;

    @Autowired
    @Qualifier("sqlDBDatasource")
    DataSource sqlDBDatasource;

    @Autowired
    @Qualifier("datasource")
    DataSource datasource;


    @Bean
    public Job migrationJob() {

        return jobBuilderFactory
                .get("migrationJob")
                .incrementer(new RunIdIncrementer())
                .start(migrationStep())
                .build();
    }

    @Bean
    public Step migrationStep() {
        return stepBuilderFactory
                .get("migrationStep")
                .<Department, com.example.copydatatwo.model.sql.Department>chunk(1)
                .reader(jdbcCursorItemReader())
                .writer(jdbcBatchItemWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Department> jdbcCursorItemReader() {
        var b = new BeanPropertyRowMapper<Department>();
        b.setMappedClass(Department.class);

        var d = new Department() {{
            setId(1L);
            setDeptName("test");
        }};


        return new JdbcCursorItemReaderBuilder<Department>()
                .dataSource(postgreDBDatasource)
                .saveState(false)
                .sql("Select id,dept_name as deptName from department")
//                .rowMapper(new BeanPropertyRowMapper<>() {
//                    {
//                        setMappedClass(Department.class);
//                    }
//                })
//                .rowMapper(b)
                .rowMapper((resultSet, i) -> new Department() {{
                            setId(resultSet.getLong("id"));
                            setDeptName(resultSet.getString("deptName"));
                        }}
                )
                .build();

    }

    @Bean
    public JdbcBatchItemWriter<com.example.copydatatwo.model.sql.Department> jdbcBatchItemWriter() {

        return new JdbcBatchItemWriterBuilder<com.example.copydatatwo.model.sql.Department>()
                .sql("insert into department(id,dept_name) values(:id,:deptName)")
                .dataSource(sqlDBDatasource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }


}
