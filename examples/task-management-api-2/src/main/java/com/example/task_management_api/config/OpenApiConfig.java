package com.example.task_management_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

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
