package com.blog.config;

// Importing necessary Swagger and Spring classes
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// This annotation tells Spring that this class is a configuration class, 
// indicating it's a source of bean definitions for the Spring application context.
@Configuration
public class SwaggerConfig {
    
    // Constant for the authorization header name
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // This method creates and configures the OpenAPI object for Swagger documentation
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            // Set basic information about the API
            .info(new Info()
                .title("Blog API")
                .version("1.0")
                .description("API documentation for Blog application"))
            // Add security requirement for Bearer Authentication
            .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
            // Add security scheme components
            .components(new Components()
                .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    // This method creates a grouped API for public endpoints
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("blogapp-public")
            .pathsToMatch("/api/v1/**")  // Match all paths starting with /api/v1/
            .build();
    }

    // This method creates a security scheme for JWT authentication
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer");
    }
}