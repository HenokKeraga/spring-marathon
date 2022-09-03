package com.example.endpointauthorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class EndpointauthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EndpointauthorizationApplication.class, args);
    }

}


@RestController
class DemoController {

    @GetMapping("/demo")
    public String demo(){
        return "demo!";
    }
}
