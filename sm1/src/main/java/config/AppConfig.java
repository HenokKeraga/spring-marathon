package config;

import controller.LoginController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration //  in spring boot it is mandatory
@ComponentScan(basePackages = "controller")
public class AppConfig {
//    @Bean// will be added to the spring context and the bean identifier is the name of the method
//    LoginController loginController(){
//        return new LoginController();
//    }
//
//    @Bean// will be added to the spring context and the bean identifier is the name of the method
//    LoginController loginController2(){
//        return new LoginController();
//    }
}
