package com.example.scsbsw1.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {
     @Autowired
     JobBuilderFactory jobBuilderFactory;
     @Autowired
     StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job() {

        return jobBuilderFactory
                .get("job")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .tasklet(
                        (sc, cc) -> {
                            System.out.println("tasklet");
                            return RepeatStatus.FINISHED;
                        }
                )
                .build();
    }
}
