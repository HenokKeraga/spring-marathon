package com.example.copytable;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class CopytableApplication {

    public static void main(String[] args) {
        SpringApplication.run(CopytableApplication.class, args);
    }

}
