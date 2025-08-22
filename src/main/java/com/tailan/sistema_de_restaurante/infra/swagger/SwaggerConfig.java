package com.tailan.sistema_de_restaurante.infra.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth", // Nome do esquema de segurança (você usará isso em @SecurityRequirement)
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT", // Formato do token
        scheme = "bearer" // O esquema é 'bearer', como em "Bearer <token>"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Reserva de Restaurante")
                        .description("API de Reserva de Restaurante")
                        .version("1.0"));

    }
}
