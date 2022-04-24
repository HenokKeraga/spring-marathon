package main;

import config.AppConfig;
import controller.LoginController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
//        IoC -
        var context= new AnnotationConfigApplicationContext(AppConfig.class);
        // make logincontroller instance part of the context  so you can use them other place

        LoginController loginController=context.getBean("loginController",LoginController.class);
        var lc=context.getBean(LoginController.class);

        System.out.println(loginController);
    }
}
