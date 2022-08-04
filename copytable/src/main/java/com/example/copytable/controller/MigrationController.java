package com.example.copytable.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/start")
public class MigrationController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job migrationJob;

    @PostMapping
    public ResponseEntity startMigration() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new  JobParametersBuilder()

                .toJobParameters();


        JobExecution jobExecution = jobLauncher.run(migrationJob, jobParameters);


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jobExecution.getJobId());
    }
}
