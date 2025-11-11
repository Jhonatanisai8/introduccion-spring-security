package com.microservice.estudiantes.demospringsecurityexample01.config;

import com.microservice.estudiantes.demospringsecurityexample01.controllers.CustomerController;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = Logger.getLogger(SecurityConfig.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    /*
    CONFIGURACION N°1
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
     */
/*
successHandler => nos rediregir cuando nos logueamos correctamente
ALWAYS = crea una sesion siempre y cuando no exista ninguna.
IF_REQUIRED = crea una sesion si necesario
NEVER = nunca crea una sesion, pero si existe una la utiliza.
STATELESS = nunca crea una sesion y si existe una la ignora.
 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers("customers/api/v1/welcome-not-secured").permitAll()
                            .anyRequest().authenticated();
                }).formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer.successHandler(successHandler())
                            .permitAll();
                })
                .sessionManagement(sessionManagementConfigurer -> {
                    sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                            .invalidSessionUrl("/login")
                            .maximumSessions(1)
                            .expiredUrl("/login")
                            .sessionRegistry(sessionManagementConfigurer());
                }).sessionManagement(sessionManagementConfigurer -> {
                    sessionManagementConfigurer.
                            sessionFixation().migrateSession();
                })
                .build();
    }

    /*
    hace un seguimiento de los datos que del usuario autenticado en la sesion
     */
    public SessionRegistry sessionManagementConfigurer() {
        return new SessionRegistryImpl();
    }

    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            log.warn("Redirigiendo al inicio...");
            response.sendRedirect("/customers/api/v1/inicio");
        };
    }
}
