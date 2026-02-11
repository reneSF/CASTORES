package com.castores.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth

            // Login y recursos públicos
            .requestMatchers("/auth/**", "/css/**", "/js/**").permitAll()

            // Ver inventario (ambos)
            .requestMatchers("/inventario").hasAnyRole("ADMIN", "ALMACENISTA")

            // Acciones SOLO ADMIN
            .requestMatchers(
                "/inventario/nuevo",
                "/inventario/entrada",
                "/inventario/baja/**",
                "/inventario/reactivar/**"
            ).hasRole("ADMIN")

            // Salidas SOLO ALMACENISTA
            .requestMatchers("/salidas/**").hasRole("ALMACENISTA")

            // Histórico SOLO ADMIN
            .requestMatchers("/historico/**").hasRole("ADMIN")

            .anyRequest().authenticated()
        );

        return http.build();
    }
}