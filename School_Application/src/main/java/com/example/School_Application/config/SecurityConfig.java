package com.example.School_Application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS (uses WebConfig mappings)
                .cors(Customizer.withDefaults())

                // FIX: Disable CSRF — safe for stateless REST APIs using JWT
                .csrf(AbstractHttpConfigurer::disable)

                // FIX: Stateless session — no server-side session needed with JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // All endpoints are public for now (auth is handled by JWT in service layer)
                // TODO: When you add a JWT filter, lock down protected routes like:
                // .requestMatchers("/api/application/**").authenticated()
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())

                // FIX: Removed httpBasic — not needed for a JWT-based REST API
                // FIX: Disabled form login — this is a REST API, not a web app
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}