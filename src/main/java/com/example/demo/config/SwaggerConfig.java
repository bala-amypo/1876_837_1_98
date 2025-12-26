// package com.example.demo.config;

// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.servers.Server;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import java.util.List;

// @Configuration
// public class SwaggerConfig {

//     @Bean
//     public OpenAPI customOpenAPI() {
//         return new OpenAPI()
        
//                 .servers(List.of(
//                         new Server().url("https://9114.pro604cr.amypo.ai/")
//                 ));
//         }
// }
// package com.example.demo.config;

// import io.swagger.v3.oas.models.Components;
// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.security.SecurityRequirement;
// import io.swagger.v3.oas.models.security.SecurityScheme;
// import io.swagger.v3.oas.models.servers.Server;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import java.util.List;

// @Configuration
// public class SwaggerConfig {

//     @Bean
//     public OpenAPI customOpenAPI() {
//         return new OpenAPI()
//                 .info(new Info()
//                         .title("Micro-Learning Content Recommendation API")
//                         .description("REST API for micro-learning content and personalized recommendations")
//                         .version("1.0.0"))
//                 .servers(List.of(
//                         new Server().url("https://9203.408procr.amypo.ai/")
//                 ))
//                 .addSecurityItem(new SecurityRequirement().addList("JWT"))
//                 .components(new Components()
//                         .addSecuritySchemes("JWT", new SecurityScheme()
//                                 .name("JWT")
//                                 .type(SecurityScheme.Type.HTTP)
//                                 .scheme("bearer")
//                                 .bearerFormat("JWT")));
//     }
// }

package com.example.demo.config;

import io.swagger.v3.oas.models.Components;
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

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // 1. Setup the info (Title, Description, Version)
                .info(new Info()
                        .title("Micro-Learning Content Recommendation API")
                        .version("1.0.0")
                        .description("REST API for micro-learning content and personalized recommendations"))
                
                // 2. Add the Servers (Deployment URL and Localhost)
                .servers(List.of(
                        new Server().url("https://9203.408procr.amypo.ai/").description("Remote Server"),
                        new Server().url("http://localhost:9001").description("Local Server")
                ))

                // 3. Add Security Requirement so all endpoints show the lock icon
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                
                // 4. Configure JWT Bearer Token Security Scheme
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}