package cl.eventia.eventia_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "Eventia API", version = "1.0", description = "Documentación oficial de Eventia"),
    security = @SecurityRequirement(name = "bearerAuth") // Aplica seguridad a toda la API
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class EventiaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventiaBackendApplication.class, args);
    }

}