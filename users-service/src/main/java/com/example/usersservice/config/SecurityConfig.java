package com.example.usersservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // isključuje CSRF zaštitu (samo za razvoj)
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll() // sve endpointove dozvoli bez auth
                );
        return http.build();
    }
}
