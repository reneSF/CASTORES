package com.castores.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // para formularios simples

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ui/login", "/css/**", "/js/**").permitAll()

                .requestMatchers("/ui/inventario/**").hasAnyRole("ADMIN", "ALMACENISTA")
                .requestMatchers("/ui/salida/**").hasRole("ALMACENISTA")
                .requestMatchers("/ui/historico/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .formLogin(login -> login
                .loginPage("/ui/login")      // TU LOGIN REAL
                .loginProcessingUrl("/login") // endpoint interno de Spring
                .defaultSuccessUrl("/ui/inventario", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/ui/login?logout")
                .permitAll()
            );

        return http.build();
    }
}