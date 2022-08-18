package com.example.scsbsw1;

import lombok.Data;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;

@SpringBootApplication
@EnableBatchProcessing
public class ScSbSw1Application {

    public static void main(String[] args) {
        SpringApplication.run(ScSbSw1Application.class, args);
    }

}

@RestController
@RequestMapping("/product")
class ProductController {
    final JobLauncher jobLauncher;
    final Job job;

    ProductController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/start")
    public String startJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        var jp = new JobParametersBuilder().toJobParameters();
       var je= jobLauncher.run(job,jp);

       return je.getExitStatus().toString();
    }


}

