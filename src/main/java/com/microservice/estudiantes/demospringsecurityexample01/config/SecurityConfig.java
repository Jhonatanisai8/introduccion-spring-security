package com.microservice.estudiantes.demospringsecurityexample01.config;

import com.microservice.estudiantes.demospringsecurityexample01.controllers.CustomerController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = Logger.getLogger(SecurityConfig.class.getName());
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.warning("Security Filter Chain....");
        return http
                //.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers("customers/api/v1/welcome-not-secured")
                            .permitAll()
                            .anyRequest().authenticated();
                }).formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }
}
