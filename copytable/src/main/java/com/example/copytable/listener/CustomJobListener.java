package com.example.copytable.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.stereotype.Component;

@Component
public class CustomJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().put("key","value");
        //jobExecution.getJobParameters().getParameters().put("A",new JobParameter("B"));
        System.out.println(jobExecution.getJobId());
        System.out.println(jobExecution.getExecutionContext());
        System.out.println(jobExecution.getId());
        System.out.println(jobExecution.getJobParameters());
        System.out.println("before job");



    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        System.out.println("After job");

    }
}
