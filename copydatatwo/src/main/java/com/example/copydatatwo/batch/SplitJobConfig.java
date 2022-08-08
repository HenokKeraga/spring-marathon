package com.example.copydatatwo.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class SplitJobConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("migrationStep")
    Step migrationStep;


    @Bean
    public Job parallelJob() {
        return jobBuilderFactory
                .get("parallelJob")
                .incrementer(new RunIdIncrementer())
                .start(splitFlow())
                .end()
                .build();
    }

    public TaskletStep tasklet1() {

        return stepBuilderFactory
                .get("tasklet1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(" tasklet 1");
                    return RepeatStatus.FINISHED;
                })
                .listener(new Tasklet1Listener())
                .build();
    }

    public TaskletStep tasklet2() {

        return stepBuilderFactory
                .get("tasklet1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(" tasklet 2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    public TaskletStep tasklet3() {

        return stepBuilderFactory
                .get("tasklet1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(" tasklet 3");
                    return RepeatStatus.FINISHED;
                })
                //.listener()
                .build();
    }

    @Bean
    public Flow splitFlow() {

        return new FlowBuilder<SimpleFlow>(" splitFlow")
                .split(new SimpleAsyncTaskExecutor())
                .add(flow1(), flow2(), flow3())
                .build();
    }

    @Bean
    public Flow flow1() {
        return new FlowBuilder<SimpleFlow>("flow 1")
                .start(tasklet1())
                .from(tasklet1())
                .on("*").end()
                .on(ExitStatus.FAILED.getExitCode()).to(onFailRun())
                .next(migrationStep)
                .build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<SimpleFlow>("flow2 ")
                .start(tasklet2())
                .build();
    }

    @Bean
    public Flow flow3() {
        return new FlowBuilder<SimpleFlow>("flow3")
                .start(tasklet3())
                .build();
    }

    @Bean
    public TaskletStep onFailRun() {
        return stepBuilderFactory.get("fail")
                .tasklet((contribution, chunkContext) -> {

                    System.out.println("OnFailedTasklet");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}

class Tasklet1Listener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("before step");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println(" after step");
        return ExitStatus.FAILED;
    }
}

