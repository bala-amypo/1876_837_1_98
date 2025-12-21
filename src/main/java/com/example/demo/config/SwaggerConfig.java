package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {

    // ✅ Renamed bean from "customOpenAPI" to "swaggerOpenAPI"
    @Bean(name = "swaggerOpenAPI")
    public OpenAPI swaggerOpenAPI() {
        final String securitySchemeName = "JWT";

        return new OpenAPI()
                .info(new Info()
                        .title("Micro-Learning Content Recommendation API (Swagger UI)")
                        .version("1.0.0")
                        .description("JWT-secured REST API documentation for micro-learning portal"))
                .addServersItem(new Server().url("http://localhost:9001"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
mvn