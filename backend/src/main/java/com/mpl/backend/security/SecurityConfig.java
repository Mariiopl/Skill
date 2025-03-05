package com.mpl.backend.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                               PasswordEncoder passwordEncoder,
                                               UserDetailsService userDetailsService) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();

    }

    @SuppressWarnings({ "deprecation", "removal" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Habilitamos CORS utilizando el bean definido
        http.cors();

        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests(requests -> requests
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/participantes/**").permitAll()
                .requestMatchers("/especialidad/**").permitAll()
                .requestMatchers("/pruebas/**").permitAll()
                .requestMatchers("/items/**").permitAll()
                .requestMatchers("/evaluaciones/**").permitAll()
                .requestMatchers("/evaluacion-items/**").permitAll()
                .requestMatchers("/users/**").permitAll()
                // Permitir acceso a Swagger y OpenAPI
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .anyRequest().authenticated());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    // Define la configuración CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite solicitudes desde http://localhost:4200
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        // Permite los métodos que necesites
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permite todos los encabezados
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Permite exponer el encabezado Authorization, por ejemplo
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        // Permite credenciales (cookies, etc.) si es necesario
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica la configuración a todas las rutas
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
