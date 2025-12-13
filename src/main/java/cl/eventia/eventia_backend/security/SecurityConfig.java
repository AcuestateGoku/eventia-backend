package cl.eventia.eventia_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. PÚBLICO: Login, Registro y Swagger (Documentación)
                        .requestMatchers("/api/usuarios/login", "/api/usuarios/registro").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 2. SOLO ADMIN: Crear, Editar o Borrar Eventos
                        // Ojo: HttpMethod.POST, etc. importan org.springframework.http.HttpMethod
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/eventos/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/eventos/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/eventos/**").hasRole("ADMIN")

                        // 3. CUALQUIER USUARIO AUTENTICADO:
                        // Ver eventos (GET) y Comprar Tickets
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/eventos/**").permitAll() // O
                                                                                                                 // .authenticated()
                                                                                                                 // si
                                                                                                                 // quieres
                                                                                                                 // obligar
                                                                                                                 // login
                                                                                                                 // para
                                                                                                                 // ver
                        .requestMatchers("/api/tickets/**").authenticated()

                        // 4. EL RESTO: Bloqueado por defecto
                        .anyRequest().authenticated());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

}
