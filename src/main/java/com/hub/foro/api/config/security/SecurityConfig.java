package com.hub.foro.api.config.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(permitAllEndpoints()).permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/public/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/public/**").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/public/**").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/public/**")
                                .hasAnyAuthority("USER", "ADMIN")

                                .requestMatchers("/api/v1/user/**", "/api/v1/topic/**", "/api/v1/response/**")
                                .hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before
                                                                                             // UsernamePasswordAuthenticationFilter
                .build();
    }

    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration configuration = new CorsConfiguration();
    //     configuration.addAllowedOriginPattern("http://localhost:8080");
    //     configuration.addAllowedOriginPattern("https://foro-hub.herokuapp.com");
    //     configuration.addAllowedMethod("*"); // Permite todos los métodos (GET, POST, PUT, DELETE)
    //     configuration.addAllowedHeader("*"); // Permite todas las cabeceras
    //     configuration.setExposedHeaders(List.of("Content-Disposition")); // Cabeceras expuestas
    //     configuration.setAllowCredentials(true); // Permite credenciales (cookies, auth)
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration);
    //     return source;
    // }

    private static String[] permitAllEndpoints() {
        return new String[] {
                "/api/v1/auth/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/"
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
                User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build());
    }
}
