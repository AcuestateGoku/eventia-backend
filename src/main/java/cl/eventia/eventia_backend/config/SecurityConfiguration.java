package cl.eventia.eventia_backend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    // ... otros Beans y métodos ...

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 1. Permite el acceso desde tu Frontend (Local y de Producción)
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "https://eventia-backend-9khs.onrender.com" // <--- CAMBIA ESTO POR TU URL REAL DE FRONTEND EN RENDER
        ));
        
        // 2. Permite todos los métodos HTTP que React usa (GET, POST, PUT, DELETE)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // 3. Permite la cabecera 'Authorization' (donde va el JWT)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        
        // 4. Importante para credenciales y tokens
        configuration.setAllowCredentials(true); 
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica esta configuración a todas las rutas (/**)
        source.registerCorsConfiguration("/**", configuration); 
        
        return source;
    }
    
    // Debes añadir .cors() en el método configure(HttpSecurity http)
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            // AGREGAR ESTA LÍNEA PARA HABILITAR CORS
            .cors(Customizer.withDefaults()) 
            // ... resto de tu configuración
            .build();
    }
    
}