package com.example.taskapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI taskApiDocs() {
        return new OpenAPI().info(new Info()
            .title("Task Management API")
            .description("A small task-management REST API built in the Spring Boot crash course")
            .version("1.0"));
    }
}
