package com.example.sc1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class Sc1Application {

    public static void main(String[] args) {
        SpringApplication.run(Sc1Application.class, args);
    }

}

record Product(int id, String name) {
}

@RestController
@RequestMapping("/product")
class ProductController {

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(List.of(new Product(1, "Mange")));
    }
}

@Configuration
class AppConfig{
    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails userDetails= User
                .withUsername("henok")
                .password("12345")
                .authorities("read")
                .build();
        return  new InMemoryUserDetailsManager(List.of(userDetails));
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
