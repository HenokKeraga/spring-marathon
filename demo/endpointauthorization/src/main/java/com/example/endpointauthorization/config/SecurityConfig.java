package com.example.endpointauthorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                //.httpBasic(Customizer.withDefaults())
                .httpBasic()
                .and()
                //   .authorizeRequests().anyRequest().authenticated() // authenticated request -> authorize
//                .authorizeRequests(
//                        request -> request.anyRequest().authenticated()
//                )// authenticated request -> authorize
                // .authorizeRequests().anyRequest().permitAll() // all request -> authorize
                //  .authorizeRequests().anyRequest().denyAll() // all request -> authorize
                // .authorizeRequests().anyRequest().hasAuthority("read") // all request -> authorize
                //.authorizeRequests().mvcMatchers("/demo").hasAuthority("read") // all request -> authorize
//                .authorizeRequests(expressionInterceptUrlRegistry -> {
//                    expressionInterceptUrlRegistry.mvcMatchers("/demo").hasAuthority("read");
//                    expressionInterceptUrlRegistry.mvcMatchers("/demo1").hasAuthority("write");
//                })
                .authorizeRequests(new CustomExpressionInterceptUrlRegistry())
                .csrf(AbstractHttpConfigurer::disable)
                //  .and()
                .build();
    }

    class CustomExpressionInterceptUrlRegistry implements Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> {
        @Override
        public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry) {
            expressionInterceptUrlRegistry.mvcMatchers("/demo").hasAuthority("read");
            expressionInterceptUrlRegistry.mvcMatchers("/demo1").hasAuthority("write");
        }
    }

    @Bean
    public UserDetailsService userDetailsService() {

        var uds = new InMemoryUserDetailsManager();

        var ud1 = User
                .withUsername("henok")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                .build();


        uds.createUser(ud1);
        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
