package com.labtrack;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Rutas que TODOS pueden acceder sin login
    public static final String[] PUBLIC_URLS = {"/", "/login", "/acceso_denegado",
        "/fav/**", "/webjars/**", "/js/**", "/css/**"};

    // Rutas de solo consulta (listados), para cualquier usuario autenticado
    public static final String[] CONSULTA_URLS = {"/laboratorio/listado",
        "/equipo/listado", "/solicitud/listado", "/equipo/buscarPorLaboratorio",
        "/laboratorio/buscarPorEstado"};

    // Rutas de gestión de laboratorios y equipos: ENCARGADO o ADMIN
    public static final String[] ENCARGADO_URLS = {"/laboratorio/**", "/equipo/**"};

    // Rutas exclusivas de ADMIN (para futura gestión de usuarios)
    public static final String[] ADMIN_URLS = {"/usuario/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                .requestMatchers(PUBLIC_URLS).permitAll()
                .requestMatchers(CONSULTA_URLS).authenticated()
                .requestMatchers(ENCARGADO_URLS).hasAnyRole("ENCARGADO", "ADMIN")
                .requestMatchers(ADMIN_URLS).hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        http.formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/laboratorio/listado", true)
                .failureUrl("/login?error=true")
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        );

        http.exceptionHandling(ex -> ex.accessDeniedPage("/acceso_denegado"));

        http.sessionManagement(ses -> ses
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
}
